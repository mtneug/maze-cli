/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.cli.adapter.outputs;

import de.mtneug.maze_cli.cli.adapter.AbstractCliAdapter;
import de.mtneug.maze_cli.exception.MazeSolverNotFoundException;
import de.mtneug.maze_cli.model.Maze;
import de.mtneug.maze_cli.model.MazeSolutions;
import de.mtneug.maze_cli.outputs.AbstractMazeOutput;
import de.mtneug.maze_cli.solvers.AbstractMazeSolverAlgorithm;
import de.mtneug.maze_cli.solvers.MazeSolverClassRegistry;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

import java.lang.reflect.Constructor;

import static org.apache.commons.cli.PatternOptionBuilder.STRING_VALUE;

/**
 * Common CLI to maze output adapter.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractMazeOutputCliAdapter extends AbstractCliAdapter<AbstractMazeOutput> {
  /**
   * The name of the default solver.
   */
  public static final String DEFAULT_SOLVER_NAME = "none";

  /**
   * Code to instantiate a new maze output configured with the given parameters.
   *
   * @param commandLine Parsed CLI arguments.
   * @param obj         Needs to include a maze as first parameter.
   * @return A maze output.
   * @throws ParseException
   */
  @Override
  public AbstractMazeOutput doGenerate(CommandLine commandLine, Object... obj) throws ParseException {
    if (obj.length < 1)
      throw new IllegalArgumentException("a maze need to be passed, too");

    return doGenerate(createMazeSolutions((Maze) obj[0], commandLine), commandLine);
  }

  /**
   * Code to instantiate a new maze output configured with the given parameters.
   *
   * @param mazeSolutions The maze and its solution(s) to output.
   * @param commandLine   Parsed CLI arguments.
   * @return A maze output.
   * @throws ParseException
   */
  public abstract AbstractMazeOutput doGenerate(MazeSolutions mazeSolutions, CommandLine commandLine) throws ParseException;

  /**
   * Finds the solution with the given solver or with the default "none" solver.
   *
   * @param maze        The maze to solve.
   * @param commandLine Parsed CLI arguments.
   * @return The maze solutions object.
   */
  protected MazeSolutions createMazeSolutions(Maze maze, CommandLine commandLine) {
    String solverName = DEFAULT_SOLVER_NAME;

    if (commandLine.hasOption("solver-name"))
      solverName = commandLine.getOptionValue("solver-name");

    try {
      return doCreateMazeSolutions(maze, solverName);
    } catch (Exception e) {
      System.err.println("solver returned with an error");
      System.err.println("trying again with the " + DEFAULT_SOLVER_NAME + " solver");
      System.err.println(e.getMessage());
      e.printStackTrace();

      try {
        return doCreateMazeSolutions(maze, DEFAULT_SOLVER_NAME);
      } catch (Exception e1) {
        // the none solver throws no exceptions
        throw new IllegalStateException("solver returned again with an error");
      }
    }
  }

  /**
   * Solves the {@code maze} with a solver named {@code solverName}.
   *
   * @param maze       The maze to solve.
   * @param solverName The name of the solver to use for solving the maze.
   * @return The maze solutions object.
   * @throws MazeSolverNotFoundException if the solver does not exists.
   * @throws Exception                   if the solver throws an exception.
   */
  private MazeSolutions doCreateMazeSolutions(Maze maze, String solverName) throws Exception {
    Class<? extends AbstractMazeSolverAlgorithm> solverClass = MazeSolverClassRegistry.getInstance().getClass(solverName);

    if (solverClass == null)
      throw new MazeSolverNotFoundException("The solver " + solverName + " could not be found");

    Constructor<? extends AbstractMazeSolverAlgorithm> solverConstructor = solverClass.getConstructor(Maze.class);
    AbstractMazeSolverAlgorithm solver = solverConstructor.newInstance(maze);

    return solver.call();
  }

  /**
   * Add additional options.
   */
  @Override
  protected void buildOptions() {
    super.buildOptions();

    options.addOption(Option.builder("s")
            .longOpt("solver-name")
            .desc("use SOLVER to solve the maze")
            .hasArg().numberOfArgs(1).argName("SOLVER").type(STRING_VALUE)
            .build()
    );
  }
}
