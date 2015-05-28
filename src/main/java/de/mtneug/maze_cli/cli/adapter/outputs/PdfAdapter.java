/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.cli.adapter.outputs;

import de.mtneug.maze_cli.annotations.Output;
import de.mtneug.maze_cli.model.Maze;
import de.mtneug.maze_cli.outputs.AbstractMazeOutput;
import de.mtneug.maze_cli.outputs.PdfOutput;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

/**
 * Adapter for the maze PDF output.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
@Output(name = "pdf")
public class PdfAdapter extends AbstractFileOutputAdapter {
  /**
   * Code to instantiate a new maze PDF output configured with the given parameters.
   *
   * @param maze        The maze to output.
   * @param commandLine Parsed CLI arguments.
   * @return The returned object of the maze PDF output.
   * @throws ParseException
   */
  @Override
  public AbstractMazeOutput doGenerate(Maze maze, CommandLine commandLine) throws ParseException {
    return new PdfOutput(
        maze,
        getPath(commandLine)
    );
  }
}
