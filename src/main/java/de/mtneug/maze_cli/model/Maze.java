/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static de.mtneug.maze_cli.model.Direction.*;

/**
 * Model of a maze. This is essentially a linked graph of cells.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class Maze implements Iterable<Cell> {
  /**
   * The width of the maze.
   */
  public final int width;

  /**
   * The height of the maze.
   */
  public final int height;

  /**
   * The 2d grid storing the cells.
   */
  private final Cell[][] grid;

  /**
   * The start point of the maze.
   */
  private Point startPoint;

  /**
   * The end point of the maze. This position should be reached to solve the maze.
   */
  private Point endPoint;

  /**
   * The constructor.
   *
   * @param width  The width of the maze.
   * @param height The height of the maze.
   */
  public Maze(int width, int height) {
    if (width < 2 || height < 2)
      throw new IllegalArgumentException("Dimension must be at least 2x2");

    this.width = width;
    this.height = height;

    grid = new Cell[width][height];
    createCells();
    setCellRelations();
  }

  /**
   * Creates new cells for each grid point.
   */
  private void createCells() {
    for (int x = 0; x < width; x++)
      for (int y = 0; y < height; y++)
        grid[x][y] = new Cell(x, y);
  }

  /**
   * Links the new cells to each other.
   */
  private void setCellRelations() {
    for (int x = 0; x < width; x++)
      for (int y = 0; y < height; y++) {
        grid[x][y].setNeighborPositioned(TOP, getCell(x, y - 1));
        grid[x][y].setNeighborPositioned(BOTTOM, getCell(x, y + 1));
        grid[x][y].setNeighborPositioned(LEFT, getCell(x - 1, y));
        grid[x][y].setNeighborPositioned(RIGHT, getCell(x + 1, y));
      }
  }

  /**
   * The width of the maze.
   *
   * @return The width.
   */
  public int getWidth() {
    return width;
  }

  /**
   * The height of the maze.
   *
   * @return The height.
   */
  public int getHeight() {
    return height;
  }

  /**
   * Returns the start cell of the maze.
   *
   * @return The start cell.
   */
  public Cell getStartCell() {
    return getCell(startPoint);
  }

  /**
   * Sets the start cell of the maze.
   *
   * @param p The position of the new start cell.
   */
  public void setStartCell(Point p) {
    if (width <= p.x || p.x < 0 ||
        height <= p.y || p.y < 0)
      throw new IndexOutOfBoundsException();

    if (endPoint != null && endPoint.equals(p))
      throw new IllegalArgumentException("Start and Endpoint can't be the same");

    startPoint = p;
  }

  /**
   * Sets the start cell of the maze.
   *
   * @param x The x position of the new start cell.
   * @param y The y position of the new start cell.
   */
  public void setStartCell(int x, int y) {
    setStartCell(new Point(x, y));
  }

  /**
   * Checks whether this maze has a set start cell.
   *
   * @return {@code true} if the start cell is set, {@code false} otherwise.
   */
  public boolean hasStartCell() {
    return startPoint != null;
  }

  /**
   * Returns the end cell of the maze.
   *
   * @return The end cell.
   */
  public Cell getEndCell() {
    return getCell(endPoint);
  }

  /**
   * Sets the end cell of the maze.
   *
   * @param p The position of the new end cell.
   */
  public void setEndCell(Point p) {
    if (width <= p.x || p.x < 0 ||
        height <= p.y || p.y < 0)
      throw new IndexOutOfBoundsException();

    if (startPoint != null && startPoint.equals(p))
      throw new IllegalArgumentException("Start and Endpoint can't be the same");

    this.endPoint = p;
  }

  /**
   * Sets the end cell of the maze.
   *
   * @param x The x position of the new end cell.
   * @param y The y position of the new end cell.
   */
  public void setEndCell(int x, int y) {
    setEndCell(new Point(x, y));
  }

  /**
   * Checks whether this maze has a set end cell.
   *
   * @return {@code true} if the end cell is set, {@code false} otherwise.
   */
  public boolean hasEndCell() {
    return endPoint != null;
  }

  /**
   * Returns the cell at the given position.
   *
   * @param p The position of the cell.
   * @return The cell or {@code null} if the position is not valid.
   */
  public Cell getCell(Point p) {
    return getCell(p.x, p.y);
  }

  /**
   * Returns the cell at the given position.
   *
   * @param x The x position of the cell.
   * @param y The y position of the cell.
   * @return The cell or {@code null} if the position is not valid.
   */
  public Cell getCell(int x, int y) {
    if (width <= x || x < 0 ||
        height <= y || y < 0)
      return null;

    return grid[x][y];
  }

  /**
   * Returns a list of all cells column wise.
   *
   * @return A list of all cells.
   */
  public List<Cell> getCells() {
    List<Cell> list = new ArrayList<Cell>();

    for (int x = 0; x < width; x++)
      list.addAll(Arrays.asList(grid[x]));

    return list;
  }

  /**
   * Returns an iterator over all cells in this maze.
   *
   * @return A maze cell iterator.
   */
  @Override
  public Iterator<Cell> iterator() {
    return new MazeCellIterator(this);
  }
}
