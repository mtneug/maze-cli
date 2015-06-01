/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.solvers;

import de.mtneug.maze_cli.annotations.Solver;
import de.mtneug.maze_cli.model.*;
import de.mtneug.maze_cli.util.Lists;

import java.util.*;

/**
 * Implementation of Trémaux’ method for solving mazes.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
@Solver(name = "tremaux")
public class TremauxMazeSolverAlgorithm extends AbstractMazeSolverAlgorithm {
  /**
   * TODO: make more general
   */
  protected final Random random = new Random();

  /**
   * The found solution.
   */
  private final SimpleCorrectPath solution;

  /**
   * A map of encountered places with there cells.
   */
  private final Map<Cell, Place> places = new LinkedHashMap<>();

  /**
   * The constructor.
   *
   * @param maze The maze to solve.
   */
  public TremauxMazeSolverAlgorithm(Maze maze) {
    super(maze);
    // Trémaux’ method finds only one solution, if it exists
    this.solution = new SimpleCorrectPath(maze);
  }

  /**
   * Code of the Trémaux’ method for solving mazes. A description of followed steps can be found
   * <a href="http://de.wikipedia.org/wiki/Tr%C3%A9maux%E2%80%99_Methode#Regeln">here</a>.
   *
   * @param maze      The maze to solve.
   * @param solutions The list to which the found solutions should be added.
   */
  @Override
  public void solve(Maze maze, List<SimpleCorrectPath> solutions) {
    solution.addCell(maze.getStartCell());

    if (handlePlace(maze, maze.getStartCell(), null, solution))
      solutions.add(solution);
  }

  /**
   * Code to handle an encountered place.
   *
   * @param maze       The maze to solve.
   * @param cell       The cell the place is based of.
   * @param comingFrom The direction we have entered the place from. {@code null} for the initial start place.
   * @param path       The current sub path.
   * @return {@code true} if this place is part of the solution, {@code false} if not. So if {@code cell} is the start
   * cell of the maze and {@code false} is returned, the maze has no solution.
   */
  private boolean handlePlace(Maze maze, Cell cell, Direction comingFrom, SimpleCorrectPath path) {
    boolean isNewPlace = !places.containsKey(cell);

    if (isNewPlace)
      places.put(cell, new Place(cell));

    final Place place = places.get(cell);

    if (comingFrom != null)
      // mark as seen from that direction
      place.addSeen(comingFrom);

    if (place.getLowestSeenValue() >= 2)
      // place has no other direction we have not seen at least twice
      // this place is not in the solution
      return false;

    if (!isNewPlace && comingFrom != null && place.getSeenValueOf(comingFrom) == 1) {
      // place has been visited before, but not via this direction => go back and mark as seen again
      place.addSeen(comingFrom);
      return false;
    }

    if (cell.getLinkedDirections().isEmpty())
      // it is not possible to go anywhere from this cell
      // this can only happen, if this is the start cell
      return false;

    // being here means that there are still directions we can follow

    // choose direction to follow
    final SimpleCorrectPath subPath = new SimpleCorrectPath(maze);
    final Direction chosenDirection = Lists.chooseRandomItem(place.getLeastSeenDirections(), random);
    place.addSeen(chosenDirection);

    if (follow(maze, cell, chosenDirection, subPath)) {
      // the direction did lead to the end
      path.merge(subPath);
      return true;
    } else
      // the direction did not lead to the end
      return handlePlace(maze, cell, comingFrom, path);
  }

  /**
   * Follows {@code currentCell} in the the initially given {@code currentDirection} and thus builds up {@code path},
   * until it encounters a place or the end cell of the maze. {@code currentCell} is not part of the {@code path}, the
   * found place or the end cell is.
   *
   * @param maze             The maze to solve.
   * @param currentCell      The cell to start for following.
   * @param currentDirection The initial direction to follow.
   * @param path             The path to build.
   * @return {@code true} if {@code path} leads to the end cell, {@code false} otherwise.
   */
  private boolean follow(Maze maze, Cell currentCell, Direction currentDirection, SimpleCorrectPath path) {
    currentCell = currentCell.getNeighborPositioned(currentDirection);
    path.addCell(currentCell);

    while (true) {
      if (currentCell.equals(maze.getEndCell()))
        // found end cell
        return true;

      // otherwise look how to go from here
      final Set<Direction> directions = currentCell.getLinkedDirections();
      switch (directions.size()) {
        case 0:
          // how did we got here if there is no direction we can follow from here?
          throw new IllegalStateException("the maze is not properly instantiated");

        case 1:
          // dead end => not part of the solution
          return false;

        case 2:
          // find the other direction we can follow
          for (Direction direction : directions)
            if (currentDirection != direction.getOpposite()) {
              currentDirection = direction;
              currentCell = path.goAndAdd(currentDirection);
              break;
            }
          break;

        default:
          // it is a place
          return handlePlace(maze, currentCell, currentDirection.getOpposite(), path);
      }
    }
  }
}
