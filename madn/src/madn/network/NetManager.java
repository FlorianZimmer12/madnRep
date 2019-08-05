
package madn.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import madn.gui.Server;
import madn.logic.GameboardInterface;

public class NetManager implements GameboardInterface {

  @Override
  public void addPlayer (String playerName, int position, boolean isBot) {
    System.out.println ("Calling addPlayer on server with arguments: "
                        + playerName + ", " + position + ", " + isBot);
  }

  @Override
  public void startGame () {
    System.out.println ("Calling startGame on server");
  }

  @Override
  public void rollDice () {
    System.out.println ("Calling rollDice on server");
  }
  
  @Override
  public void determineMovableFigures () {
    System.out.println ("Calling determineMovableFigures on server");
  }
  
  @Override
  public void moveFigure (int figureId) {
    System.out.println ("Calling moveFigure on server");
  }
  
  @Override
  public void endTurn () {
    System.out.println ("Calling endTurn on server");
  }
  
  @Override
  public int getDestination (int figureId) {
    Scanner s = new Scanner(System.in);
    System.out.println ("Calling getDestination on server with " + 
                        "parameter: " + figureId);
    System.out.println ("Input destination of figure to be moved: ");
    return Integer.valueOf (s.nextLine ());
  }
  
  public static List<Server> loadServers () {
    ArrayList<Server> servers = new ArrayList<>();
    Scanner s = new Scanner(System.in);
    String input, name, ip, password;
    System.out.println ("Input server, consisting of a name, IP and " + 
                        "a password (leave input empty to stop):");
    do {
      System.out.println ("New Server");
      System.out.println ("Server name: ");
      input = s.nextLine ();
      if(input.equals ("")) {
        break;
      }
      name = input;
      System.out.println ("Server IP: ");
      input = s.nextLine ();
      if(input.equals ("")) {
        break;
      }
      ip = input;
      System.out.println ("Server password: ");
      input = s.nextLine ();
      if(input.equals ("")) {
        break;
      }
      password = input;
      servers.add (new Server(name, ip, password));
    } while (true);
    return servers;
  }

  @Override
  public void load (String savegame) {
    System.out.println ("Calling load on server with argument: " + 
                        savegame);
  }

}
