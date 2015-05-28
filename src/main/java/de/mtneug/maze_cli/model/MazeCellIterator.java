/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An iterator over all cells of the given maze row wise.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class MazeCellIterator implements Iterator<Cell> {
  /**
   * The maze this is iterating over.
   */
  private final Maze maze;

  /**
   * The current position.
   */
  private int position = 0;

  /**
   * The constructor.
   *
   * @param maze The maze to iterate over.
   */
  public MazeCellIterator(Maze maze) {
    if (maze == null)
      throw new IllegalArgumentException();

    this.maze = maze;
  }

  /**
   * Returns whether there is a next cell.
   *
   * @return {@code true} if there is a next cell, {@code false} otherwise.
   */
  @Override
  public boolean hasNext() {
    return position < maze.getWidth() * maze.getHeight();
  }

  /**
   * Returns the next cell.
   *
   * @return The next cell.
   * @throws NoSuchElementException if there is no next cell.
   */
  @Override
  public Cell next() {
    if (!hasNext())
      throw new NoSuchElementException();

    Cell cell = maze.getCell(
        position % maze.getWidth(),
        position / maze.getWidth()
    );

    position++;
    return cell;
  }

  /**
   * Unsupported operation.
   */
  @Override
  public void remove() {
    throw new UnsupportedOperationException("remove");
  }
}
