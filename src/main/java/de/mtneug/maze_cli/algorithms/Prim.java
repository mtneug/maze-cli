/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.algorithms;

import de.mtneug.maze_cli.model.Cell;
import de.mtneug.maze_cli.model.Direction;
import de.mtneug.maze_cli.model.Wall;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Implementation of the Prim algorithm for maze generation. Loosely based on the
 * <a hreg="http://en.wikipedia.org/wiki/Maze_generation_algorithm#Randomized_Prim.27s_algorithm">Wikipedia
 * description.</a>
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @see AbstractIterativeMazeAlgorithm
 * @see AbstractMazeAlgorithm
 * @since 1.0
 */
public class Prim extends AbstractIterativeMazeAlgorithm {
  /**
   * String instance that is used to mark the visited maze cells.
   */
  public final static String MARK = "visited";

  /**
   * The name of the algorithm.
   */
  public static final String NAME = "prim";

  /**
   * The set of walls i.e. cell and associated direction, which still needs to be looked at.
   */
  private final Set<Wall> wallSet = new LinkedHashSet<>();

  /**
   * The constructor.
   *
   * @param width  The width of the maze to generate.
   * @param height The height of the maze to generate.
   * @param random The random number generator object to use when creating the maze.
   */
  public Prim(int width, int height, Random random) {
    super(width, height, random);
  }

  /**
   * Code that runs before the loop. Here an initial cell is visited and
   */
  @Override
  protected void before() {
    // save all walls of a random cell
    saveWalls(output.getCell(
        random.nextInt(output.getWidth()),
        random.nextInt(output.getHeight())
    ));
  }

  /**
   * The loop of the Prim maze generation algorithm.
   */
  @Override
  protected boolean step() {
    if (wallSet.isEmpty())
      return false;

    final Wall[] walls = wallSet.toArray(new Wall[wallSet.size()]);
    final Wall wall = walls[random.nextInt(wallSet.size())];
    wallSet.remove(wall);

    final Direction direction = wall.direction;
    final Cell currentCell = wall.cell;
    final Cell neighbor = wall.cell.getNeighborPositioned(wall.direction);

    // If the cell on the opposite side isn't in the maze yet:
    if (!neighbor.getLabels().contains(MARK)) {
      // Make the wall a passage and mark the cell on the opposite side as part of the maze
      currentCell.link(direction);

      // Save all relevant walls
      saveWalls(neighbor);
    }

    return !wallSet.isEmpty();
  }

  /**
   * Saves all relevant walls of {@code cell}.
   *
   * @param cell The relevant cell.
   */
  private void saveWalls(Cell cell) {
    // Mark cell as visited
    cell.getLabels().add(MARK);

    // Add the neighboring walls of the cell to the wall list.
    for (Map.Entry<Direction, Cell> entry : cell.getNeighbors().entrySet())
      if (!entry.getValue().getLabels().contains(MARK))
        wallSet.add(new Wall(cell, entry.getKey()));
  }

  /**
   * Returns the name of the object.
   *
   * @return The name of the object.
   */
  @Override
  public String getName() {
    return NAME;
  }
}