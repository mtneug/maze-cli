/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.cli.adapter;

import de.mtneug.maze_cli.annotations.Output;
import de.mtneug.maze_cli.cli.adapter.outputs.AbstractMazeOutputCliAdapter;
import de.mtneug.maze_cli.util.AnnotationHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry of all CLI arguments to maze output adapter. This is a singleton. To get an instance use
 * {@link #getInstance()}.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class MazeOutputCliAdapterRegistry {
  /**
   * Singleton instance.
   */
  private final static MazeOutputCliAdapterRegistry INSTANCE = new MazeOutputCliAdapterRegistry();

  /**
   * Map of adapter names to adapter instances.
   */
  private final Map<String, AbstractMazeOutputCliAdapter> adapter = new HashMap<>();

  /**
   * Private singleton constructor. It start an initial search.
   */
  private MazeOutputCliAdapterRegistry() {
    search();
  }

  /**
   * Get the singleton instance.
   *
   * @return The singleton instance of this class.
   */
  public static MazeOutputCliAdapterRegistry getInstance() {
    return INSTANCE;
  }

  /**
   * Searches for adapter annotated as maze outputs.
   */
  public void search() {
    adapter.clear();

    try {
      adapter.putAll(AnnotationHelper.searchAndInstantiate(Output.class, "name", String.class, AbstractMazeOutputCliAdapter.class));
    } catch (Exception e) {
      // Ignore errors
    }
  }

  /**
   * Returns the maze output adapter with the given {@code name}.
   *
   * @param name The name of the maze output adapter.
   * @return The maze output adapter.
   */
  public AbstractMazeOutputCliAdapter getAdapter(String name) {
    return adapter.get(name);
  }
}
