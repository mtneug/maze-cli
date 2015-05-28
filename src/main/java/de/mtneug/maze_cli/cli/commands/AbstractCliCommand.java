/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.cli.commands;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Common CLI command.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractCliCommand implements Callable<Object> {
  /**
   * List of arguments for this command.
   */
  protected final List<String> arguments;

  /**
   * The constructor.
   *
   * @param args List of arguments.
   */
  public AbstractCliCommand(List<String> args) {
    this.arguments = args;
  }

  /**
   * Prints how to use this command.
   */
  public abstract void printUsage();
}
