/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.model;

/**
 * Base class for all path, which are only defined by start and end cell. All other will be calculated from those.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractCalculatedPath extends AbstractPath {
  /**
   * The start cell of the path.
   */
  protected final Cell startCell;

  /**
   * The end cell of the path.
   */
  protected final Cell endCell;

  /**
   * The constructor.
   *
   * @param startCell The start cell of the path.
   * @param endCell   The end cell of the path.
   * @param maze      The maze the path lies in.
   */
  public AbstractCalculatedPath(Cell startCell, Cell endCell, Maze maze) {
    super(maze);

    if (startCell == null || endCell == null)
      throw new IllegalArgumentException();

    this.startCell = startCell;
    this.endCell = endCell;
  }

  /**
   * Code to calculate the path. It is not invoked automatically.
   */
  public abstract void calculatePath();

  /**
   * Returns the start cell of the path.
   *
   * @return The start cell of the path.
   */
  public Cell getStartCell() {
    return startCell;
  }

  /**
   * Returns the end cell of the path.
   *
   * @return The end cell of the path.
   */
  public Cell getEndCell() {
    return endCell;
  }
}
