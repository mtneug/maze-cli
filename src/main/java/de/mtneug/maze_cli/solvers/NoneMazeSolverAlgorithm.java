/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.solvers;

import de.mtneug.maze_cli.annotations.Solver;
import de.mtneug.maze_cli.model.Maze;
import de.mtneug.maze_cli.model.SimpleCorrectPath;

import java.util.List;

/**
 * Solver, which does not solve the maze.
 *
 * @author Matthias Neugebauer
 */
@Solver(name = "none")
public class NoneMazeSolverAlgorithm extends AbstractMazeSolverAlgorithm {
  /**
   * The constructor.
   *
   * @param maze The maze to solve.
   */
  public NoneMazeSolverAlgorithm(Maze maze) {
    super(maze);
  }

  /**
   * Does nothing and returns.
   *
   * @param maze      The maze to solve.
   * @param solutions The list to which the found solutions should be added.
   */
  @Override
  public void solve(Maze maze, List<SimpleCorrectPath> solutions) {
  }
}
