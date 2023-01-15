package de.hhn.it.pp.javafx.controllers.roadchase;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;

/**
 * Class for Labels.
 */
public class SmallInfoLabel extends Label {

  public SmallInfoLabel(String text, String urlBackroundLabelImage, int reqWidth, int reqHeight) {

    BackgroundImage backgroundImage =
        new BackgroundImage(new Image(urlBackroundLabelImage, reqWidth, reqHeight, false, false),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            null);
    setBackground(new Background(backgroundImage));
    setAlignment(Pos.CENTER_LEFT);
    setPadding(new Insets(10, 10, 10, 10));
    setText(text);


  }

}
