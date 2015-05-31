/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.model;

import de.mtneug.maze_cli.exception.MazeNotYetSolvedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model of solutions to a given maze.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @see MazeSolverAlgorithm
 * @since 1.0
 */
public class MazeSolutions {
  /**
   * List of paths from the start cell to the end cell, i.e. solutions of the maze.
   */
  protected final List<SimpleCorrectPath> solutions = new ArrayList<>();

  /**
   * The solver to use.
   */
  protected final MazeSolverAlgorithm mazeSolver;

  /**
   * The solved maze.
   */
  protected final Maze maze;

  /**
   * Whether the solving algorithm has run or not.
   */
  private boolean solved = false;

  /**
   * The constructor.
   *
   * @param maze The solved maze.
   */
  public MazeSolutions(Maze maze, MazeSolverAlgorithm mazeSolver) {
    if (maze == null || mazeSolver == null)
      throw new IllegalArgumentException("maze and mazeSolver can't be null");

    if (!maze.hasStartCell() && !maze.hasEndCell())
      throw new IllegalArgumentException("maze has no start or end cell defined");

    this.maze = maze;
    this.mazeSolver = mazeSolver;
  }

  /**
   * Runs the {@link #mazeSolver} on the given maze.
   */
  public void solve() {
    if (solved) return;
    mazeSolver.solve(maze, solutions);
  }

  /**
   * Returns the solved maze.
   *
   * @return The solved maze.
   */
  public Maze getMaze() {
    return maze;
  }

  /**
   * Returns a read only list of paths from the start cell to the end cell, i.e. solutions of the maze.
   *
   * @return The list of solutions
   * @throws MazeNotYetSolvedException if the solver has not run yet.
   */
  public List<SimpleCorrectPath> getSolutions() {
    throwExceptionIfNotSolved();
    return Collections.unmodifiableList(solutions);
  }

  /**
   * Whether the maze has a solution of not.
   *
   * @return {@code true} if there is a solution available, {@code false} otherwise.
   * @throws MazeNotYetSolvedException if the solver has not run yet.
   */
  public boolean hasSolution() {
    throwExceptionIfNotSolved();
    return !solutions.isEmpty();
  }

  /**
   * Returns the maze solver for solving the maze.
   *
   * @return The maze solver for solving the maze.
   */
  public MazeSolverAlgorithm getMazeSolver() {
    return mazeSolver;
  }

  /**
   * Whether the maze solver has run yet.
   *
   * @return {@code true} if the maze solver has run, {@code false} otherwise.
   */
  public boolean isSolved() {
    return solved;
  }

  /**
   * Throws an exception if the maze is not yet solved.
   *
   * @throws MazeNotYetSolvedException
   */
  protected void throwExceptionIfNotSolved() {
    if (!solved)
      throw new MazeNotYetSolvedException();
  }
}
