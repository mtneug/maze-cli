/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.outputs;

import de.mtneug.maze_cli.model.MazeSolutions;

import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Maze output, which outputs statistics.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class StatisticsOutput extends AbstractMazeOutput {
  /**
   * A lock object to synchronize multiple threads on.
   */
  public final static Object LOCK = new Object();

  /**
   * Path to a file where statistics should be written.
   */
  private String writeStatisticsPath;

  /**
   * The constructor.
   *
   * @param mazeSolutions       The maze and solutions to output.
   * @param writeStatisticsPath Path to a file where statistics should be written.
   * @throws IllegalArgumentException if {@code writeStatisticsPath} is {@code null}.
   */
  public StatisticsOutput(MazeSolutions mazeSolutions, String writeStatisticsPath) {
    super(mazeSolutions);

    if (writeStatisticsPath == null)
      throw new IllegalArgumentException("writeStatisticsPath can't be null");

    setWriteStatisticsPath(writeStatisticsPath);
  }

  /**
   * Outputs statistics about the solver, which had running on the given maze.
   *
   * @return {@code null}
   */
  @Override
  public Object call() throws Exception {
    String csvStatistics = "" +
        mazeSolutions.getMaze().getStatistics() + "," +
        mazeSolutions.getMaze().getMazeAlgorithm().getStatistics() + "," +
        mazeSolutions.getStatistics() + "," +
        mazeSolutions.getMazeSolver().getStatistics();

    // TODO: how to lower the synchronization if write happens to different files?
    synchronized (LOCK) {
      try (PrintWriter fileWriter = new PrintWriter(new FileWriter(writeStatisticsPath, true))) {
        fileWriter.println(csvStatistics);
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
