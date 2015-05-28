/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.model;

import de.mtneug.maze_cli.util.Drawable;
import de.mtneug.maze_cli.util.Graphics;

/**
 * Model of a simple path between to given points. It includes all cells between the two point necessary to build a
 * linked path.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class SimpleCalculatedPath extends AbstractCalculatedPath implements Drawable {
  /**
   * The constructor.
   *
   * @param startCell The start cell of the path.
   * @param endCell   The end cell of the path.
   * @param maze      The maze the path lies in.
   */
  public SimpleCalculatedPath(Cell startCell, Cell endCell, Maze maze) {
    super(startCell, endCell, maze);
  }

  /**
   * Code to calculate the path. This class uses a modified version of Bresenham's line rasterisation algorithm.
   */
  @Override
  public void calculatePath() {
    Graphics.bresenham(
        startCell.getPosition().x,
        startCell.getPosition().y,
        endCell.getPosition().x,
        endCell.getPosition().y,
        0,
        this
    );
  }

  /**
   * Called by a rasterisation algorithm to include this cell in the path.
   *
   * @param x The x position of the cell.
   * @param y The y position of the cell.
   */
  @Override
  public void drawCell(int x, int y) {
    Cell cell = maze.getCell(x, y);

    if (cell != null)
      path.add(cell);
  }
}
