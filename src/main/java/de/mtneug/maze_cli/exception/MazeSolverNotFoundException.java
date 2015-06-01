/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.exception;

/**
 * Maze solver was not found.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class MazeSolverNotFoundException extends Exception {
  public MazeSolverNotFoundException() {
  }

  public MazeSolverNotFoundException(String message) {
    super(message);
  }

  public MazeSolverNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public MazeSolverNotFoundException(Throwable cause) {
    super(cause);
  }
}
