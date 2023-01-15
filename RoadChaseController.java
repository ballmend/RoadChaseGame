package de.hhn.it.pp.javafx.controllers;

import de.hhn.it.pp.components.roadchase.RoadChaseGame;
import de.hhn.it.pp.components.roadchase.providers.settings.Keybinds;
import de.hhn.it.pp.components.roadchase.providers.stats.Coins;
import de.hhn.it.pp.javafx.controllers.roadchase.GameController;
import de.hhn.it.pp.javafx.controllers.roadchase.HighscoreController;
import de.hhn.it.pp.javafx.controllers.roadchase.SettingsController;
import de.hhn.it.pp.javafx.controllers.roadchase.ShopController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;


/**
 * Main Controller.
 */
public class RoadChaseController {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(RoadChaseController.class);

  @FXML
  public AnchorPane mainPane;

  public RoadChaseController() {
    logger.info("Main Menu Controller created");
  }

  @FXML
  protected void newShopWindow() {
    try {
      //Coins.setCoins(999);
      logger.info("Open Shop Window");
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/roadchase/Shop.fxml"));
      fxmlLoader.load();
      ShopController shopController = fxmlLoader.getController();
      mainPane.getChildren().clear();
      mainPane.getChildren().removeAll();
      mainPane.getChildren().add(shopController.shopPane);

    } catch (Exception e) {
      logger.error("Failed to open Shop Window", e);
    }
  }

  @FXML
  protected void newSettingWindow() {
    try {
      logger.info("Open Settings Window");
      FXMLLoader fxmlLoader =
          new FXMLLoader(getClass().getResource("/fxml/roadchase/Settings.fxml"));
      fxmlLoader.load();
      SettingsController settingsController = fxmlLoader.getController();
      mainPane.getChildren().clear();
      mainPane.getChildren().removeAll();
      mainPane.getChildren().add(settingsController.settingPane);
    } catch (Exception e) {
      logger.error("Failed to open Settings Window", e);
    }
  }

  @FXML
  protected void newHighscoreWindow() {
    try {
      logger.info("Open Highscore Window");
      FXMLLoader fxmlLoader =
          new FXMLLoader(getClass().getResource("/fxml/roadchase/Highscore.fxml"));
      fxmlLoader.load();
      HighscoreController highscoreController = fxmlLoader.getController();
      mainPane.getChildren().clear();
      mainPane.getChildren().removeAll();
      mainPane.getChildren().add(highscoreController.highscorePane);
    } catch (Exception e) {
      logger.error("Failed to open Highscore Window", e);
    }
  }

  // Game start
  @FXML
  protected void startingNewGame() {
    try {
      logger.info("Open Game Window");
      GameController gameController = new GameController();
      Scene scene = mainPane.getScene();
      mainPane.getChildren().clear();
      mainPane.getChildren().removeAll();
      createKeyListeners(scene, gameController);
      mainPane.getChildren().add(gameController.gamePane);
      gameController.createNewGame(mainPane);

    } catch (Exception e) {
      logger.error("Failed to open Game Window", e);
    }
  }

  private void createKeyListeners(Scene scene, GameController gameController) {
    logger.info("KeyListener created");
    scene.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.getKeyCode(Keybinds.getMoveLeft())) {
        gameController.isLeftKeyPressed = true;

      } else if (event.getCode() == KeyCode.getKeyCode(Keybinds.getMoveRight())) {
        gameController.isRightKeyPressed = true;
      } else if (event.getCode() == KeyCode.getKeyCode(Keybinds.getActivateSloMoKey())) {
        gameController.isActivateItemPressedSloMo = true;
      } else if (event.getCode() == KeyCode.getKeyCode(Keybinds.getActivateShieldKey())) {
        gameController.isActivateItemPressedShield = true;
      } else if (event.getCode() == KeyCode.L) {
        gameController.cheatingKeyL = true;
      } else if (event.getCode() == KeyCode.P) {
        if (gameController.cheatingKeyL) {
          gameController.cheating();
        }
      } else if (event.getCode() == KeyCode.DIGIT1) {
        if (RoadChaseGame.inGame) {
          if (gameController.isPauseKeyPressed) {
            gameController.isPauseKeyPressed = false;
            gameController.resumeGame();
          } else {
            gameController.isPauseKeyPressed = true;
            gameController.pauseGame();
          }
        }
      }
    });

    scene.setOnKeyReleased(event -> {
      if (event.getCode() == KeyCode.getKeyCode(Keybinds.getMoveLeft())) {
        gameController.isLeftKeyPressed = false;

      } else if (event.getCode() == KeyCode.getKeyCode(Keybinds.getMoveRight())) {
        gameController.isRightKeyPressed = false;

      } else if (event.getCode() == KeyCode.getKeyCode(Keybinds.getActivateSloMoKey())) {
        gameController.isActivateItemPressedSloMo = false;

      } else if (event.getCode() == KeyCode.getKeyCode(Keybinds.getActivateShieldKey())) {
        gameController.isActivateItemPressedShield = false;

      } else if (event.getCode() == KeyCode.L) {
        gameController.cheatingKeyL = false;
      }

    });
  }

  public AnchorPane getMainPane() {
    return mainPane;
  }
}
