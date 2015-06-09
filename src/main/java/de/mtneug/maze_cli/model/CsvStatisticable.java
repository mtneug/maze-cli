/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.model;

/**
 * Type, which can return statistics about itself as CSV formatted string.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public interface CsvStatisticable {
  /**
   * Returns statistics about this object in a CSV formatted string.
   *
   * @return A CSV formatted string.
   */
  String getStatistics();
}
