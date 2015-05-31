/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.exception;

/**
 * Maze is not yet solved.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class MazeNotYetSolvedException extends RuntimeException {
  public MazeNotYetSolvedException() {
    super("maze is not yet solved");
  }

  public MazeNotYetSolvedException(Throwable cause) {
    super("maze is not yet solved", cause);
  }
}
