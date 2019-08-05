/* Contains the methods and variables used to create and interact 
 * with a view that shows the available servers.
 *
 * File: NetworkView.java Author: Florian Zimmer
 * Date: 25.12.2018 Version: 1.0
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class NetworkView {

  @SuppressWarnings("unchecked")
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

    Button back = new Button("Zurueck");
    back.setLayoutX(Variables.initWidth * 0.1);
    back.setLayoutY(Variables.initHeight * 0.8);
    back.setMinSize(Variables.initWidth * 0.1,
            Variables.initHeight * 0.1);

    Button createServer = new Button("Server \nerstellen");
    createServer.setLayoutX(Variables.initWidth * 0.8);
    createServer.setLayoutY(Variables.initHeight * 0.8);
    createServer.setPrefSize(Variables.initWidth * 0.1,
            Variables.initHeight * 0.1);

    Button refreshBtn = new Button(
            "Server \naktualisieren");
    refreshBtn.setLayoutX(Variables.initWidth * 0.1);
    refreshBtn.setLayoutY(Variables.initHeight * 0.7);
    refreshBtn.setPrefSize(Variables.initWidth * 0.1,
            Variables.initHeight * 0.1);

    TableView<Server> serverTable = new TableView<Server>();

    TableColumn<Server, String> serverName
            = new TableColumn<Server, String>("Servername");
    serverName.setMinWidth(Variables.initWidth * 0.2);
    serverName.setCellValueFactory(
            new PropertyValueFactory<Server, String>("name"));

    TableColumn<Server, String> serverIp = new TableColumn<Server, 
            String>("Server-IP");
    serverIp.setMinWidth(Variables.initWidth * 0.2);
    serverIp.setCellValueFactory(
            new PropertyValueFactory<Server, String>("ip"));

    TableColumn<Server, String> serverPass
            = new TableColumn<Server, String>(
                    "Passwort");
    serverPass.setMinWidth(Variables.initWidth * 0.2);
    serverPass.setCellValueFactory(
            new PropertyValueFactory<
                                        Server, String>("password"));

    serverTable.setItems(Variables.serverData);
    serverTable.getColumns().addAll(serverName, serverIp,
            serverPass);
    serverTable.setLayoutX(Variables.initWidth * 0.2);
    serverTable.setLayoutY(Variables.initHeight * 0.2);
    serverTable.setMinSize(Variables.initWidth * 0.6,
            Variables.initHeight * 0.6);

    Text netText = new Text();
    netText.setText("Verfuegbare Server");
    netText.setFont(Font.font("Verdana", FontWeight.BOLD,
            Variables.initWidth / 20));
    netText.setFill(Color.BLACK);
    netText.setX(Variables.initWidth * 0.25);
    netText.setY(Variables.initHeight * 0.15);

    Group root = new Group();
    root.getChildren().addAll(back, netText,
            serverTable, createServer, refreshBtn);
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

    //Refreshes the server list 
    refreshBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        madn.network.NetManager.loadServers();
        networkView.start(theStage, viewsArray);
        System.out.println("clicked refresh");
      }
    });

    createServer.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        serverView.start(theStage, viewsArray);
        System.out.println(
                "clicked createServer");
      }
    });
  }
}
