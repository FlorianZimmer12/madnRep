/* Contains variables used by serveral classes.
 *
 * File: Variables.java Author: Florian Zimmer
 * Date: 25.12.2019 Version: 1.0
 */
package madn.gui;

import java.util.ArrayList;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;

public class Variables {

  public static madn.logic.GameCoordinator localGameCoordinator;
  public static Boolean noDupulicatedChildrenAllowed = false;
  public static Boolean resumeBool = false;
  public static double initWidth = 800;
  public static double initHeight = 800;
  public static double scaleFactor = 0.5;
  public static double volume = 0.0;
  public static int numViews = 9;
  public static int numServers = 2;
  public static int numProfiles = 2;
  public static int playersReady = 0;
  public static String pos1Url = "file:red.png";
  public static String pos2Url = "file:red.png";
  public static String pos3Url = "file:red.png";
  public static String pos4Url = "file:red.png";
  public static Boolean diceClickable = false;
  public static String pos1String = "Spieler 1";
  public static String pos2String = "Spieler 2";
  public static String pos3String = "Spieler 3";
  public static String pos4String = "Spieler 4";
  public static Image[] dieFaces = new Image[6];
  public static Image[] explosionImgs = new Image[7];
  public static Boolean bool = true;
  
  //Manually created servers for testing reasons
  final static ObservableList<Server> serverData = FXCollections
          .observableArrayList(new Server("server1", "192.192.192.1",
                  "Ja"),
                  new Server("server2", "111.111.111.1", "Nein"));
  
  //Manually created save games for testing reasons
  final static ObservableList<SaveGame> saveData = FXCollections
          .observableArrayList(new SaveGame("Spielstand1", 
                  "01.01.1000"),
                  new SaveGame("Spielstand2", "02.02.2000"));
  
  //Manually created profiles for testing reasons
  static ArrayList<Profile> profilesList = new ArrayList<Profile>(10);
  public static Profile prof1 = new Profile("name1", 
          "file:resources/images/die_face_1.png", false);
  public static Profile prof2 = new Profile("name2", 
          "file:resources/images/die_face_2.png", false);
  public static Profile prof3 = new Profile("name3", 
          "file:resources/images/die_face_3.png", false);
  public static Profile prof4 = new Profile("name4", 
          "file:resources/images/die_face_4.png", false);
  public static Profile prof5 = new Profile("name5", 
          "file:resources/images/die_face_5.png", false);
  public static Profile prof6 = new Profile("name6", 
          "file:resources/images/die_face_6.png", false);
  public static Profile prof7 = new Profile("name7", 
          "file:resources/images/die_face_6.png", false);
  public static Profile prof8 = new Profile("name8", 
          "file:resources/images/die_face_6.png", false);
  public static Profile prof9 = new Profile("name9", 
          "file:resources/images/die_face_6.png", false);

  public static Profile bot = new Profile("bot",
          "file:resources/images/bot.png", true);

  static Point2D[] fields = new Point2D[72];
  static ArrayList<Timeline> timelines = new ArrayList<Timeline>();
  static ImageView figures[] = new ImageView[16];
  /*String language = "eng";
	String[] engArr = new String[9];
	Map<String, String[]> map = new HashMap<String, String[]>();*/

  public static Pane backgroundPane;
  public static Pane gameBackgroundPane;

  public Variables() {

    Image image = new Image(
            "file:resources/images/brickBackground.png");
    BackgroundSize backgroundSize = new BackgroundSize(
            Variables.initWidth * 0.1, Variables.initHeight 
                    * 0.1, false, false, false, false);
    BackgroundImage backgroundImage = new BackgroundImage(
            image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
            BackgroundPosition.DEFAULT, backgroundSize);
    Background background = new Background(backgroundImage);

    Image gameImage = new Image("file:resources/images/wood.png");
    BackgroundSize gameBackgroundSize = new BackgroundSize(
            Variables.initWidth * 0.2, Variables.initHeight * 0.2, 
            false, false, false, false);
    BackgroundImage gameBackgroundImg = new BackgroundImage(
            gameImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
            BackgroundPosition.DEFAULT, gameBackgroundSize);
    Background gameBackground = new Background(gameBackgroundImg);

    backgroundPane = new Pane();
    backgroundPane.setMinWidth(initWidth);
    backgroundPane.setMinHeight(initHeight);
    backgroundPane.setBackground(background);
    
    gameBackgroundPane = new Pane();
    gameBackgroundPane.setMinWidth(initWidth);
    gameBackgroundPane.setMinHeight(initHeight);
    gameBackgroundPane.setBackground(gameBackground);
    
    //Faces of the die
    dieFaces[0] = new Image("file:resources/images/die_face_1.png");
    dieFaces[1] = new Image("file:resources/images/die_face_2.png");
    dieFaces[2] = new Image("file:resources/images/die_face_3.png");
    dieFaces[3] = new Image("file:resources/images/die_face_4.png");
    dieFaces[4] = new Image("file:resources/images/die_face_5.png");
    dieFaces[5] = new Image("file:resources/images/die_face_6.png");
    //Explosion sprites
    explosionImgs[0] = new Image(
            "file:resources/images/explosion1.png");
    explosionImgs[1] = new Image(
            "file:resources/images/explosion2.png");
    explosionImgs[2] = new Image(
            "file:resources/images/explosion3.png");
    explosionImgs[3] = new Image(
            "file:resources/images/explosion4.png");
    explosionImgs[4] = new Image(
            "file:resources/images/explosion5.png");
    explosionImgs[5] = new Image(
            "file:resources/images/explosion6.png");
    explosionImgs[5] = new Image(
            "file:resources/images/explosion7.png");
    explosionImgs[5] = new Image(
            "file:resources/images/explosion8.png");
    explosionImgs[5] = new Image(
            "file:resources/images/explosion9.png");

    //Bases
    //Player1
    fields[0] = new Point2D(0.215, 0.217); //
    fields[1] = new Point2D(0.268, 0.217); //
    fields[2] = new Point2D(0.215, 0.267); //
    fields[3] = new Point2D(0.268, 0.267); //
    //Player2
    fields[4] = new Point2D(0.678, 0.215); //
    fields[5] = new Point2D(0.733, 0.215); //
    fields[6] = new Point2D(0.678, 0.265); //
    fields[7] = new Point2D(0.733, 0.265); //
    //Player3
    fields[8] = new Point2D(0.215, 0.682); //
    fields[9] = new Point2D(0.268, 0.682); //
    fields[10] = new Point2D(0.216, 0.74); //
    fields[11] = new Point2D(0.267, 0.739);//
    //Player4
    fields[12] = new Point2D(0.68, 0.685); //
    fields[13] = new Point2D(0.733, 0.685);//
    fields[14] = new Point2D(0.68, 0.74);  //
    fields[15] = new Point2D(0.733, 0.74); //
    //regular Fields starting with the first field outside of player1
    //base
    fields[16] = new Point2D(0.217, 0.425);//
    fields[17] = new Point2D(0.262, 0.425);//
    fields[18] = new Point2D(0.312, 0.425);//
    fields[19] = new Point2D(0.361, 0.425);//
    fields[20] = new Point2D(0.423, 0.422);//
    //now we are going up
    fields[21] = new Point2D(0.423, 0.37); //
    fields[22] = new Point2D(0.423, 0.317);//
    fields[23] = new Point2D(0.423, 0.265);//
    fields[24] = new Point2D(0.423, 0.219);//
    //now to the right
    fields[25] = new Point2D(0.478, 0.217);//
    fields[26] = new Point2D(0.53, 0.216); //
    //now down
    fields[27] = new Point2D(0.53, 0.264); //
    fields[28] = new Point2D(0.53, 0.312); //
    fields[29] = new Point2D(0.53, 0.362); //
    fields[30] = new Point2D(0.53, 0.422); //
    //now to the right
    fields[31] = new Point2D(0.577, 0.422);//
    fields[32] = new Point2D(0.63, 0.422); //
    fields[33] = new Point2D(0.683, 0.422);//
    fields[34] = new Point2D(0.733, 0.422);//
    //now down
    fields[35] = new Point2D(0.733, 0.477);//
    fields[36] = new Point2D(0.733, 0.53); //
    //now left
    fields[37] = new Point2D(0.683, 0.53); //
    fields[38] = new Point2D(0.63, 0.53);  //
    fields[39] = new Point2D(0.577, 0.53); //
    fields[40] = new Point2D(0.53, 0.531); //
    //now down
    fields[41] = new Point2D(0.53, 0.58);  //
    fields[42] = new Point2D(0.53, 0.63);  //
    fields[43] = new Point2D(0.53, 0.681); //
    fields[44] = new Point2D(0.53, 0.738); //
    //now left
    fields[45] = new Point2D(0.478, 0.737);//
    fields[46] = new Point2D(0.423, 0.736);//
    //now up
    fields[47] = new Point2D(0.423, 0.685);//
    fields[48] = new Point2D(0.423, 0.632);//
    fields[49] = new Point2D(0.423, 0.581);//
    fields[50] = new Point2D(0.423, 0.53); //
    //now left
    fields[51] = new Point2D(0.362, 0.528);//
    fields[52] = new Point2D(0.312, 0.528);//
    fields[53] = new Point2D(0.262, 0.528);//
    fields[54] = new Point2D(0.217, 0.528);//
    //now up
    fields[55] = new Point2D(0.218, 0.478);//
    //goal fields
    //Player1
    fields[56] = new Point2D(0.266, 0.478);//
    fields[57] = new Point2D(0.319, 0.478);//
    fields[58] = new Point2D(0.372, 0.478);//
    fields[59] = new Point2D(0.422, 0.478);//
    //Player2
    fields[60] = new Point2D(0.475, 0.267);//
    fields[61] = new Point2D(0.475, 0.32); //
    fields[62] = new Point2D(0.475, 0.373);//
    fields[63] = new Point2D(0.475, 0.42); //
    //Player3
    fields[64] = new Point2D(0.685, 0.477);//
    fields[65] = new Point2D(0.635, 0.477);//
    fields[66] = new Point2D(0.582, 0.477);//
    fields[67] = new Point2D(0.529, 0.477);//
    //Player4
    fields[68] = new Point2D(0.476, 0.686);//
    fields[69] = new Point2D(0.476, 0.633);//
    fields[70] = new Point2D(0.476, 0.582);//
    fields[71] = new Point2D(0.476, 0.532);//
  }
}
