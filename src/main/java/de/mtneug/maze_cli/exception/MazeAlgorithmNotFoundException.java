/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.exception;

/**
 * Maze algorithm was not found.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class MazeAlgorithmNotFoundException extends Exception {
  public MazeAlgorithmNotFoundException() {
  }

  public MazeAlgorithmNotFoundException(String message) {
    super(message);
  }

  public MazeAlgorithmNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public MazeAlgorithmNotFoundException(Throwable cause) {
    super(cause);
  }
}
