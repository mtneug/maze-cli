/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.model;

/**
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public enum Orientation {
  HORIZONTAL, VERTICAL;

  static {
    HORIZONTAL.opposite = VERTICAL;
    VERTICAL.opposite = HORIZONTAL;
  }

  /**
   * The opposite direction of this one.
   */
  private Orientation opposite;

  /**
   * Returns the opposite orientation of this one. Solution found here:
   * http://stackoverflow.com/questions/18883646/java-enum-methods
   *
   * @return The opposite orientation.
   */
  public Orientation getOpposite() {
    return opposite;
  }
}