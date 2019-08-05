/* Acts as an intermediary between gameboard and GUI. Each of this
 * class' methods (except the constructors) calls a method of either 
 * board or gui (specified / created in constructors) of the same or
 * similar name.
 * 
 * File: GameCoordinator.java      Author:  Philipp Keller 
 * Date: 13.12.18                 
 * 
 */ 
package madn.logic;

import madn.network.NetManager;
import madn.network.ServerNetManager;

public class GameCoordinator {
  
  private GameboardInterface board;
  private UserInterface gui;
  
  /**
   * Creates a server by creating an objects of ServerNetManager and
   * Gameboard.
   * @param name name of the server
   * @param password password of the server
   */
  public GameCoordinator (String name, String password) {
    this (new ServerNetManager (name, password), false);
  }
  
  /**
   * Creates Gameboard object for a local game, or NetManager object for
   * (a client of) a network game, and assigns the specified GUI.
   * @param gui GUI to be used for the game
   * @param networkGame indication whether the game is a network game
   *                    or a local game
   */
  public GameCoordinator (UserInterface gui, boolean networkGame) {

    if (networkGame) {
      //create client
      this.board = new NetManager ();
    } else {
      //create local game
      this.board = new Gameboard (this);
    }
    this.gui = gui;
  }
  
  public void addPlayer (String playerName, int position,
                         boolean isBot) {
    board.addPlayer (playerName, position, isBot);
  }
  
  public void startGame () throws Exception{
    board.startGame ();
  }
  
  public void enableDice (boolean enable) {
    gui.enableDice (enable);
  }
  
  public void rollDice (){
    board.rollDice ();
  }
  
  public void showDiceroll (int diceroll) {
    gui.rollDice (diceroll);
  }
  
  public void determineMovableFigures () {
    board.determineMovableFigures ();
  }
  
  public void showMovableFigures (int[] figureIds) {
    gui.highlightFigures (figureIds);
  }
  
  public int getDestination (int figureId) {
    return board.getDestination (figureId);
  }
  
  public void moveFigure (int figureId) {
    board.moveFigure (figureId);
  }
  
  public void showFigureMovement (int figureId, int destination) {
    gui.moveFigure (figureId, destination);
  }
  
  public void showFigureMovement (int figureId, int destination,
                                  int resetId, int resetDestination) {
    gui.moveFigure (figureId, destination, resetId, resetDestination);
  }
  
  public void endTurn () {
    board.endTurn ();
  }
  
  public void changeTurn (String playerName) {
    gui.changeTurn (playerName);
  }
  
  public void victory (String winnerName) {
    gui.victory (winnerName);
  }
  
  public void load (String savegame) {
    board.load (savegame);
  }
}
