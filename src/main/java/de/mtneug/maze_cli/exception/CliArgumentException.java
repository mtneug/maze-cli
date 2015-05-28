/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.exception;

/**
 * Exception with passed CLI arguments.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class CliArgumentException extends IllegalArgumentException {
  public CliArgumentException() {
  }

  public CliArgumentException(String message) {
    super(message);
  }

  public CliArgumentException(String message, Throwable cause) {
    super(message, cause);
  }

  public CliArgumentException(Throwable cause) {
    super(cause);
  }
}
