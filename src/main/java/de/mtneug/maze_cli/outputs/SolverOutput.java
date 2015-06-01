/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.outputs;

import de.mtneug.maze_cli.model.MazeSolutions;

/**
 * Maze output, which outputs statistics of the run solver.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class SolverOutput extends AbstractMazeOutput {
  /**
   * The constructor.
   *
   * @param mazeSolutions The maze and solutions to output.
   */
  public SolverOutput(MazeSolutions mazeSolutions) {
    super(mazeSolutions);
  }

  /**
   * Outputs the given maze.
   *
   * @return {@code null}
   */
  @Override
  public Object call() throws Exception {
    // TODO: output better
    System.out.print("" +
            mazeSolutions.getSolutions().size() + "," +
            mazeSolutions.getMazeSolver().getSteps()
    );

    return null;
  }
}
