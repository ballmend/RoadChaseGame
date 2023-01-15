package de.hhn.it.pp.components.roadchase.providers.settings;

import de.hhn.it.pp.components.roadchase.Difficulty;

/**
 * Settings submenu for adjusting game aspects like the difficulty.
 */
public class Game {

  private static Difficulty difficulty = Difficulty.EASY;

  public static void setDifficulty(Difficulty difficulty) {
    Game.difficulty = difficulty;
  }

  public static Difficulty getDifficulty() {
    return difficulty;
  }

}
