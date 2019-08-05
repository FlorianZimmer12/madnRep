
package madn.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import madn.gui.Profile;
import madn.gui.SaveGame;
import madn.logic.Constants;

public class DataManager {

  public DataManager () {
    
  }
  
  public static void newGame (String[] players) {
    String output = "";
    for(int i = 0; i < players.length; i++) {
      if(players[i] != null) {
        if(!output.equals ("")) {
          output += ", ";
        } 
        output += players[i];
      }
    }
    System.out.println ("Created new save file with players:\n" +
                        output);
  }
  
  public static void save (int diceroll, int movedFigureId) {
    System.out.println ("Saved turn with diceroll " + diceroll + 
                        " and Figure " + movedFigureId);
  }
  
  public static void saveReplay () {
    System.out.println ("Saved finished game as replay");
  }
  
  public static void manualSave(String name){
    System.out.println("Manually saved game under name " + name);
  }
  
  public static boolean isReplay (String savegame) {
    Scanner s = new Scanner(System.in);
    String input;
    System.out.println ("Is this savegame a replay? [y/n]");
    do{
      input = s.nextLine ();
    } while (!(input.startsWith ("y") || input.startsWith ("n")));
    return input.startsWith ("y");
  }
  
  public static String[] loadPlayers (String savegame) {
    Scanner s = new Scanner(System.in);
    String[] playerNames = new String[Constants.NUM_PLAYERS];
    String input;
    System.out.println ("Input Player names (bot names start with " + 
                        "\"\\n\", leave empty to skip a position):");
    for(int i = 0; i < playerNames.length; i++) {
      System.out.println ("Position " + (i + 1) + ":");
      playerNames[i] = s.nextLine ();
    }
    return playerNames;
  }
  
  public static int[][] loadTurns (String savegame) {
    int[][] turns = new int[100][2];
    Scanner s = new Scanner(System.in);
    String stringInput;
    int input = 0;
    boolean stop = false;
    System.out.println ("Input turns, consisting of a diceroll and " + 
                        "the ID of the moved figure (leave input " + 
                        "empty to stop, the maximum amount of turns " + 
                        "to input is 100):");
    for(int i = 0; i < turns.length; i++) {
      System.out.println ("Turn " + ((i / 2) + 1));
      
      do {
        System.out.println ("Diceroll:");
        stringInput = s.nextLine ();
        if(stringInput == "") {
          stop = true;
          break;
        }
        try {
          input = Integer.valueOf (stringInput);
        } catch (NumberFormatException e) {
          continue;
        }
      } while (!((input >= 1) && (input <= 6)));
      if(stop) {
        break;
      }
      turns[i][0] = input;
      
      do {
        System.out.println ("Figure ID:");
        stringInput = s.nextLine ();
        if(stringInput == "") {
          stop = true;
          break;
        }
        try {
          input = Integer.valueOf (stringInput);
        } catch (NumberFormatException e) {
          continue;
        }
      } while (!((input >= 0) && (input <= (Constants.NUM_PLAYERS * 
               Constants.NUM_FIGURES))));
      if(stop) {
        turns[i][0] = 0;
        break;
      }
      turns[i][1] = input;
    }
    return turns;
  }
  
  public static List<SaveGame> loadSaveGames () {
    ArrayList<SaveGame> savegames = new ArrayList<>();
    Scanner s = new Scanner(System.in);
    String input, name, date;
    System.out.println ("Input savegames, consisting of a name and " + 
                        "a date (leave input empty to stop):");
    do {
      System.out.println ("New Savegame");
      System.out.println ("Savegame name: ");
      input = s.nextLine ();
      if(input.equals ("")) {
        break;
      }
      name = input;
      System.out.println ("Savegame date: ");
      input = s.nextLine ();
      if(input.equals ("")) {
        break;
      }
      date = input;
      savegames.add (new SaveGame(name, date));
    } while (true);
    return savegames;
  }
  
  public static List<Profile> loadProfile () {
    ArrayList<Profile> profiles = new ArrayList<>();
    Scanner s = new Scanner(System.in);
    String input, name, url;
    boolean isBot;
    System.out.println ("Input savegames, consisting of a name and " + 
                        "an image URL (leave input empty to stop):");
    do {
      System.out.println ("New Profile");
      System.out.println ("Profile name: ");
      input = s.nextLine ();
      if(input.equals ("")) {
        break;
      }
      name = input;
      System.out.println ("Profile image URL: ");
      input = s.nextLine ();
      if(input.equals ("")) {
        break;
      }
      url = input;
      System.err.println ("Is this profile a bot? (y/n)");
      input = s.nextLine ();
      if(input.equals ("")) {
        break;
      }
      isBot = input.startsWith ("y");
      profiles.add (new Profile(name, url, isBot));
    } while (true);
    return profiles;
  }
  
  public static void saveProfile (String profileName) {
    System.out.println ("Profile " + profileName + " has been saved");
  }
  
}
