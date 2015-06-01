/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.util;

import java.util.List;
import java.util.Random;

/**
 * Helper methods for list operations.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public final class Lists {
  /**
   * Private constructor.
   */
  private Lists() {
  }

  /**
   * Returns a random item of the given list.
   *
   * @param list A list of items.
   * @return A random item of {@code list}
   * @throws IllegalArgumentException
   */
  public static <T> T chooseRandomItem(List<T> list, Random random) {
    if (list == null || list.isEmpty())
      throw new IllegalArgumentException("list must include at least one item");

    return list.get(random.nextInt(list.size()));
  }
}
