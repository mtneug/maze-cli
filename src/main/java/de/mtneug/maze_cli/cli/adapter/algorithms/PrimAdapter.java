/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.cli.adapter.algorithms;

import de.mtneug.maze_cli.algorithms.AbstractMazeAlgorithm;
import de.mtneug.maze_cli.algorithms.Prim;
import de.mtneug.maze_cli.annotations.Algorithm;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

/**
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
@Algorithm(name = "prim")
public class PrimAdapter extends AbstractMazeAlgorithmCliAdapter {
  /**
   * Code to instantiate a new Prim maze generation algorithm configured with the given options.
   *
   * @param width       The width of the maze to generate.
   * @param height      The height of the maze to generate.
   * @param commandLine Parsed CLI arguments.
   * @return A Prim maze generation algorithm.
   * @throws ParseException
   */
  @Override
  public AbstractMazeAlgorithm doGenerate(int width, int height, CommandLine commandLine) throws ParseException {
    return new Prim(width, height, createRandom(commandLine));
  }
}
