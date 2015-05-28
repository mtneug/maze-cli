/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.cli;

import de.mtneug.maze_cli.cli.adapter.CliCommandRegistry;
import de.mtneug.maze_cli.cli.commands.AbstractCliCommand;
import de.mtneug.maze_cli.cli.commands.HelpCommand;
import de.mtneug.maze_cli.exception.CliArgumentException;
import org.apache.commons.cli.ParseException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Wrapper of all CLI commands.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class Cli extends AbstractCliCommand {
  /**
   * Index of the command argument.
   */
  public final static int ARGUMENTS_COMMAND_INDEX = 0;

  /**
   * Return code in case of an error.
   */
  public final static int RETURN_ERROR_CODE = 1;

  /**
   * Current command. Defaults to the {@link HelpCommand}.
   */
  public AbstractCliCommand command = new HelpCommand(null);

  /**
   * The constructor.
   *
   * @param args List of arguments.
   */
  public Cli(List<String> args) {
    super(args);
  }

  /**
   * The same as {@link #call()} only with coughed exceptions. In case of an exception the program is terminated with
   * {@link #RETURN_ERROR_CODE} as return code.
   *
   * @return The returned object of the command.
   */
  public Object safeCall() {
    try {
      return call();
    } catch (CliArgumentException | ParseException e) {
      System.err.println(e.getMessage());
      command.printUsage();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    System.exit(RETURN_ERROR_CODE);
    return null;
  }

  /**
   * Call the command.
   *
   * @return The returned object of the command.
   * @throws Exception
   */
  @Override
  public Object call() throws Exception {
    if (arguments.size() == 0)
      throw new CliArgumentException("No command specified. Type 'help' to see a list of commands.");

    createCommand();
    return command.call();
  }

  /**
   * Creates a command object.
   */
  private void createCommand() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    if (arguments.get(ARGUMENTS_COMMAND_INDEX).equals("help"))
      // already created
      return;

    // Get command class
    final String commandName = arguments.get(ARGUMENTS_COMMAND_INDEX);
    final Class<? extends AbstractCliCommand> commandClass = CliCommandRegistry.getInstance().getCommand(commandName);

    if (commandClass == null)
      throw new CliArgumentException("Command " + commandName + " was not found");

    // Instantiate command object
    final Constructor<? extends AbstractCliCommand> commandConstructor = commandClass.getConstructor(List.class);
    final List<String> commandArgs = arguments.subList(ARGUMENTS_COMMAND_INDEX + 1, arguments.size());
    command = commandConstructor.newInstance(commandArgs);
  }

  /**
   * Prints how to use this command.
   */
  @Override
  public void printUsage() {
  }
}
