package de.hhn.it.pp.javafx.controllers.roadchase;

import de.hhn.it.pp.components.roadchase.Difficulty;
import de.hhn.it.pp.components.roadchase.providers.settings.Game;
import de.hhn.it.pp.components.roadchase.providers.settings.Keybinds;
import de.hhn.it.pp.javafx.controllers.RoadChaseController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 * Controller for Settings.
 */
public class SettingsController {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(SettingsController.class);

  @FXML
  public AnchorPane settingPane;
  public Label moveLeftKey;
  public Label moveRightKey;
  public Label activateSloMoKey;
  public Label activateShieldKey;
  public Slider musicSlider;
  public ChoiceBox<Difficulty> difficultyBox;
  public TabPane tabPane;
  public Tab keybindsTab;

  public SettingsController() {
    logger.info("Settings Controller created");
  }

  /**
    * Initialize for Settings.
   */
  public void initialize() {
    logger.info("Getting Difficulty Setting");
    //TODO: ArrowKeys changen
    tabPane.addEventFilter(KeyEvent.ANY, event -> {
      if (event.getCode().isArrowKey()) {
        //System.out.println("Is arrow key");
        event.consume();
      }
    });
    //keybindsTab.set
    difficultyBox.getSelectionModel().select(Game.getDifficulty());
    difficultyBox.getItems()
        .setAll(Difficulty.EASY, Difficulty.MEDIUM, Difficulty.HARD, Difficulty.EXTREME);
    moveLeftKey.setDisable(false);
    moveRightKey.setDisable(false);
    activateSloMoKey.setDisable(false);
    activateShieldKey.setDisable(false);
    logger.info("Getting Keybinds Setting");
    moveLeftKey.setText(Keybinds.getMoveLeft());
    moveRightKey.setText(Keybinds.getMoveRight());
    activateSloMoKey.setText(Keybinds.getActivateSloMoKey());
    activateShieldKey.setText(Keybinds.getActivateShieldKey());
  }

  @FXML
  protected void backToHome() {
    try {
      logger.info("Returning to Home from Settings-Menu");
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/RoadChase.fxml"));
      Parent root = fxmlLoader.load();
      RoadChaseController roadchasecontroller = fxmlLoader.getController();
      settingPane.getChildren().clear();
      settingPane.getChildren().removeAll();
      settingPane.getChildren().add(roadchasecontroller.mainPane);
      logger.info("Saving all Settings");
      Game.setDifficulty(difficultyBox.getSelectionModel().getSelectedItem());
    } catch (Exception e) {
      logger.error("Failed to return to Home", e);
    }
  }

  /**
   * Changes the Key for moving the car to the left.
   *
   * @param mouseEvent for clicking on the assigned Key.
   */
  public void changeKey1(MouseEvent mouseEvent) {
    logger.info("Trying to change moveLeftKey");
    moveRightKey.setDisable(true);
    activateSloMoKey.setDisable(true);
    activateShieldKey.setDisable(true);
    moveLeftKey.setTextFill(Color.BLUE);
    moveLeftKey.setText("Press any Key to change");
    settingPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if (moveRightKey.isDisabled() && activateSloMoKey.isDisabled()) {
          if (moveRightKey.getText().equals(event.getCode().toString())
              || activateSloMoKey.getText().equals(event.getCode().toString())
              || activateShieldKey.getText().equals(event.getCode().toString())
              || event.getCode().toString().equals("ESCAPE")
              || event.getCode().toString().equals("ENTER")) {
            moveLeftKey.setText(event.getCode() + " is already assigned!");
          } else {
            moveLeftKey.setText(event.getCode().toString());
            moveLeftKey.setTextFill(Color.BLACK);
            Keybinds.setMoveLeft(moveLeftKey.getText());
            logger.info("moveLeftKey has changed");
            moveRightKey.setDisable(false);
            activateSloMoKey.setDisable(false);
            activateShieldKey.setDisable(false);
          }
        }
      }
    });
  }

  /**
   * Changes the Key for moving the car to the right.
   *
   * @param mouseEvent for clicking on the assigned Key.
   */
  public void changeKey2(MouseEvent mouseEvent) {
    logger.info("Trying to change moveRightKey");
    moveLeftKey.setDisable(true);
    activateSloMoKey.setDisable(true);
    activateShieldKey.setDisable(true);
    moveRightKey.setTextFill(Color.BLUE);
    moveRightKey.setText("Press any Key to change");
    settingPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if (moveLeftKey.isDisabled() && activateSloMoKey.isDisabled()
            && activateShieldKey.isDisabled()) {
          if (moveLeftKey.getText().equals(event.getCode().toString())
              || activateSloMoKey.getText().equals(event.getCode().toString())
              || activateShieldKey.getText().equals(event.getCode().toString())
              || event.getCode().toString().equals("ESCAPE")
              || event.getCode().toString().equals("ENTER")) {
            moveRightKey.setText(event.getCode() + " is already assigned!");
          } else {
            moveRightKey.setText(event.getCode().toString());
            moveRightKey.setTextFill(Color.BLACK);
            Keybinds.setMoveRight(moveRightKey.getText());
            logger.info("moveRightKey has changed");
            moveLeftKey.setDisable(false);
            activateSloMoKey.setDisable(false);
            activateShieldKey.setDisable(false);
          }
        }
      }
    });
  }

  /**
   * Changes the Key for activating Slowmotion.
   *
   * @param mouseEvent for clicking on the assigned Key.
   */
  public void changeKey3(MouseEvent mouseEvent) {
    logger.info("Trying to change activateSloMoKey");
    moveLeftKey.setDisable(true);
    moveRightKey.setDisable(true);
    activateShieldKey.setDisable(true);
    activateSloMoKey.setTextFill(Color.BLUE);
    activateSloMoKey.setText("Press any Key to change");
    settingPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if (moveLeftKey.isDisabled() && moveRightKey.isDisabled()) {
          if (moveLeftKey.getText().equals(event.getCode().toString())
              || moveRightKey.getText().equals(event.getCode().toString())
              || activateShieldKey.getText().equals(event.getCode().toString())
              || event.getCode().toString().equals("ESCAPE")
              || event.getCode().toString().equals("ENTER")) {
            activateSloMoKey.setText(event.getCode() + " is already assigned!");
          } else {
            activateSloMoKey.setText(event.getCode().toString());
            activateSloMoKey.setTextFill(Color.BLACK);
            Keybinds.setActivateSloMoKey(activateSloMoKey.getText());
            logger.info("activateItemKey has changed");
            moveLeftKey.setDisable(false);
            moveRightKey.setDisable(false);
            activateShieldKey.setDisable(false);
          }
        }
      }
    });
  }

  /**
   * Changes the Key for activating Shield.
   *
   * @param mouseEvent for clicking on the assigned Key.
   */
  public void changeKey4(MouseEvent mouseEvent) {
    logger.info("Trying to change activateShieldKey");
    moveLeftKey.setDisable(true);
    moveRightKey.setDisable(true);
    activateSloMoKey.setDisable(true);
    activateShieldKey.setTextFill(Color.BLUE);
    activateShieldKey.setText("Press any Key to change");
    settingPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if (moveLeftKey.isDisabled() && moveRightKey.isDisabled()) {
          if (moveLeftKey.getText().equals(event.getCode().toString())
              || moveRightKey.getText().equals(event.getCode().toString())
              || activateSloMoKey.getText().equals(event.getCode().toString())
              || event.getCode().toString().equals("ESCAPE")
              || event.getCode().toString().equals("ENTER")) {
            activateShieldKey.setText(event.getCode() + " is already assigned!");
          } else {
            activateShieldKey.setText(event.getCode().toString());
            activateShieldKey.setTextFill(Color.BLACK);
            Keybinds.setActivateShieldKey(activateShieldKey.getText());
            logger.info("activateItemKey has changed");
            moveLeftKey.setDisable(false);
            moveRightKey.setDisable(false);
            activateSloMoKey.setDisable(false);
          }
        }
      }
    });
  }

  /**
   * Changes the Volume of the Backgroundmusic.
   *
   * @param mouseDragEvent for dragging on the Slider.
   */
  public void changeVolume(MouseDragEvent mouseDragEvent) {
    logger.info("Trying to change Volume");
    musicSlider.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable,
                          Number oldValue, Number newValue) {
        musicSlider.setValue((Double) newValue);
        // TODO: change volume background music
      }
    });
  }


}
