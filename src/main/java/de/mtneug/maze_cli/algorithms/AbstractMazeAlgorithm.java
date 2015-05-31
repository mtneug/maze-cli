/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.algorithms;

import de.mtneug.maze_cli.model.AbstractAlgorithm;
import de.mtneug.maze_cli.model.Maze;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 * This is the base class for all maze generation algorithms. To implement a maze generator, one has to implement the
 * {@link #running()} method. This method can assume, that a proper initial maze is generated. The generated initial
 * maze by can be changed in {@link #prepareMaze()}.
 * <p/>
 * To use a maze algorithm one should not use the {@link #running()} method directly, but rather {@link #call()}. Maze
 * algorithms are thus {@link Callable<Maze>} and can be run in threads.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractMazeAlgorithm extends AbstractAlgorithm<Maze> {
  /**
   * The random number generator object to use when creating the maze.
   */
  protected final Random random;

  /**
   * Common constructor of a maze algorithm class.
   *
   * @param width  The width of the maze to generate.
   * @param height The height of the maze to generate.
   * @param random The random number generator object to use when creating the maze.
   */
  public AbstractMazeAlgorithm(int width, int height, Random random) {
    this.random = random;
    this.output = new Maze(width, height);
    prepareMaze();
  }

  /**
   * Code that prepares an initial maze ready for use at {@link #running()}.
   */
  protected void prepareMaze() {
  }
}