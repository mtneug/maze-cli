/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.util;

import static java.lang.Math.*;

/**
 * Helper methods for graphical operations.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public final class Graphics {
  /**
   * Private constructor.
   */
  private Graphics() {
  }

  /**
   * Modified implementation of Bresenham's line rasterisation algorithm. The basic algorithm is based on the German
   * Wikipedia <a href="http://de.wikipedia.org/wiki/Bresenham-Algorithmus#Kompakte_Variante">article</a> where the
   * idea of how to produce thicker lines comes from A. S. Murphy "Line Thickening by Modification to Bresenham's
   * Algorithm", IBM TDB, May 1978, pp. 5358-5366, which can be found
   * <a href="http://www.zoo.co.uk/~murphy/thickline/index.html">here</a>.
   *
   * @param x0    The x position of the start point.
   * @param y0    The y position of the start point.
   * @param x1    The x position of the end point.
   * @param y1    the y position of the end point.
   * @param width The width of the line.
   * @param grid  The grid to use for drawing.
   */
  public static void bresenham(int x0, int y0, int x1, int y1, double width, Drawable grid) {
    int // delta (determine length)
        dx = abs(x1 - x0),
        dy = -abs(y1 - y0),

        // direction
        sx = x0 < x1 ? 1 : -1,
        sy = y0 < y1 ? 1 : -1;

    // if start and end are the same, reset the width to 0
    if (dx == 0 && dy == 0) width = 0;

    // draw first cell
    grid.drawCell(x0, y0);

    // draw the rest of the path
    bresenhamIntern(x0, y0, dx, dy, sx, sy, width / 2, grid);
  }

  /**
   * The modified inner loop of Bresenham's line rasterisation algorithm.
   *
   * @param x     The x position of the start point.
   * @param y     The y position of the start point.
   * @param dx    The positive distance to travel in {@code sx} direction.
   * @param dy    The negative distance to travel in {@code sy} direction.
   * @param sx    The direction to travel in x direction.
   * @param sy    The direction to travel in y direction.
   * @param width The width of the line.
   * @param grid  The grid to use for drawing.
   * @see #bresenham(int, int, int, int, double, Drawable)
   */
  private static void bresenhamIntern(int x, int y, double dx, double dy, int sx, int sy, double width, Drawable grid) {
    final double
        // path vector
        xVector = sx * dx,
        yVector = sy * -dy;

    double
        // delta
        dxCurrent = dx - 1 / 2,
        dyCurrent = -dy - 1 / 2,

        // error
        err = dx + dy,
        err2,

        // perpendicular vector
        xPerpendicularVector = 0, yPerpendicularVector = 0;

    int // perpendicular vector direction
        sxPerpendicular = 0, syPerpendicular = 0;

    // calculate perpendicular vector if we need to
    if (width > 0) {
      // dx == 0 & dy == 0 => width = 0 => not landing here :)
      if (dx == 0) {
        final double normalize = 1 / sqrt(1 + pow(dx / dy, 2));
        xPerpendicularVector = width * normalize;
        yPerpendicularVector = width * normalize * -xVector / yVector;
      } else {
        final double normalize = 1 / sqrt(pow(dy / dx, 2) + 1);
        xPerpendicularVector = width * normalize * -yVector / xVector;
        yPerpendicularVector = width * normalize;
      }

      sxPerpendicular = (int) signum(xPerpendicularVector);
      syPerpendicular = (int) signum(yPerpendicularVector);
    }

    while (dxCurrent > 0 || dyCurrent > 0) {
      err2 = 2 * err;

      // move horizontal?
      if ((err2 > dy && dxCurrent > 0) || (dxCurrent > 0 && dyCurrent <= 0)) {
        // draw perpendicular lines on either side
        if (width > 0) {
          bresenhamIntern(x, y, abs(xPerpendicularVector), -abs(yPerpendicularVector), sxPerpendicular, syPerpendicular, 0, grid);
          bresenhamIntern(x, y, abs(xPerpendicularVector), -abs(yPerpendicularVector), -sxPerpendicular, -syPerpendicular, 0, grid);
        }

        // update progress variables
        err += dy;
        dxCurrent--;

        // draw pixel
        grid.drawCell(x += sx, y);
      }

      // move vertical?
      if ((err2 < dx && dyCurrent > 0) || (dyCurrent > 0 && dxCurrent <= 0)) {
        // draw perpendicular lines on either side
        if (width > 0) {
          bresenhamIntern(x, y, abs(xPerpendicularVector), -abs(yPerpendicularVector), sxPerpendicular, syPerpendicular, 0, grid);
          bresenhamIntern(x, y, abs(xPerpendicularVector), -abs(yPerpendicularVector), -sxPerpendicular, -syPerpendicular, 0, grid);
        }

        // update progress variables
        err += dx;
        dyCurrent--;

        // draw pixel
        grid.drawCell(x, y += sy);
      }
    }

    // draw perpendicular lines on either side for the last pixel
    if (width > 0) {
      bresenhamIntern(x, y, abs(xPerpendicularVector), -abs(yPerpendicularVector), sxPerpendicular, syPerpendicular, 0, grid);
      bresenhamIntern(x, y, abs(xPerpendicularVector), -abs(yPerpendicularVector), -sxPerpendicular, -syPerpendicular, 0, grid);
    }
  }

  /**
   * Draw the perpendicular lines with exact width. Currently this method does not work and is not used.
   *
   * @param x                    The x position of the start point of the perpendicular line.
   * @param y                    The y position of the start point of the perpendicular line.
   * @param xInitial             The x position of the start point of the line.
   * @param yInitial             The x position of the start point of the line.
   * @param xVector              The x component of the distance vector of the line.
   * @param yVector              The y component of the distance vector of the line.
   * @param xPerpendicularVector The x component of the distance vector of the perpendicular line.
   * @param yPerpendicularVector The y component of the distance vector of the perpendicular line.
   * @param sxPerpendicular      The x component of the direction vector of the perpendicular line.
   * @param syPerpendicular      The y component of the direction vector of the perpendicular line.
   * @param grid                 The grid to use for drawing.
   */
  private static void drawPerpendicularLines(int x, int y, int xInitial, int yInitial, double xVector, double yVector,
                                             double xPerpendicularVector, double yPerpendicularVector,
                                             int sxPerpendicular, int syPerpendicular, Drawable grid) {
    final double[] projection = projection(
        new double[]{xInitial, yInitial},
        new double[]{xVector, yVector},
        new double[]{x, y}
    );

    final double
        dxProjection = x - projection[0],
        dyProjection = y - projection[1],

        xPerpendicularVectorCorrected1 = xPerpendicularVector - dxProjection,
        yPerpendicularVectorCorrected1 = yPerpendicularVector - dyProjection,

        xPerpendicularVectorCorrected2 = xPerpendicularVector + dxProjection,
        yPerpendicularVectorCorrected2 = yPerpendicularVector + dyProjection;

    bresenhamIntern(x, y, xPerpendicularVectorCorrected1, yPerpendicularVectorCorrected1, sxPerpendicular, syPerpendicular, 0, grid);
    bresenhamIntern(x, y, xPerpendicularVectorCorrected2, yPerpendicularVectorCorrected2, -sxPerpendicular, -syPerpendicular, 0, grid);
  }

  /**
   * Calculates the projection point of the point {@code x} on the line defined by {@code r} and {@code u}. All arrays
   * are assumed to have two components.
   *
   * @param r The start vector of the line.
   * @param u The direction of the line.
   * @param x The point to project from.
   * @return The components of the projected point.
   */
  public static double[] projection(double[] r, double[] u, double[] x) {
    return new double[]{
        r[0] + ((x[0] - r[0]) * u[0] + (x[1] - r[1]) * u[1]) / (pow(u[0], 2) + pow(u[1], 2)) * u[0],
        r[1] + ((x[0] - r[0]) * u[0] + (x[1] - r[1]) * u[1]) / (pow(u[0], 2) + pow(u[1], 2)) * u[1]
    };
  }
}