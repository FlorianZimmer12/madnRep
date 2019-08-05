/* Contains the methods and variables used to create and interact with 
 * a view (a window) that shows the game board.
 *
 * File: GameView.java Author: Florian Zimmer
 * Date: 25.12.2018 Version: 1.0
 *
 * History: 
 *
 */
package madn.gui;

import static java.lang.Thread.sleep;
import java.util.Arrays;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.StrokeTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import static madn.gui.Variables.figures;

public class GameView implements madn.logic.UserInterface {
  
  //Player names and images above their respective positions
  Text pos1Text = new Text();
  Text pos2Text = new Text();
  Text pos3Text = new Text();
  Text pos4Text = new Text();
  ImageView pos1 = new ImageView(Variables.pos1Url);
  ImageView pos2 = new ImageView(Variables.pos2Url);
  ImageView pos3 = new ImageView(Variables.pos3Url);
  ImageView pos4 = new ImageView(Variables.pos4Url);
  ImageView die;
  MediaHandler mediaHandler;
  Stage theStage;
  SFXHandler sfxHandler;
  Group root;
  /*Arcs that are used to highlight positions on the game board
    and the variables that assist them*/
  Arc arc;
  Arc arcFig;
  Arc hoverArc;
  Stack<Object> highlightedFields;
  boolean[] highlighted;
  boolean[] highlightedFigs;
  Stack<Object> highlightedFigures;
  //Assisting variables
  StackPane winStackpane;
  Pane battlePane;
  Text winText;
  Button back;
  Text turnText;
  ImageView explosionImg;
  Image defeatedImg;
  Button ownedBtn;
  Timeline backToBase;
  Rectangle turnRect;
  public Scene theScene;
  Text noMoveText;
  StackPane noMoveStackpane;
  Rectangle u;

  /*Used when a new game ist started;
  resets all objects ont he game board*/
  public void newGame(Stage theStage, Object[] viewsArray) {
    Variables.pos1Url = ("file:red.png");
    Variables.pos2Url = ("file:red.png");
    Variables.pos3Url = ("file:red.png");
    Variables.pos4Url = ("file:red.png");
    Variables.pos1String = ("Spieler 1");
    Variables.pos2String = ("Spieler 2");
    Variables.pos3String = ("Spieler 3");
    Variables.pos4String = ("Spieler 4");
    start(theStage, viewsArray);
  }

  /*Initilizes the gam board and all assoziated objects on the board*/
  public void start(Stage theStage, Object[] viewsArray) {
    MediaHandler mediaHandler = (MediaHandler) viewsArray[0];
    StartView startView = (StartView) viewsArray[1];
    GameView gameView = (GameView) viewsArray[2];
    OptionsView optionsView = (OptionsView) viewsArray[3];
    NetworkView networkView = (NetworkView) viewsArray[4];
    ServerView serverView = (ServerView) viewsArray[5];
    SaveView savesView = (SaveView) viewsArray[6];
    PlayerView playerView = (PlayerView) viewsArray[7];
    sfxHandler = (SFXHandler) viewsArray[8];
    hoverArc = new Arc();
      hoverArc.setCenterX(Variables.fields[0].getX() * 
              Variables.initWidth + Variables.initWidth * 0.02);
      hoverArc.setCenterY(Variables.fields[0].getY() * 
              Variables.initHeight + Variables.initHeight * 0.02);
      hoverArc.setRadiusX(Variables.initWidth * 0.02);
      hoverArc.setRadiusY(Variables.initHeight * 0.02);
      hoverArc.setFill(Color.TRANSPARENT);
      hoverArc.setStroke(Color.BROWN);
      hoverArc.setStrokeWidth(4);
      hoverArc.setStartAngle(45.0f);
      hoverArc.setLength(360);
      hoverArc.setType(ArcType.OPEN);
      hoverArc.setPickOnBounds(true);
      hoverArc.setOpacity(0);
    this.theStage = theStage;
    highlightedFields = new Stack<>();
    highlightedFigures = new Stack<>();
    highlighted = new boolean[72];
    highlightedFigs = new boolean[16];

    Image gameImage = new Image("file:resources/images/wood.png");
    BackgroundSize gameBackgroundSize = new BackgroundSize(
            Variables.initWidth * 0.2, Variables.initHeight 
                    * 0.2, false, false, false, false);
    BackgroundImage gameBackgroundImg = new BackgroundImage(
            gameImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
            BackgroundPosition.DEFAULT, gameBackgroundSize);
    Background gameBackground = new Background(gameBackgroundImg);

    Pane gameBackgroundPane = new Pane();
    gameBackgroundPane.setMinWidth(Variables.initWidth);
    gameBackgroundPane.setMinHeight(Variables.initHeight);
    gameBackgroundPane.setBackground(gameBackground);

    pos1 = new ImageView(Variables.pos1Url);
    pos2 = new ImageView(Variables.pos2Url);
    pos3 = new ImageView(Variables.pos3Url);
    pos4 = new ImageView(Variables.pos4Url);

    back = new Button("Hauptmenue");
    back.setLayoutX(Variables.initWidth * 0.45);
    back.setLayoutY(Variables.initHeight * 0.9);
    back.setMinSize(Variables.initWidth * 0.1, Variables.initHeight
            * 0.05);

    ImageView startImg = new ImageView(
            "file:resources/images/play.png");
    startImg.setFitWidth(Variables.initWidth * 0.2);
    startImg.setFitHeight(Variables.initHeight * 0.2);

    Button startBtn = new Button("");
    startBtn.setLayoutX(Variables.initWidth * 0.385);
    startBtn.setLayoutY(Variables.initHeight * 0.4);
    startBtn.setMinSize(Variables.initWidth * 0.1, Variables.initHeight
            * 0.05);
    startBtn.setGraphic(startImg);

    pos1.setFitHeight(Variables.initHeight * 0.15);
    pos1.setFitWidth(Variables.initWidth * 0.15);
    pos1.setX(Variables.initWidth * 0.05);
    pos1.setY(Variables.initHeight * 0.05);

    pos2.setFitHeight(Variables.initHeight * 0.15);
    pos2.setFitWidth(Variables.initWidth * 0.15);
    pos2.setX(Variables.initWidth * 0.8);
    pos2.setY(Variables.initWidth * 0.05);

    pos3.setFitHeight(Variables.initHeight * 0.15);
    pos3.setFitWidth(Variables.initWidth * 0.15);
    pos3.setX(Variables.initWidth * 0.8);
    pos3.setY(Variables.initHeight * 0.8);

    pos4.setFitHeight(Variables.initHeight * 0.15);
    pos4.setFitWidth(Variables.initWidth * 0.15);
    pos4.setX(Variables.initWidth * 0.05);
    pos4.setY(Variables.initHeight * 0.8);

    pos1Text.setText(Variables.pos1String);
    pos1Text.setStyle("-fx-font-weight: bold");
    pos1Text.setFill(Color.BLACK);
    pos1Text.setX(Variables.initWidth * 0.05);
    pos1Text.setY(Variables.initHeight * 0.04);
    pos1Text.setFont(javafx.scene.text.Font.font("Verdana", 
            FontWeight.EXTRA_BOLD, Variables.initHeight * 0.02));

    pos2Text.setText(Variables.pos2String);
    pos2Text.setStyle("-fx-font-weight: bold");
    pos2Text.setFill(Color.BLACK);
    pos2Text.setX(Variables.initWidth * 0.8);
    pos2Text.setY(Variables.initHeight * 0.04);
    pos2Text.setFont(javafx.scene.text.Font.font("Verdana", 
            FontWeight.EXTRA_BOLD, Variables.initHeight * 0.02));

    pos3Text.setText(Variables.pos3String);
    pos3Text.setStyle("-fx-font-weight: bold");
    pos3Text.setFill(Color.BLACK);
    pos3Text.setX(Variables.initWidth * 0.8);
    pos3Text.setY(Variables.initHeight * 0.79);
    pos3Text.setFont(javafx.scene.text.Font.font("Verdana",
            FontWeight.EXTRA_BOLD, Variables.initHeight * 0.02));

    pos4Text.setText(Variables.pos4String);
    pos4Text.setStyle("-fx-font-weight: bold");
    pos4Text.setFill(Color.BLACK);
    pos4Text.setX(Variables.initWidth * 0.05);
    pos4Text.setY(Variables.initHeight * 0.79);
    pos4Text.setFont(javafx.scene.text.Font.font("Verdana", 
            FontWeight.EXTRA_BOLD, Variables.initHeight * 0.02));

    turnText = new Text();
    turnText.setText("Am Zug: ");
    turnText.setFont(Font.font("Verdana", FontWeight.BOLD,
            Variables.initWidth / 30));
    turnText.setFill(Color.BLACK);
    turnText.setX(Variables.initWidth * 0.325);
    turnText.setY(Variables.initHeight * 0.86);

    turnRect = new Rectangle();
    turnRect.setWidth(Variables.initWidth * 0.4);
    turnRect.setHeight(Variables.initHeight * 0.075);
    turnRect.setArcHeight(Variables.initHeight * 0.01);
    turnRect.setArcWidth(Variables.initWidth * 0.01);
    turnRect.setX(Variables.initWidth * 0.3);
    turnRect.setY(Variables.initHeight * 0.81);
    turnRect.setFill(Color.TRANSPARENT);
    turnRect.setStroke(Color.BROWN);
    turnRect.setStrokeWidth(Variables.initWidth * 0.01);

    Canvas canvas = new Canvas(Variables.initWidth,
            Variables.initHeight);
    canvas.setLayoutX(0);
    canvas.setLayoutY(0);

    ImageView board = new ImageView("file:resources/images/board.png");
    board.setFitHeight(Variables.initHeight * 0.6);
    board.setFitWidth(Variables.initWidth * 0.6);
    board.setX(Variables.initHeight * 0.2);
    board.setY(Variables.initHeight * 0.2);

    die = new ImageView("file:resources/images/die_face_"
            + "6.png");
    die.setFitHeight(Variables.initHeight * 0.1);
    die.setFitWidth(Variables.initWidth * 0.1);
    die.setX(Variables.initWidth * 0.45);
    die.setY(Variables.initHeight * 0.05);
    
    
    //Player 1 figures
    figures[0] = new ImageView("file:resources/images/p1figure.png");
    figures[0].setFitHeight(Variables.initHeight * 0.04);
    figures[0].setFitWidth(Variables.initWidth * 0.04);
    figures[0].setX(Variables.initWidth * Variables.fields[0].getX());
    figures[0].setY(Variables.initHeight * Variables.fields[0].getY());

    figures[1] = new ImageView("file:resources/images/p1figure.png");
    figures[1].setFitHeight(Variables.initHeight * 0.04);
    figures[1].setFitWidth(Variables.initWidth * 0.04);
    figures[1].setX(Variables.initWidth * Variables.fields[1].getX());
    figures[1].setY(Variables.initHeight * Variables.fields[1].getY());

    figures[2] = new ImageView("file:resources/images/p1figure.png");
    figures[2].setFitHeight(Variables.initHeight * 0.04);
    figures[2].setFitWidth(Variables.initWidth * 0.04);
    figures[2].setX(Variables.initWidth * Variables.fields[2].getX());
    figures[2].setY(Variables.initHeight * Variables.fields[2].getY());

    figures[3] = new ImageView("file:resources/images/p1figure.png");
    figures[3].setFitHeight(Variables.initHeight * 0.04);
    figures[3].setFitWidth(Variables.initWidth * 0.04);
    figures[3].setX(Variables.initWidth * Variables.fields[3].getX());
    figures[3].setY(Variables.initHeight * Variables.fields[3].getY());
    //Player2 figures
    figures[4] = new ImageView("file:resources/images/p2figure.png");
    figures[4].setFitHeight(Variables.initHeight * 0.04);
    figures[4].setFitWidth(Variables.initWidth * 0.04);
    figures[4].setX(Variables.initWidth * Variables.fields[4].getX());
    figures[4].setY(Variables.initHeight * Variables.fields[4].getY());

    figures[5] = new ImageView("file:resources/images/p2figure.png");
    figures[5].setFitHeight(Variables.initHeight * 0.04);
    figures[5].setFitWidth(Variables.initWidth * 0.04);
    figures[5].setX(Variables.initWidth * Variables.fields[5].getX());
    figures[5].setY(Variables.initHeight * Variables.fields[5].getY());

    figures[6] = new ImageView("file:resources/images/p2figure.png");
    figures[6].setFitHeight(Variables.initHeight * 0.04);
    figures[6].setFitWidth(Variables.initWidth * 0.04);
    figures[6].setX(Variables.initWidth * Variables.fields[6].getX());
    figures[6].setY(Variables.initHeight * Variables.fields[6].getY());

    figures[7] = new ImageView("file:resources/images/p2figure.png");
    figures[7].setFitHeight(Variables.initHeight * 0.04);
    figures[7].setFitWidth(Variables.initWidth * 0.04);
    figures[7].setX(Variables.initWidth * Variables.fields[7].getX());
    figures[7].setY(Variables.initHeight * Variables.fields[7].getY());
    //Player3 figures
    figures[12] = new ImageView("file:resources/images/p3figure.png");
    figures[12].setFitHeight(Variables.initHeight * 0.04);
    figures[12].setFitWidth(Variables.initWidth * 0.04);
    figures[12].setX(Variables.initWidth * Variables.fields[8].getX());
    figures[12].setY(Variables.initHeight * Variables.fields[8].getY());

    figures[13] = new ImageView("file:resources/images/p3figure.png");
    figures[13].setFitHeight(Variables.initHeight * 0.04);
    figures[13].setFitWidth(Variables.initWidth * 0.04);
    figures[13].setX(Variables.initWidth * Variables.fields[9].getX());
    figures[13].setY(Variables.initHeight * Variables.fields[9].getY());

    figures[14] = new ImageView("file:resources/images/p3figure.png");
    figures[14].setFitHeight(Variables.initHeight * 0.04);
    figures[14].setFitWidth(Variables.initWidth * 0.04);
    figures[14].setX(Variables.initWidth * Variables.fields[10].getX());
    figures[14].setY(Variables.initHeight * Variables.fields[10].getY());

    figures[15] = new ImageView("file:resources/images/p3figure.png");
    figures[15].setFitHeight(Variables.initHeight * 0.04);
    figures[15].setFitWidth(Variables.initWidth * 0.04);
    figures[15].setX(Variables.initWidth * Variables.fields[11].getX());
    figures[15].setY(Variables.initHeight * Variables.fields[11].getY());
    //Player4 figures
    figures[8] = new ImageView("file:resources/images/p4figure.png");
    figures[8].setFitHeight(Variables.initHeight * 0.04);
    figures[8].setFitWidth(Variables.initWidth * 0.04);
    figures[8].setX(Variables.initWidth * Variables.fields[12].getX());
    figures[8].setY(Variables.initHeight * Variables.fields[12].getY());

    figures[9] = new ImageView("file:resources/images/p4figure.png");
    figures[9].setFitHeight(Variables.initHeight * 0.04);
    figures[9].setFitWidth(Variables.initWidth * 0.04);
    figures[9].setX(Variables.initWidth * Variables.fields[13].getX());
    figures[9].setY(Variables.initHeight * Variables.fields[13].getY());

    figures[10] = new ImageView("file:resources/images/p4figure.png");
    figures[10].setFitHeight(Variables.initHeight * 0.04);
    figures[10].setFitWidth(Variables.initWidth * 0.04);
    figures[10].setX(Variables.initWidth * Variables.fields[14].getX());
    figures[10].setY(Variables.initHeight * Variables.fields[14].getY());

    figures[11] = new ImageView("file:resources/images/p4figure.png");
    figures[11].setFitHeight(Variables.initHeight * 0.04);
    figures[11].setFitWidth(Variables.initWidth * 0.04);
    figures[11].setX(Variables.initWidth * Variables.fields[15].getX());
    figures[11].setY(Variables.initHeight * Variables.fields[15].getY());
    
    //Add all nodes to the Group and then set the scen accordingly
    root = new Group();
    root.getChildren().addAll(gameBackgroundPane, board, back, pos1,
            pos2, pos3, pos4,die, pos1Text, pos2Text, pos3Text,
            pos4Text,  turnText, turnRect, Variables.figures[0],
            Variables.figures[1], 
            Variables.figures[2], Variables.figures[3],
            Variables.figures[4], Variables.figures[5],
            Variables.figures[6], Variables.figures[7],
            Variables.figures[8], Variables.figures[9],
            Variables.figures[10], Variables.figures[11], 
            Variables.figures[12], Variables.figures[13],
            Variables.figures[14], Variables.figures[15], startBtn);
    root.getChildren().add(hoverArc);
    theScene = new Scene(root, Variables.initWidth,
            Variables.initHeight);
    theStage.setScene(theScene);
    theStage.show();
    
     die.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
          if(Variables.diceClickable){
          System.out.println("clicked die");
          Variables.localGameCoordinator.rollDice();
          }else{
            System.out.println("currently not clickable");
          }
        }
      });
    
    
    
    //MouseEventHandlers for Buttons and clickable Images
    pos1.setPickOnBounds(true);
    pos1.setOnMouseClicked(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent e) {

        System.out.println("clicked pos1");
        playerView.start(theStage, viewsArray, 1);
      }
    });
    pos2.setPickOnBounds(true);
    pos2.setOnMouseClicked(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent e) {
        System.out.println("clicked pos2");
        playerView.start(theStage, viewsArray, 2);
      }
    });
    pos3.setPickOnBounds(true);
    pos3.setOnMouseClicked(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent e) {
        System.out.println("clicked pos3");
        playerView.start(theStage, viewsArray, 3);
      }
    });
    pos4.setPickOnBounds(true);
    pos4.setOnMouseClicked(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent e) {
        System.out.println("clicked pos4");
        playerView.start(theStage, viewsArray, 4);
      }
    });
    
    back.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        startView.start(theStage, viewsArray);
        System.out.println("clicked hauptmenu");
      }
    });

    startBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {

        try {
          Variables.localGameCoordinator.startGame();
          Variables.resumeBool = true;
          pos1.setDisable(true);
          pos2.setDisable(true);
          pos3.setDisable(true);
          pos4.setDisable(true);
          root.getChildren().remove(startBtn);
        } catch (Exception exc) {
          System.out.println(exc.getMessage());
          Alert alert = new Alert(AlertType.INFORMATION);
          alert.setTitle("Fehler");
          alert.setHeaderText("Fehler");
          alert.setContentText("Nicht genug Spielerprofile ausgewählt!");
          alert.showAndWait();
          return;
        }

      }
    });

  }
  
  //Moves the figure to the field
  public void moveFigure(int figureId, int fieldId) {
    double x = Variables.fields[fieldId].getX() * Variables.initWidth;
    double y = Variables.fields[fieldId].getY() * Variables.initHeight;

    Variables.timelines.add(new Timeline());
    Variables.timelines.get(Variables.timelines.size() - 1)
            .getKeyFrames().add(new KeyFrame(Duration.millis(1000),
                    new KeyValue(Variables.figures[figureId].xProperty(),
                            x),
            new KeyValue(Variables.figures[figureId].yProperty(), y)));
    Variables.timelines.get(Variables.timelines.size() - 1).play();
    
    Variables.localGameCoordinator.endTurn();
    
  }
  
  /*Moves the first figure to the first field, plays a battle animation
  and then moves the second figure to the second field*/
  public void moveFigure(int figureIdA,  int fieldIdA,int figureIdB,
          int fieldIdB) {

    ImageView figure1 = new ImageView(Variables.figures[figureIdA].
            getImage());
    figure1.setFitHeight(Variables.initHeight * 0.1);
    figure1.setFitWidth(Variables.initWidth * 0.1);

    ImageView figure2 = new ImageView(Variables.figures[figureIdB].
            getImage());
    figure2.setFitHeight(Variables.initHeight * 0.1);
    figure2.setFitWidth(Variables.initWidth * 0.1);

    explosionImg = new ImageView();
    explosionImg.setFitHeight(Variables.initHeight * 0.1);
    explosionImg.setFitWidth(Variables.initWidth * 0.1);

    defeatedImg = new Image("file:resources/images/X.png");

    ownedBtn = new Button("Owned.");
    ownedBtn.setLayoutX(Variables.initWidth * 0.15);
    ownedBtn.setLayoutY(Variables.initHeight * 0.2);
    ownedBtn.setPrefSize(Variables.initWidth * 0.2,
            Variables.initHeight * 0.1);

    battlePane = new Pane();
    battlePane.setOpacity(1);
    battlePane.setLayoutX(Variables.initWidth * 0.25);
    battlePane.setLayoutY(Variables.initHeight * 0.35);

    Rectangle r = new Rectangle();
    r.setWidth(Variables.initWidth * 0.5);
    r.setHeight(Variables.initHeight * 0.3);
    r.setFill(Color.WHITE);

    DropShadow shadow1 = new DropShadow();
    shadow1.setOffsetX(10);
    shadow1.setOffsetY(10);
    r.setEffect(shadow1);

    Image battleBackground = new Image("file:resources/images/battle.jpg");
    r.setFill(new ImagePattern(battleBackground));

    Timeline moveToFigure1 = new Timeline();
    KeyValue keyValueX = new KeyValue(Variables.figures[figureIdA].
            xProperty(), Variables.fields[fieldIdA].getX() * 
                    Variables.initWidth);
    KeyValue keyValueY = new KeyValue(Variables.figures[figureIdA].
            yProperty(), Variables.fields[fieldIdA].getY() * 
                    Variables.initHeight);
    KeyFrame keyframe = new KeyFrame(Duration.millis(1000),
            keyValueX, keyValueY);
    moveToFigure1.getKeyFrames().add(keyframe);

    Timeline moveToFigure2 = new Timeline();
    KeyValue keyValueX2 = new KeyValue(figure1.xProperty(), 
            Variables.initWidth * 0.3);
    KeyFrame keyframe2 = new KeyFrame(Duration.millis(1000), keyValueX2);
    moveToFigure2.getKeyFrames().add(keyframe2);

    Timeline moveFigureToBase = new Timeline();
    KeyValue keyValueX3 = new KeyValue(Variables.figures[figureIdB]
            .xProperty(), Variables.fields[fieldIdB].getX());
    KeyFrame keyframe3 = new KeyFrame(Duration.millis(1000), keyValueX3);
    moveFigureToBase.getKeyFrames().add(keyframe3);

    backToBase = new Timeline();
    KeyValue keyValueX4 = new KeyValue(Variables.figures[figureIdB].
            xProperty(), Variables.fields[fieldIdB].getX() 
                    * Variables.initWidth);
    KeyValue keyValueY4 = new KeyValue(Variables.figures[figureIdB].
            yProperty(), Variables.fields[fieldIdB].getY() 
                    * Variables.initHeight);
    KeyFrame keyframe4 = new KeyFrame(Duration.millis(1000),
            keyValueX4, keyValueY4);
    backToBase.getKeyFrames().add(keyframe4);

    Transition explosionAnimation = new Transition() {
      {
        setCycleDuration(Duration.millis(1000)); // total time for animation
      }

      @Override
      protected void interpolate(double fraction) {
        int index = (int) (fraction * 
                Variables.explosionImgs.length - 1);
        explosionImg.setImage(Variables.explosionImgs[index]);
      }
    };

    ownedBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        root.getChildren().remove(battlePane);
        backToBase.play();
        Variables.localGameCoordinator.endTurn();
      }
    });
    //moves figure1 to figure2 on the game board
    moveToFigure1.setOnFinished(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        System.out.println(".handle()");
        battlePane.getChildren().addAll(r, figure1, figure2);
        figure1.setX(Variables.initWidth * 0.0);
        figure1.setY(Variables.initHeight * 0.1);
        figure2.setX(Variables.initWidth * 0.4);
        figure2.setY(Variables.initHeight * 0.1);
        root.getChildren().addAll(battlePane);
        moveToFigure2.play();
        System.err.println("a");
      }
    });
    //Moves figure1 on the battle screen towards figure2 and initiates
    //the explosion animation
    moveToFigure2.setOnFinished(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        System.out.println(".handle()");
        explosionImg.setX(Variables.initWidth * 0.6);
        explosionImg.setY(Variables.initHeight * 0.45);
        root.getChildren().addAll(explosionImg);
        explosionAnimation.play();
      }
    });
    //Animates an explosion, sets the defeated image for figure2 and
    //spawns the owned button
    explosionAnimation.setOnFinished(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        figure2.setImage(defeatedImg);
        battlePane.getChildren().addAll(ownedBtn);
      }
    });
    moveToFigure1.play();
    
  }
  
  //Plays the dice rolling animation and ends at the given face-1
  public void rollDice(int endFace) {
    Transition animation = new Transition() {
      {
        setCycleDuration(Duration.millis(1000)); // total time for animation
      }
      @Override
      protected void interpolate(double fraction) {
        int indexDie = (int) (fraction * (Variables.
                dieFaces.length - 1));
        die.setImage(Variables.dieFaces[indexDie]);
      }
    };
    animation.setOnFinished(e -> {
    die.setImage(Variables.dieFaces[endFace-1]);
    Variables.localGameCoordinator.determineMovableFigures();
    });
    animation.play();
    
  }

  //Makes the image of the die clickable
  public void enableDice(boolean bool) {
    Variables.diceClickable = bool;
  }
  
  /*Highlights the given figures with a brown circle;
  if no figures are moveable by the player a notification is shown
  */
  public void highlightFigures(int[] figureIds) {
    if (figureIds[0] != -1) {
      int x = 0;
      for (int i = 0; i < figureIds.length; i++) {
        if(figureIds[i]== -1){
          break;
        }
        x = figureIds[i];

        arcFig = new Arc();
        arcFig.setCenterX(Variables.figures[x].getX() + 0.0205 *
                Variables.initWidth);
        arcFig.setCenterY(Variables.figures[x].getY() + 0.0205 * 
                Variables.initHeight);
        arcFig.setRadiusX(Variables.initWidth * 0.025);
        arcFig.setRadiusY(Variables.initHeight * 0.025);
        arcFig.setFill(Color.TRANSPARENT);
        arcFig.setStroke(Color.BROWN);
        arcFig.setStrokeWidth(4);
        arcFig.setStartAngle(45.0f);
        arcFig.setLength(360);
        arcFig.setType(ArcType.OPEN);
        arcFig.setPickOnBounds(true);
        final int figureId = x;
        arcFig.setOnMouseEntered(new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent e) {
            System.out.println("entered a figure hitbox");
            hoverArc.setOpacity(1);
            highlightHoverField(Variables.localGameCoordinator.getDestination(figureId));
          }
        });
        arcFig.setOnMouseExited(new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent e) {
            if(Variables.noDupulicatedChildrenAllowed){
              return;
            }else{
              System.out.println("exited a figure hitbox");
            try{
            hoverArc.setOpacity(0);
            }catch(Exception ee ){
              
              } 
            }
                       
          }
        });
        arcFig.setOnMouseClicked(new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent e) {
            System.out.println("clicked on figure: ");
            deHighlightFigure();
            
            hoverArc.setOpacity(0);
            Variables.localGameCoordinator.moveFigure(figureId);
            //root.getChildren().remove(hoverArc);
              
            //deHighlightField();
            }
            
          
        });
        System.out.println(arcFig.centerXProperty());
        highlightedFigures.push(arcFig);
        highlightedFigs[x] = true;
        if(root.getChildren().indexOf(arcFig) == -1){
          if(root.getChildren().indexOf(arcFig)==-1){
          root.getChildren().add(arcFig);
          }
          
        }
        
      }
    } else {
      noMoveStackpane = new StackPane();
      noMoveStackpane.setOpacity(1);
      noMoveStackpane.setLayoutX(Variables.initWidth * 0.25);
      noMoveStackpane.setLayoutY(Variables.initHeight * 0.3);

       u = new Rectangle();
      u.setWidth(Variables.initWidth * 0.5);
      u.setHeight(Variables.initHeight * 0.3);
      u.setFill(Color.WHITE);

      DropShadow shadow1 = new DropShadow();
      shadow1.setOffsetX(10);
      shadow1.setOffsetY(10);
      u.setEffect(shadow1);

      Image noMoveBackground = new Image(
              "file:resources/images/winBackground.png");
      u.setFill(new ImagePattern(noMoveBackground));

      noMoveText = new Text();
      noMoveText.setText("Du kannst keine Figur bewegen.");
      noMoveText.setFont(Font.font("Verdana", FontWeight.BOLD,
              Variables.initWidth / 50));
      noMoveText.setFill(Color.BLACK);
      noMoveText.setTranslateY(-(Variables.initHeight * 0.035));
      noMoveStackpane.getChildren().addAll(u, noMoveText);
      root.getChildren().addAll(noMoveStackpane);
      Timer timer = new java.util.Timer();
    timer.schedule(new TimerTask() {
      public void run() {
        Platform.runLater(new Runnable() {
          public void run() {
            root.getChildren().remove(noMoveStackpane);
            Variables.localGameCoordinator.endTurn();
          }
        });
      }
    }, 2000);
    }
    
  }
  
  //Highlights the given fields on the game board with a brown circle
  public void highlightField(int fieldId) {
    if (!highlighted[fieldId] == true) {
      arc = new Arc();
      arc.setCenterX(Variables.fields[fieldId].getX() * 
              Variables.initWidth + Variables.initWidth * 0.02);
      arc.setCenterY(Variables.fields[fieldId].getY() * 
              Variables.initHeight + Variables.initHeight * 0.02);
      arc.setRadiusX(Variables.initWidth * 0.02);
      arc.setRadiusY(Variables.initHeight * 0.02);
      arc.setFill(Color.TRANSPARENT);
      arc.setStroke(Color.BROWN);
      arc.setStrokeWidth(4);
      arc.setStartAngle(45.0f);
      arc.setLength(360);
      arc.setType(ArcType.OPEN);
      arc.setPickOnBounds(true);
      
      highlightedFields.push(arc);
      highlighted[fieldId] = true;
      if(root.getChildren().indexOf(arc)==-1){
        root.getChildren().add(arc);
      }
      try {
        sleep(10000);
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
      root.getChildren().remove(arc);
    }
  }
  
  public void highlightHoverField(int fieldId) {
      hoverArc.setCenterX(Variables.fields[fieldId].getX() * 
              Variables.initWidth + Variables.initWidth * 0.02);
      hoverArc.setCenterY(Variables.fields[fieldId].getY() * 
              Variables.initHeight + Variables.initHeight * 0.02);
  }
  
  //Removes the highliht circle from highlighted figures
  public void deHighlightFigure() {
    for(int i = 0; i < highlightedFigs.length;i++){
      root.getChildren().remove(highlightedFigs[i]);
    }
    while (!highlightedFigures.isEmpty()) {
      root.getChildren().remove(highlightedFigures.pop());
    }
    Arrays.fill(highlightedFigs, false);
  }
  
  //Removes the highlight circle form highlightes fields
  public void deHighlightField() {
    while (!highlightedFields.isEmpty()) {
      root.getChildren().remove(highlightedFields.pop());
    }
    Arrays.fill(highlighted, false);
  }
  
  //Sets all profiles on the game board according to the save
  //file loaded
  public void startLoaded(String[] profileNames,String[] profileImgs) {
    if(profileImgs[0] != null){
    Image image1 = new Image(profileImgs[0]);
    pos1.setImage(image1);
    }
    if(profileImgs[1] != null){
    Image image2 = new Image(profileImgs[1]);
    pos2.setImage(image2);
    }
    if(profileImgs[2] != null){
    Image image3 = new Image(profileImgs[2]);
    pos3.setImage(image3);
    }
    if(profileImgs[3] != null){
    Image image4 = new Image(profileImgs[3]);
    pos4.setImage(image4);
    }
    if(profileNames[0] != null){
    pos1Text.setText(profileNames[0]);
    }
    if(profileNames[1] != null){
    pos2Text.setText(profileNames[1]);
    }
    if(profileNames[2] != null){
    pos3Text.setText(profileNames[2]);
    }
    if(profileNames[3] != null){
    pos4Text.setText(profileNames[3]);
    }
  }

  //Makes a notification that shows that a player has won the game
  public void victory(String playerName) {
    winStackpane = new StackPane();
    winStackpane.setOpacity(1);
    winStackpane.setLayoutX(Variables.initWidth * 0.25);
    winStackpane.setLayoutY(Variables.initHeight * 0.3);

    Rectangle r = new Rectangle();
    r.setWidth(Variables.initWidth * 0.5);
    r.setHeight(Variables.initHeight * 0.3);
    r.setFill(Color.WHITE);

    DropShadow shadow1 = new DropShadow();
    shadow1.setOffsetX(10);
    shadow1.setOffsetY(10);
    r.setEffect(shadow1);

    Image winBackground = new Image(
            "file:resources/images/winBackground.png");
    r.setFill(new ImagePattern(winBackground));

    winText = new Text();
    winText.setText(playerName + "\nhat gewonnen");
    winText.setFont(Font.font("Verdana", FontWeight.BOLD,
            Variables.initWidth / 30));
    winText.setFill(Color.BLACK);
    winText.setTranslateY(-(Variables.initHeight * 0.08));
    winStackpane.getChildren().addAll(r, winText, back);
    root.getChildren().addAll(winStackpane);
    try {
      sleep(1000);
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
  }

  /*Changes the player name in the turn section of the window to the
  given string and plays a color changing animation*/
  public void changeTurn(String playerName) {
    StrokeTransition turnToOrange = new StrokeTransition();
    turnToOrange.setShape(turnRect);
    turnToOrange.setDuration(new Duration(1000));
    turnToOrange.setToValue(Color.ORANGE);

    StrokeTransition turnToBrown = new StrokeTransition();
    turnToBrown.setShape(turnRect);
    turnToBrown.setDuration(new Duration(1000));
    turnToBrown.setToValue(Color.BROWN);

    turnToOrange.setOnFinished(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        turnText.setText("Am Zug: " + playerName);
        turnToBrown.play();
      }
    });
    turnToBrown.setOnFinished(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
      }
    });
    turnToOrange.play();
  }
}
