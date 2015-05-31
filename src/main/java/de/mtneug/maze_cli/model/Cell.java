/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.model;

import java.util.*;

/**
 * Model of a maze cell.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @see Maze
 * @since 1.0
 */
public class Cell {
  /**
   * The position of this cell in the overall maze.
   */
  private final Point position;

  /**
   * Set of the directions one can go to from this cell.
   */
  private final Set<Direction> links = new HashSet<>();

  /**
   * Set of labels given to this cell.
   */
  private final Set<String> labels = new HashSet<>();

  /**
   * Map of neighbor cells and the direction they lie in.
   */
  private final Map<Direction, Cell> neighbors = new HashMap<>();

  /**
   * The constructor.
   *
   * @param x The x position of this cell.
   * @param y The y position of this cell.
   */
  public Cell(int x, int y) {
    this(new Point(x, y));
  }

  /**
   * The constructor.
   *
   * @param position The position of this cell.
   */
  public Cell(Point position) {
    if (position == null)
      throw new IllegalArgumentException("cells need a position");

    this.position = position;
  }

  /**
   * Links this cell to the cell in the given {@code direction}.
   *
   * @param direction The direction of the cell to link to.
   * @return {@code true} if the link is new, {@code false} otherwise.
   * @throws IllegalArgumentException if no neighbor cell in that direction exists.
   */
  public boolean link(Direction direction) {
    if (canGoTo(direction)) return false;

    if (!hasNeighborPositioned(direction))
      throw new IllegalArgumentException("Has no neighbor cell in that direction");

    links.add(direction);
    getNeighborPositioned(direction).link(direction.getOpposite());

    return true;
  }

  /**
   * Unlinks this cell to the cell in the given {@code direction}.
   *
   * @param direction The direction of the cell to unlink to.
   * @return {@code true} if there was a link before, {@code false} otherwise.
   */
  public boolean unlink(Direction direction) {
    if (cannotGoTo(direction)) return false;

    links.remove(direction);
    getNeighborPositioned(direction).unlink(direction.getOpposite());

    return true;
  }

  /**
   * Checks if a link exists between this cell and the cell in the given {@code direction}.
   *
   * @param direction The direction of the other cell.
   * @return {@code true} if there exists a connection, {@code false} otherwise.
   */
  public boolean canGoTo(Direction direction) {
    return links.contains(direction);
  }

  /**
   * Checks if no link exists between this cell and the cell in the given {@code direction}.
   * <p/>
   * This allows for expressive formulation of algorithms.
   *
   * @param direction The direction of the other cell.
   * @return {@code true} if there exists no connection, {@code false} otherwise.
   */
  public boolean cannotGoTo(Direction direction) {
    return !canGoTo(direction);
  }

  /**
   * Returns the position of this cell in the overall maze.
   *
   * @return The position of this cell.
   */
  public Point getPosition() {
    return position;
  }

  /**
   * Returns the set of labels given to this cell.
   *
   * @return The set of labels.
   */
  public Set<String> getLabels() {
    return labels;
  }

  /**
   * Returns the neighbor cell in the given {@code direction}.
   *
   * @param direction The direction of the cell.
   * @return The neighbor cell.
   */
  public Cell getNeighborPositioned(Direction direction) {
    return neighbors.get(direction);
  }

  /**
   * Sets the neighbor cell in the given {@code direction}.
   *
   * @param direction The direction of the cell.
   * @param cell      The new neighbor cell. If it is {@code null} the neighbor is not set.
   * @return {@code true} if the new neighbor cell was set, {@code false} otherwise.
   */
  public boolean setNeighborPositioned(Direction direction, Cell cell) {
    if (cell == null) return false;
    neighbors.put(direction, cell);
    return true;
  }

  /**
   * Checks of a neighbor in that direction exists.
   *
   * @param direction The direction of the cell.
   * @return {@code true} if there is a neighbor cell, {@code false} otherwise.
   */
  public boolean hasNeighborPositioned(Direction direction) {
    return neighbors.containsKey(direction);
  }

  /**
   * Returns a read only map of all directions to neighbor cells.
   *
   * @return The map of directions to neighbor cells.
   */
  public Map<Direction, Cell> getNeighbors() {
    return Collections.unmodifiableMap(neighbors);
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

    Cell cell = (Cell) o;

    if (position != null ? !position.equals(cell.position) : cell.position != null) return false;
    if (links != null ? !links.equals(cell.links) : cell.links != null) return false;
    if (labels != null ? !labels.equals(cell.labels) : cell.labels != null) return false;
    return !(neighbors != null ? !neighbors.equals(cell.neighbors) : cell.neighbors != null);
  }

  // TODO: hashCode
}
