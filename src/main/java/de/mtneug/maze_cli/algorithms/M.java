/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.algorithms;

import com.google.common.collect.Sets;
import de.mtneug.maze_cli.model.*;

import java.util.*;

import static de.mtneug.maze_cli.model.DifficultyLevel.HARD;
import static de.mtneug.maze_cli.model.DifficultyLevel.MEDIUM;
import static de.mtneug.maze_cli.model.Orientation.HORIZONTAL;
import static de.mtneug.maze_cli.model.Orientation.VERTICAL;

/**
 * This maze generation algorithm tries to generate mazes with different difficulty levels by dividing the cells into a
 * passable and a non passable set. The sizes and the generation of the sets depends on the chosen difficulty level.
 * While the passable set can always the thought of a connected graph, the non passable set could be disconnected.
 * <p/>
 * In a second and third step a maze generation algorithm - in this case Prim - is run for the passable and non passable
 * set. For a human solver it so seems probably possible to reach every cell, while in fact the correct path is directed
 * through the passable set.
 * <p/>
 * This algorithm was developed by Matthias Neugebauer during the seminar "Procedural Content generation in Games" at
 * the University of Münster in the summer semester 2015. To which degree this method can fulfill the set goal, can be
 * read in the essay for this seminar.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @see AbstractMazeAlgorithm
 * @since 1.0
 */
public class M extends AbstractMazeAlgorithm {
  // TODO: tweak
  /**
   * The percentage of the shorter side the width of the path can be at maximum. This only applies up to a difficulty of
   * 99 since at 100 all cells are declared passable.
   */
  public final static double SHORTER_SIDE_PERCENTAGE = 1.5;

  /**
   * Cell distance between the initial segments of the path between the start and end cell. This also determines the
   * area a point of the path can be changed along the bigger side of the maze (only applicable if the difficulty level
   * is HARD).
   */
  public final static int DASH_DISTANCE = 20;

  /**
   * The percentage of the {@link #DASH_DISTANCE} defining the area a point of the path can be changed along the bigger
   * side of the maze (only applicable if the difficulty level is HARD).
   */
  public final static int DASH_DISTANCE_DELTA_FACTOR = 2;

  /**
   * The maximum difficulty.
   */
  public final static int MAXIMUM_DIFFICULTY = 100;

  /**
   * The difficulty.
   */
  private final double difficulty;

  /**
   * The set of all passable cells. The non passable cells are the difference between all cells and the passable cells.
   */
  private final Set<Cell> passableCellSet = new HashSet<>();

  /**
   * Whether to print out statistics about the generated maze.
   */
  private boolean printingStatistics;

  /**
   * The constructor.
   *
   * @param width              The width of the maze to generate.
   * @param height             The height of the maze to generate.
   * @param random             The random number generator object to use when creating the maze.
   * @param difficulty         The difficulty of the maze to generate. It should be a value between 0 and 100, where 0 is the
   * @param printingStatistics Whether to print out statistics about the generated maze.
   */
  public M(int width, int height, Random random, int difficulty, boolean printingStatistics) {
    super(width, height, random);

    if (difficulty < 0)
      this.difficulty = 0;
    else if (difficulty > MAXIMUM_DIFFICULTY)
      this.difficulty = MAXIMUM_DIFFICULTY;
    else
      this.difficulty = difficulty;

    this.printingStatistics = printingStatistics;
  }

  /**
   * Initializes the maze. This algorithm sets the start and end cell fix at {@code (0, 1)} and
   * {@code (width - 1, height - 2)} respectively.
   */
  @Override
  protected void prepareMaze() {
    super.prepareMaze();
    output.setStartCell(0, 1);
    output.setEndCell(output.getWidth() - 1, output.getHeight() - 2);
  }

  /**
   * The main method wrapping the steps for the generation of the maze.
   */
  @Override
  protected void running() {
    // 1. Generate area of cells, which is passable
    generatePassableCellSet();

    // 2. Run Prim on the passable area beginning with the start cell
    primOnCellSet(passableCellSet, output.getStartCell());

    // 3. Run Prim on the not passable area beginning with a random cell
    primOnCellSet(getNonPassableCells());

    // Print statistics if requested
    if (printingStatistics)
      printStatistics();
  }

  /**
   * The method responsible for generating a valid set of passable cells. The set is valid if the chosen cells could
   * build at least one path from the start to the end cell.
   * <p/>
   * This implementation is not optimized as it simply follows a written description of the algorithm. But thus it can
   * illustrate the method better.
   */
  private void generatePassableCellSet() {
    // On the hardest level, all area is passable
    if (difficulty == MAXIMUM_DIFFICULTY) {
      passableCellSet.addAll(output.getCells());
      return;
    }

    ///////
    // initialize local help variables
    final DifficultyLevel level = DifficultyLevel.getDifficultyLevel(difficulty, MAXIMUM_DIFFICULTY);
    final Orientation shorterSide = (output.getWidth() < output.getHeight())
        ? HORIZONTAL
        : VERTICAL;
    final int shorterSideLength = (output.getWidth() < output.getHeight())
        ? output.getWidth()
        : output.getHeight();

    ///////
    // 1. Get every DASH_DISTANCEs Cell on the path from start to end
    final AbstractCalculatedPath initialPath = new DashedCalculatedPath(output.getStartCell(), output.getEndCell(), output, 1, DASH_DISTANCE, false);
    initialPath.calculatePath();

    final List<Cell> pathCells = new ArrayList<>(initialPath.getPathCells());
    pathCells.remove(output.getStartCell());
    pathCells.remove(output.getEndCell());

    ///////
    // 2. Beginning at level MEDIUM, choose new random cells along the shorter side
    if (level.isBiggerEqualThan(MEDIUM)) {
      for (int i = 0; i < pathCells.size(); i++) {
        final Cell oldCell = pathCells.get(i);
        final int newPosition = random.nextInt(shorterSideLength);

        if (shorterSide == HORIZONTAL)
          pathCells.set(i, output.getCell(newPosition, oldCell.getPosition().y));
        else
          pathCells.set(i, output.getCell(oldCell.getPosition().x, newPosition));
      }
    }

    ///////
    // 3. Beginning at level HARD, choose new random cells along the longer side, within DASH_DISTANCE_DELTA_FACTOR * DASH_DISTANCE
    if (level.isBiggerEqualThan(HARD)) {
      for (int i = 0; i < pathCells.size(); i++) {
        final Cell oldCell = pathCells.get(i);
        final int deltaPosition = DASH_DISTANCE - random.nextInt(DASH_DISTANCE_DELTA_FACTOR * DASH_DISTANCE + 1);
        int newPosition;

        if (shorterSide == HORIZONTAL) {
          newPosition = oldCell.getPosition().y + deltaPosition;

          if (newPosition < 0)
            newPosition = 0;
          else if (newPosition >= output.getHeight())
            newPosition = output.getHeight() - 1;

          pathCells.set(i, output.getCell(oldCell.getPosition().x, newPosition));
        } else {
          newPosition = oldCell.getPosition().x + deltaPosition;

          if (newPosition < 0)
            newPosition = 0;
          else if (newPosition >= output.getWidth())
            newPosition = output.getWidth() - 1;

          pathCells.set(i, output.getCell(newPosition, oldCell.getPosition().y));
        }
      }
    }

    ///////
    // 4. Complete path cells with start and end
    pathCells.remove(output.getStartCell());
    pathCells.remove(output.getEndCell());

    pathCells.add(0, output.getStartCell());
    pathCells.add(output.getEndCell());

    ///////
    // 5. Build the path and add the cells to the passable cell set
    final double pathWidth = difficulty / MAXIMUM_DIFFICULTY * shorterSideLength * SHORTER_SIDE_PERCENTAGE;

    for (int i = 0; i < pathCells.size() - 1; i++) {
      final WidthCalculatedPath pathSegment = new WidthCalculatedPath(pathCells.get(i), pathCells.get(i + 1), output, pathWidth);
      pathSegment.calculatePath();
      passableCellSet.addAll(pathSegment.getAreaCells());
    }
  }

  /**
   * Runs the Prim maze generation algorithm in {@code cellSet}. A random cell is selected as starting point. The given
   * set can be empty, in which case nothing is done.
   *
   * @param cellSet The cell set.
   */
  private void primOnCellSet(Set<Cell> cellSet) {
    if (cellSet.size() > 0)
      primOnCellSet(
          cellSet,
          cellSet.toArray(new Cell[cellSet.size()])[random.nextInt(cellSet.size())]
      );
  }

  /**
   * Runs the Prim maze generation algorithm in {@code cellSet} with {@code initialCell} as starting point. The given
   * set can be empty, in which case nothing is done.
   *
   * @param cellSet     The cell set.
   * @param initialCell The starting point.
   */
  private void primOnCellSet(Set<Cell> cellSet, Cell initialCell) {
    final Set<Cell> notSeenCellSet = new HashSet<>(cellSet);
    final Set<Wall> wallSet = new HashSet<>();

    // Save all relevant walls of the initial cell
    saveWalls(initialCell, cellSet, notSeenCellSet, wallSet);

    while (!wallSet.isEmpty()) {
      final Wall[] walls = wallSet.toArray(new Wall[wallSet.size()]);
      final Wall wall = walls[random.nextInt(wallSet.size())];
      wallSet.remove(wall);

      final Direction direction = wall.direction;
      final Cell currentCell = wall.cell;
      final Cell neighbor = wall.cell.getNeighborPositioned(wall.direction);

      // If the cell on the opposite side isn't in the maze yet:
      if (notSeenCellSet.contains(neighbor)) {
        // Make the wall a passage and mark the cell on the opposite side as part of the maze.
        currentCell.link(direction);

        // Save all relevant walls
        saveWalls(neighbor, cellSet, notSeenCellSet, wallSet);
      }
    }

    if (notSeenCellSet.size() > 0)
      primOnCellSet(notSeenCellSet);
  }

  /**
   * Saves all relevant walls of {@code cell}.
   *
   * @param cell           The relevant cell.
   * @param cellSet        The set of all considered cells.
   * @param notSeenCellSet The set of all cells, which have not been seen yet.
   * @param wallSet        The set of all walls, which still needs to be looked at.
   */
  private void saveWalls(Cell cell, Set<Cell> cellSet, Set<Cell> notSeenCellSet, Set<Wall> wallSet) {
    // Mark cell as seen
    notSeenCellSet.remove(cell);

    // Add the neighboring walls of the cell to the wall list.
    for (Map.Entry<Direction, Cell> entry : cell.getNeighbors().entrySet())
      if (cellSet.contains(entry.getValue()) && notSeenCellSet.contains(entry.getValue()))
        wallSet.add(new Wall(cell, entry.getKey()));
  }

  /**
   * Prints statistics about the generated maze.
   */
  private void printStatistics() {
    System.out.println("" +

            // Dimensions
            output.getWidth() + "," +
            output.getHeight() + "," +

            // Number of all cells
            output.getWidth() * output.getHeight() + "," +

            // Number of passable cells
            getPassableCellSet().size() + "," +

            // Number of non-passable cells
            getNonPassableCells().size()
    );
  }

  /**
   * The difficulty of the maze to generate.
   *
   * @return The difficulty.
   */
  public double getDifficulty() {
    return difficulty;
  }

  /**
   * The set of cell, which are passable. They build a connected graph and include the start and end cell as well as at
   * least one path that is a solution between them.
   *
   * @return The passable cell set.
   */
  public Set<Cell> getPassableCellSet() {
    return passableCellSet;
  }

  /**
   * The set of cell, which are not passable. They could potentially build a disconnected graph.
   *
   * @return The non passable cell set.
   */
  public Set<Cell> getNonPassableCells() {
    return Sets.difference(new HashSet<Cell>(output.getCells()), passableCellSet);
  }

  /**
   * Check if statistics about the generated maze are printed to standard output.
   *
   * @return {@code true} if statistics are printed, {@code false} otherwise.
   */
  public boolean isPrintingStatistics() {
    return printingStatistics;
  }

  /**
   * Set whether statistics about the generated maze are printed to standard output.
   *
   * @param printingStatistics Whether to print out statistics.
   */
  public void setPrintingStatistics(boolean printingStatistics) {
    this.printingStatistics = printingStatistics;
  }
}
