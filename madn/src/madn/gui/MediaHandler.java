/* Starts the background music and provides the methods to
 * to turn it on/off as well as change the volume.
 *
 * File: MediaHandler.java Author: Florian Zimmer
 * Date: 25.12.2018 Version: 1.0
 *
 */
package madn.gui;

import java.io.File;
import java.net.MalformedURLException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class MediaHandler extends Application {

  boolean playing = true;
  private static MediaPlayer player = null;
  private static MediaPlayer player1 = null;
  
  //Initializes the media player and plays a song 
  public void start(Stage mediaStage) throws Exception {
    File f = new File("./resources/songs/song1.mp3");
    String urlString = null;
    try {
      urlString = f.toURI().toURL().toString();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    Media song = new Media(urlString);
    player = new MediaPlayer(song);
    MediaView mediaView = new MediaView(player);
    Group root = new Group(mediaView);
    Scene mediaScene = new Scene(root);
    mediaStage.setScene(mediaScene);
    mediaStage.show();
    player.setVolume(Variables.volume);
    player.play();
  }

  public void stop() {
    player.pause();
    playing = false;
  }

  public void play() {
    player.play();
    playing = true;
  }

  public void applyVolume(double new_volume) {
    Variables.volume = new_volume;
    player.setVolume(Variables.volume);
  } 
}
