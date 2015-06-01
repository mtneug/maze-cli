/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.cli.commands;

import de.mtneug.maze_cli.annotations.CliCommand;
import de.mtneug.maze_cli.cli.adapter.MazeAlgorithmCliAdapterRegistry;
import de.mtneug.maze_cli.cli.adapter.MazeOutputCliAdapterRegistry;
import de.mtneug.maze_cli.cli.adapter.algorithms.AbstractMazeAlgorithmCliAdapter;
import de.mtneug.maze_cli.cli.adapter.outputs.AbstractMazeOutputCliAdapter;
import de.mtneug.maze_cli.exception.CliArgumentException;
import de.mtneug.maze_cli.exception.MazeAlgorithmNotFoundException;
import de.mtneug.maze_cli.exception.MazeOutputNotFoundException;
import de.mtneug.maze_cli.model.Maze;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * CLI command to generate multiple mazes and output them somehow.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
@CliCommand(name = "multi-generate")
public class MultiGenerateCommand extends AbstractCliCommand {
  /**
   * Index of the times argument.
   */
  public final static int ARGUMENTS_TIMES_INDEX = 0;

  /**
   * Index of the dimension argument.
   */
  public final static int ARGUMENTS_DIMENSION_INDEX = 1;

  /**
   * Index of the algorithm argument.
   */
  public final static int ARGUMENTS_ALGORITHM_INDEX = 2;

  /**
   * Start index of the output argument.
   */
  public final static int ARGUMENTS_OUTPUT_START_INDEX = 3;

  /**
   * Number of arguments needed.
   */
  public final static int NUMBER_OF_NEEDED_ARGUMENTS = 4;

  /**
   * Index of the output argument.
   */
  private int arguments_output_index;

  /**
   * Whether to print "." or "F" when a generation is complete.
   */
  private boolean printingProgress = true;

  /**
   * The constructor.
   *
   * @param args List of arguments.
   */
  public MultiGenerateCommand(List<String> args) {
    super(args);
  }

  /**
   * Call the command.
   *
   * @return {@code null}
   * @throws Exception
   */
  @Override
  public Object call() throws Exception {
    if (arguments.size() < NUMBER_OF_NEEDED_ARGUMENTS)
      throw new CliArgumentException("Not all arguments were specified");

    arguments_output_index = findOutputArgument();

    // parse dimensions and times
    final int times = parseTimes();
    final Object[] dimensions = parseDimensions();

    // find adapter
    final AbstractMazeAlgorithmCliAdapter mazeAlgorithmAdapter = getMazeAlgorithmAdapter();
    final AbstractMazeOutputCliAdapter mazeOutputAdapter = getMazeOutputAdapter();

    // separate arguments for generator and output
    final List<String> mazeAlgorithmArgs = arguments.subList(ARGUMENTS_ALGORITHM_INDEX + 1, arguments_output_index);
    final List<String> mazeOutputArgs = arguments.subList(arguments_output_index + 1, arguments.size());

    // thread data structures
    final List<Callable<Object>> callableList = new LinkedList<>();
    final ExecutorService executorService = Executors.newWorkStealingPool();

    // create callables
    for (int i = 0; i < times; i++)
      callableList.add(new Callable<Object>() {
        @Override
        public Object call() throws Exception {
          try {
            Maze maze = mazeAlgorithmAdapter.generate(mazeAlgorithmArgs, dimensions).call();
            Object output = mazeOutputAdapter.generate(mazeOutputArgs, maze).call();

            if (printingProgress)
              System.out.print(".");

            return output;
          } catch (Exception e) {
            if (printingProgress)
              System.out.print("F");

            throw e;
          }
        }
      });

    // run and checks for possible Exceptions
    for (Future<Object> future : executorService.invokeAll(callableList))
      future.get();

    // new line
    System.out.println();

    return null;
  }

  /**
   * Parses the times argument.
   *
   * @return The parsed times argument.
   */
  private int parseTimes() {
    int times;

    try {
      times = Integer.parseInt(arguments.get(ARGUMENTS_TIMES_INDEX));
    } catch (Exception e) {
      throw new CliArgumentException("times must be an integer");
    }

    if (times < 1)
      throw new CliArgumentException("times must be positive");

    return times;
  }

  /**
   * Parses the dimension argument.
   *
   * @return The parsed dimensions of the maze.
   */
  private Object[] parseDimensions() {
    String[] dimensionsStr = arguments.get(ARGUMENTS_DIMENSION_INDEX).split(":");

    if (dimensionsStr.length != 2)
      throw new CliArgumentException("dimensions must be specified in this form: WIDTH:HEIGHT");

    Integer[] dimensions = new Integer[2];

    dimensions[0] = Integer.parseInt(dimensionsStr[0]);
    dimensions[1] = Integer.parseInt(dimensionsStr[1]);

    return dimensions;
  }

  /**
   * Finds the index of the output argument.
   *
   * @return The index of the output argument.
   */
  private int findOutputArgument() {
    for (int i = ARGUMENTS_OUTPUT_START_INDEX; i < arguments.size(); i++)
      if (!arguments.get(i).startsWith("-"))
        return i;

    throw new CliArgumentException("Not all arguments were specified");
  }

  /**
   * Returns the maze generation algorithm adapter.
   *
   * @return The maze generation algorithm adapter.
   * @throws Exception
   */
  private AbstractMazeAlgorithmCliAdapter getMazeAlgorithmAdapter() throws Exception {
    String algorithmName = arguments.get(ARGUMENTS_ALGORITHM_INDEX).toLowerCase();
    AbstractMazeAlgorithmCliAdapter adapter = MazeAlgorithmCliAdapterRegistry.getInstance().getAdapter(algorithmName);

    if (adapter == null)
      throw new MazeAlgorithmNotFoundException("The algorithm " + algorithmName + " could not be found");

    return adapter;
  }

  /**
   * Returns the maze output adapter.
   *
   * @return The maze output adapter.
   * @throws Exception
   */
  private AbstractMazeOutputCliAdapter getMazeOutputAdapter() throws Exception {
    String outputName = arguments.get(arguments_output_index).toLowerCase();
    AbstractMazeOutputCliAdapter adapter = MazeOutputCliAdapterRegistry.getInstance().getAdapter(outputName);

    if (adapter == null)
      throw new MazeOutputNotFoundException("The output " + outputName + " could not be found");

    return adapter;
  }

  /**
   * Returns whether "." or "F" will be printed after each generated and outputed maze.
   *
   * @return {@code true} if it will be printed, {@code false} otherwise.
   */
  public boolean isPrintingProgress() {
    return printingProgress;
  }

  /**
   * Set whether "." or "F" will be printed after each generated and outputed maze.
   *
   * @param printingProgress Whether it to be printed.
   */
  public void setPrintingProgress(boolean printingProgress) {
    this.printingProgress = printingProgress;
  }

  /**
   * Prints how to use this command.
   */
  @Override
  public void printUsage() {
    System.out.println(
        "Usage maze multi-generate TIMES WIDTH:HEIGHT ALGO [ALGO-ARGS...] OUTPUT [OUTPUT-ARGS...]\n" +
            "\n" +
            "    TIMES:\t\tHow many times the maze generator algorithm should be run\n" +
            "    WIDTH:\t\tThe width of the maze\n" +
            "    HEIGHT:\t\tThe height of the maze\n" +
            "    ALGO:\t\tThe algorithm to use\n" +
            "    OUTPUT:\t\tHow to output the maze"
    );
  }
}
