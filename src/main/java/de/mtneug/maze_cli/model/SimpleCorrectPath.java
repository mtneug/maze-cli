/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.model;

import de.mtneug.maze_cli.exception.CannotGoInThatDirectionException;

/**
 * Model of a simple path, which abides to the link definitions of the maze.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class SimpleCorrectPath extends AbstractPath {
  /**
   * The constructor.
   *
   * @param maze The maze the path lies in.
   */
  public SimpleCorrectPath(Maze maze) {
    super(maze);
  }

  /**
   * Goes to the given {@code direction} and add that cell to the end of the path.
   *
   * @param direction The direction to go to.
   * @throws CannotGoInThatDirectionException if there is a wall in that direction.
   */
  public void goAndAdd(Direction direction) {
    if (isEmpty() || lastCell().cannotGoTo(direction))
      throw new CannotGoInThatDirectionException();

    path.add(lastCell().getNeighborPositioned(direction));
  }

  /**
   * Add {@code cell} to the end of the path, if it is a neighbor of the last cell.
   *
   * @param cell The cell to add.
   * @throws IllegalArgumentException if the cell is not a neighbor of the last cell
   */
  public void addCell(Cell cell) {
    if (!lastCell().getNeighbors().containsValue(cell))
      throw new IllegalArgumentException("cell is not a neighbor of the last cell");

    path.add(cell);
  }

  /**
   * Returns the first cell of the path.
   *
   * @return The first cell of the path.
   * @throws IndexOutOfBoundsException if the path is empty.
   */
  public Cell firstCell() {
    return path.get(0);
  }

  /**
   * Returns the last cell of the path.
   *
   * @return The last cell of the path.
   * @throws IndexOutOfBoundsException if the path is empty.
   */
  public Cell lastCell() {
    return path.get(path.size() - 1);
  }
}
