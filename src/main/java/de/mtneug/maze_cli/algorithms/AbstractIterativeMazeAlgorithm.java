/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.algorithms;

import java.util.Random;

import static de.mtneug.maze_cli.algorithms.AbstractMazeAlgorithm.State.FINISHED;

/**
 * Common class of all maze algorithms which mainly consist of a loop. Implementing a {@link AbstractIterativeMazeAlgorithm}
 * is done by implementing the {@link #step()} method, which will be executed until the {@link #state} is set to
 * {@link State#FINISHED}. Code that should be run before and after the loop goes into the methods with the same name.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @see AbstractMazeAlgorithm
 * @since 1.0
 */
public abstract class AbstractIterativeMazeAlgorithm extends AbstractMazeAlgorithm {
  /**
   * Current number of iterations.
   */
  private int steps = 0;

  /**
   * Common constructor of a maze algorithm class.
   *
   * @param width  The width of the maze to generate.
   * @param height The height of the maze to generate.
   * @param random The random number generator object to use when creating the maze.
   */
  public AbstractIterativeMazeAlgorithm(int width, int height, Random random) {
    super(width, height, random);
  }

  /**
   * Code that runs before the loop.
   */
  protected void before() {
  }

  /**
   * Code that runs after the loop
   */
  protected void after() {
  }

  /**
   * Method that is wrapping the before, loop and after calls.
   */
  @Override
  protected void running() {
    before();
    for (; state != FINISHED; steps++) step();
    after();
  }

  /**
   * This method is called after {@link #before()}, once per iteration and before {@link #after()}. It is also responsible for
   * stopping the iterations by setting {@link #state} to {@link State#FINISHED}.
   */
  protected abstract void step();

  /**
   * Returns the current number of iterations.
   *
   * @return number of iterations
   */
  public int getSteps() {
    return steps;
  }
}
