/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.model;

/**
 * Model of a point in 2d space.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class Point {
  /**
   * The x position.
   */
  public final int x;

  /**
   * The y position.
   */
  public final int y;

  /**
   * The constructor.
   *
   * @param x The x position.
   * @param y The y position.
   */
  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Returns if the given object is equal to this one.
   *
   * @param o The other object.
   * @return {@code true} if it is equal, {@code false} otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Point point = (Point) o;

    return x == point.x && y == point.y;
  }

  /**
   * Returns a string representation of this object.
   *
   * @return A string representation.
   */
  @Override
  public String toString() {
    return "Point(" + x + ", " + y + ')';
  }
}
