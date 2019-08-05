/* Represents a bot (player controlled by the computer). Performs 
 * player actions when prompted.
 * 
 * File: Bot.java                  Author:  Philipp Keller 
 * Date: 14.11.18                 
 * 
 */ 
package madn.logic;

public class Bot extends Player {

  public Bot (Gameboard gb, String name) {
    super (gb, name);
  }

  /**
   * Simulates the bot rolling the dice.
   */
  @Override
  public void rollDice () {
    gb.rollDice ();
  }

  /**
   * Simulates the bot choosing a figure to move.
   * @param movableFigures figures that can be moved
   */
  @Override
  public void selectFigure (Figure[] movableFigures) {
    int numFigures = 0;
    for (int i = 0; i < movableFigures.length; i++) {
      if (movableFigures[i] != null) {
        numFigures++;
      } else {
        //if a figure is null, all following figures are also null
        break;
      }
    }
    switch (numFigures) {
      case 0:                           //no figures can be moved
        gb.endTurn ();
        break;
      case 1:                           //only one figure can be moved
        gb.moveFigure (movableFigures[0].getFigureId ());
        break;
      default:                          //2 or more figures can be moved
        int destination;
        int[] priority = new int[numFigures];
        for (int i = 0; i < numFigures; i++) {
          destination = gb.getDestination (
                        movableFigures[i].getFigureId ());
          //set initial priority
          priority[i] = 9;
          //check if a figure can reach the finish
          if (destination >= ((Constants.NUM_PLAYERS * 
             Constants.NUM_FIGURES) + Constants.NUM_FIELDS)) {
            priority[i] = 1;
          }
          //check if another figure can be captured
          else if (gb.getFigureOnField (destination) != null) {
            priority[i] = 2;
          }
          //check if figure can exit base
          else if (movableFigures[i].getField () < 
                   (Constants.NUM_PLAYERS * 
                   Constants.NUM_FIGURES)) {
            priority[i] = 3;
          }
          //check if figure is standing on a player's starting field
          else if (((movableFigures[i].getField () - 
                   (Constants.NUM_PLAYERS * 
                   Constants.NUM_FIGURES)) % (Constants.NUM_FIELDS / 
                   Constants.NUM_PLAYERS)) == 0) {
            priority[i] = 4;
          }
          //check if figure moves past another player's figure
          else {
            Figure figureAhead;
            for (int j = 1; j < gb.getDiceroll (); j++) {
              figureAhead = gb.getFigureOnField (gb.getDestination (
                            movableFigures[i].getFigureId (), j));
              if ((figureAhead != null) && (figureAhead.getPlayer () != 
                  this)) {
                priority[i] = 10;
                break;
              }
            }
          }
        }
        //determine and execute the move with the lowest priority
        int minPriority = 100;          
        int minPriorityIndex = 0;          
        for (int i = 0; i < priority.length; i++) {
          if (priority[i] < minPriority) {
            minPriority = priority[i];
            minPriorityIndex = i;
          }
        }
        gb.moveFigure (movableFigures[minPriorityIndex].getFigureId ());
        break;

    }
  }

}
