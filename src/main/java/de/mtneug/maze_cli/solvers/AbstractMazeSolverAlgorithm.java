/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.solvers;

import de.mtneug.maze_cli.model.AbstractAlgorithm;
import de.mtneug.maze_cli.model.Maze;
import de.mtneug.maze_cli.model.MazeSolutions;
import de.mtneug.maze_cli.model.MazeSolverAlgorithm;

/**
 * Base class for all maze solver algorithms.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractMazeSolverAlgorithm extends AbstractAlgorithm<MazeSolutions>
    implements MazeSolverAlgorithm {
  /**
   * The number of steps needed for solving the maze.
   */
  protected long steps = 0;

  /**
   * The constructor.
   *
   * @param maze The maze to solve.
   */
  public AbstractMazeSolverAlgorithm(Maze maze) {
    this.output = new MazeSolutions(maze, this);
  }

  /**
   * Starts the solving of the algorithm.
   */
  @Override
  protected void running() {
    this.output.solve();
  }

  /**
   * Returns the number of steps needed to solve the maze.
   *
   * @return The number of steps needed to solve the maze.
   */
  @Override
  public long getSteps() {
    return steps;
  }
}
