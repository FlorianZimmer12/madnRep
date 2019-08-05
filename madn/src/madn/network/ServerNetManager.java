
package madn.network;

import madn.logic.UserInterface;

public class ServerNetManager implements UserInterface{
  
  public ServerNetManager (String name, String pass) {
    
  }

  @Override
  public void enableDice (boolean enable) {
    System.out.println ("Calling enableDice on client with argument: "
                        + enable);
  }

  @Override
  public void rollDice (int diceroll) {
    System.out.println ("Calling rollDice on clients with argument: "
                        + diceroll);
  }

  @Override
  public void highlightFigures (int[] figureIds) {
    String figureIdString = "";
    for(int id : figureIds) {
      if(!figureIdString.equals ("")) {
        figureIdString += ", ";
      }
      figureIdString += id;
    }
    System.out.println ("Calling highlightFigures on client with" +
                        "argument: " + figureIdString);
  }

  @Override
  public void moveFigure (int figureId, int destination) {
    System.out.println ("Calling moveFigure on clients with " + 
                        "arguments: " + figureId + ", " + destination);
  }

  @Override
  public void moveFigure (int figureId, int destination, int resetId, int resetDestination) {
    System.out.println ("Calling moveFigure on clients with " + 
                        "arguments: " + figureId + ", " + destination
                        + ", " + resetId + ", " + resetDestination);
  }
  
  @Override
  public void changeTurn (String playerName) {
    System.out.println ("Calling changeTurn on clients with " + 
                        "argument: " + playerName);
  }

  @Override
  public void victory (String winnerName) {
    System.out.println ("Calling victory on clients with " + 
                        "argument: " + winnerName);
  }

}
