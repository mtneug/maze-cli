/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.cli.adapter;

import org.apache.commons.cli.*;

import java.util.List;

/**
 * Common base class for all CLI arguments to Java object adapter.
 *
 * @param <T> The type of the returned object.
 * @author Matthias Neugebauer
 */
public abstract class AbstractCliAdapter<T> {
  /**
   * Parser of CLI arguments.
   */
  protected final CommandLineParser parser = new DefaultParser();

  /**
   * Available CLI arguments. Adapter can add additional options by overwriting the {@link #buildOptions()}
   * method.
   */
  protected final Options options = new Options();

  /**
   * The constructor.
   */
  public AbstractCliAdapter() {
    buildOptions();
  }

  /**
   * This method will return a {@link T} object configured with the given parameters.
   *
   * @param args Arguments for the object.
   * @param obj  Additional arguments, which don't need parsing.
   * @return A {@link T} object.
   * @throws ParseException
   */
  public T generate(List<String> args, Object... obj) throws ParseException {
    return doGenerate(parseArguments(args), obj);
  }

  /**
   * This method will return a {@link T} object configured with the given parameters.
   *
   * @param commandLine Parsed CLI arguments.
   * @param obj         Additional arguments, which don't need parsing.
   * @return A {@link T} object.
   * @throws ParseException
   */
  public abstract T doGenerate(CommandLine commandLine, Object... obj) throws ParseException;

  /**
   * Parses the given CLI arguments.
   *
   * @param args The CLI arguments.
   * @return The parsed CLI arguments.
   * @throws ParseException
   */
  private synchronized CommandLine parseArguments(List<String> args) throws ParseException {
    return parser.parse(options, args.toArray(new String[args.size()]));
  }

  /**
   * Can be overwritten to add options.
   */
  protected void buildOptions() {
  }
}
