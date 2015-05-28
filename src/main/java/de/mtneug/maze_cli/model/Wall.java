/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.model;

/**
 * Model of all wall i.e. a direction associated with a cell.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class Wall {
  /**
   * The associated cell.
   */
  public final Cell cell;

  /**
   * The associated direction.
   */
  public final Direction direction;

  /**
   * The constructor.
   *
   * @param cell      The associated cell.
   * @param direction The associated direction.
   */
  public Wall(Cell cell, Direction direction) {
    if (cell == null || direction == null)
      throw new IllegalArgumentException();

    this.cell = cell;
    this.direction = direction;
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

    Wall wall = (Wall) o;

    return cell.equals(wall.cell) && direction == wall.direction;
  }
}