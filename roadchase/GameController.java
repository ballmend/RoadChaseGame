package de.hhn.it.pp.javafx.controllers.roadchase;

import de.hhn.it.pp.components.exceptions.IllegalParameterException;
import de.hhn.it.pp.components.roadchase.Difficulty;
import de.hhn.it.pp.components.roadchase.RoadChaseGame;
import de.hhn.it.pp.components.roadchase.providers.items.PowerUp;
import de.hhn.it.pp.components.roadchase.providers.settings.Game;
import de.hhn.it.pp.components.roadchase.providers.stats.Coins;
import de.hhn.it.pp.javafx.controllers.RoadChaseController;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Game Controller is used for everything that happens during the game progress.
 */
public class GameController {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(GameController.class);
  public boolean cheatingKeyL;

  RoadChaseGame roadchasegame = new RoadChaseGame();
  public AnchorPane gamePane = new AnchorPane();

  private GridPane gridPane1;
  private GridPane gridPane2;
  private AnimationTimer gameTimer;
  Random randomPositionGenerator;

  private static final double GAME_WIDTH = 1000;
  private static final double GAME_HEIGHT = 660;

  private boolean noShieldIsActivated;
  private boolean noSloMotionIsActivated;
  public boolean isLeftKeyPressed;
  public boolean isRightKeyPressed;
  public boolean isPauseKeyPressed;
  public boolean isActivateItemPressedSloMo;
  public boolean isActivateItemPressedShield;
  double copyGameScore;
  private int angle;
  private int coins;
  private int gameSublevelScore = 200;

  private static final String BACKGROUND_IMAGE = "fxml/roadchase/ROAD_CHASE_IMAGES/Straße.png";
  private static final String CAR_IMAGE = "fxml/roadchase/ROAD_CHASE_IMAGES/Auto.png";
  private static final String CAR_SHIELD_IMAGE = "fxml/roadchase/ROAD_CHASE_IMAGES/AutoShield.png";
  private static final String ENEMY_POLICE_IMAGE = "fxml/roadchase/ROAD_CHASE_IMAGES/Polizei.png";
  private static final String ENEMY_MILITARY_IMAGE = "fxml/roadchase/ROAD_CHASE_IMAGES/Militär.png";
  private static final String COINS_IMAGE = "fxml/roadchase/ROAD_CHASE_IMAGES/Coin.png";
  private static final String SPEED_IMAGE = "fxml/roadchase/ROAD_CHASE_IMAGES/Speed.png";
  private static final String SAFE_GAME_IMAGE = "fxml/roadchase/ROAD_CHASE_IMAGES/Safegame.png";
  private static final String SCORE_IMAGE = "fxml/roadchase/ROAD_CHASE_IMAGES/Scorelabel.png";
  private static final String SHIELD_IMAGE = "fxml/roadchase/ROAD_CHASE_IMAGES/Shield.png";
  private static final String GAME_OVER_IMAGE = "fxml/roadchase/ROAD_CHASE_IMAGES/Gameover.png";
  private static final String PAUSE_GAME_IMAGE = "fxml/roadchase/ROAD_CHASE_IMAGES/Pausegame.png";
  private static final String SLOW_MOTION_POWERUP =
      "fxml/roadchase/ROAD_CHASE_IMAGES/Slowmotion.png";
  private static final String HOME_IMAGE = "fxml/roadchase/ROAD_CHASE_IMAGES/Homebutton.png";

  private static final int enemycarWIDTH = 67;
  private static final int enemycarHEIGHT = 180;
  private static final int playercarWIDTH = 35;
  private static final int playercarHEIGHT = 30;
  private static final int coinWIDTH = 30;
  private static final int coinHEIGHT = 35;
  private static final int shieldWIDTH = 35;
  private static final int shieldHEIGHT = 30;

  private SmallInfoLabel safeGameWhenEnoughPointsLabel;
  private SmallInfoLabel shieldPowerUpLabel;
  private SmallInfoLabel slowMotionPowerupLabel;
  private SmallInfoLabel drivenSpeedLabel;
  private SmallInfoLabel coinsLabel;
  private SmallInfoLabel scoreLabel;


  private ImageView[] coin;
  private ImageView[] policeCars;
  private ImageView[] militaryCars;
  private ImageView car;
  private ImageView pauseGame;
  private ImageView shieldDrop;
  private ImageView carShield;
  private ImageView carBackUp;
  private ImageView slowMotionPowerup;
  private ImageView shieldPowerup;
  private int policeCnt = 0;
  private int militaryCnt = 0;
  private int militaryVsPoliceCnt = 0;

  /**
   * Constructor for game controller.
   */
  public GameController() throws IllegalParameterException {

    logger.info("Settings Controller created");

    initializeStage();
    randomPositionGenerator = new Random();
  }

  //initialize Game setup, music volume
  private void initializeStage() throws IllegalParameterException {
    Difficulty difficulty = Game.getDifficulty();
    roadchasegame.initializeGame(difficulty);
  }

  /**
   * This method creates a new game by
   * creating the Background
   * creating the player´s car
   * creating the Game elements
   * creating the Labels.
   *
   * @param mainPane synchronize gamePane with the mainPane given from the RoadchaseController
   */
  public void createNewGame(AnchorPane mainPane) {
    this.gamePane = mainPane;
    createBackground();
    createCar();
    createGameElements();
    createLabels();
    createGameLoop();
  }

  private void createGameElements() {
    coin = new ImageView[5];
    for (int i = 0; i < coin.length; i++) {
      coin[i] = new ImageView(COINS_IMAGE);
      setNewElementPosition(coin[i]);
      gamePane.getChildren().add(coin[i]);
    }

    policeCars = new ImageView[roadchasegame.spawnRatePolice];  //schwer und hardcore modus mit 3
    for (int i = 0; i < policeCars.length; i++) {
      policeCars[i] = new ImageView(ENEMY_POLICE_IMAGE);
      setNewElementPosition(policeCars[i]);
      gamePane.getChildren().add(policeCars[i]);
    }

    militaryCars = new ImageView[roadchasegame.spawnRateMilitary];
    for (int i = 0; i < militaryCars.length; i++) {
      militaryCars[i] = new ImageView(ENEMY_MILITARY_IMAGE);
      setNewElementPosition(militaryCars[i]);
      gamePane.getChildren().add(militaryCars[i]);
    }

  }

  private void createLabels() {

    scoreLabel = new SmallInfoLabel("0m", SCORE_IMAGE, 300, 70);
    scoreLabel.setLayoutX(40);
    scoreLabel.setLayoutY(20);
    scoreLabel.setFont(Font.font("Berlin Sans FB Demi", FontWeight.EXTRA_BOLD, 40));
    gamePane.getChildren().add(scoreLabel);

    coinsLabel = new SmallInfoLabel(" 0 ", COINS_IMAGE, 80, 80);
    coinsLabel.setLayoutX(40);
    coinsLabel.setLayoutY(120);
    coinsLabel.setFont(Font.font("Berlin Sans FB Demi", FontWeight.EXTRA_BOLD, 50));
    gamePane.getChildren().add(coinsLabel);

    drivenSpeedLabel = new SmallInfoLabel(roadchasegame.speedDriven + " km/h", SPEED_IMAGE, 80, 80);
    drivenSpeedLabel.setLayoutX(40);
    drivenSpeedLabel.setLayoutY(220);
    drivenSpeedLabel.setFont(Font.font("Berlin Sans FB Demi", FontWeight.EXTRA_BOLD, 50));
    gamePane.getChildren().add(drivenSpeedLabel);

    shieldDrop = new ImageView(SHIELD_IMAGE);
    shieldDrop.setFitWidth(50);
    shieldDrop.setFitHeight(50);
    setNewElementPosition(shieldDrop);
    gamePane.getChildren().add(shieldDrop);

    shieldPowerup = new ImageView(SHIELD_IMAGE);
    shieldPowerup.setFitHeight(90);
    shieldPowerup.setFitWidth(90);
    shieldPowerup.setLayoutX(170);
    shieldPowerup.setLayoutY(550);

    shieldPowerUpLabel = new SmallInfoLabel("" + PowerUp.shield, SPEED_IMAGE, 80, 80);
    shieldPowerUpLabel.setBackground(null);
    shieldPowerUpLabel.setLayoutX(185);
    shieldPowerUpLabel.setLayoutY(550);
    shieldPowerUpLabel.setFont(Font.font("Berlin Sans FB Demi", FontWeight.EXTRA_BOLD, 50));
    shieldPowerUpLabel.setTextFill(Color.GRAY);

    gamePane.getChildren().add(shieldPowerup);
    gamePane.getChildren().add(shieldPowerUpLabel);


    slowMotionPowerup = new ImageView(SLOW_MOTION_POWERUP);
    slowMotionPowerup.setLayoutX(60);
    slowMotionPowerup.setLayoutY(550);

    slowMotionPowerupLabel = new SmallInfoLabel("" + PowerUp.slowMotion, SPEED_IMAGE, 80, 80);
    slowMotionPowerupLabel.setBackground(null);
    slowMotionPowerupLabel.setLayoutX(80);
    slowMotionPowerupLabel.setLayoutY(550);
    slowMotionPowerupLabel.setFont(Font.font("Berlin Sans FB Demi", FontWeight.EXTRA_BOLD, 50));
    slowMotionPowerupLabel.setTextFill(Color.ALICEBLUE);

    gamePane.getChildren().add(slowMotionPowerup);
    gamePane.getChildren().add(slowMotionPowerupLabel);

    noShieldIsActivated = true;
    noSloMotionIsActivated = true;
    doigotPowerups();
  }

  private void moveGameElements() {

    shieldDrop.setLayoutY(shieldDrop.getLayoutY() + roadchasegame.moveSpeed);

    for (ImageView imageView : coin) {
      imageView.setLayoutY(imageView.getLayoutY() + roadchasegame.moveSpeed);
      //coin[i].setRotate(coin[i].getRotate()+moveSpeed*2);
    }

    for (ImageView policeCar : policeCars) {
      policeCar.setLayoutY(policeCar.getLayoutY() + roadchasegame.policeMoveSpeed);
      policeCar.setRotate(policeCar.getRotate() + roadchasegame.policeRotate);
    }

    for (ImageView militaryCar : militaryCars) {
      militaryCar.setLayoutY(militaryCar.getLayoutY() + roadchasegame.militaryMoveSpeed);
      militaryCar.setRotate(militaryCar.getRotate() + roadchasegame.militaryRotate);
    }
  }

  private void checkIfElementAreBehindTheCarAndRelocateIt() {
    if (shieldDrop.getLayoutY() > 3000) {
      setNewElementPosition(shieldDrop);
      shieldDrop.setLayoutY(-randomPositionGenerator.nextInt(10000) - 5000);
    }

    for (ImageView coin : coin) {
      if (coin.getLayoutY() > 700) {
        setNewElementPosition(coin);
      }
    }

    for (ImageView policeCar : policeCars) {
      if (policeCar.getLayoutY() > 700) {
        setNewElementPosition(policeCar);
      }
    }

    for (ImageView militaryCar : militaryCars) {
      if (militaryCar.getLayoutY() > 1200) {
        setNewElementPosition(militaryCar);
      }
    }
  }

  private void setNewElementPosition(ImageView image) {
    image.setLayoutX(randomPositionGenerator.nextInt(1000)); //spawn width
    image.setLayoutY(-randomPositionGenerator.nextInt(3000) - 500); //spawn height
  }

  private void createCar() {
    car = new ImageView(CAR_IMAGE); //chosenCar.getSkin() for next updates to change skin
    carShield = new ImageView(CAR_SHIELD_IMAGE);
    carBackUp = new ImageView(CAR_IMAGE);
    car.setLayoutX(GAME_WIDTH / 2);
    car.setLayoutY(GAME_HEIGHT - 200);
    gamePane.getChildren().add(car);
  }

  private void createGameLoop() {
    gameTimer = new AnimationTimer() {

      @Override
      public void handle(long now) {
        moveBackground();
        moveGameElements();
        checkIfElementAreBehindTheCarAndRelocateIt();
        checkIfElementsCollide();
        moveCar();
        activateItem();
      }
    };
    gameTimer.start();
  }

  /**
   * This method is used to pause the game.
   */
  public void pauseGame() {
    logger.info("Game paused");
    gameTimer.stop();
    pauseGame = new ImageView(PAUSE_GAME_IMAGE);
    gamePane.getChildren().add(pauseGame);
  }

  /**
   * This method is used to resume the game.
   */
  public void resumeGame() {
    logger.info("Game resumed");
    gamePane.getChildren().remove(pauseGame);
    gameTimer.start();
  }

  /**
   * This method is used to move the car.
   */
  private void moveCar() {
    if (isLeftKeyPressed && !isRightKeyPressed) {
      if (angle > -12) {
        angle -= 4;
      }
      car.setRotate(angle);
      if (car.getLayoutX() > 0) {
        car.setLayoutX(car.getLayoutX() - roadchasegame.playerMoveSpeed);
      }
    }

    if (isRightKeyPressed && !isLeftKeyPressed) {
      if (angle < 12) {
        angle += 4;
      }
      car.setRotate(angle);
      if (car.getLayoutX() < GAME_WIDTH) {
        car.setLayoutX(car.getLayoutX() + roadchasegame.playerMoveSpeed);
      }
    }

    if (!isLeftKeyPressed && !isRightKeyPressed) {
      if (angle < 0) {
        angle += 2;
      } else if (angle > 0) {
        angle -= 2;
      }
      car.setRotate(angle);
    }

    if (isLeftKeyPressed && isRightKeyPressed) {
      if (angle < 0) {
        angle += 2;
      } else if (angle > 0) {
        angle -= 2;
      }
      car.setRotate(angle);
    }
  }

  private void createBackground() {
    gridPane1 = new GridPane();
    gridPane2 = new GridPane();

    for (int i = 0; i < 15; i++) {
      ImageView backgroundImage1 = new ImageView(BACKGROUND_IMAGE);
      ImageView backgroundImage2 = new ImageView(BACKGROUND_IMAGE);
      backgroundImage1.setFitWidth(1100);
      backgroundImage2.setFitWidth(1100);
      GridPane.setConstraints(backgroundImage1, 0, i / 3);
      GridPane.setConstraints(backgroundImage2, 0, i / 3);
      gridPane1.getChildren().add(backgroundImage1);
      gridPane2.getChildren().add(backgroundImage2);
    }

    gridPane2.setLayoutY(-600);
    gamePane.getChildren().addAll(gridPane1, gridPane2);
  }

  private void moveBackground() {
    // EASY easyMoveSpeed = 2 / MEDIUM mediumMoveSpeed = 3 /
    // HARD hardMoveSpeed = 4 / EXTREME = hardMoveSpeed = 6
    if (RoadChaseGame.gameScore > gameSublevelScore) {
      roadchasegame.changeSublevel();
      drivenSpeedLabel.setText("" + roadchasegame.speedDriven + "  km/h");
      gameSublevelScore = (int) (gameSublevelScore * 2.15);
      logger.info("SublevelScore now: " + gameSublevelScore);
    }

    gridPane1.setLayoutY(gridPane1.getLayoutY() + roadchasegame.moveSpeed);
    gridPane2.setLayoutY(gridPane2.getLayoutY() + roadchasegame.moveSpeed);

    RoadChaseGame.gameScore = RoadChaseGame.gameScore + roadchasegame.addScoreFactor;
    scoreLabel.setText((int) RoadChaseGame.gameScore + "m  " + Game.getDifficulty());

    if (gridPane1.getLayoutY() >= 600) {
      gridPane1.setLayoutY(-600);
    }

    if (gridPane2.getLayoutY() >= 600) {
      gridPane2.setLayoutY(-600);
    }
  }

  /**
   * This method checks in the game loop if game elements collide
   * by calculating this with the method.
   */
  private void checkIfElementsCollide() {
    if (shieldDrop.getLayoutY() > 0) {
      if (roadchasegame.calculateCollision(shieldDrop.getLayoutX(), shieldDrop.getLayoutX() + 70,
          car.getLayoutX(), car.getLayoutX() + 67)
          && roadchasegame.calculateCollision(shieldDrop.getLayoutY(), shieldDrop.getLayoutY() + 70,
          car.getLayoutY(), car.getLayoutY() + 165)
      ) {
        logger.info("Shield PowerUp collides with Car");

        setNewElementPosition(shieldDrop);
        shieldDrop.setLayoutY(-randomPositionGenerator.nextInt(15000) - 10000);
        if (noShieldIsActivated) {      //if a shield is in use
          //change car model to shielded car
          carShield.setLayoutX(car.getLayoutX());
          carShield.setLayoutY(car.getLayoutY());
          gamePane.getChildren().remove(car);
          car = carShield;
          gamePane.getChildren().add(car);

          noShieldIsActivated = false; //shield is in use
          doigotPowerups();
        }
      }
    }


    for (ImageView imageView : coin) {
      if (imageView.getLayoutY() > 0) {
        if (roadchasegame.calculateCollision(imageView.getLayoutX(), imageView.getLayoutX() + 67,
            car.getLayoutX(), car.getLayoutX() + 73)
            && roadchasegame.calculateCollision(imageView.getLayoutY(), imageView.getLayoutY() + 67,
            car.getLayoutY(), car.getLayoutY() + 193)
        ) {
          logger.info("Coin collides with Car");
          setNewElementPosition(imageView);
          Coins.setCoins(Coins.getCoins() + 1);
          coins++;
          coinsLabel.setText(" " + coins + "  ");
        }
      }
    }
    checkIfCarCrashesIntoPlayersCar(policeCars);
    checkIfCarCrashesIntoPlayersCar(militaryCars);
    checkIfEnemyCarsCrashThemself(policeCars, militaryCars);
  }

  /**
   * This method checks if the police cars and the military cars spawn over each other,
   * if they do they will respawn. This action happens before they drive into the watching screen.
   * The military cars drive faster, then the police, so they can drive over the police models,
   * if they do they will crash the police cars and force them to respawn.
   *
   * @param firstCar  police cars
   * @param secondCar military cars
   */
  private void checkIfEnemyCarsCrashThemself(ImageView[] firstCar, ImageView[] secondCar) {
    //police vs police
    for (int i = 0; i < firstCar.length; i++) {
      for (int j = 0; j < firstCar.length; j++) {
        if (j != i) {
          if (firstCar[i].getLayoutY() < 0 && firstCar[j].getLayoutY() < 0) {
            if (roadchasegame.calculateCollision(firstCar[i].getLayoutX(),
                firstCar[i].getLayoutX() + 67, firstCar[j].getLayoutX(),
                firstCar[j].getLayoutX() + 67)
                && roadchasegame.calculateCollision(firstCar[i].getLayoutY(),
                firstCar[i].getLayoutY() + 187, firstCar[j].getLayoutY(),
                firstCar[j].getLayoutY() + 187)) {
              policeCnt++;
              logger.info("respawn police " + policeCnt);
              setNewElementPosition(firstCar[i]);
            }
          }
        }
      }
    }
    //military vs military
    for (int i = 0; i < secondCar.length; i++) {
      for (int j = 0; j < secondCar.length; j++) {
        if (j != i) {
          if (secondCar[i].getLayoutY() < 0 && secondCar[j].getLayoutY() < 0) {
            militaryCnt++;
            logger.info("military abfrage (kann minimiert werden wenn zu hoch) " + militaryCnt);
            if (roadchasegame.calculateCollision(secondCar[i].getLayoutX(),
                secondCar[i].getLayoutX() + 67, secondCar[j].getLayoutX(),
                secondCar[j].getLayoutX() + 67)
                && roadchasegame.calculateCollision(secondCar[i].getLayoutY(),
                secondCar[i].getLayoutY() + 187, secondCar[j].getLayoutY(),
                secondCar[j].getLayoutY() + 187)) {
              setNewElementPosition(secondCar[i]);
            }
          }
        }
      }
    }
    //military vs police
    for (int i = 0; i < secondCar.length; i++) {
      for (int j = 0; j < firstCar.length; j++) {
        if (secondCar[i].getLayoutY() > -200 && firstCar[j].getLayoutY() > -200
            && secondCar[i].getLayoutY() < 660 && firstCar[j].getLayoutY() < 660) {
          if (roadchasegame.calculateCollision(secondCar[i].getLayoutX(),
              secondCar[i].getLayoutX() + 63, firstCar[j].getLayoutX(),
              firstCar[j].getLayoutX() + 63)
              && roadchasegame.calculateCollision(secondCar[i].getLayoutY(),
              secondCar[i].getLayoutY() + 187, firstCar[j].getLayoutY(),
              firstCar[j].getLayoutY() + 187)) {
            militaryVsPoliceCnt++;
            logger.info("Military car kills police " + militaryVsPoliceCnt
                + " MiliPos " +  secondCar[i].getLayoutY()+ " PoliPos " + firstCar[i].getLayoutY());
            setNewElementPosition(firstCar[j]);
          }
        }
      }
    }


  }

  private void checkIfCarCrashesIntoPlayersCar(ImageView[] enemyCar) {
    for (ImageView imageView : enemyCar) {
      if (imageView.getLayoutY() > 0) {
        if (roadchasegame.calculateCollision(imageView.getLayoutX(), imageView.getLayoutX() + 63,
            car.getLayoutX(), car.getLayoutX() + 67)
            && roadchasegame.calculateCollision(imageView.getLayoutY() + 30,
            imageView.getLayoutY() + 187, car.getLayoutY(), car.getLayoutY() + 179)
        ) {
          gameOver();
          setNewElementPosition(imageView);
        }
      }
    }
  }

  /**
   * This method checks in the game loop, if you activate an item from your inventory.
   */
  public void activateItem() {
    if (isActivateItemPressedSloMo && PowerUp.igotaslowmotionPowerup()
        && noSloMotionIsActivated) { //Ist Powerup-Taste zum gedrückt und ein Item zum benutzen da?
      logger.info("Slow - Motion activated");
      copyGameScore = RoadChaseGame.gameScore + 50;
      roadchasegame.changeSublevelSlowMo();
      noSloMotionIsActivated = false;
      slowMotionPowerupLabel.setVisible(false);
      slowMotionPowerup.setVisible(false);
      PowerUp.slowMotion--;
      PowerUp.setUgotSlowMotionLeft(false);
      slowMotionPowerup.setVisible(false);
    }
    //Slow Motion back to normal
    if (RoadChaseGame.gameScore >= copyGameScore && !noSloMotionIsActivated) {
      logger.info("Slow - Motion over");
      roadchasegame.undoSloMoPowerup();
      noSloMotionIsActivated = true;
      doigotPowerups();
    }
    //Powerup shield key must be pressed, u need to have shields left, no shield is activated
    //then you are allowed to use a shield
    if (isActivateItemPressedShield && PowerUp.ugotShieldsLeft() && noShieldIsActivated) {
      logger.info("Shield activated");
      shieldPowerup.setVisible(false);
      shieldPowerUpLabel.setVisible(false);
      carShield.setLayoutX(car.getLayoutX());
      carShield.setLayoutY(car.getLayoutY());
      gamePane.getChildren().remove(car);
      car = carShield;
      gamePane.getChildren().add(car);
      PowerUp.shield--;
      noShieldIsActivated = false;        //set shield on activated
      gamePane.getChildren()
          .removeAll(shieldPowerup, shieldPowerUpLabel, slowMotionPowerup, slowMotionPowerupLabel);
      gamePane.getChildren()
          .addAll(shieldPowerup, shieldPowerUpLabel, slowMotionPowerup, slowMotionPowerupLabel);

    }

  }

  /**
   * this method checks if you got Powerups in your inventory,
   * if yes - set visible powerup image and labels the amount of powerups
   * for both cases - Shield & Slow Motion.
   */
  private void doigotPowerups() {
    gamePane.getChildren()
        .removeAll(shieldPowerup, shieldPowerUpLabel, slowMotionPowerup, slowMotionPowerupLabel);
    gamePane.getChildren()
        .addAll(shieldPowerup, shieldPowerUpLabel, slowMotionPowerup, slowMotionPowerupLabel);

    if (PowerUp.shield > 0 && noShieldIsActivated) {
      logger.info("You got " + PowerUp.shield + "shield left!");
      PowerUp.setUgotShieldsLeft(true);
      shieldPowerup.setVisible(true);
      shieldPowerUpLabel.setVisible(true);
      shieldPowerUpLabel.setText("" + PowerUp.shield);
    } else if (noShieldIsActivated) {
      logger.info("You got no Shield left!");
      PowerUp.setUgotShieldsLeft(false);
      shieldPowerup.setVisible(false);
      shieldPowerUpLabel.setVisible(false);
    }

    if (PowerUp.slowMotion > 0 && noSloMotionIsActivated) {
      logger.info("You got " + PowerUp.slowMotion + "Slo-Mo left!");
      PowerUp.setUgotSlowMotionLeft(true);
      slowMotionPowerup.setVisible(true);
      slowMotionPowerupLabel.setVisible(true);
      slowMotionPowerupLabel.setText("" + PowerUp.slowMotion);
    } else if (noSloMotionIsActivated) {
      logger.info("You got no Slo-Mo left!");
      PowerUp.setUgotSlowMotionLeft(false);
      slowMotionPowerup.setVisible(false);
      slowMotionPowerupLabel.setVisible(false);
    }

  }

  /**
   * This method is used if an enemy car collides with the players car
   * and decide if the game is over, or if the player has a shield.
   * If the player has a shield he gets another chance to beat the Highscore.
   */
  private void gameOver() {
    if (!noShieldIsActivated) {
      logger.info("Shield is breaking");  //change car model to normal
      carBackUp.setLayoutX(car.getLayoutX());
      carBackUp.setLayoutY(car.getLayoutY());
      gamePane.getChildren().remove(car);
      car = carBackUp;
      gamePane.getChildren().add(car);
      logger.info("Your shield broke, you can use a new one");
      noShieldIsActivated = true;  //you can use a shield again, when your old shield broke
      doigotPowerups();
    } else {
      logger.info("Game over");
      RoadChaseGame.inGame = false;
      gameTimer.stop();
      ImageView gameOver = new ImageView(GAME_OVER_IMAGE);
      gameOver.setLayoutX(0);
      gameOver.setLayoutY(0);
      gamePane.getChildren().add(gameOver);
      gamePane.getChildren().removeAll(coinsLabel, scoreLabel, drivenSpeedLabel);


      ImageView backToHomeImage = new ImageView(HOME_IMAGE);
      backToHomeImage.setLayoutX(GAME_WIDTH / 2 + 220);
      backToHomeImage.setLayoutY(GAME_HEIGHT / 2);
      backToHomeImage.setFitWidth(50);
      backToHomeImage.setFitHeight(50);

      backToHomeImage.setOnMouseClicked(event -> backToHome());

      ImageView safeGameWhenEnoughPoints = new ImageView(SAFE_GAME_IMAGE);
      safeGameWhenEnoughPoints.setLayoutX(GAME_WIDTH / 2 - 180);
      safeGameWhenEnoughPoints.setLayoutY(GAME_HEIGHT / 2);
      safeGameWhenEnoughPoints.setFitWidth(50);
      safeGameWhenEnoughPoints.setFitHeight(50);
      safeGameWhenEnoughPoints.setVisible(false);


      safeGameWhenEnoughPointsLabel = new SmallInfoLabel("", COINS_IMAGE, 40, 40);
      safeGameWhenEnoughPointsLabel.setBackground(null);
      safeGameWhenEnoughPointsLabel.setText(
          "type in your name and\npress ENTER to be one of the LEGENDS");
      safeGameWhenEnoughPointsLabel.setTextFill(Color.BEIGE);
      safeGameWhenEnoughPointsLabel.setFont(Font.font(25));
      safeGameWhenEnoughPointsLabel.setLayoutX(GAME_WIDTH / 2 - 200);
      safeGameWhenEnoughPointsLabel.setLayoutY(GAME_HEIGHT / 2 + 70);

      scoreLabel.setText((int) RoadChaseGame.gameScore + "m  ");
      scoreLabel.setBackground(null);
      scoreLabel.setTextFill(Color.BLACK);
      scoreLabel.setLayoutY(322);
      scoreLabel.setLayoutX(600);

      try {
        logger.info("Check if Highscore is valid");
        if (roadchasegame.checkHighscore()) {
          safeGameWhenEnoughPoints.setVisible(true);
        }
      } catch (Exception e) {
        logger.error("Can't save Highscore", e);
      }

      safeGameWhenEnoughPoints.setOnMouseClicked(t -> typeInHighScore());
      gamePane.getChildren().addAll(safeGameWhenEnoughPoints, backToHomeImage, scoreLabel);
    }
  }

  /**
   * This method is used to Type in your Highscore when you achieved a worthy Highscore.
   * (worthy means if you beat at least one of the Top-Ten in the Highscorelist)
   */
  public void typeInHighScore() {
    TextField typeInNameTextFlield = new TextField();
    typeInNameTextFlield.setFont(Font.font("Berlin Sans FB Demi", FontWeight.BOLD, 20));
    typeInNameTextFlield.setLayoutX(GAME_WIDTH / 2 - 120);
    typeInNameTextFlield.setLayoutY(GAME_HEIGHT / 2 + 5);
    typeInNameTextFlield.setMaxSize(80, 20);
    typeInNameTextFlield.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        String name = typeInNameTextFlield.getText();
        try {
          logger.info("Trying to save Highscore");
          roadchasegame.updateHighscore(name, (int) RoadChaseGame.gameScore,
              Game.getDifficulty().toString());
        } catch (Exception e) {
          logger.error("Failed to save Highscore", e);
        }
        backToHome();
      }
    });

    gamePane.getChildren().addAll(typeInNameTextFlield, safeGameWhenEnoughPointsLabel);

  }

  @FXML
  protected void backToHome() {
    try {
      logger.info("Returning to Home from GameOver-Screen");
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/RoadChase.fxml"));
      fxmlLoader.load();
      RoadChaseController roadchasecontroller = fxmlLoader.getController();
      gamePane.getChildren().clear();
      gamePane.getChildren().removeAll();
      gamePane.getChildren().add(roadchasecontroller.mainPane);
      RoadChaseGame.gameScore = 0;
    } catch (Exception e) {
      logger.error("Failed to return to Home", e);
    }
  }

  /**
   * cheating method to give coins and Powerups.
   */
  public  void cheating() {
    Coins.setCoins(999);
    PowerUp.setSlowMotion(9);
    PowerUp.setShield(9);
  }
}
