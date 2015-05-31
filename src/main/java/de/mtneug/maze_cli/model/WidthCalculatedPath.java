/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.model;

import de.mtneug.maze_cli.util.Graphics;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Model of a simple path between to given points with a certain width. It at least includes all cells between the two
 * point necessary to build a linked path.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class WidthCalculatedPath extends SimpleCalculatedPath {
  /**
   * The set of all cell part of the path.
   */
  protected final Set<Cell> area = new HashSet<>();

  /**
   * The width of the path.
   */
  private final double width;

  /**
   * The constructor. It sets {@link #width} to {@code 0}.
   *
   * @param startCell The start cell of the path.
   * @param endCell   The end cell of the path.
   * @param maze      The maze the path lies in.
   */
  public WidthCalculatedPath(Cell startCell, Cell endCell, Maze maze) {
    this(startCell, endCell, maze, 0);
  }

  /**
   * The constructor.
   *
   * @param startCell The start cell of the path.
   * @param endCell   The end cell of the path.
   * @param maze      The maze the path lies in.
   * @param width     The width of the path.
   */
  public WidthCalculatedPath(Cell startCell, Cell endCell, Maze maze, double width) {
    super(startCell, endCell, maze);

    if (width < 0)
      throw new IllegalArgumentException("width must be positive");

    this.width = width;
  }

  /**
   * Code to calculate the path. This class uses a modified version of Bresenham's line rasterisation algorithm.
   */
  @Override
  public void calculatePath() {
    Graphics.bresenham(
        startCell.getPosition().x,
        startCell.getPosition().y,
        endCell.getPosition().x,
        endCell.getPosition().y,
        width,
        this
    );
  }

  /**
   * Called by a rasterisation algorithm to include this cell in the path.
   *
   * @param x The x position of the cell.
   * @param y The y position of the cell.
   */
  @Override
  public void drawCell(int x, int y) {
    Cell cell = maze.getCell(x, y);

    if (cell != null)
      area.add(cell);
  }

  /**
   * Returns the width of the path.
   *
   * @return The width of the path.
   */
  public double getWidth() {
    return width;
  }

  /**
   * Returns a read only set of all cells part of this path.
   *
   * @return The set of all cell part of this path.
   */
  public Set<Cell> getAreaCells() {
    return Collections.unmodifiableSet(area);
  }

  /**
   * Unsupported operation.
   */
  @Override
  public List<Cell> getPathCells() {
    throw new UnsupportedOperationException("getPathCells(); use getAreaCells() instead");
  }

  /**
   * Whether there are any cells on the path.
   *
   * @return {@code true} if there are no cells on the path, {@code false} otherwise.
   */
  @Override
  public boolean isEmpty() {
    return area.isEmpty();
  }
}
