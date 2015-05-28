/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.cli.commands;

import de.mtneug.maze_cli.algorithms.AbstractMazeAlgorithm;
import de.mtneug.maze_cli.annotations.Command;
import de.mtneug.maze_cli.cli.adapter.MazeAlgorithmCliAdapterRegistry;
import de.mtneug.maze_cli.cli.adapter.MazeOutputCliAdapterRegistry;
import de.mtneug.maze_cli.cli.adapter.algorithms.AbstractMazeAlgorithmCliAdapter;
import de.mtneug.maze_cli.cli.adapter.outputs.AbstractMazeOutputCliAdapter;
import de.mtneug.maze_cli.exception.CliArgumentException;
import de.mtneug.maze_cli.exception.MazeAlgorithmNotFoundException;
import de.mtneug.maze_cli.exception.MazeOutputNotFoundException;
import de.mtneug.maze_cli.model.Maze;
import de.mtneug.maze_cli.outputs.AbstractMazeOutput;

import java.util.List;

/**
 * CLI command to generate a maze and output it somehow.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
@Command(name = "generate")
public class GenerateCommand extends AbstractCliCommand {
  /**
   * Index of the dimension argument.
   */
  public final static int ARGUMENTS_DIMENSION_INDEX = 0;

  /**
   * Index of the algorithm argument.
   */
  public final static int ARGUMENTS_ALGORITHM_INDEX = 1;

  /**
   * Start index of the output argument.
   */
  public final static int ARGUMENTS_OUTPUT_START_INDEX = 2;

  /**
   * Number of arguments needed.
   */
  public final static int NUMBER_OF_NEEDED_ARGUMENTS = 3;

  /**
   * Index of the output argument.
   */
  public int arguments_output_index;

  /**
   * The constructor.
   *
   * @param args List of arguments.
   */
  public GenerateCommand(List<String> args) {
    super(args);
  }

  /**
   * Call the command.
   *
   * @return The returned object of the output.
   * @throws Exception
   */
  @Override
  public Object call() throws Exception {
    if (arguments.size() < NUMBER_OF_NEEDED_ARGUMENTS)
      throw new CliArgumentException("Not all arguments were specified");

    arguments_output_index = findOutputArgument();

    return getOutput(getMaze());
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
   * Creates the maze generation algorithm and generates a maze.
   *
   * @return The generated maze.
   * @throws Exception
   */
  private Maze getMaze() throws Exception {
    String algorithmName = arguments.get(ARGUMENTS_ALGORITHM_INDEX).toLowerCase();
    AbstractMazeAlgorithmCliAdapter adapter = MazeAlgorithmCliAdapterRegistry.getInstance().getAdapter(algorithmName);

    if (adapter == null)
      throw new MazeAlgorithmNotFoundException("The algorithm " + algorithmName + " could not be found");

    List<String> algoOutArgs = arguments.subList(ARGUMENTS_ALGORITHM_INDEX + 1, arguments_output_index);
    AbstractMazeAlgorithm algorithm = adapter.generate(algoOutArgs, parseDimensions());
    return algorithm.call();
  }

  /**
   * Parses the dimension argument.
   *
   * @return The parsed dimensions of the maze.
   */
  private Object[] parseDimensions() {
    String[] dimensionsStr = arguments.get(ARGUMENTS_DIMENSION_INDEX).split(":");

    if (dimensionsStr.length != 2)
      throw new CliArgumentException("Dimensions must be specified in this form: WIDTH:HEIGHT");

    Integer[] dimensions = new Integer[2];

    dimensions[0] = Integer.parseInt(dimensionsStr[0]);
    dimensions[1] = Integer.parseInt(dimensionsStr[1]);

    return dimensions;
  }

  /**
   * Creates the maze output and passes the given {@code maze} to it.
   *
   * @param maze The maze to output.
   * @return The returned output.
   * @throws Exception
   */
  private Object getOutput(Maze maze) throws Exception {
    String outputName = arguments.get(arguments_output_index).toLowerCase();
    AbstractMazeOutputCliAdapter adapter = MazeOutputCliAdapterRegistry.getInstance().getAdapter(outputName);

    if (adapter == null)
      throw new MazeOutputNotFoundException("The output " + outputName + " could not be found");

    List<String> algoOutArgs = arguments.subList(arguments_output_index + 1, arguments.size());
    AbstractMazeOutput output = adapter.generate(algoOutArgs, maze);
    return output.call();
  }

  /**
   * Prints how to use this command.
   */
  @Override
  public void printUsage() {
    System.out.println(
        "Usage maze.jar generate WIDTH:HEIGHT ALGO [ALGO-ARGS...] OUTPUT [OUTPUT-ARGS...]\n" +
            "\n" +
            "    WIDTH:\t\tThe width of the maze\n" +
            "    HEIGHT:\t\tThe height of the maze\n" +
            "    ALGO:\t\tThe algorithm to use\n" +
            "    OUTPUT:\t\tHow to output the maze"
    );
  }
}
