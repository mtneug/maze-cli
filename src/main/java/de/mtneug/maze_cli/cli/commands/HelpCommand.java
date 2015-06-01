/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.cli.commands;

import de.mtneug.maze_cli.annotations.CliCommand;

import java.util.List;

/**
 * CLI command to print out how to use this program.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
@CliCommand(name = "help")
public class HelpCommand extends AbstractCliCommand {
  /**
   * The constructor.
   *
   * @param args List of arguments.
   */
  public HelpCommand(List<String> args) {
    super(args);
  }

  /**
   * Call the command.
   *
   * @return {@code null}.
   * @throws Exception
   */
  @Override
  public Object call() throws Exception {
    printUsage();
    return null;
  }

  /**
   * Prints how to use this command.
   */
  @Override
  public void printUsage() {
    System.out.println(
        "Usage maze.jar\n" +
            "\n" +
            "    generate WIDTH:HEIGHT ALGO [ALGO-ARGS...] OUTPUT [OUTPUT-ARGS...]\n" +
            "    help"
    );
  }
}
