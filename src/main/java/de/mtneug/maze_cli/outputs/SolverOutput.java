/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.outputs;

import de.mtneug.maze_cli.model.MazeSolutions;

import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Maze output, which outputs statistics of the run solver.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class SolverOutput extends AbstractMazeOutput {
  /**
   * A lock object to synchronize multiple threads on.
   */
  public final static Object LOCK = new Object();

  /**
   * Path to a file where statistics should be written. {@code null} if none should be written.
   */
  private String writeStatisticsPath;

  /**
   * The constructor.
   *
   * @param mazeSolutions       The maze and solutions to output.
   * @param writeStatisticsPath Path to a file where statistics should be written.
   * @throws IllegalArgumentException if {@code writeStatisticsPath} is {@code null}.
   */
  public SolverOutput(MazeSolutions mazeSolutions, String writeStatisticsPath) {
    super(mazeSolutions);
    setWriteStatisticsPath(writeStatisticsPath);
  }

  /**
   * Outputs statistics about the solver, which had running on the given maze.
   *
   * @return {@code null}
   */
  @Override
  public Object call() throws Exception {
    String text = "" +
        // identifier
        mazeSolutions.getMaze().hashCode() + "," +

        // dimensions
        mazeSolutions.getMaze().getWidth() + "," +
        mazeSolutions.getMaze().getHeight() + "," +

        // number of all cells
        mazeSolutions.getMaze().getWidth() * mazeSolutions.getMaze().getHeight() + "," +

        // number of solutions
        mazeSolutions.getSolutions().size() + "," +

        // solver statistics
        mazeSolutions.getMazeSolver().getSteps();

    // TODO: how to lower the synchronization if write happens to different files?
    synchronized (LOCK) {
      try (PrintWriter fileWriter = new PrintWriter(new FileWriter(writeStatisticsPath, true))) {
        fileWriter.println(text);
        fileWriter.flush();
      }
    }

    return null;
  }

  /**
   * Returns the path to a file where statistics should be written.
   *
   * @return The path to a file where statistics should be written.
   */
  public String getWriteStatisticsPath() {
    return writeStatisticsPath;
  }

  /**
   * Sets the path to a file where statistics should be written.
   *
   * @param writeStatisticsPath The new path.
   * @throws IllegalArgumentException if {@code writeStatisticsPath} is {@code null}.
   */
  public void setWriteStatisticsPath(String writeStatisticsPath) {
    if (writeStatisticsPath == null)
      throw new IllegalArgumentException("writeStatisticsPath can't be null");

    this.writeStatisticsPath = writeStatisticsPath;
  }
}
