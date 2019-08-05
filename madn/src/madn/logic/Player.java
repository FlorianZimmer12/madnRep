/* Represents a player of the game. Provides methods to be called
 * whenever player interaction is required.
 * 
 * File: Player.java               Author:  Philipp Keller 
 * Date: 14.11.18                 
 * 
 */ 
package madn.logic;

public abstract class Player {
  
  protected Gameboard gb;
  protected Figure[] figures;
  protected String name;
  
  public Player (Gameboard gb, String name) {
    this.gb = gb;
    this.name = name;
    figures = new Figure[Constants.NUM_FIGURES];
  }
  
  /**
   * Performs player action when the dice can be rolled.
   */
  public abstract void rollDice ();
  
  /**
   * Makes the player choose which figure to move.
   * @param movableFigures figures that can be moved
   */
  public abstract void selectFigure (Figure[] movableFigures);
  
  /**
   * Returns an array containing this player's figures.
   * @return this player's figures
   */
  public Figure[] getFigures () {
    return figures;
  }
  
  /**
   * Sets this player's figures to be the specified figures.
   * @param figures figures to be set
   */
  public void setFigures (Figure[] figures) {
    this.figures = figures;
  }
  
  /**
   * Returns this player's name.
   * @return this player's name
   */
  public String getName () {
    return name;
  }
  
}
