/*
 * Copyright (c) 2015. Matthias Neugebauer. All rights reserved.
 */

package de.mtneug.maze_cli.model;

/**
 * An enum defining three difficulty level.
 *
 * @author Matthias Neugebauer
 * @version 1.0
 * @since 1.0
 */
public enum DifficultyLevel {
  EASY, MEDIUM, HARD;

  /**
   * Returns the associated difficulty level with the given {@code difficulty}. It is assumed the {@code difficulty} is
   * a value between {@code 0.0} and {@code 1.0}.
   *
   * @param difficulty The difficulty.
   * @return The associated difficulty level.
   */
  public static DifficultyLevel getDifficultyLevel(double difficulty) {
    if (difficulty < 1.0 / 3.0)
      return EASY;
    else if (difficulty < 2.0 / 3.0)
      return MEDIUM;
    else
      return HARD;
  }

  /**
   * Returns the associated difficulty level with the given {@code difficulty}. It is assumed the {@code difficulty} is
   * at most {@code maximumDifficulty}.
   *
   * @param difficulty        The difficulty.
   * @param maximumDifficulty The maximum difficulty.
   * @return The associated difficulty level.
   */
  public static DifficultyLevel getDifficultyLevel(double difficulty, double maximumDifficulty) {
    return getDifficultyLevel(difficulty / maximumDifficulty);
  }

  /**
   * Less than comparison between this and {@code otherLevel}.
   *
   * @param otherLevel The other difficulty level.
   * @return {@code true} if this difficulty level is less than {@code otherLevel}, {@code false} otherwise.
   */
  public boolean isLessThan(DifficultyLevel otherLevel) {
    return this.ordinal() < otherLevel.ordinal();
  }

  /**
   * Less ot equal than comparison between this and {@code otherLevel}.
   *
   * @param otherLevel The other difficulty level.
   * @return {@code true} if this difficulty level is less ot equal than {@code otherLevel}, {@code false} otherwise.
   */
  public boolean isLessEqualThan(DifficultyLevel otherLevel) {
    return this.ordinal() <= otherLevel.ordinal();
  }

  /**
   * Equals comparison between this and {@code otherLevel}.
   *
   * @param otherLevel The other difficulty level.
   * @return {@code true} if this difficulty level equals {@code otherLevel}, {@code false} otherwise.
   */
  public boolean equals(DifficultyLevel otherLevel) {
    return this.ordinal() == otherLevel.ordinal();
  }

  /**
   * Bigger or equal than comparison between this and {@code otherLevel}.
   *
   * @param otherLevel The other difficulty level.
   * @return {@code true} if this difficulty level is bigger or equal than {@code otherLevel}, {@code false} otherwise.
   */
  public boolean isBiggerEqualThan(DifficultyLevel otherLevel) {
    return this.ordinal() >= otherLevel.ordinal();
  }

  /**
   * Bigger than comparison between this and {@code otherLevel}.
   *
   * @param otherLevel The other difficulty level.
   * @return {@code true} if this difficulty level is bigger than {@code otherLevel}, {@code false} otherwise.
   */
  public boolean isBiggerThan(DifficultyLevel otherLevel) {
    return this.ordinal() > otherLevel.ordinal();
  }
}