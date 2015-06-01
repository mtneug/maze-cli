/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.outputs;

import de.mtneug.maze_cli.model.MazeSolutions;

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
   * The maze and solutions to output.
   */
  protected final MazeSolutions mazeSolutions;

  /**
   * The constructor.
   *
   * @param mazeSolutions The maze and solutions to output.
   */
  public AbstractMazeOutput(MazeSolutions mazeSolutions) {
    this.mazeSolutions = mazeSolutions;
  }

  /**
   * Returns the maze and solutions to output.
   *
   * @return The maze and solutions to output.
   */
  public MazeSolutions getMazeSolutions() {
    return mazeSolutions;
  }
}
