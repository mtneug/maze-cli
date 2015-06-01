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
   * @return The added cell.
   * @throws CannotGoInThatDirectionException if there is a wall in that direction.
   */
  public Cell goAndAdd(Direction direction) {
    if (isEmpty() && getLastCell().cannotGoTo(direction))
      throw new CannotGoInThatDirectionException();

    Cell cell = getLastCell().getNeighborPositioned(direction);
    path.add(cell);
    return cell;
  }

  /**
   * Add {@code cell} to the end of the path, if it is a neighbor of the last cell.
   *
   * @param cell The cell to add.
   * @return The added cell.
   * @throws IllegalArgumentException if the cell is not a neighbor of the last cell
   */
  public Cell addCell(Cell cell) {
    if (!isEmpty() && cell.isNotNeighborOf(getLastCell()))
      throw new IllegalArgumentException("cell is not a neighbor of the last cell");

    path.add(cell);
    return cell;
  }

  /**
   * Returns the first cell of the path.
   *
   * @return The first cell of the path.
   * @throws IndexOutOfBoundsException if the path is empty.
   */
  public Cell getFirstCell() {
    return path.get(0);
  }

  /**
   * Returns the last cell of the path.
   *
   * @return The last cell of the path.
   * @throws IndexOutOfBoundsException if the path is empty.
   */
  public Cell getLastCell() {
    return path.get(path.size() - 1);
  }

  /**
   * Merges two paths, where {@code otherPath} continues this one.
   *
   * @param otherPath The continuation path of this one.
   * @throws IllegalArgumentException if the other path does not continue this path
   */
  public void merge(SimpleCorrectPath otherPath) {
    if (!isEmpty() & !otherPath.isEmpty() && !getLastCell().isNeighborOf(otherPath.getFirstCell()))
      throw new IllegalArgumentException("otherPath does not continue this path");

    path.addAll(otherPath.path);
  }
}
