/* Contains the methods and variables used to create and interact with
 * a view that lets the user choose an already existing profile or
 * create a new profile and then use it.
 *
 * File: PlayerView.java Author: Florian Zimmer
 * Date: 25.12.2019 Version: 1.0
 *
 */
package madn.gui;

import java.util.ArrayList;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PlayerView {

  ImageView botImg;
  Label botLabel;
  TextField tf;
  Label[] labelArray = new Label[9];                                              //contains all labels that show the profile names
  ImageView[] imageArray = new ImageView[9];                                      //contains all the porfile images
  StackPane[] z = new StackPane[9];                                               //contains StackPanes that contain and order the image and name of a profile
  //and the corresponding "change profile" buttons
  int counter = 0;
  Button[] btnArray = new Button[9];                                              //contains the buttons that open the "new profile name" dialog window
  ImageView[] btnImgArray = new ImageView[9];                                     //contains the "save profile" imageViews
  ArrayList<EventHandler<MouseEvent>> e = new ArrayList<>(9);                     //Structure for EventHandlers that handle the onclick action
  //on the profile images
  ArrayList<EventHandler<ActionEvent>> profileChangeArray
          = new ArrayList<>(9);                                                   //Structure for EventHandler that handle the onclick action
  //on the "change profile" button

  public void start(Stage theStage, Object[] viewsArray, int posNum) {
    MediaHandler mediaHandler = (MediaHandler) viewsArray[0];
    StartView startView = (StartView) viewsArray[1];
    GameView gameView = (GameView) viewsArray[2];
    OptionsView optionsView = (OptionsView) viewsArray[3];
    NetworkView networkView = (NetworkView) viewsArray[4];
    ServerView serverView = (ServerView) viewsArray[5];
    SaveView savesView = (SaveView) viewsArray[6];
    PlayerView playerView = (PlayerView) viewsArray[7];

    Image gameImage = new Image("file:resources/images/wood.png");
    BackgroundSize gameBackgroundSize = new BackgroundSize(Variables.
            initWidth * 0.2, Variables.initHeight * 0.2, false, false,
            false, false);
    BackgroundImage gameBackgroundImg = new BackgroundImage(gameImage,
            BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
            BackgroundPosition.DEFAULT, gameBackgroundSize);
    Background gameBackground = new Background(gameBackgroundImg);

    Pane gameBackgroundPane = new Pane();
    gameBackgroundPane.setMinWidth(Variables.initWidth);
    gameBackgroundPane.setMinHeight(Variables.initHeight);
    gameBackgroundPane.setBackground(gameBackground);

    counter = 0;
    // Reset the ArrayList containing the EventHandlers for the profiles
    if (!e.isEmpty()) {
      e.clear();
    }
    if (!profileChangeArray.isEmpty()) {
      profileChangeArray.clear();
    }
    Button back = new Button("Zurueck");
    back.setLayoutX(Variables.initWidth * 0.1);
    back.setLayoutY(Variables.initHeight * 0.8);
    back.setMinSize(Variables.initWidth * 0.1,
            Variables.initHeight * 0.1);

    Text playerText = new Text();
    playerText.setText("Profil auswaehlen");
    playerText.setFont(Font.font("Verdana", FontWeight.BOLD,
            Variables.initWidth / 20));
    playerText.setFill(Color.BLACK);
    playerText.setX(Variables.initWidth * 0.25);
    playerText.setY(Variables.initHeight * 0.15);

    GridPane grid = new GridPane();
    grid.setMinSize(100, 100);
    grid.setLayoutX(Variables.initWidth * 0.1);
    grid.setLayoutY(Variables.initHeight * 0.1);
    grid.setHgap(Variables.initHeight * 0.15);
    grid.setVgap(Variables.initWidth * 0.15);

    for (int i = 0; i < Variables.profilesList.size() / 3; i++) {
      System.out.println("new row");
      StackPane p = new StackPane();
      for (int j = 0; j < 3; j++) {

        labelArray[counter]
                = new Label(Variables.profilesList.get(counter).name);
        labelArray[counter].setFont(javafx.scene.text.Font.font(
                "Verdana", FontWeight.EXTRA_BOLD, Variables.initHeight
                * 0.02));
        imageArray[counter]
                = new ImageView(Variables.profilesList.get(counter).
                        img);
        imageArray[counter].setFitWidth(Variables.initWidth * 0.1);
        imageArray[counter].setFitHeight(Variables.initHeight * 0.1);
        btnArray[counter] = new Button();
        btnImgArray[counter]
                = new ImageView("file:resources/images/save.png");
        btnImgArray[counter].setFitWidth(Variables.initWidth * 0.02);
        btnImgArray[counter].setFitHeight(Variables.initHeight * 0.02);
        btnArray[counter].setGraphic(btnImgArray[counter]);
        btnArray[counter].setMaxWidth(Variables.initWidth * 0.02);
        btnArray[counter].setMaxHeight(Variables.initHeight * 0.02);
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {

          @Override
          public void handle(MouseEvent event) {
            System.out.println("HIER GEHTS NOCH" + counter);
            System.out.println("Tile "
                    + Variables.profilesList.get(e.indexOf(this)).name
                    + " pressed ");
            switch (posNum) {
              case 1:
                if (Variables.pos1String.equals(Variables.profilesList.
                        get(
                                e.indexOf(this)).name)
                        || Variables.pos2String.equals(Variables.
                                profilesList.get(
                                e.indexOf(this)).name)
                        || Variables.pos3String.equals(Variables.
                                profilesList.get(
                                e.indexOf(this)).name)
                        || Variables.pos4String.equals(Variables.
                                profilesList.get(
                                e.indexOf(this)).name)) {
                  System.out.println("Profil ist bereits gewählt");
                  Alert alert = new Alert(AlertType.INFORMATION);
                  alert.setTitle("Fehler");
                  alert.setHeaderText("Fehler");
                  alert.setContentText("Profil wurde bereits von einem"
                          + " anderen Spieler ausgewählt!");
                  alert.showAndWait();
                  return;
                } else {
                  Variables.pos1Url = Variables.profilesList.get(
                          e.indexOf(this)).img;
                  Variables.pos1String = Variables.profilesList.get(
                          e.indexOf(this)).name;
                  //Variables.playersReady++;
                  Variables.localGameCoordinator.addPlayer(Variables.
                          profilesList.get(
                          e.indexOf(this)).name, posNum, Variables.
                                  profilesList.get(
                          e.indexOf(this)).bot);
                  break;
                }
              case 2:
                if (Variables.pos1String.equals(Variables.profilesList.
                        get(
                                e.indexOf(this)).name)
                        || Variables.pos2String.equals(Variables.
                                profilesList.get(
                                e.indexOf(this)).name)
                        || Variables.pos3String.equals(Variables.
                                profilesList.get(
                                e.indexOf(this)).name)
                        || Variables.pos4String.equals(Variables.
                                profilesList.get(
                                e.indexOf(this)).name)) {
                  System.out.println("Profil ist bereits gewaehlt");
                  Alert alert = new Alert(AlertType.INFORMATION);
                  alert.setTitle("Fehler");
                  alert.setHeaderText("Fehler");
                  alert.setContentText(
                          "Profil wurde bereits ausgewaehlt!");
                  alert.showAndWait();
                  return;
                } else {
                  Variables.pos2Url = Variables.profilesList.get(
                          e.indexOf(this)).img;
                  Variables.pos2String = Variables.profilesList.get(
                          e.indexOf(this)).name;
                  //Variables.playersReady++;
                  Variables.localGameCoordinator.addPlayer(Variables.
                          profilesList.get(
                          e.indexOf(this)).name, posNum, Variables.
                                  profilesList.get(
                          e.indexOf(this)).bot);
                  break;
                }
              case 3:
                if (Variables.pos1String.equals(Variables.profilesList.
                        get(
                                e.indexOf(this)).name)
                        || Variables.pos2String.equals(Variables.
                                profilesList.get(
                                e.indexOf(this)).name)
                        || Variables.pos3String.equals(Variables.
                                profilesList.get(
                                e.indexOf(this)).name)
                        || Variables.pos4String.equals(Variables.
                                profilesList.get(
                                e.indexOf(this)).name)) {
                  System.out.println("Profil ist bereits gewaehlt");
                  Alert alert = new Alert(AlertType.INFORMATION);
                  alert.setTitle("Fehler");
                  alert.setHeaderText("Fehler");
                  alert.setContentText(
                          "Profil wurde bereits ausgewaehlt!");
                  alert.showAndWait();
                  return;
                } else {
                  Variables.pos3Url = Variables.profilesList.get(
                          e.indexOf(this)).img;
                  Variables.pos3String = Variables.profilesList.get(
                          e.indexOf(this)).name;
                  //Variables.playersReady++;
                  Variables.localGameCoordinator.addPlayer(Variables.
                          profilesList.get(
                          e.indexOf(this)).name, posNum, Variables.
                                  profilesList.get(
                          e.indexOf(this)).bot);
                  break;
                }
              case 4:
                if (Variables.pos1String.equals(Variables.profilesList.
                        get(
                                e.indexOf(this)).name)
                        || Variables.pos2String.equals(Variables.
                                profilesList.get(
                                e.indexOf(this)).name)
                        || Variables.pos3String.equals(Variables.
                                profilesList.get(
                                e.indexOf(this)).name)
                        || Variables.pos4String.equals(Variables.
                                profilesList.get(
                                e.indexOf(this)).name)) {
                  System.out.println("Profil ist bereits gewaehlt");
                  Alert alert = new Alert(AlertType.INFORMATION);
                  alert.setTitle("Fehler");
                  alert.setHeaderText("Fehler");
                  alert.setContentText(
                          "Profil wurde bereits ausgewaehlt!");
                  alert.showAndWait();
                  return;
                } else {
                  Variables.pos4Url = Variables.profilesList.get(
                          e.indexOf(this)).img;
                  Variables.pos4String = Variables.profilesList.get(
                          e.indexOf(this)).name;
                  //Variables.playersReady++;
                  Variables.localGameCoordinator.addPlayer(Variables.
                          profilesList.get(
                          e.indexOf(this)).name, posNum, Variables.
                                  profilesList.get(
                          e.indexOf(this)).bot);
                  break;
                }

            }
            event.consume();

            gameView.start(theStage, viewsArray);
          }
        };

        //newProfileNameChange
        EventHandler<ActionEvent> profileChange = new EventHandler<ActionEvent>() {

          @Override
          public void handle(ActionEvent e) {
            if (Variables.pos1String.equals(Variables.profilesList.get(
                    profileChangeArray.indexOf(this)).name)
                    || Variables.pos2String.equals(Variables.
                            profilesList.get(
                            profileChangeArray.indexOf(this)).name)
                    || Variables.pos3String.equals(Variables.
                            profilesList.get(
                            profileChangeArray.indexOf(this)).name)
                    || Variables.pos4String.equals(Variables.
                            profilesList.get(
                            profileChangeArray.indexOf(this)).name)) {
              System.out.println("Profil ist bereits gewaehlt");
              Alert alert = new Alert(AlertType.INFORMATION);
              alert.setTitle("Fehler");
              alert.setHeaderText("Fehler");
              alert.setContentText("Profil wurde bereits ausgewaehlt!");
              alert.showAndWait();
              return;
            }
            TextInputDialog dialog = new TextInputDialog("Name");

            dialog.setTitle("New Profile");
            dialog.setHeaderText("Enter new profile name:");
            dialog.setContentText("Name:");
            System.out.println("clicked changeProf");
            tf = dialog.getEditor();
            addTextLimiter(tf, 8);
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
              System.out.println("new profile name: " + result.get());
              String resultString = result.get();
              System.out.println(resultString);
              Variables.profilesList.get(profileChangeArray.indexOf(
                      this)).setName(result.get());
              labelArray[profileChangeArray.indexOf(this)].setText(
                      resultString);
              madn.data.DataManager.saveProfile(resultString);
            } else {
              return;
            }
          }
        };
        profileChangeArray.add(profileChange);
        e.add(eventHandler);
        btnArray[counter].setOnAction(profileChange);
        imageArray[counter].addEventHandler(
                MouseEvent.MOUSE_CLICKED, eventHandler);

        z[counter] = new StackPane();
        z[counter].setLayoutX(grid.getHeight() * 0.2);
        z[counter].getChildren().addAll(imageArray[counter],
                labelArray[counter], btnArray[counter]);
        labelArray[counter].setTranslateY(Variables.initHeight * 0.06);
        btnArray[counter].setTranslateX(Variables.initWidth * 0.07);
        btnArray[counter].setTranslateY(Variables.initHeight * 0.02);

        grid.add(z[counter], j, i);
        System.out.println("added "
                + Variables.profilesList.get(counter).name);
        counter++;
      }
    }

    grid.setLayoutX(Variables.initWidth * 0.2);
    grid.setLayoutY(Variables.initHeight * 0.2);
    grid.setMinSize(Variables.initWidth * 0.8,
            Variables.initHeight * 0.8);

    botImg = new ImageView(Variables.bot.getImg());
    botImg.setFitWidth(Variables.initWidth * 0.1);
    botImg.setFitHeight(Variables.initHeight * 0.1);
    botImg.setX(Variables.initWidth * 0.45);
    botImg.setY(Variables.initHeight * 0.85);
    botImg.setPickOnBounds(true);
    botImg.addEventHandler(MouseEvent.MOUSE_CLICKED,
            new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        switch (posNum) {
          case 1:
            Variables.pos1Url = Variables.bot.img;
            Variables.pos1String = Variables.bot.name;
            Variables.localGameCoordinator.addPlayer(Variables.
                    profilesList.get(9).name, posNum, Variables.
                            profilesList.get(9).bot);
            break;
          case 2:
            Variables.pos2Url = Variables.bot.img;
            Variables.pos2String = Variables.bot.name;
            Variables.localGameCoordinator.addPlayer(Variables.
                    profilesList.get(9).name, posNum, Variables.
                            profilesList.get(9).bot);
            break;
          case 3:
            Variables.pos3Url = Variables.bot.img;
            Variables.pos3String = Variables.bot.name;
            Variables.localGameCoordinator.addPlayer(Variables.
                    profilesList.get(9).name, posNum, Variables.
                            profilesList.get(9).bot);
            break;
          case 4:
            Variables.pos4Url = Variables.bot.img;
            Variables.pos4String = Variables.bot.name;
            Variables.localGameCoordinator.addPlayer(Variables.
                    profilesList.get(9).name, posNum, Variables.
                            profilesList.get(9).bot);
            break;
        }

        //theStage.setScene(gameView.theScene);
        gameView.start(theStage, viewsArray);
        event.consume();
      }
    });

    botLabel = new Label(Variables.bot.getName());
    botLabel.setFont(javafx.scene.text.Font.font("Verdana",
            FontWeight.EXTRA_BOLD, Variables.initHeight * 0.02));
    botLabel.setLayoutX(Variables.initWidth * 0.45);
    botLabel.setLayoutY(Variables.initHeight * 0.95);

    Group root = new Group();
    root.getChildren().addAll(gameBackgroundPane, Variables.
            gameBackgroundPane, back, playerText, grid, botImg,
            botLabel);
    Scene theScene = new Scene(root,
            Variables.initWidth, Variables.initHeight);
    theStage.setScene(theScene);
    theStage.show();

    back.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        theStage.setScene(gameView.theScene);
        //gameView.start(theStage, viewsArray);
        System.out.println("clicked back");
      }
    });
  }

  //Sets a maximum length to the input of the given TextField
  //https://stackoverflow.com/questions/15159988/javafx-2-2-textfield-maxlength
  //12/13/2018  11.29 am
  public static void addTextLimiter(final TextField tf,
          final int maxLength) {
    tf.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(final ObservableValue<? extends String> ov,
              final String oldValue, final String newValue) {
        if (tf.getText().length() > maxLength) {
          String s = tf.getText().substring(0, maxLength);
          tf.setText(s);
        }
      }
    });
  }
}
