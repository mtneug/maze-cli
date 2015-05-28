/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.util;

import static java.lang.Math.pow;

/**
 * Helper methods for math operations.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public final class Math {
  /**
   * Private constructor.
   */
  private Math() {
  }

  // TODO:
  public static double quarticEaseOut(double x) {
    if (x < 0 || x > 1)
      throw new IllegalArgumentException("x must be between 0 and 1");

    return 1 - pow(1 - x, 4);
  }
}
