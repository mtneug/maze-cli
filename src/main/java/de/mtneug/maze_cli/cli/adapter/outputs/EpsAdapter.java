/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.cli.adapter.outputs;

import de.mtneug.maze_cli.annotations.Output;
import de.mtneug.maze_cli.model.Maze;
import de.mtneug.maze_cli.outputs.AbstractMazeOutput;
import de.mtneug.maze_cli.outputs.EpsOutput;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

import static org.apache.commons.cli.PatternOptionBuilder.STRING_VALUE;

/**
 * Adapter for the maze EPS output.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
@Output(name = "eps")
public class EpsAdapter extends AbstractFileOutputAdapter {
  /**
   * Code to instantiate a new maze EPS output configured with the given parameters.
   *
   * @param maze        The maze to output.
   * @param commandLine Parsed CLI arguments.
   * @return The returned object of the maze EPS output.
   * @throws ParseException
   */
  @Override
  public AbstractMazeOutput doGenerate(Maze maze, CommandLine commandLine) throws ParseException {
    return new EpsOutput(
        maze,
        getPath(commandLine)
    );
  }
}
