/* Contains the methods and variables used to create and interact with
 * a view that shows the available savegames as well as save the 
 * current game in a new or already existing savegame.
 *
 * File: SaveView.java Author: Florian Zimmer
 * Date: 25.12.2019 Version: 1.0
 *
 */
package madn.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import madn.logic.GameCoordinator;

public class SaveView {

  @SuppressWarnings("unchecked")
  public void start(Stage theStage, Object[] viewsArray) {
    MediaHandler mediaHandler = (MediaHandler) viewsArray[0];
    StartView startView = (StartView) viewsArray[1];
    GameView gameView = (GameView) viewsArray[2];
    OptionsView optionsView = (OptionsView) viewsArray[3];
    NetworkView networkView = (NetworkView) viewsArray[4];
    ServerView serverView = (ServerView) viewsArray[5];
    SaveView savesView = (SaveView) viewsArray[6];
    PlayerView playerView = (PlayerView) viewsArray[7];

    Button back = new Button("Zurueck");
    back.setLayoutX(Variables.initWidth * 0.1);
    back.setLayoutY(Variables.initHeight * 0.8);
    back.setMinSize(Variables.initWidth * 0.1, Variables.
            initHeight * 0.1);

    Button saveBtn = new Button("Speichern");
    saveBtn.setLayoutX(Variables.initWidth * 0.4);
    saveBtn.setLayoutY(Variables.initHeight * 0.9);
    saveBtn.setMinSize(Variables.initWidth * 0.2,
            Variables.initHeight * 0.1);

    Button loadBtn = new Button("Laden");
    loadBtn.setLayoutX(Variables.initWidth * 0.8);
    loadBtn.setLayoutY(Variables.initHeight * 0.8);
    loadBtn.setMinSize(Variables.initWidth * 0.1,
            Variables.initHeight * 0.1);

    TextField saveField = new TextField();
    saveField.setLayoutX(Variables.initWidth * 0.4);
    saveField.setLayoutY(Variables.initHeight * 0.85);
    saveField.setPrefSize(Variables.initWidth * 0.2,
            Variables.initHeight * 0.025);
    saveField.setPromptText("Spielstandname");

    TableView<SaveGame> saveTable = new TableView<SaveGame>();

    TableColumn<SaveGame, String> saveName
            = new TableColumn<SaveGame, String>(
                    "Spielstandname");
    saveName.setMinWidth(Variables.initWidth * 0.3);
    saveName.setCellValueFactory(
            new PropertyValueFactory<SaveGame, String>("name"));

    TableColumn<SaveGame, String> saveDate
            = new TableColumn<SaveGame, String>("Datum");
    saveDate.setMinWidth(Variables.initWidth * 0.3);
    saveDate.setCellValueFactory(
            new PropertyValueFactory<SaveGame, String>("date"));

    saveTable.setItems(Variables.saveData);
    saveTable.getColumns().addAll(saveName, saveDate);
    saveTable.setLayoutX(Variables.initWidth * 0.2);
    saveTable.setLayoutY(Variables.initHeight * 0.2);
    saveTable.setMinSize(Variables.initWidth * 0.6,
            Variables.initHeight * 0.6);

    Text saveText = new Text();
    saveText.setText("Verfuegbare Spielstaende");
    saveText.setFont(Font.font("Verdana", FontWeight.BOLD,
            Variables.initWidth / 20));
    saveText.setFill(Color.BLACK);
    saveText.setX(Variables.initWidth * 0.175);
    saveText.setY(Variables.initHeight * 0.15);

    Group root = new Group();
    root.getChildren().addAll(back, saveTable, saveText,
            saveBtn, saveField, loadBtn);
    Scene theScene = new Scene(root, Variables.initWidth,
            Variables.initHeight);
    theStage.setScene(theScene);
    theStage.show();

    back.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        startView.start(theStage, viewsArray);
        System.out.println("clicked back");
      }
    });

    saveBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        madn.data.DataManager.manualSave(
                saveField.getText());
        System.out.println("clicked save");
      }
    });

    loadBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        if (saveTable.getSelectionModel().getSelectedItem() != null) {
          String selectedName = saveTable.getSelectionModel()
                  .getSelectedItem().name.toString();

          System.out.println("Selected Save: " + selectedName);
          Variables.localGameCoordinator = new GameCoordinator(gameView,
                  false);
          Variables.localGameCoordinator.load(saveTable.
                  getSelectionModel().getSelectedItem().name.toString());
        }
        System.out.println("clicked load");
      }
    });
  }
}
