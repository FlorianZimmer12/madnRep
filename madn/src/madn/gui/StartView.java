/* Contains the methods and variables used to create and interact 
 * with a view that shows the game menu.
 *
 * File: StartView.java Author: Florian Zimmer
 * Date: 25.12.2019 Version: 1.0
 *
 */
package madn.gui;

import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class StartView {

  public void start(Stage theStage, Object[] viewsArray) {
    MediaHandler mediaHandler = (MediaHandler) viewsArray[0];
    StartView startView = (StartView) viewsArray[1];
    GameView gameView = (GameView) viewsArray[2];
    OptionsView optionsView = (OptionsView) viewsArray[3];
    NetworkView networkView = (NetworkView) viewsArray[4];
    ServerView serverView = (ServerView) viewsArray[5];
    SaveView saveView = (SaveView) viewsArray[6];
    PlayerView playerView = (PlayerView) viewsArray[7];

    Rectangle2D primaryScreenBounds = Screen.getPrimary().
            getVisualBounds();

    Image image = new Image(
            "file:resources/images/brickBackground.png");
    BackgroundSize backgroundSize = new BackgroundSize(
            Variables.initWidth * 0.1, Variables.initHeight * 0.1,
            false, false,false, false);
    BackgroundImage backgroundImage = new BackgroundImage(image,
            BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
            BackgroundPosition.DEFAULT, backgroundSize);
    Background background = new Background(backgroundImage);

    Pane backgroundPane = new Pane();
    backgroundPane.setMinWidth(Variables.initWidth);
    backgroundPane.setMinHeight(Variables.initHeight);
    backgroundPane.setBackground(background);

    theStage.setTitle("Mensch aergere dich nicht");
    Group root = new Group();

    ImageView signImg = new ImageView("file:resources/images/sign.png");
    signImg.setFitHeight(Variables.initHeight * 0.3);
    signImg.setFitWidth(Variables.initWidth * 0.45);
    signImg.setX(Variables.initWidth * 0.275);
    signImg.setY((Variables.initHeight * 0));

    DropShadow shadow1 = new DropShadow();
    shadow1.setOffsetX(10);
    shadow1.setOffsetY(10);
    signImg.setEffect(shadow1);

    Text mainText = new Text();
    mainText.setText("Hauptmenue");
    mainText.setFont(Font.font("Verdana", FontWeight.BOLD,
            Variables.initWidth / 20));
    mainText.setFill(Color.BLACK);
    //mainText.setStyle("-fx-border-color: blue;");
    mainText.setX(Variables.initWidth * 0.325);
    mainText.setY(Variables.initHeight * 0.225);

    Button lokalesSpiel = new Button("Neues lokales Spiel");
    lokalesSpiel.setLayoutX(Variables.initWidth * 0.2);
    lokalesSpiel.setLayoutY(Variables.initHeight * 0.4);
    lokalesSpiel.setPrefSize(Variables.initWidth * 0.2,
            Variables.initHeight * 0.1);

    Button networkBtn = new Button("Neues Netzwerkspiel");
    networkBtn.setLayoutX(Variables.initWidth * 0.6);
    networkBtn.setLayoutY(Variables.initHeight * 0.4);
    networkBtn.setPrefSize(Variables.initWidth * 0.2,
            Variables.initHeight * 0.1);

    Button resumeBtn = new Button("Spiel fortsetzen");
    resumeBtn.setLayoutX(Variables.initWidth * 0.2);
    resumeBtn.setLayoutY(Variables.initHeight * 0.6);
    resumeBtn.setPrefSize(Variables.initWidth * 0.2,
            Variables.initHeight * 0.1);

    Button savesBtn = new Button("Spielstaende");
    savesBtn.setLayoutX(Variables.initWidth * 0.6);
    savesBtn.setLayoutY(Variables.initHeight * 0.6);
    savesBtn.setPrefSize(Variables.initWidth * 0.2,
            Variables.initHeight * 0.1);

    Button options = new Button("Einstellungen");
    options.setLayoutX(Variables.initWidth * 0.2);
    options.setLayoutY(Variables.initHeight * 0.8);
    options.setPrefSize(Variables.initWidth * 0.2,
            Variables.initHeight * 0.1);

    Button close = new Button("Beenden");
    close.setLayoutX(Variables.initWidth * 0.6);
    close.setLayoutY(Variables.initHeight * 0.8);
    close.setPrefSize(Variables.initWidth * 0.2,
            Variables.initHeight * 0.1);

    root.getChildren().addAll(backgroundPane, signImg, mainText,
            lokalesSpiel, networkBtn, resumeBtn, savesBtn, options,
            close);
    Scene theScene = new Scene(root, Variables.initWidth,
            Variables.initHeight);
    theStage.setScene(theScene);
    theStage.setResizable(false);
    theStage.show();

    lokalesSpiel.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        try {
          if (Variables.resumeBool) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Bist du dir sicher?");
            alert.setHeaderText(
                    "Damit wird das bisherige Spiel abgebrochen.");
            alert.setContentText("Trotzdem fortfahren?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
              // ... user chose OK
              gameView.newGame(theStage, viewsArray);
              Variables.localGameCoordinator = new madn.logic.
                      GameCoordinator(gameView, false);
            } else {
              return;
            }
          } else {
            gameView.newGame(theStage, viewsArray);
            Variables.localGameCoordinator = new madn.logic.
                    GameCoordinator(gameView, false);
          }
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
    });

    resumeBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        try {
          theStage.setScene(gameView.theScene);
          theStage.show();
          System.out.println(".handle()");
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
    });

    if (!Variables.resumeBool) {
      resumeBtn.setDisable(true);
      resumeBtn.setOpacity(0.3);
    } else {
      resumeBtn.setDisable(false);
    }

    options.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        try {
          System.out.println("test1");
          optionsView.start(theStage, viewsArray);
          System.out.println("test2");
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
    });

    networkBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        try {
          //final  ObservableList<Server> servers =madn.network.NetManager.loadServers();
          //TODO:networtView start gets the list
          networkView.start(theStage, viewsArray);
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
    });

    savesBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        try {
          System.out.println("");
          saveView.start(theStage, viewsArray);
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
    });

    close.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        theStage.close();
      }
    });
  }
}
