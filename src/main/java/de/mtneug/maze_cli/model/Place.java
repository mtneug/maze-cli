/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.model;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import de.mtneug.maze_cli.exception.CannotGoInThatDirectionException;
import de.mtneug.maze_cli.util.MapValueComparator;

import java.util.*;

/**
 * Model of a place i.e. a cell with all directions one can go from there mapping to how many times there have been
 * seen/followed.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class Place {
  /**
   * The map of followable directions to how many times there have been seen/followed.
   */
  private final Map<Direction, Integer> directionStates = new LinkedHashMap<>();

  /**
   * The comparator to sort the map after its values.
   */
  private final MapValueComparator<Direction, Integer> comparator;

  /**
   * The cell the place is based of.
   */
  private final Cell cell;

  /**
   * The constructor.
   *
   * @param cell The cell the place is based of.
   */
  public Place(Cell cell) {
    if (cell == null)
      throw new IllegalArgumentException("cell can't be null");

    this.cell = cell;
    this.comparator = new MapValueComparator<>(directionStates);

    for (Direction direction : cell.getLinkedDirections())
      directionStates.put(direction, 0);
  }

  /**
   * Returns a value sorted view of {@link #directionStates}.
   *
   * @return A value sorted view of {@link #directionStates}.
   */
  private TreeMap<Direction, Integer> getSortedDirectionStates() {
    TreeMap<Direction, Integer> sortedMap = new TreeMap<>(comparator);
    sortedMap.putAll(directionStates);
    return sortedMap;
  }

  /**
   * Returns the list of direction, which have been seen the least.
   *
   * @return The list of direction, which have been seen the least.
   */
  public List<Direction> getLeastSeenDirections() {
    return getDirectionsWithSeenValueOf(getLowestSeenValue());
  }

  /**
   * Returns the lowest seen value.
   *
   * @return The lowest seen value.
   */
  public int getLowestSeenValue() {
    return getSortedDirectionStates().firstEntry().getValue();
  }

  /**
   * Returns the list of directions with a seen value of {@code value}.
   *
   * @param value The seen value the directions should have.
   * @return The list of directions with a seen value of {@code value}.
   */
  public List<Direction> getDirectionsWithSeenValueOf(final int value) {
    return new ArrayList<>(Collections2.filter(directionStates.keySet(), new Predicate<Direction>() {
      @Override
      public boolean apply(Direction input) {
        return directionStates.get(input) == value;
      }
    }));
  }

  /**
   * Increment the seen value of the given {@code direction}.
   *
   * @param direction The direction.
   * @return The new value of the direction.
   */
  public int addSeen(Direction direction) {
    if (!directionStates.containsKey(direction))
      throw new CannotGoInThatDirectionException();

    return 1 + directionStates.put(direction, directionStates.get(direction) + 1);
  }

  /**
   * Returns the seen value of the given {@code direction}.
   *
   * @param direction The direction.
   * @return The seen value of the given {@code direction}.
   */
  public int getSeenValueOf(Direction direction) {
    return directionStates.get(direction);
  }

  /**
   * Returns the cell the place is based of.
   *
   * @return The cell the place is based of.
   */
  public Cell getCell() {
    return cell;
  }

  /**
   * Returns list of all directions with a seen value of at least one.
   *
   * @return List of all directions with a seen value of at least one.
   */
  public List<Direction> allSeenDirections() {
    return new ArrayList<>(Collections2.filter(directionStates.keySet(), new Predicate<Direction>() {
      @Override
      public boolean apply(Direction input) {
        return directionStates.get(input) > 0;
      }
    }));
  }
}
