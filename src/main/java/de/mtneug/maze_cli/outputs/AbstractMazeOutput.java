/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.outputs;

import de.mtneug.maze_cli.model.Maze;

import java.util.concurrent.Callable;

/**
 * Base class of all maze outputs.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractMazeOutput implements Callable<Object> {
  /**
   * The maze to output.
   */
  protected final Maze maze;

  /**
   * The constructor.
   *
   * @param maze The maze to output.
   */
  public AbstractMazeOutput(Maze maze) {
    this.maze = maze;
  }

  /**
   * Returns the maze to output.
   *
   * @return The maze to output.
   */
  public Maze getMaze() {
    return maze;
  }
}
