/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.model;

import java.util.List;

/**
 * This type defines a maze solver algorithm, which will typically be called in {@link MazeSolutions#solve()}. A maze
 * solver algorithm has the method {@link #solve(Maze, List)}, which contains the code to solve the given maze. The
 * solutions are essentially a list of {@link SimpleCorrectPath} objects and should be added to the given list.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @see MazeSolutions
 * @since 1.0
 */
public interface MazeSolverAlgorithm extends CsvStatisticable {
  /**
   * Code to solve the given {@code maze}. The solutions are essentially a list of {@link SimpleCorrectPath} objects and
   * should be added to {@code solutions}.
   *
   * @param maze      The maze to solve.
   * @param solutions The list to which the found solutions should be added.
   */
  void solve(Maze maze, List<SimpleCorrectPath> solutions);

  /**
   * Returns the number of steps needed to solve the maze.
   *
   * @return The number of steps needed to solve the maze.
   */
  long getSteps();
}
