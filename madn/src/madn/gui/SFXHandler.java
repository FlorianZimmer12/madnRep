/* Provides the methods to play sound effects.
 *
 * File: SFXHandler.java Author: Florian Zimmer
 * Date: 18.01.2019 Version: 1.0
 *
 * History: 
 *
 */
package madn.gui;

import java.io.File;
import static java.lang.Thread.sleep;
import java.net.MalformedURLException;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class SFXHandler {

  boolean playing = true;
  private static MediaPlayer player1 = null;
  Stage mediaStage;

  public void start(Stage mediaStage) throws Exception {
    this.mediaStage = mediaStage;
    File sfx1 = new File("./resources/songs/sfx1.mp3");
    String urlString1 = null;
    try {
      urlString1 = sfx1.toURI().toURL().toString();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    Media song = new Media(urlString1);
    player1 = new MediaPlayer(song);
    MediaView mediaView = new MediaView(player1);
    Group root = new Group(mediaView);
    Scene mediaScene = new Scene(root);
    mediaStage.setScene(mediaScene);
    mediaStage.show();
    player1.setVolume(Variables.volume);
  }

  public void playMoveSound() throws InterruptedException {
    player1.stop();
    player1.play();
  }

  public void applyVolume(double new_volume) {
    Variables.volume = new_volume;
    player1.setVolume(Variables.volume * 2);
  }
}
