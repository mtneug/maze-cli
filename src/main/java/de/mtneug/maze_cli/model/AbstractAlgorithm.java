/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.model;

import java.util.concurrent.Callable;

import static de.mtneug.maze_cli.model.AlgorithmState.*;

/**
 * Base class for all algorithm implementations. To create implement an algorithm, one first has to instantiate the
 * initial {@link #output} object in the constructor. The code for the algorithm itself goes into {@link #running()},
 * which will be called from {@link #call()}.
 *
 * @param <T> The type of the returned object.
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractAlgorithm<T> implements Callable<T>, Nameable, CsvStatisticable {
  /**
   * The current state the algorithm is in.
   */
  protected AlgorithmState state = NOT_STARTED;

  /**
   * The output of the algorithm.
   */
  protected T output;

  /**
   * The constructor.
   */
  public AbstractAlgorithm() {
  }

  /**
   * Start the algorithm.
   *
   * @return The generated output.
   * @throws Exception
   */
  @Override
  public T call() throws Exception {
    if (state != FINISHED) {
      state = RUNNING;
      running();
      state = FINISHED;
    }

    return output;
  }

  /**
   * Code of the algorithm.
   *
   * @throws Exception
   */
  protected abstract void running() throws Exception;

  /**
   * Returns the state the algorithm is currently in.
   *
   * @return The current state.
   */
  public AlgorithmState getState() {
    return state;
  }

  /**
   * Returns the output of the algorithm.
   *
   * @return The output of the algorithm.
   */
  public T getOutput() {
    return output;
  }

  /**
   * Returns statistics about this object in a CSV formatted string.
   *
   * @return A CSV formatted string.
   */
  @Override
  public String getStatistics() {
    return getName();
  }
}
