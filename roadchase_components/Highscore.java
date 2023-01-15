package de.hhn.it.pp.components.roadchase;

import de.hhn.it.pp.components.roadchase.providers.settings.Game;

/**
 * Displays users local Highscore.
 */
public class Highscore {

  String no;
  String username;
  int distance;
  String difficulty;

  /**
   * Standard constructor for Highscore.
   *
   * @param username name of the player
   * @param distance how far you go after Gameover
   */
  public Highscore(String no, String username, int distance, String difficulty) {
    this.no = no;
    this.username = username;
    this.distance = distance;
    this.difficulty = difficulty;
  }

  public Highscore() {
  }

  public void setNo(String no) {
    this.no = no;
  }

  public String getNo() {
    return no;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public void setDistance(int distance) {
    this.distance = distance;
  }

  public int getDistance() {
    return distance;
  }

  public String getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(Difficulty difficulty) {
    difficulty = Game.getDifficulty();
    this.difficulty = String.valueOf(difficulty);
  }
}
