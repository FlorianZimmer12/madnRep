/* Initializes objects of the different views and the variables class;
 * starts the background music by initializing an object of 
 * MediaHandler.java and starts the main menu.
 *
 * File: GuiStart.java Author: Florian Zimmer
 * Date: 25.12.2018 Version: 1.0
 *
 */
package madn.gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class GuiStart extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  //Needs to be used by launch so that the JavaFX Stage can be set.
  public void start(Stage theStage) {
    MediaHandler mediaHandler = new MediaHandler();
    GameView gameView = new GameView();
    OptionsView optionsView = new OptionsView();
    StartView startView = new StartView();
    NetworkView networkView = new NetworkView();
    ServerView serverView = new ServerView();
    SaveView saveView = new SaveView();
    PlayerView playerView = new PlayerView();
    SFXHandler sfxHandler = new SFXHandler();
    Object[] viewsArray = new Object[Variables.numViews];
    try {
      mediaHandler.start(theStage);
    } catch (Exception e2) {
      e2.printStackTrace();
    }
    try {
      sfxHandler.start(theStage);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    
    Variables variables = new Variables();
    Variables.profilesList.add(0, Variables.prof1);
    Variables.profilesList.add(1, Variables.prof2);
    Variables.profilesList.add(2, Variables.prof3);
    Variables.profilesList.add(3, Variables.prof4);
    Variables.profilesList.add(4, Variables.prof5);
    Variables.profilesList.add(5, Variables.prof6);
    Variables.profilesList.add(6, Variables.prof7);
    Variables.profilesList.add(7, Variables.prof8);
    Variables.profilesList.add(8, Variables.prof9);
    Variables.profilesList.add(9, Variables.bot);
    
    viewsArray[0] = mediaHandler;
    viewsArray[1] = startView;
    viewsArray[2] = gameView;
    viewsArray[3] = optionsView;
    viewsArray[4] = networkView;
    viewsArray[5] = serverView;
    viewsArray[6] = saveView;
    viewsArray[7] = playerView;
    viewsArray[8] = sfxHandler;
    startView.start(theStage, viewsArray);
  }
}
