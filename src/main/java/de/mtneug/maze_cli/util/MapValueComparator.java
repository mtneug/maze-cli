/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.util;

import java.util.Comparator;
import java.util.Map;

/**
 * Comparator designed for sorting a formally unsorted map by its values. This solution is based on
 * <a href="http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java">this</a>.
 *
 * @param <K> The type of the key.
 * @param <V> The type of the value.
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class MapValueComparator<K, V extends Comparable<V>> implements Comparator<K> {
  /**
   * The unsorted map.
   */
  private final Map<K, V> unsortedMap;

  /**
   * The constructor.
   *
   * @param unsortedMap the unsorted map.
   */
  public MapValueComparator(Map<K, V> unsortedMap) {
    this.unsortedMap = unsortedMap;
  }

  /**
   * Compares the two values behind the given keys.
   *
   * @param k1 The first key.
   * @param k2 the second key.
   * @return {@code 1} if the value behind key one is greater or equal, {@code -1} otherwise.
   */
  @Override
  public int compare(K k1, K k2) {
    if (unsortedMap.get(k1).compareTo(unsortedMap.get(k2)) >= 0)
      return 1;

    // returning 0 would merge keys
    return -1;
  }
}