/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.cli.adapter;

import de.mtneug.maze_cli.annotations.AlgorithmAdapter;
import de.mtneug.maze_cli.cli.adapter.algorithms.AbstractMazeAlgorithmCliAdapter;
import de.mtneug.maze_cli.util.AnnotationHelper;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Registry of all CLI arguments to maze generation algorithm adapter. This is a singleton. To get an instance use
 * {@link #getInstance()}.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class MazeAlgorithmCliAdapterRegistry {
  /**
   * Singleton instance.
   */
  private final static MazeAlgorithmCliAdapterRegistry INSTANCE = new MazeAlgorithmCliAdapterRegistry();

  /**
   * Map of adapter names to adapter instances.
   */
  private final Map<String, AbstractMazeAlgorithmCliAdapter> adapter = new LinkedHashMap<>();

  /**
   * Private singleton constructor. It start an initial search.
   */
  private MazeAlgorithmCliAdapterRegistry() {
    search();
  }

  /**
   * Get the singleton instance.
   *
   * @return The singleton instance of this class.
   */
  public static MazeAlgorithmCliAdapterRegistry getInstance() {
    return INSTANCE;
  }

  /**
   * Searches for adapter annotated as maze generation algorithm.
   */
  public void search() {
    adapter.clear();

    try {
      adapter.putAll(AnnotationHelper.searchAndInstantiate(AlgorithmAdapter.class, "name", String.class, AbstractMazeAlgorithmCliAdapter.class));
    } catch (Exception e) {
      // Ignore errors
    }
  }

  /**
   * Returns the maze generation algorithm adapter with the given {@code name}.
   *
   * @param name The name of the maze generation algorithm adapter.
   * @return The maze generation algorithm adapter.
   */
  public AbstractMazeAlgorithmCliAdapter getAdapter(String name) {
    return adapter.get(name);
  }
}
