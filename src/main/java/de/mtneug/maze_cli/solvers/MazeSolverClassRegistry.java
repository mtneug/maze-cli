/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.solvers;

import de.mtneug.maze_cli.annotations.Solver;
import de.mtneug.maze_cli.util.AnnotationHelper;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Registry of all maze solver classes. This is a singleton. To get an instance use {@link #getInstance()}.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class MazeSolverClassRegistry {
  /**
   * Singleton instance.
   */
  private final static MazeSolverClassRegistry INSTANCE = new MazeSolverClassRegistry();

  /**
   * Map of names to instances.
   */
  private final Map<String, Class<? extends AbstractMazeSolverAlgorithm>> adapter = new LinkedHashMap<>();

  /**
   * Private singleton constructor. It start an initial search.
   */
  private MazeSolverClassRegistry() {
    search();
  }

  /**
   * Get the singleton instance.
   *
   * @return The singleton instance of this class.
   */
  public static MazeSolverClassRegistry getInstance() {
    return INSTANCE;
  }

  /**
   * Searches for classes annotated as maze solvers.
   */
  public void search() {
    adapter.clear();

    try {
      adapter.putAll(AnnotationHelper.search(Solver.class, "name", String.class, AbstractMazeSolverAlgorithm.class));
    } catch (Exception e) {
      // Ignore errors
    }
  }

  /**
   * Returns the maze solver class with the given {@code name}.
   *
   * @param name The name of the maze solver.
   * @return The maze solver class.
   */
  public Class<? extends AbstractMazeSolverAlgorithm> getClass(String name) {
    return adapter.get(name);
  }
}
