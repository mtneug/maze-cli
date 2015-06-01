/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.outputs;

import de.mtneug.maze_cli.graphics.FixAspectRatioComponentAdapter;
import de.mtneug.maze_cli.graphics.MazePanel;
import de.mtneug.maze_cli.model.Maze;
import de.mtneug.maze_cli.model.MazeSolutions;

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
   * @param mazeSolutions The maze and solutions to output.
   */
  public GuiOutput(MazeSolutions mazeSolutions) {
    super(mazeSolutions);
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
    frame.addComponentListener(new FixAspectRatioComponentAdapter(
        (double) mazeSolutions.getMaze().getWidth() / (double) mazeSolutions.getMaze().getHeight())
    );

    // create maze panel
    // TODO: make panel more like AbstractGraphicOutput
    MazePanel mazePanel = new MazePanel(mazeSolutions.getMaze());
    if (mazeSolutions.hasSolution())
      mazePanel.addAdditionallyMarkedCells(mazeSolutions.getSolutions().get(0).getPathCells());

    // add the maze panel to the frame
    frame.setLayout(new BorderLayout());
    frame.add(mazePanel, BorderLayout.CENTER);
    frame.pack();

    // center window on the screen
    frame.setLocationRelativeTo(null);

    // terminate on closing and show the window
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setVisible(true);

    return null;
  }
}
