/* Contains the methods and variables used to create and interact with 
 * a view that lets the user create his own game server.
 *
 * File: ServerView.java Author: Florian Zimmer
 * Date: 25.12.2019 Version: 1.0
 *
 */
package madn.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import madn.logic.GameCoordinator;

public class ServerView {

  public void start(Stage theStage, Object[] viewsArray) {
    MediaHandler mediaHandler = (MediaHandler) viewsArray[0];
    StartView startView = (StartView) viewsArray[1];
    GameView gameView = (GameView) viewsArray[2];
    OptionsView optionsView = (OptionsView) viewsArray[3];
    NetworkView networkView = (NetworkView) viewsArray[4];
    ServerView serverView = (ServerView) viewsArray[5];
    PlayerView playerView = (PlayerView) viewsArray[7];

    Button back = new Button("Zurueck");
    back.setLayoutX(Variables.initWidth * 0.1);
    back.setLayoutY(Variables.initHeight * 0.8);
    back.setMinSize(Variables.initWidth * 0.1, Variables.initHeight * 
            0.1);

    Button createBtn = new Button("Ok");
    createBtn.setLayoutX(Variables.initWidth * 0.8);
    createBtn.setLayoutY(Variables.initHeight * 0.8);
    createBtn.setPrefSize(Variables.initWidth * 0.1,
            Variables.initHeight * 0.1);

    Text serverText = new Text();
    serverText.setText("Server erstellen");
    serverText.setFont(Font.font("Verdana", FontWeight.BOLD,
            Variables.initWidth / 20));
    serverText.setFill(Color.BLACK);
    serverText.setX(Variables.initWidth * 0.25);
    serverText.setY(Variables.initHeight * 0.15);

    Text nameText = new Text();
    nameText.setText("Name:");
    nameText.setStyle("-fx-font-weight: bold");
    nameText.setFill(Color.BLACK);
    nameText.setX(Variables.initWidth * 0.45);
    nameText.setY(Variables.initHeight * 0.275);

    TextField nameField = new TextField();
    nameField.setLayoutX(Variables.initWidth * 0.45);
    nameField.setLayoutY(Variables.initHeight * 0.3);
    nameField.setPrefSize(Variables.initWidth * 0.1,
            Variables.initHeight * 0.025);

    Text passText = new Text();
    passText.setText("Passwort:");
    passText.setStyle("-fx-font-weight: bold");
    passText.setFill(Color.BLACK);
    passText.setX(Variables.initWidth * 0.45);
    passText.setY(Variables.initHeight * 0.375);

    TextField passField = new TextField();
    passField.setLayoutX(Variables.initWidth * 0.45);
    passField.setLayoutY(Variables.initHeight * 0.4);
    passField.setPrefSize(Variables.initWidth * 0.1,
            Variables.initHeight * 0.025);

    Group root = new Group();
    root.getChildren().addAll(back, serverText, nameText, passText,
            nameField, passField, createBtn);
    Scene theScene = new Scene(root, Variables.initWidth,
            Variables.initHeight);
    theStage.setScene(theScene);
    theStage.show();

    back.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        networkView.start(theStage, viewsArray);
        System.out.println("clicked back");
      }
    });

    createBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        gameView.start(theStage, viewsArray);
        Variables.localGameCoordinator = new madn.logic.
                GameCoordinator(gameView, true);
        madn.logic.GameCoordinator gameCoordinator = 
                new GameCoordinator(nameField.getText(), 
                        passField.getText());
        System.out.println("clicked Ok");
      }
    });
  }
}
