/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.model;

/**
 * Model of a dashed path between to given points. The calculated cells can thus not be linked. An example were this can
 * be useful is to get every n's cell on a certain line.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class DashedCalculatedPath extends SimpleCalculatedPath {
  /**
   * The length of the dash i.e. the number of cells included.
   */
  private final int dashLength;

  /**
   * The distance i.e. the number of cells between the dashes.
   */
  private final int dashDistance;

  /**
   * Stores how many cells still need to be included in the current dash.
   */
  private int leftDashLength;

  /**
   * Stores how many cells still need to be included in the current gap.
   */
  private int leftDashDistance;

  /**
   * The constructor.
   *
   * @param startCell    The start cell of the path.
   * @param endCell      The end cell of the path.
   * @param maze         The maze the path lies in.
   * @param dashLength   The length of the dash.
   * @param dashDistance The length of the gaps between the dashes.
   */
  public DashedCalculatedPath(Cell startCell, Cell endCell, Maze maze, int dashLength, int dashDistance) {
    this(startCell, endCell, maze, dashLength, dashDistance, true);
  }

  /**
   * The constructor.
   *
   * @param startCell     The start cell of the path.
   * @param endCell       The end cell of the path.
   * @param maze          The maze the path lies in.
   * @param dashLength    The length of the dash.
   * @param dashDistance  The length of the gaps between the dashes.
   * @param startWithDash Whether this path starts with a dash or with a gap.
   */
  public DashedCalculatedPath(Cell startCell, Cell endCell, Maze maze, int dashLength, int dashDistance, boolean startWithDash) {
    super(startCell, endCell, maze);

    if (dashLength < 1 || dashDistance < 0)
      throw new IllegalArgumentException("dashLength must be at least one; dashDistance at least zero");

    this.dashLength = dashLength;
    this.dashDistance = leftDashDistance = dashDistance;

    leftDashLength = startWithDash ? dashLength : 0;
  }

  /**
   * Called by a rasterisation algorithm to include this cell in the path.
   *
   * @param x The x position of the cell.
   * @param y The y position of the cell.
   */
  @Override
  public void drawCell(int x, int y) {
    if (--leftDashLength > 0)
      super.drawCell(x, y);
    else if (--leftDashDistance < 0) {
      super.drawCell(x, y);
      leftDashLength = dashLength;
      leftDashDistance = dashDistance;
    }
  }
}
