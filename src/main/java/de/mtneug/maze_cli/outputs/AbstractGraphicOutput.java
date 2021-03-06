/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.outputs;

import de.mtneug.maze_cli.model.Cell;
import de.mtneug.maze_cli.model.Direction;
import de.mtneug.maze_cli.model.MazeSolutions;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 * Base class for all graphical maze outputs.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractGraphicOutput extends AbstractMazeOutput {
  /**
   * The actual width.
   */
  private final float width;

  /**
   * The actual height.
   */
  private final float height;

  /**
   * The preferred length of the walls. The actual length depends on the actual size of the component.
   */
  private float preferredWallLength = 50.0f;

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
   * The background color of the end cell.
   */
  private Color firstSolutionSurfaceColor = new Color(217, 200, 176);

  /**
   * Whether to mark the start cell with the start cell background color.
   */
  private boolean markingStartCell = true;

  /**
   * Whether to mark the end cell with the end cell background color.
   */
  private boolean markingEndCell = true;

  /**
   * Whether to mark the first solution.
   */
  private boolean markingFirstSolution = true;

  /**
   * The constructor.
   *
   * @param mazeSolutions The maze and solutions to output.
   */
  public AbstractGraphicOutput(MazeSolutions mazeSolutions) {
    super(mazeSolutions);

    this.width = mazeSolutions.getMaze().getWidth() * preferredWallLength + getXWallThickness();
    this.height = mazeSolutions.getMaze().getHeight() * preferredWallLength + getYWallThickness();
  }

  /**
   * Code to paint the maze.
   *
   * @param g2 The graphic object.
   */
  protected void paintComponent(Graphics2D g2) {
    // draw first solution
    if (mazeSolutions.hasSolution())
      for (Cell cell : mazeSolutions.getSolutions().get(0).getPathCells()) {
        g2.setColor(firstSolutionSurfaceColor);
        drawCellSurface(g2, cell);
      }

    // draw start cell background
    if (markingStartCell && mazeSolutions.getMaze().hasStartCell()) {
      g2.setColor(startCellSurfaceColor);
      drawCellSurface(g2, mazeSolutions.getMaze().getStartCell());
    }

    // draw end cell background
    if (markingEndCell && mazeSolutions.getMaze().hasEndCell()) {
      g2.setColor(endCellSurfaceColor);
      drawCellSurface(g2, mazeSolutions.getMaze().getEndCell());
    }

    // draw cell walls
    g2.setColor(wallColor);
    for (Cell cell : mazeSolutions.getMaze())
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
          cell.getPosition().x * getXWallLength(),
          getYWallThickness() / 2 + cell.getPosition().y * getYWallLength(),
          getXWallThickness() + cell.getPosition().x * getXWallLength() + getXWallLength(),
          getYWallThickness() / 2 + cell.getPosition().y * getYWallLength()
      ));

    if (!cell.canGoTo(Direction.BOTTOM))
      g2.draw(new Line2D.Float(
          cell.getPosition().x * getXWallLength(),
          getYWallThickness() / 2 + cell.getPosition().y * getYWallLength() + getYWallLength(),
          getXWallThickness() + cell.getPosition().x * getXWallLength() + getXWallLength(),
          getYWallThickness() / 2 + cell.getPosition().y * getYWallLength() + getYWallLength()
      ));

    g2.setStroke(new BasicStroke(getXWallThickness()));

    if (!cell.canGoTo(Direction.RIGHT)) {
      g2.draw(new Line2D.Float(
          getXWallThickness() / 2 + cell.getPosition().x * getXWallLength() + getXWallLength(),
          cell.getPosition().y * getYWallLength(),
          getXWallThickness() / 2 + cell.getPosition().x * getXWallLength() + getXWallLength(),
          getYWallThickness() + cell.getPosition().y * getYWallLength() + getYWallLength()
      ));
    }

    if (!cell.canGoTo(Direction.LEFT))
      g2.draw(new Line2D.Float(
          getXWallThickness() / 2 + cell.getPosition().x * getXWallLength(),
          cell.getPosition().y * getYWallLength(),
          getXWallThickness() / 2 + cell.getPosition().x * getXWallLength(),
          getYWallThickness() + cell.getPosition().y * getYWallLength() + getYWallLength()
      ));
  }

  /**
   * The actual length of a wall in x direction.
   *
   * @return The length of a wall in x direction.
   */
  public float getXWallLength() {
    return getWidth() / (mazeSolutions.getMaze().getWidth() + preferredWallThicknessFactor);
  }

  /**
   * The actual length of a wall in y direction.
   *
   * @return The length of a wall in y direction.
   */
  public float getYWallLength() {
    return getHeight() / (mazeSolutions.getMaze().getHeight() + preferredWallThicknessFactor);
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
   * Sets the background color of the start cell.
   *
   * @param startCellSurfaceColor The new background color of the start cell.
   */
  public void setStartCellSurfaceColor(Color startCellSurfaceColor) {
    this.startCellSurfaceColor = startCellSurfaceColor;
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
   * Sets the background color of the end cell.
   *
   * @param endCellSurfaceColor The new background color of the end cell.
   */
  public void setEndCellSurfaceColor(Color endCellSurfaceColor) {
    this.endCellSurfaceColor = endCellSurfaceColor;
  }

  /**
   * Returns the background color of the first solution.
   *
   * @return The background color of the first solution.
   */
  public Color getFirstSolutionSurfaceColor() {
    return firstSolutionSurfaceColor;
  }

  /**
   * Sets the background color of the first solution.
   *
   * @param firstSolutionSurfaceColor The new background color of the first solution.
   */
  public void setFirstSolutionSurfaceColor(Color firstSolutionSurfaceColor) {
    this.firstSolutionSurfaceColor = firstSolutionSurfaceColor;
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
  }

  /**
   * Returns whether the first solution is marked.
   *
   * @return {@code true} if the first solution is marked, {@code false} otherwise.
   */
  public boolean isMarkingFirstSolution() {
    return markingFirstSolution;
  }

  /**
   * Sets whether the first solution is marked.
   *
   * @param markingFirstSolution Whether to mark the first solution.
   */
  public void setMarkingFirstSolution(boolean markingFirstSolution) {
    this.markingFirstSolution = markingFirstSolution;
  }

  /**
   * Returns the width of the graphic.
   *
   * @return The width of the graphic.
   */
  public float getWidth() {
    return width;
  }

  /**
   * Returns the height of the graphic.
   *
   * @return The height of the graphic.
   */
  public float getHeight() {
    return height;
  }
}
