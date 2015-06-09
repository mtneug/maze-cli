/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.cli.adapter.algorithms;

import de.mtneug.maze_cli.algorithms.AbstractMazeAlgorithm;
import de.mtneug.maze_cli.algorithms.M;
import de.mtneug.maze_cli.annotations.AlgorithmAdapter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

import static org.apache.commons.cli.PatternOptionBuilder.NUMBER_VALUE;
import static org.apache.commons.cli.PatternOptionBuilder.STRING_VALUE;

/**
 * Adapter for the M maze generation algorithm.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
@AlgorithmAdapter(name = "m")
public class MAdapter extends AbstractMazeAlgorithmCliAdapter {
  /**
   * Code to instantiate a new M maze generation algorithm configured with the given options.
   *
   * @param width       The width of the maze to generate.
   * @param height      The height of the maze to generate.
   * @param commandLine Parsed CLI arguments.
   * @return A M maze generation algorithm.
   * @throws ParseException
   */
  @Override
  public AbstractMazeAlgorithm doGenerate(int width, int height, CommandLine commandLine) throws ParseException {
    return new M(
        width,
        height,
        createRandom(commandLine),
        ((Long) commandLine.getParsedOptionValue("m-difficulty")).intValue()
    );
  }

  /**
   * Add additional options.
   */
  @Override
  protected void buildOptions() {
    super.buildOptions();

    options.addOption(Option.builder()
            .required(true)
            .longOpt("m-difficulty")
            .desc("set DIFF a number between 0 and 100 as the desired difficulty")
            .hasArg().numberOfArgs(1).argName("DIFF").type(NUMBER_VALUE)
            .build()
    );
  }
}
