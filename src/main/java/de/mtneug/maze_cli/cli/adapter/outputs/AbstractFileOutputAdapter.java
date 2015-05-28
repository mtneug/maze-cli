/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.cli.adapter.outputs;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import static org.apache.commons.cli.PatternOptionBuilder.STRING_VALUE;

/**
 * Adapter for maze outputs, which write to some file.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractFileOutputAdapter extends AbstractMazeOutputCliAdapter {
  /**
   * Add additional options.
   */
  @Override
  protected void buildOptions() {
    super.buildOptions();

    options.addOption(Option.builder()
            .required(true)
            .longOpt("file")
            .desc("output the file to PATH")
            .hasArg().numberOfArgs(1).argName("PATH").type(STRING_VALUE)
            .build()
    );
  }

  /**
   * Returns the specified path.
   *
   * @param commandLine The parsed CLI arguments.
   * @return The specified path.
   */
  protected String getPath(CommandLine commandLine) {
    return commandLine.getOptionValue("file");
  }
}
