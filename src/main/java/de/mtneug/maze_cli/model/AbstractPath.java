/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Base class for path in a maze.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractPath {
  /**
   * The maze the path lies in.
   */
  protected final Maze maze;

  /**
   * The path as list of {@link Cell}.
   */
  protected final List<Cell> path = new ArrayList<>();

  /**
   * The constructor.
   *
   * @param maze The maze the path lies in.
   */
  public AbstractPath(Maze maze) {
    if (maze == null)
      throw new IllegalArgumentException();

    this.maze = maze;
  }

  /**
   * Returns the maze the path lies in.
   *
   * @return The maze the path lies in.
   */
  public Maze getMaze() {
    return maze;
  }

  /**
   * Returns the read only list of the {@link Cell}, which build this path.
   * @return A read only list of {@link Cell}.
   */
  public List<Cell> getPathCells() {
    return Collections.unmodifiableList(path);
  }

  /**
   * Whether there are any cells on the path.
   *
   * @return {@code true} if there are no cells on the path, {@code false} otherwise.
   */
  public boolean isEmpty() {
    return path.isEmpty();
  }
}
