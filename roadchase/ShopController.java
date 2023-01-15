package de.hhn.it.pp.javafx.controllers.roadchase;

import de.hhn.it.pp.components.roadchase.providers.items.PowerUp;
import de.hhn.it.pp.components.roadchase.providers.stats.Coins;
import de.hhn.it.pp.javafx.controllers.RoadChaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * Controller for Shop.
 */
public class ShopController {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(ShopController.class);


  @FXML
  public AnchorPane shopPane;
  public Label coins;
  public Text shopInfo;

  public ShopController() {
    logger.info("Shop Controller created");
  }

  public void initialize() {
    coins.setText("" + Coins.getCoins());
  }

  @FXML
  protected void backToHome() {
    try {
      logger.info("Returning to Home from Shop-Menu");
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/RoadChase.fxml"));
      Parent root = fxmlLoader.load();
      RoadChaseController roadchasecontroller = fxmlLoader.getController();
      shopPane.getChildren().clear();
      shopPane.getChildren().removeAll();
      shopPane.getChildren().add(roadchasecontroller.mainPane);
      logger.info("Saving all Settings");
    } catch (Exception e) {
      logger.error("Failed to return to Home", e);
    }
  }

  /**
   * Method to buy a Powerup shield.
   *
   * @param actionEvent not used
   */
  public void buyShield(ActionEvent actionEvent) {
    logger.info("Trying to buy Powerup Shield");
    shopInfo.setText("");
    String zw = coins.getText();
    int erg = Integer.parseInt(zw);
    erg -= 50;
    if (erg >= 0) {
      coins.setText("" + erg);
      Coins.setCoins(erg);
      PowerUp.shield++;
    } else {
      logger.error("Failed to buy Powerup Shield");
      shopInfo.setText("Not enough coins");
    }
  }

  /**
   * Method to buy a Powerup slowmotion.
   *
   * @param actionEvent not used
   */
  public void buySlowmotion(ActionEvent actionEvent) {
    logger.info("Trying to buy Powerup Slowmotion");
    shopInfo.setText("");
    String zw = coins.getText();
    int erg = Integer.parseInt(zw);
    erg -= 80;
    if (erg >= 0) {
      coins.setText("" + erg);
      Coins.setCoins(erg);
      PowerUp.slowMotion++;
    } else {
      logger.error("Failed to buy Powerup Slowmotion");
      shopInfo.setText("Not enough coins");
    }
  }

  /**
   * nice to have.
   *
   * @param actionEvent not used
   */
  public void buyskin(ActionEvent actionEvent) {
    logger.info("Trying to buy Skin");
    shopInfo.setText("");
    String zw = coins.getText();
    int erg = Integer.parseInt(zw);
    erg -= 0;
    if (erg >= 0) {
      coins.setText("" + erg);
      Coins.setCoins(erg);
    } else {
      logger.error("Failed to buy Powerup Slowmotion");
      shopInfo.setText("Not enough coins");
    }
  }
}
