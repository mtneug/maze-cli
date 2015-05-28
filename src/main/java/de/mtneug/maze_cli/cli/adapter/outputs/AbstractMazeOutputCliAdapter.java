/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.cli.adapter.outputs;

import de.mtneug.maze_cli.cli.adapter.AbstractCliAdapter;
import de.mtneug.maze_cli.model.Maze;
import de.mtneug.maze_cli.outputs.AbstractMazeOutput;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

/**
 * Common CLI to maze output adapter.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractMazeOutputCliAdapter extends AbstractCliAdapter<AbstractMazeOutput> {
  /**
   * Code to instantiate a new maze output configured with the given parameters.
   *
   * @param commandLine Parsed CLI arguments.
   * @param obj         Needs to include a maze as first parameter.
   * @return A maze output.
   * @throws ParseException
   */
  @Override
  public AbstractMazeOutput doGenerate(CommandLine commandLine, Object... obj) throws ParseException {
    if (obj.length < 1)
      throw new IllegalArgumentException("a maze need to be passed, too");

    return doGenerate((Maze) obj[0], commandLine);
  }

  /**
   * Code to instantiate a new maze output configured with the given parameters.
   *
   * @param maze        The maze to output.
   * @param commandLine Parsed CLI arguments.
   * @return A maze output.
   * @throws ParseException
   */
  public abstract AbstractMazeOutput doGenerate(Maze maze, CommandLine commandLine) throws ParseException;
}
