/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.graphics;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Component adapter to force a given aspect ratio on that component.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public class FixAspectRatioComponentAdapter extends ComponentAdapter {
  /**
   * The forced aspect ration.
   */
  private final double aspectRatio;

  /**
   * The width of the component before resizing.
   */
  private int oldWidth = -1;

  /**
   * The height of the component before resizing.
   */
  private int oldHeight = -1;

  /**
   * The constructor.
   *
   * @param aspectRatio The forced aspect ration.
   */
  public FixAspectRatioComponentAdapter(double aspectRatio) {
    this.aspectRatio = aspectRatio;
  }

  /**
   * Called when a resize happens.
   *
   * @param e The event object.
   */
  @Override
  public void componentResized(ComponentEvent e) {
    int correctedWidth, correctedHeight;

    final int newWidth = correctedWidth = e.getComponent().getWidth();
    final int newHeight = correctedHeight = e.getComponent().getHeight();

    final boolean widthChanged = (newWidth != oldWidth);
    final boolean heightChanged = (newHeight != oldHeight);

    if (!widthChanged && heightChanged)
      correctedWidth = (int) (newHeight * aspectRatio);
    else
      correctedHeight = (int) (newWidth * (1 / aspectRatio));

    Rectangle r = e.getComponent().getBounds();
    e.getComponent().setBounds(r.x, r.y, correctedWidth, correctedHeight);

    oldWidth = correctedWidth;
    oldHeight = correctedHeight;
  }
}
