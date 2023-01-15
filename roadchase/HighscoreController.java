package de.hhn.it.pp.javafx.controllers.roadchase;

import de.hhn.it.pp.components.roadchase.Highscore;
import de.hhn.it.pp.components.roadchase.RoadChaseGame;
import de.hhn.it.pp.components.roadchase.RoadChaseService;
import de.hhn.it.pp.javafx.controllers.RoadChaseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;


/**
 * Highscore Controller.
 */
public class HighscoreController {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(HighscoreController.class);

  public HighscoreController() {
    logger.info("Highscore Controller created");
  }

  RoadChaseService service = new RoadChaseGame();

  @FXML
  public AnchorPane highscorePane = new AnchorPane();
  public TableView<Highscore> highscoreTable = new TableView<>();
  public TableColumn<Highscore, String> No = new TableColumn<>();
  public TableColumn<Highscore, String> player = new TableColumn<>();
  public TableColumn<Highscore, Integer> distance = new TableColumn<>();
  public TableColumn<Highscore, String> difficulty = new TableColumn<>();

  private final ObservableList<Highscore> data =
      FXCollections.observableArrayList(
          service.gethighscore()
      );

  /**
   *  initialize tableview.
   */
  @FXML
  public void initialize() {
    for (int i = 1; i <= 10; i++) {
      No.setCellValueFactory(
          new PropertyValueFactory<>("no"));
      player.setCellValueFactory(
          new PropertyValueFactory<>("username"));
      distance.setCellValueFactory(
          new PropertyValueFactory<>("distance"));
      difficulty.setCellValueFactory(
          new PropertyValueFactory<>("difficulty"));
      highscoreTable.setItems(data);
      highscoreTable.getColumns().addAll(No, player, distance, difficulty);
      No.setSortType(TableColumn.SortType.ASCENDING);
      //highscoreTable.getSortOrder().add(No);
      //highscoreTable.sort();
      highscoreTable.setEditable(false);
    }
  }

  @FXML
  protected void backToHome() {
    try {
      logger.info("Returning to Home from Highscore-Menu");
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/RoadChase.fxml"));
      Parent root = fxmlLoader.load();
      RoadChaseController roadchasecontroller = fxmlLoader.getController();
      highscorePane.getChildren().clear();
      highscorePane.getChildren().removeAll();
      highscorePane.getChildren().add(roadchasecontroller.mainPane);
    } catch (Exception e) {
      logger.error("Failed to return to Home", e);
    }
  }
}
