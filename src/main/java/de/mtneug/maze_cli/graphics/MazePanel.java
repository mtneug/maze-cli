/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.graphics;

import de.mtneug.maze_cli.model.Cell;
import de.mtneug.maze_cli.model.Direction;
import de.mtneug.maze_cli.model.Maze;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Panel, which draws the given maze onto it's surface.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class MazePanel extends JPanel {
  /**
   * The maze to draw.
   */
  private final Maze maze;

  /**
   * TODO: change with maze solution object
   * List of cells, which should be marked
   */
  private final java.util.List<Cell> additionallyMarkedCells;

  /**
   * The preferred length of the walls. The actual length depends on the actual size of the component.
   */
  private float preferredWallLength = 15.0f;

  /**
   * The percent of the wall length, which determines the thickness of a drawn wall.
   */
  private float preferredWallThicknessFactor = .2f;

  /**
   * The line color of the wall.
   */
  private Color wallColor = new Color(69, 53, 39);

  /**
   * The background color of the start cell.
   */
  private Color startCellSurfaceColor = new Color(248, 203, 51);

  /**
   * The background color of the end cell.
   */
  private Color endCellSurfaceColor = new Color(169, 40, 57);

  /**
   * TODO: change with maze solution object.
   */
  private Color additionallyMarkedCellsColor = new Color(217, 200, 176);

  /**
   * Whether to mark the start cell with the start cell background color.
   */
  private boolean markingStartCell = true;

  /**
   * Whether to mark the end cell with the end cell background color.
   */
  private boolean markingEndCell = true;

  /**
   * The constructor.
   *
   * @param maze The maze to draw.
   */
  public MazePanel(Maze maze) {
    this(maze, new ArrayList<Cell>());
  }

  /**
   * The constructor.
   *
   * @param maze The maze to draw.
   */
  public MazePanel(Maze maze, java.util.List<Cell> additionallyMarkedCells) {
    if (maze == null || additionallyMarkedCells == null)
      throw new IllegalArgumentException();

    this.maze = maze;
    this.additionallyMarkedCells = additionallyMarkedCells;
  }

  /**
   * TODO: change with maze solution object.
   */
  public boolean addAdditionallyMarkedCells(Collection<? extends Cell> c) {
    return additionallyMarkedCells.addAll(c);
  }

  /**
   * The preferred size depends on the {@link #preferredWallLength} and the size of the maze.
   *
   * @return The preferred size of this component.
   * @see #setPreferredWallLength(float)
   */
  public Dimension getPreferredSize() {
    return new Dimension(
        (int) (maze.getWidth() * preferredWallLength + getXWallThickness()),
        (int) (maze.getHeight() * preferredWallLength + getYWallThickness())
    );
  }

  /**
   * Code to paint the maze.
   *
   * @param g The graphic object. It is assumed to be a subclass of {@link Graphics2D}.
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g;

    // TODO: change with maze solution object
    // draw additionally marked cells background
    if (!additionallyMarkedCells.isEmpty())
      for (Cell cell : additionallyMarkedCells) {
        g2.setColor(additionallyMarkedCellsColor);
        drawCellSurface(g2, cell);
      }

    // draw start cell background
    if (markingStartCell && maze.hasStartCell()) {
      g2.setColor(startCellSurfaceColor);
      drawCellSurface(g2, maze.getStartCell());
    }

    // draw end cell background
    if (markingEndCell && maze.hasEndCell()) {
      g2.setColor(endCellSurfaceColor);
      drawCellSurface(g2, maze.getEndCell());
    }

    // draw cell walls
    g2.setColor(wallColor);
    for (Cell cell : maze)
      drawCellWalls(g2, cell);
  }

  /**
   * Draws a background for the given {@code cell}.
   *
   * @param g2   The graphic object.
   * @param cell The cell to draw the background for.
   */
  private void drawCellSurface(Graphics2D g2, Cell cell) {
    g2.fill(new Rectangle2D.Float(
        getXWallThickness() / 2 + cell.getPosition().x * getXWallLength(),
        getYWallThickness() / 2 + cell.getPosition().y * getYWallLength(),
        getXWallLength(),
        getYWallLength()
    ));
  }

  /**
   * Draws the walls for the given {@code cell}.
   *
   * @param g2   The graphic object.
   * @param cell The cell to draw the walls for.
   */
  private void drawCellWalls(Graphics2D g2, Cell cell) {
    g2.setStroke(new BasicStroke(getYWallThickness()));

    if (!cell.canGoTo(Direction.TOP))
      g2.draw(new Line2D.Float(
          getXWallThickness() / 2 + cell.getPosition().x * getXWallLength(),
          getYWallThickness() / 2 + cell.getPosition().y * getYWallLength(),
          getXWallThickness() / 2 + cell.getPosition().x * getXWallLength() + getXWallLength(),
          getYWallThickness() / 2 + cell.getPosition().y * getYWallLength()
      ));

    if (!cell.canGoTo(Direction.BOTTOM))
      g2.draw(new Line2D.Float(
          getXWallThickness() / 2 + cell.getPosition().x * getXWallLength(),
          getYWallThickness() / 2 + cell.getPosition().y * getYWallLength() + getYWallLength(),
          getXWallThickness() / 2 + cell.getPosition().x * getXWallLength() + getXWallLength(),
          getYWallThickness() / 2 + cell.getPosition().y * getYWallLength() + getYWallLength()
      ));

    g2.setStroke(new BasicStroke(getXWallThickness()));

    if (!cell.canGoTo(Direction.RIGHT)) {
      g2.draw(new Line2D.Float(
          getXWallThickness() / 2 + cell.getPosition().x * getXWallLength() + getXWallLength(),
          getYWallThickness() / 2 + cell.getPosition().y * getYWallLength(),
          getXWallThickness() / 2 + cell.getPosition().x * getXWallLength() + getXWallLength(),
          getYWallThickness() / 2 + cell.getPosition().y * getYWallLength() + getYWallLength()
      ));
    }

    if (!cell.canGoTo(Direction.LEFT))
      g2.draw(new Line2D.Float(
          getXWallThickness() / 2 + cell.getPosition().x * getXWallLength(),
          getYWallThickness() / 2 + cell.getPosition().y * getYWallLength(),
          getXWallThickness() / 2 + cell.getPosition().x * getXWallLength(),
          getYWallThickness() / 2 + cell.getPosition().y * getYWallLength() + getYWallLength()
      ));
  }

  /**
   * The actual length of a wall in x direction.
   *
   * @return The length of a wall in x direction.
   */
  public float getXWallLength() {
    return getWidth() / (maze.getWidth() + preferredWallThicknessFactor);
  }

  /**
   * The actual length of a wall in y direction.
   *
   * @return The length of a wall in y direction.
   */
  public float getYWallLength() {
    return getHeight() / (maze.getHeight() + preferredWallThicknessFactor);
  }

  /**
   * The actual thickness of a wall in x direction.
   *
   * @return The thickness of a wall in x direction.
   */
  public float getXWallThickness() {
    return getXWallLength() * preferredWallThicknessFactor;
  }

  /**
   * The actual thickness of a wall in y direction.
   *
   * @return The thickness of a wall in y direction.
   */
  public float getYWallThickness() {
    return getYWallLength() * preferredWallThicknessFactor;
  }

  /**
   * Returns the preferred length of a wall.
   *
   * @return The preferred length of a wall.
   */
  public float getPreferredWallLength() {
    return preferredWallLength;
  }

  /**
   * Sets the preferred length of a wall.
   *
   * @param preferredWallLength The preferred length of a wall.
   */
  public void setPreferredWallLength(float preferredWallLength) {
    this.preferredWallLength = preferredWallLength;
    repaint();
  }

  /**
   * Returns the preferred thickness factor of a wall.
   *
   * @return The preferred thickness factor of a wall.
   */
  public float getPreferredWallThicknessFactor() {
    return preferredWallThicknessFactor;
  }

  /**
   * Sets the preferred thickness factor of a wall.
   *
   * @param preferredWallThicknessFactor The preferred thickness factor of a wall.
   */
  public void setPreferredWallThicknessFactor(float preferredWallThicknessFactor) {
    this.preferredWallThicknessFactor = preferredWallThicknessFactor;
    repaint();
  }

  /**
   * Returns the color of the walls.
   *
   * @return The color of the walls.
   */
  public Color getWallColor() {
    return wallColor;
  }

  /**
   * Sets the color of the walls.
   *
   * @param wallColor The new color.
   */
  public void setWallColor(Color wallColor) {
    this.wallColor = wallColor;
    repaint();
  }

  /**
   * Returns the background color of the start cell.
   *
   * @return The background color of the start cell.
   */
  public Color getStartCellSurfaceColor() {
    return startCellSurfaceColor;
  }

  /**
   * Returns the background color of the start cell.
   *
   * @param startCellSurfaceColor The new background color of the start cell.
   */
  public void setStartCellSurfaceColor(Color startCellSurfaceColor) {
    this.startCellSurfaceColor = startCellSurfaceColor;
    repaint();
  }

  /**
   * Returns the background color of the end cell.
   *
   * @return The background color of the end cell.
   */
  public Color getEndCellSurfaceColor() {
    return endCellSurfaceColor;
  }

  /**
   * Returns the background color of the end cell.
   *
   * @param endCellSurfaceColor The new background color of the end cell.
   */
  public void setEndCellSurfaceColor(Color endCellSurfaceColor) {
    this.endCellSurfaceColor = endCellSurfaceColor;
    repaint();
  }

  /**
   * Returns whether the start cell is marked.
   *
   * @return {@code true} if the start cell is marked, {@code false} otherwise.
   */
  public boolean isMarkingStartCell() {
    return markingStartCell;
  }

  /**
   * Sets whether the start cell is marked.
   *
   * @param markingStartCell Whether to mark the start cell.
   */
  public void setMarkingStartCell(boolean markingStartCell) {
    this.markingStartCell = markingStartCell;
    repaint();
  }

  /**
   * Returns whether the end cell is marked.
   *
   * @return {@code true} if the end cell is marked, {@code false} otherwise.
   */
  public boolean isMarkingEndCell() {
    return markingEndCell;
  }

  /**
   * Sets whether the end cell is marked.
   *
   * @param markingEndCell Whether to mark the end cell.
   */
  public void setMarkingEndCell(boolean markingEndCell) {
    this.markingEndCell = markingEndCell;
    repaint();
  }
}