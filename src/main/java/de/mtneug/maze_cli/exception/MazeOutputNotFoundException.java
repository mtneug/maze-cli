/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.exception;

/**
 * Maze output was not found.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class MazeOutputNotFoundException extends Exception {
  public MazeOutputNotFoundException() {
  }

  public MazeOutputNotFoundException(String message) {
    super(message);
  }

  public MazeOutputNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public MazeOutputNotFoundException(Throwable cause) {
    super(cause);
  }
}
