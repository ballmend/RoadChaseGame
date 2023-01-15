package de.hhn.it.pp.components.roadchase;

import de.hhn.it.pp.components.exceptions.IllegalParameterException;
import java.util.ArrayList;

/**
 * Interface for Game.
 */
public interface RoadChaseService {

  /**
   * Initialize a new Game.
   *
   * @param difficult is the difficult
   * @throws IllegalParameterException if difficult is not valid
   */
  void initializeGame(Difficulty difficult) throws IllegalParameterException;

  /**
   * Changes the Sublevel.
   */
  void changeSublevel();

  /**
   * Change Game settings in Slow Motion Mode and copies the current Game settings.
   */
  void changeSublevelSlowMo();

  /**
   * Changes Game settings back to the copied Game settings.
   */
  void undoSloMoPowerup();

  /**
   * Calculate distance from game models in comparison to the player car.
   *
   * @param x11 x coordinate model 1
   * @param x12 x2 coordinate model 1
   * @param x21 x coordinate model 2
   * @param x22 x2 coordinate model 2
   * @return the calculated distance
   * @throws IllegalParameterException if parameter are not double
   */
  boolean calculateCollision(double x11, double x12, double x21, double x22)
      throws IllegalParameterException, NullPointerException;

  /**
   * Displays users Highscores after Gameover.
   *
   * @return a list with users local Highscores
   */
  ArrayList<Highscore> gethighscore();

  /**
   * if player can save Highscore.
   *
   * @return true: can save Highscore, false: can't save Highscore
   */
  boolean checkHighscore();

  /**
   * save new Highscore.
   *
   * @param name       of username
   * @param score      is the distance
   * @param difficulty is the gamemode
   * @throws IllegalParameterException if parameter are not valid
   */
  void updateHighscore(String name, int score, String difficulty) throws IllegalParameterException,
      NullPointerException;
}
