/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.outputs;

import de.erichseifert.vectorgraphics2d.PDFGraphics2D;
import de.mtneug.maze_cli.model.Maze;

import java.io.FileOutputStream;

/**
 * Maze output, which writes a PDF file with the maze.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class PdfOutput extends AbstractGraphicOutput {
  /**
   * The path to use for writing the PDF file.
   */
  private final String path;

  /**
   * The constructor.
   *
   * @param maze The maze to output.
   * @param path The path to use for writing the PDF file.
   */
  public PdfOutput(Maze maze, String path) {
    super(maze);

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
    PDFGraphics2D g = new PDFGraphics2D(0, 0, getWidth(), getHeight());
    paintComponent(g);

    try (FileOutputStream file = new FileOutputStream(path)) {
      file.write(g.getBytes());
    }

    return null;
  }
}
