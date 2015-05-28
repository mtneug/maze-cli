/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli;

import de.mtneug.maze_cli.cli.Cli;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Entry point of this program.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class Main {
  /**
   * Main method.
   *
   * @param args CLI arguments.
   */
  public static void main(String[] args) {
    new Cli(new ArrayList<String>(Arrays.asList(args))).safeCall();
  }
}
