/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.outputs;

import de.mtneug.maze_cli.graphics.FixAspectRatioComponentAdapter;
import de.mtneug.maze_cli.graphics.MazePanel;
import de.mtneug.maze_cli.model.Maze;

import javax.swing.*;
import java.awt.*;

/**
 * Maze output, which displays the maze in a GUI window.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class GuiOutput extends AbstractMazeOutput {
  /**
   * The constructor.
   *
   * @param maze The maze to output.
   */
  public GuiOutput(Maze maze) {
    super(maze);
  }

  /**
   * Outputs the given maze.
   *
   * @return {@code null}
   */
  @Override
  public Object call() throws Exception {
    JFrame frame = new JFrame("Maze");

    // force the aspect ratio of the maze
    frame.addComponentListener(new FixAspectRatioComponentAdapter((double) maze.getWidth() / (double) maze.getHeight()));

    // add a maze panel
    frame.setLayout(new BorderLayout());
    frame.add(new MazePanel(maze), BorderLayout.CENTER);
    frame.pack();

    // center window on the screen
    frame.setLocationRelativeTo(null);

    // terminate on closing and show the window
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setVisible(true);

    return null;
  }
}
