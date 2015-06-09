/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.cli.adapter.outputs;

import de.mtneug.maze_cli.annotations.OutputAdapter;
import de.mtneug.maze_cli.model.MazeSolutions;
import de.mtneug.maze_cli.outputs.AbstractMazeOutput;
import de.mtneug.maze_cli.outputs.StatisticsOutput;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

import static org.apache.commons.cli.PatternOptionBuilder.STRING_VALUE;

/**
 * Adapter for the maze statistics output.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
@OutputAdapter(name = "solver")
public class StatisticsAdapter extends AbstractMazeOutputCliAdapter {
  /**
   * Code to instantiate a new maze statistics output configured with the given parameters.
   *
   * @param mazeSolutions The maze and its solution(s) to output.
   * @param commandLine   Parsed CLI arguments.
   * @return A maze statistics output.
   * @throws ParseException
   */
  @Override
  public AbstractMazeOutput doGenerate(MazeSolutions mazeSolutions, CommandLine commandLine) throws ParseException {
    return new StatisticsOutput(
        mazeSolutions,
        commandLine.getOptionValue("statistics-file")
    );
  }

  /**
   * Add additional options.
   */
  @Override
  protected void buildOptions() {
    super.buildOptions();

    // Set this options as required so the user is forced to think which solver to use.
    options.getOption("s").setRequired(true);

    options.addOption(Option.builder()
            .required(true)
            .longOpt("statistics-file")
            .desc("write statistics to PATH")
            .hasArg().numberOfArgs(1).argName("PATH").type(STRING_VALUE)
            .build()
    );
  }
}