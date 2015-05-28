/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.util;

/**
 * Defines a type which can be used to draw on a 2d grid.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public interface Drawable {
  /**
   * Draw at the given position.
   *
   * @param x The x position.
   * @param y The y position.
   */
  void drawCell(int x, int y);
}
