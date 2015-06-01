/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.cli.adapter;

import de.mtneug.maze_cli.annotations.CliCommand;
import de.mtneug.maze_cli.cli.commands.AbstractCliCommand;
import de.mtneug.maze_cli.util.AnnotationHelper;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Registry of all CLI commands. This is a singleton. To get an instance use {@link #getInstance()}.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class CliCommandRegistry {
  /**
   * Singleton instance.
   */
  private final static CliCommandRegistry INSTANCE = new CliCommandRegistry();

  /**
   * Map of adapter names to adapter instances.
   */
  private final Map<String, Class<? extends AbstractCliCommand>> adapter = new LinkedHashMap<>();

  /**
   * Private singleton constructor. It start an initial search.
   */
  private CliCommandRegistry() {
    search();
  }

  /**
   * Get the singleton instance.
   *
   * @return The singleton instance of this class.
   */
  public static CliCommandRegistry getInstance() {
    return INSTANCE;
  }

  /**
   * Searches for CLI commands.
   */
  public void search() {
    adapter.clear();

    try {
      adapter.putAll(AnnotationHelper.search(CliCommand.class, "name", String.class, AbstractCliCommand.class));
    } catch (Exception e) {
      // Ignore errors
    }
  }

  /**
   * Returns the CLI command class with the given {@code name}.
   *
   * @param name The name of the command.
   * @return The command class.
   */
  public Class<? extends AbstractCliCommand> getCommand(String name) {
    return adapter.get(name);
  }
}
