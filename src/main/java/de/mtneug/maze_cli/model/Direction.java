/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.model;

/**
 * An enum defining four directions.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public enum Direction {
  TOP,
  RIGHT,
  BOTTOM,
  LEFT;

  static {
    TOP.opposite = BOTTOM;
    RIGHT.opposite = LEFT;
    BOTTOM.opposite = TOP;
    LEFT.opposite = RIGHT;
  }

  /**
   * The opposite direction of this one.
   */
  private Direction opposite;

  /**
   * Returns the opposite direction of this one. Solution found here:
   * http://stackoverflow.com/questions/18883646/java-enum-methods
   *
   * @return The opposite direction.
   */
  public Direction getOpposite() {
    return opposite;
  }
}
