/* Provides necessary methods for a user interface.
 * 
 * File: UserInterface.java        Author:  Philipp Keller 
 * Date: 3.1.19                    
 * 
 */

package madn.logic;

public interface UserInterface {
  
  /**
   * Sets whether the player can roll the dice.
   * @param enable boolean determining whether to enable or disable
   *               the dice
   */
  public void enableDice (boolean enable);
  
  /**
   * Displays the passed diceroll.
   * @param diceroll diceroll to display
   */
  public void rollDice (int diceroll);
  
  /**
   * Shows the player which figures he can move.
   * @param figureIds IDs of the movable figures
   */
  public void highlightFigures (int[] figureIds);
  
  /**
   * Displays the movement of the figure with the specified ID moving
   * to the specified destination field.
   * @param figureId ID of figure to be moved
   * @param destination figure's destination field
   */
  public void moveFigure (int figureId, int destination);
  
  /**
   * Displays the movement of the figure with the specified ID moving
   * to the specified destination field in addition to displaying the 
   * movement of the figure of another specified ID back to it's base.
   * @param figureId ID of figure to be moved
   * @param destination figure's destination field
   * @param resetId ID of the figure to be reset
   * @param resetDestination reset figure's destination field
   */
  public void moveFigure (int figureId, int destination, int resetId,
                          int resetDestination);
  
  /**
   * Displays the turn changing to the specified player's.
   * @param playerName player whose turn it is
   */
  public void changeTurn (String playerName);
  
  /**
   * Display which player has won the game.
   * @param winnerName name of the winner
   */
  public void victory (String winnerName);
  
}
