/* Contains the methods and variables used to create and interact
 * with a view that shows the game options. Methods include 
 * changing the language, handling the background music and 
 * changing the resolution.
 *
 * File: OptionsView.java Author: Florian Zimmer
 * Date: 25.12.2019 Version: 1.0

 *
 */
package madn.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OptionsView {

  public void start(Stage theStage, Object[] viewsArray) {
    MediaHandler mediaHandler
            = (MediaHandler) viewsArray[0];
    StartView startView = (StartView) viewsArray[1];
    GameView gameView = (GameView) viewsArray[2];
    OptionsView optionsView = (OptionsView) viewsArray[3];
    NetworkView networkView = (NetworkView) viewsArray[4];
    ServerView serverView = (ServerView) viewsArray[5];
    SaveView savesView = (SaveView) viewsArray[6];
    PlayerView playerView = (PlayerView) viewsArray[7];
    SFXHandler sfxHandler = (SFXHandler) viewsArray[8];
    Text musicText = new Text();
    musicText.setText("Musik");
    musicText.setStyle("-fx-font-weight: bold");
    musicText.setFill(Color.BLACK);
    musicText.setX(Variables.initWidth * 0.1);
    musicText.setY(Variables.initWidth * 0.15);

    Text resText = new Text();
    resText.setText("Aufloesung");
    resText.setStyle("-fx-font-weight: bold");
    resText.setFill(Color.BLACK);
    resText.setX(Variables.initWidth * 0.3);
    resText.setY(Variables.initWidth * 0.15);

    Text lanText = new Text();
    lanText.setText("Sprache aendern");
    lanText.setStyle("-fx-font-weight: bold");
    lanText.setFill(Color.BLACK);
    lanText.setX(Variables.initWidth * 0.5);
    lanText.setY(Variables.initWidth * 0.15);

    Slider volumeSlider = new Slider();
    volumeSlider.setMin(0);
    volumeSlider.setMax(1);
    volumeSlider.setMajorTickUnit(0.1);
    volumeSlider.setBlockIncrement(0.1);
    volumeSlider.adjustValue(0.0);
    volumeSlider.setLayoutX(Variables.initWidth * 0.1);
    volumeSlider.setLayoutY(Variables.initHeight * 0.4);

    Button handleMusic = new Button("Musik ausschalten.");
    handleMusic.setLayoutX(Variables.initWidth * 0.1);
    handleMusic.setLayoutY(Variables.initHeight * 0.2);
    handleMusic.setMinSize(Variables.initWidth * 0.1,
            Variables.initHeight * 0.1);

    Label volLabel = new Label("Lautstaerke : "
            + Variables.volume);
    volLabel.setLayoutX(Variables.initWidth * 0.1);
    volLabel.setLayoutY(Variables.initHeight * 0.35);

    Button back = new Button("Zurueck");
    back.setLayoutX(Variables.initWidth * 0.1);
    back.setLayoutY(Variables.initHeight * 0.8);
    back.setMinSize(Variables.initWidth * 0.1,
            Variables.initHeight * 0.1);

    Button res800 = new Button("800*800");
    res800.setLayoutX(Variables.initWidth * 0.3);
    res800.setLayoutY(Variables.initHeight * 0.2);
    res800.setMinSize(Variables.initWidth * 0.1,
            Variables.initHeight * 0.1);

    Button res1000 = new Button("1000*1000");
    res1000.setLayoutX(Variables.initWidth * 0.3);
    res1000.setLayoutY(Variables.initHeight * 0.35);
    res1000.setMinSize(Variables.initWidth * 0.1,
            Variables.initHeight * 0.1);

    Button gerBtn = new Button("Deutsch");
    gerBtn.setLayoutX(Variables.initWidth * 0.5);
    gerBtn.setLayoutY(Variables.initHeight * 0.2);
    gerBtn.setMinSize(Variables.initWidth * 0.1,
            Variables.initHeight * 0.1);

    Button engBtn = new Button("English");
    engBtn.setLayoutX(Variables.initWidth * 0.5);
    engBtn.setLayoutY(Variables.initHeight * 0.35);
    engBtn.setMinSize(Variables.initWidth * 0.1,
            Variables.initHeight * 0.1);

    Group root = new Group();
    root.getChildren().addAll(handleMusic, volumeSlider,
            volLabel, back, res800, res1000, lanText, gerBtn,
            engBtn, musicText, resText);
    Scene theScene = new Scene(root, Variables.initWidth,
            Variables.initHeight);
    theStage.setScene(theScene);
    theStage.show();

    handleMusic.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        if (mediaHandler.playing) {
          handleMusic.setText(
                  "Musik anschalten.");
          // Sound.play();
          mediaHandler.stop();

        } else {
          handleMusic.setText(
                  "Musik ausschalten.");
          // Sound.play();
          mediaHandler.play();
        }
      }
    });

    volumeSlider.valueProperty().addListener(
            new ChangeListener<Object>() {
      public void changed(ObservableValue<?> arg0,
              Object arg1,
              Object arg2) {
        ((MediaHandler) viewsArray[0])
                .applyVolume(
                        volumeSlider.getValue());
        sfxHandler.applyVolume(volumeSlider.getValue());
        volLabel.setText("Lautst�rke: " + volumeSlider.getValue());

      }
    });

    back.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        startView.start(theStage, viewsArray);
        System.out.println("clicked hauptmenu");
      }
    });

    res800.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        Variables.initWidth = 800;
        Variables.initHeight = 800;
        System.out.println("clicked res800");
        optionsView.start(theStage, viewsArray);
      }
    });

    res1000.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        Variables.initWidth = 1000;
        Variables.initHeight = 1000;
        System.out.println("clicked res1000");
        optionsView.start(theStage, viewsArray);
      }
    });
  }
}
