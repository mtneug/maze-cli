/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.outputs;

import de.mtneug.maze_cli.model.Maze;
import de.mtneug.maze_cli.model.MazeSolutions;

import java.io.*;

import static de.mtneug.maze_cli.model.Direction.RIGHT;
import static de.mtneug.maze_cli.model.Direction.TOP;

/**
 * Maze output which outputs a serialization of the maze. The serialization was suggested by one member of the seminar
 * group "Procedural Content generation in Games" at the University of Münster in the summer semester 2015.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.1
 */
public class SeminarSerializationOutput extends AbstractMazeOutput {
  public final static String PROTOCOL_BEGINNING = "MAZE:";
  public final static String PROTOCOL_NEW_LINE = ",";
  public final static String PROTOCOL_WALL = "W";
  public final static String PROTOCOL_PATH = "P";
  public final static String PROTOCOL_START = "S";
  public final static String PROTOCOL_END = "F";

  /**
   * Path to a file where the serialization should be written.
   */
  private String path;

  /**
   * The constructor.
   *
   * @param mazeSolutions The maze and solutions to output.
   * @param path          Path to a file where the serialization should be written.
   * @throws IllegalArgumentException if {@code path} is {@code null}.
   */
  public SeminarSerializationOutput(MazeSolutions mazeSolutions, String path) {
    super(mazeSolutions);

    if (path == null)
      throw new IllegalArgumentException("path can't be null");

    setPath(path);
  }

  /**
   * Outputs the serialization of the maze.
   *
   * @return {@code null}
   */
  @Override
  public Object call() throws Exception {
    StringWriter serializationWriter = new StringWriter();
    serialize(mazeSolutions.getMaze(), serializationWriter);

    try (PrintWriter fileWriter = new PrintWriter(new FileWriter(path))) {
      fileWriter.print(serializationWriter.toString());
      fileWriter.flush();
    }

    return null;
  }

  /**
   * Serializes the {@code maze} and writes it to {@code serializationWriter}.
   *
   * @param maze   The maze to serialize.
   * @param writer The writer to write to.
   * @throws IOException
   */
  private void serialize(Maze maze, Writer writer) throws IOException {
    writer.write(PROTOCOL_BEGINNING);
    writer.write(String.valueOf(maze.hashCode()));
    writer.write(PROTOCOL_NEW_LINE);

    for (int y = 0; y < maze.getHeight(); y++) {
      // upper row
      writer.write(PROTOCOL_WALL);
      for (int x = 0; x < maze.getWidth(); x++) {
        writer.write(
            maze.getCell(x, y).canGoTo(TOP)
                ? PROTOCOL_PATH
                : PROTOCOL_WALL
        );
        writer.write(PROTOCOL_WALL);
      }
      writer.write(PROTOCOL_NEW_LINE);

      // normal row
      writer.write(PROTOCOL_WALL);
      for (int x = 0; x < maze.getWidth(); x++) {
        writer.write(startOrEndOrDefault(maze, x, y, PROTOCOL_PATH));
        writer.write(
            maze.getCell(x, y).canGoTo(RIGHT)
                ? PROTOCOL_PATH
                : PROTOCOL_WALL
        );
      }
      writer.write(PROTOCOL_NEW_LINE);
    }

    // last row
    writer.write(PROTOCOL_WALL);
    for (int x = 0; x < maze.getWidth(); x++) {
      writer.write(PROTOCOL_WALL);
      writer.write(PROTOCOL_WALL);
    }
  }

  /**
   * Tests for and start or end cell and returns the appropriate symbol. Otherwise {@code defaultStr} is returned.
   *
   * @param maze       The maze.
   * @param x          The x position.
   * @param y          The y position.
   * @param defaultStr The default string.
   * @return {@link #PROTOCOL_START} if at that position is the start cell, {@link #PROTOCOL_END} if at that position is
   * the end cell, {@code defaultStr} otherwise.
   */
  private String startOrEndOrDefault(Maze maze, int x, int y, String defaultStr) {
    if (isStartCell(maze, x, y))
      return PROTOCOL_START;
    else if (isEndCell(maze, x, y))
      return PROTOCOL_END;

    return defaultStr;
  }

  /**
   * Test if the at the given position there is an start cell.
   *
   * @param maze The maze.
   * @param x    The x position.
   * @param y    The y position.
   * @return {@code true} if there is an start cell, {@code false} otherwise.
   */
  private boolean isStartCell(Maze maze, int x, int y) {
    return maze.getStartCell().getPosition().x == x
        && maze.getStartCell().getPosition().y == y;
  }

  /**
   * Test if the at the given position there is an end cell.
   *
   * @param maze The maze.
   * @param x    The x position.
   * @param y    The y position.
   * @return {@code true} if there is an end cell, {@code false} otherwise.
   */
  private boolean isEndCell(Maze maze, int x, int y) {
    return maze.getEndCell().getPosition().x == x
        && maze.getEndCell().getPosition().y == y;
  }

  /**
   * Returns the path to a file where the serialization should be written.
   *
   * @return The path to a file where the serialization should be written.
   */
  public String getPath() {
    return path;
  }

  /**
   * Sets the path to a file where the serialization should be written.
   *
   * @param path The new path.
   * @throws IllegalArgumentException if {@code path} is {@code null}.
   */
  public void setPath(String path) {
    if (path == null)
      throw new IllegalArgumentException("path can't be null");

    this.path = path;
  }
}
