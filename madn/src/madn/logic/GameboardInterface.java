/* Provides necessary methods for a gameboard.
 * 
 * File: GameboardInterface.java   Author:  Philipp Keller 
 * Date: 3.1.18                   
 * 
 */

package madn.logic;

public interface GameboardInterface {
  
  /**
   * Checks if enough players are registered and if so starts the game,
   * throws IllegalStateException otherwise.
   * @throws IllegalStateException if there are not enough players 
   * (less than 2) to start the game
   */
  public void startGame () throws IllegalStateException;
  
  /**
   * Creates a Player object based on the passed arguments and adds
   * that player to the game.
   * @param playerName name of the Player
   * @param position position of the player on the gameboard (1-4)
   * @param isBot boolean signifying whether the player is a bot
   */
  public void addPlayer (String playerName, int position,
                         boolean isBot);
  
  /**
   * Simulates a roll of the dice by randomly generating a number based
   * on the values of MIN_DICEROLL and MAX_DICEROLL in Constants.
   */
  public void rollDice ();
  
  /**
   * Determines which of their figures the current player can move given
   * their diceroll.
   */
  public void determineMovableFigures ();
  
  /**
   * Moves the figure with the specified ID according to the current
   * diceroll and calls resetFigure if the moved figure lands on the
   * same field as another figure, also saves this turn.
   * @param figureId ID of the figure to be moved
   */
  public void moveFigure (int figureId);
  
  /**
   * Checks if a player has won the game, and if so, informs the GUI
   * which player has won, otherwise changes the current player and 
   * calls rollDice of that player, thus starting the next turn.
   */
  public void endTurn ();
  
  /**
   * Loads a saved game by setting the players' names accordingly and 
   * moving all figures to their positions turn by turn while showing
   * the individual movements if the savegame is a replay or showing
   * only the movement to the figures' final position if it is not.
   * @param savegame name of the savegame to be loaded
   */
  public void load (String savegame);
  
  /**
   * Determines which field the specified figure moves to given the 
   * current diceroll.
   * @param figureId ID of the figure to be moved
   * @return destination field of the figure
   */
  public int getDestination (int figureId);
  
}
