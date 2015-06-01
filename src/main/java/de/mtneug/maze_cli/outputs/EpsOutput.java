/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.outputs;

import de.erichseifert.vectorgraphics2d.EPSGraphics2D;
import de.mtneug.maze_cli.model.MazeSolutions;

import java.io.FileOutputStream;

/**
 * Maze output, which writes a EPS file with the maze.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class EpsOutput extends AbstractGraphicOutput {
  /**
   * The path to use for writing the EPS file.
   */
  private final String path;

  /**
   * The constructor.
   *
   * @param mazeSolutions The maze and solutions to output.
   * @param path          The path to use for writing the EPS file.
   */
  public EpsOutput(MazeSolutions mazeSolutions, String path) {
    super(mazeSolutions);

    if (path == null)
      throw new IllegalArgumentException("No path is specified");

    this.path = path;
  }

  /**
   * Outputs the given maze.
   *
   * @return {@code null}
   */
  @Override
  public Object call() throws Exception {
    EPSGraphics2D g = new EPSGraphics2D(0, 0, getWidth(), getHeight());
    paintComponent(g);

    try (FileOutputStream file = new FileOutputStream(path)) {
      file.write(g.getBytes());
    }

    return null;
  }
}
