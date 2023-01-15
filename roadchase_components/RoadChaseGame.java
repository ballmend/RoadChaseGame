package de.hhn.it.pp.components.roadchase;

import de.hhn.it.pp.components.exceptions.IllegalParameterException;
import java.util.ArrayList;

/**
 * Represents the Game.
 */
public class RoadChaseGame implements RoadChaseService {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(RoadChaseGame.class);

  public static ArrayList<Highscore> highscoreList = new ArrayList<>();
  public int moveSpeed;
  public double policeRotate;
  public double militaryRotate;
  public int spawnRatePolice;
  public int spawnRateMilitary;
  public double militaryMoveSpeed;
  public double policeMoveSpeed;
  public static double gameScore;
  public double addScoreFactor;
  public int speedDriven;
  public int playerMoveSpeed;
  public static boolean inGame;
  public int moveSpeedCopy;
  public double policeMoveSpeedCopy;
  public double militaryMoveSpeedCopy;
  public int playerMoveSpeedCopy;
  public boolean copy = true;
  public int scorePos;
  public boolean lastPos;
  public boolean savingScore;


  public RoadChaseGame() {
  }

  /**
   * Initializes a new Game.
   */
  @Override
  public void initializeGame(Difficulty difficulty) throws NullPointerException {
    logger.info("Initializing Game");
    inGame = true;
    switch (difficulty) {
      case EASY:
        logger.info("Game start in mode easy");
        moveSpeed = 3;
        speedDriven = 60;
        policeRotate = 0;
        militaryRotate = 0;
        spawnRatePolice = 3;
        spawnRateMilitary = 0;
        policeMoveSpeed = 6;
        militaryMoveSpeed = 0;
        playerMoveSpeed = 5;
        addScoreFactor = 0.035;
        break;
      case MEDIUM:
        logger.info("Game start in mode medium");
        moveSpeed = 4;
        speedDriven = 80;
        policeRotate = 0;
        militaryRotate = 0;
        spawnRatePolice = 3;
        spawnRateMilitary = 1;
        policeMoveSpeed = 7;
        militaryMoveSpeed = 9;
        playerMoveSpeed = 6;
        addScoreFactor = 0.0525;
        break;
      case HARD:
        logger.info("Game start in mode hard");
        moveSpeed = 6;
        speedDriven = 120;
        policeRotate = 0;
        militaryRotate = 0;
        spawnRatePolice = 2;
        spawnRateMilitary = 2;
        policeMoveSpeed = 9;
        militaryMoveSpeed = 11;
        playerMoveSpeed = 8;
        addScoreFactor = 0.07;
        break;
      case EXTREME:
        logger.info("Game start in mode extreme");
        moveSpeed = 8;
        speedDriven = 180;
        policeRotate = 0;
        militaryRotate = 0;
        spawnRatePolice = 1;
        spawnRateMilitary = 3;
        policeMoveSpeed = 11;
        militaryMoveSpeed = 13;
        playerMoveSpeed = 10;
        addScoreFactor = 0.14;
        break;
      default:
        logger.error("Could not initialize game");
        throw new NullPointerException();
    }
  }

  /**
   * Changes Sublevel.
   */
  @Override
  public void changeSublevel() {
    try {
      logger.info("Changing Sublevel");
      moveSpeed = moveSpeed + 1;
      speedDriven = speedDriven + 5;
      policeMoveSpeed = policeMoveSpeed + 2;
      militaryMoveSpeed = militaryMoveSpeed + 2;
      playerMoveSpeed = playerMoveSpeed + 1;
      addScoreFactor = addScoreFactor * 1.15;
    } catch (Exception e) {
      logger.error("Failed changing sublevel", e);
    }
  }

  /**
   * Change Game settings in Slow Motion Mode and copies the current Game settings.
   */
  @Override
  public void changeSublevelSlowMo() {
    try {
      logger.info("copy current game settings");
      moveSpeedCopy = moveSpeed;
      policeMoveSpeedCopy = policeMoveSpeed;
      militaryMoveSpeedCopy = militaryMoveSpeed;
      playerMoveSpeedCopy = playerMoveSpeed;
      logger.info("change Sublevel in slow-motion");
      moveSpeed = 2;
      policeMoveSpeed = 4;
      militaryMoveSpeed = 4;
      playerMoveSpeed = 5;
    } catch (Exception e) {
      logger.error("failed to change Sublevel", e);
    }
  }

  /**
   * Changes Game settings back to the copied Game settings.
   */
  @Override
  public void undoSloMoPowerup() {
    try {
      logger.info("undoing Slowmotion");
      moveSpeed = moveSpeedCopy;
      policeMoveSpeed = policeMoveSpeedCopy;
      militaryMoveSpeed = militaryMoveSpeedCopy;
      playerMoveSpeed = playerMoveSpeedCopy;
      copy = true;
    } catch (Exception e) {
      logger.error("failed to change game settings back", e);
    }

  }

  /**
   * Calculate if 2 coordinates collide.
   * Example:
   * x11|_model1_|x12   --calculate--   x21|_model2_|x22
   * If the product of the subtractions is negative the obstacles collide.
   *
   * @param x11 x1 coordinate model 1
   * @param x12 x2 coordinate model 1
   * @param x21 x1 coordinate model 2
   * @param x22 x2 coordinate model 2
   * @return true if the product of the comparison is negative
   */
  @Override
  public boolean calculateCollision(double x11, double x12, double x21, double x22)
      throws NullPointerException {
    logger.info("Calculating Collision");
    double product = (x11 - x22) * (x12 - x21);
    return product < 0;
  }

  /**
   * List the best 10 scores with username and distance.
   */
  @Override
  public ArrayList<Highscore> gethighscore() {
    logger.info("Getting Highscore-List");
    return highscoreList;
  }

  /**
   * Checks if the player beats a Highscore or not.
   *
   * @return true: can save Highscore, false: can't save Highscore
   */
  @Override
  public boolean checkHighscore() {
    logger.info("Check if the player Score is worth to safe in the List of Legends ");
    if (highscoreList.isEmpty()) {
      savingScore = true;
      return true;
    } else {
      if (highscoreList.size() <= 10) {
        int d = highscoreList.size() - 1;
        for (int i = 0; i <= d; i++) {
          if (highscoreList.get(i) != null) {
            int a = (int) RoadChaseGame.gameScore;
            int c = highscoreList.get(i).getDistance();
            if (a > c) {
              logger.info("You can save your Highscore, your Position: " + (i + 1));
              scorePos = i;
              savingScore = true;
              return true;
            } else if (i == d) {
              if (highscoreList.size() == 10) {
                return false;
              } else {
                logger.info("You can save your Highscore, your Position: " + (i + 2));
                scorePos = i + 1;
                lastPos = true;
                savingScore = true;
                return true;
              }
            }
          }
        }
      }
    }
    return false;
  }

  /**
   * Saves a new Highscore.
   *
   * @param name       of username
   * @param score      is the distance
   * @param difficulty is the gamemode
   */
  @Override
  public void updateHighscore(String name, int score, String difficulty)
      throws NullPointerException, IllegalParameterException {
    logger.info("Saving Highscore");
    boolean addNewScore = true;
    if (savingScore) {
      savingScore = false;
      if (lastPos) {
        lastPos = false;
        highscoreList.add(scorePos,
            new Highscore(String.valueOf(scorePos + 1), name, score, difficulty));
        logger.info("adding new lastPos");
      } else {
        if (highscoreList.isEmpty()) {
          highscoreList.add(0, new Highscore("1", name, score, difficulty));
          logger.info("list was Empty");
        } else {
          int j = highscoreList.size() - 1;
          for (int i = j; i >= scorePos; i--) {
            if (scorePos == 9 && scorePos == i) {
              highscoreList.set(i,
                  new Highscore(String.valueOf(scorePos + 1), name, score, difficulty));
              logger.info("ow last pos in full List");
            } else if (i == scorePos) {
              if (scorePos == highscoreList.size() - 1) {
                highscoreList.get(i).setNo(String.valueOf(i + 2));
                highscoreList.add(i + 1, highscoreList.get(i));
                highscoreList.set(i,
                    new Highscore(String.valueOf(scorePos + 1), name, score, difficulty));
                logger.info("if list is not full yet then add at the end of the list and ow pos");
              } else {
                highscoreList.get(i).setNo(String.valueOf(i + 2));
                highscoreList.set(i + 1, highscoreList.get(i));
                highscoreList.set(i,
                    new Highscore(String.valueOf(scorePos + 1), name, score, difficulty));
                logger.info("ow a pos in the list");
              }
            } else {
              if (i == 9) {
                highscoreList.set(i, highscoreList.get(i - 1));
                highscoreList.get(i).setNo(String.valueOf(i + 2));
                addNewScore = false;
                logger.info("ow last pos from prevPos in full List");
              } else if (addNewScore) {
                highscoreList.get(i).setNo(String.valueOf(i + 2));
                highscoreList.add(highscoreList.get(i));
                highscoreList.set(i, highscoreList.get(i - 1));
                addNewScore = false;
                logger.info("adding new score at the end of the list");
              } else {
                highscoreList.get(i).setNo(String.valueOf(i + 2));
                highscoreList.set(i + 1, highscoreList.get(i));
                highscoreList.set(i, highscoreList.get(i - 1));
                logger.info("sorting list upwards to make space for new score");
              }
            }
          }
        }
      }
    }
  }
}
