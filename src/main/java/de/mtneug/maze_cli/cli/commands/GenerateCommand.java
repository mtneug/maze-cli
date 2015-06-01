/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.cli.commands;

import de.mtneug.maze_cli.annotations.CliCommand;

import java.util.List;

/**
 * CLI command to generate a maze and output it somehow.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
@CliCommand(name = "generate")
public class GenerateCommand extends AbstractCliCommand {
  /**
   * The constructor.
   *
   * @param args List of arguments.
   */
  public GenerateCommand(List<String> args) {
    super(args);
  }

  /**
   * Call the command.
   *
   * @return The returned object of the output.
   * @throws Exception
   */
  @Override
  public Object call() throws Exception {
    // implementation as specialization of the multi-generate command

    // add times argument for multi-generate
    arguments.add(0, "1");

    // create multi-generate command and execute it
    MultiGenerateCommand command = new MultiGenerateCommand(arguments);
    command.setPrintingProgress(false);
    return command.call();
  }

  /**
   * Prints how to use this command.
   */
  @Override
  public void printUsage() {
    System.out.println(
        "Usage maze generate WIDTH:HEIGHT ALGO [ALGO-ARGS...] OUTPUT [OUTPUT-ARGS...]\n" +
            "\n" +
            "    WIDTH:\t\tThe width of the maze\n" +
            "    HEIGHT:\t\tThe height of the maze\n" +
            "    ALGO:\t\tThe algorithm to use\n" +
            "    OUTPUT:\t\tHow to output the maze"
    );
  }
}
