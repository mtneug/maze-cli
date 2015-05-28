/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.cli.adapter.algorithms;

import de.mtneug.maze_cli.algorithms.AbstractMazeAlgorithm;
import de.mtneug.maze_cli.cli.adapter.AbstractCliAdapter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

import java.security.SecureRandom;
import java.util.Random;

import static org.apache.commons.cli.PatternOptionBuilder.NUMBER_VALUE;

/**
 * Common CLI to maze algorithm adapter.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractMazeAlgorithmCliAdapter extends AbstractCliAdapter<AbstractMazeAlgorithm> {
  /**
   * Code to instantiate a new maze generation algorithm configured with the given parameters.
   *
   * @param commandLine Parsed CLI arguments.
   * @param obj         Needs to include two {@link Integer} variables as width and height of the maze.
   * @return A maze generation algorithm.
   * @throws ParseException
   */
  @Override
  public AbstractMazeAlgorithm doGenerate(CommandLine commandLine, Object... obj) throws ParseException {
    if (obj.length < 2)
      throw new IllegalArgumentException("width and height need to be passed, too");

    return doGenerate((Integer) obj[0], (Integer) obj[1], commandLine);
  }

  /**
   * Code to instantiate a new maze generation algorithm configured with the given parameters.
   *
   * @param width       The width of the maze to generate.
   * @param height      The height of the maze to generate.
   * @param commandLine Parsed CLI arguments.
   * @return A maze generation algorithm.
   * @throws ParseException
   */
  public abstract AbstractMazeAlgorithm doGenerate(int width, int height, CommandLine commandLine) throws ParseException;

  /**
   * Can be overwritten to add additional options.
   */
  @Override
  protected void buildOptions() {
    options.addOption("s", "secure-random", false, "use SecureRandom as random number generator");
    options.addOption(Option.builder()
            .longOpt("random-seed")
            .desc("use INT as random seed if secure-random is not used")
            .hasArg().numberOfArgs(1).argName("INT").type(NUMBER_VALUE)
            .build()
    );
  }

  /**
   * Creates a new random number generator.
   *
   * @param commandLine The parsed CLI arguments.
   * @return A new random number generator.
   * @throws ParseException
   */
  protected Random createRandom(CommandLine commandLine) throws ParseException {
    if (commandLine.hasOption("secure-random"))
      return new SecureRandom();

    if (commandLine.hasOption("random-seed"))
      return new Random((Long) commandLine.getParsedOptionValue("random-seed"));

    return new Random();
  }
}
