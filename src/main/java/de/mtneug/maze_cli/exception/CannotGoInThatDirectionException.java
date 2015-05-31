/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.exception;

/**
 * Exception that indicates, that is is not possible to go in the desired direction.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class CannotGoInThatDirectionException extends RuntimeException {
  public CannotGoInThatDirectionException() {
    super("cannot go in that direction");
  }

  public CannotGoInThatDirectionException(Throwable cause) {
    super("cannot go in that direction", cause);
  }
}
