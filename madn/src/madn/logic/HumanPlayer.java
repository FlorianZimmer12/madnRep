/* Represents a human player. Methods inform GUI to prompt the player
 * to make an input in order to progress the game.
 * 
 * File: HumanPlayer.java          Author:  Philipp Keller 
 * Date: 14.11.18                 
 * 
 */ 
package madn.logic;

public class HumanPlayer extends Player {

  public HumanPlayer (Gameboard gb, String name) {
    super (gb, name);
  }
  
  /**
   * Informs the GUI that the dice can be rolled.
   */
  @Override
  public void rollDice () {
    gb.enableDice ();
  }

  /**
   * Informs GUI that the specified figures can be moved.
   * @param movableFigures figures that can be moved
   */
  @Override
  public void selectFigure (Figure[] movableFigures) {
    gb.showMovableFigures (movableFigures);
  }

}
