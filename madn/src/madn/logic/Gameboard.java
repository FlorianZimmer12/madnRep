/* Manages the game's logic and performs most of the related
 * calculations.
 * 
 * File:  Gameboard.java     Author:  Philipp Keller 
 * Date:  12.12.18    
 *
 */ 
package madn.logic;

import madn.data.DataManager;

public class Gameboard implements GameboardInterface{
  
  private Player[] players;             //players of the current game
  private int diceroll;                 //current diceroll                                     
  private int numDicerolls;             //number of dicerolls this turn
  private int currentPlayer;            //position of the current player
  //figure movement animations are skipped if true
  private boolean skipAnimations;
  //signifies a savegame is currently being loaded
  private boolean loading;
  private GameCoordinator coordinator;
  
  /**
   * Initializes the Object.
   */
  public Gameboard (GameCoordinator coordinator) {
    players = new Player[Constants.NUM_PLAYERS];
    diceroll = 0;
    numDicerolls = 0;
    currentPlayer = -1;
    skipAnimations = false;
    loading = false;
    this.coordinator = coordinator;
  }
  
  /**
   * @inheritDoc 
   */
  @Override
  public void startGame () throws IllegalStateException {
    int numPlayers = 0;
    //determine number of players and starting player
    String[] playerNames = new String[players.length];
    for(int i = 0; i < players.length; i++) {
      if(players[i] != null) {
        //set starting player
        if(currentPlayer == -1) {
          currentPlayer = i;
        }
        numPlayers++;
        if(players[i] instanceof Bot) {
          //add \n to bot names to recognize them when loading
          playerNames[i] = "\n" + players[i].getName ();
        } else {
          playerNames[i] = players[i].getName ();
        }
      }
    }
    if(numPlayers < 2) {
      throw new IllegalStateException("Not enough players");
    }
    DataManager.newGame (playerNames);
    coordinator.changeTurn (players[currentPlayer].getName ());
    players[currentPlayer].rollDice ();
  }
  
  /**
   * @inheritDoc 
   */
  @Override
  public void addPlayer (String playerName, int position,
                                 boolean isBot) {
    //replace backslashes with regular slashes
    playerName = playerName.replace ("\\", "/");
    //position from GUI starts at 1 instead of 0
    position--;
    if (isBot) {
      players[position] = new Bot (this, playerName);
    } else {
      players[position] = new HumanPlayer (this, playerName);
    }
    
    int[] figureIds = generateFigureIds (position);
    Figure[] figures = new Figure[Constants.NUM_FIGURES];
    for (int i = 0; i < figures.length; i++) {
      figures[i] = new Figure(players[position], figureIds[i]);
      //put new player's figures in base based on position
      figures[i].setField ((position * Constants.NUM_FIGURES) + i);
    }
    players[position].setFigures (figures);
  }
  
  /**
   * Allows the player to roll the dice.
   */
  public void enableDice () {
    coordinator.enableDice (true);
  }
  
  /**
   * @inheritDoc 
   */
  @Override
  public void rollDice () {
    coordinator.enableDice (false);
    diceroll = (int) ((Math.random () * Constants.MAX_DICEROLL)
               + Constants.MIN_DICEROLL); 
    numDicerolls++;
    System.out.println ("Player " + (currentPlayer + 1) + " rolled a " + 
                        diceroll);
    coordinator.showDiceroll(diceroll);
  }
  
  /**
   * @inheritDoc 
   */
  @Override
  public void determineMovableFigures () {
    int numMovableFigures = 0;
    int numBaseFigures = 0;             //unmovable figures in the base
    int startField = (Constants.NUM_PLAYERS * 
                     Constants.NUM_FIGURES) + (currentPlayer * 
                     (Constants.NUM_FIELDS / 
                     Constants.NUM_PLAYERS));
    Figure[] movableFigures = new Figure[Constants.NUM_FIGURES];
    for (Figure figure : players[currentPlayer].getFigures ()) {
      //check if figure is in the base
      if (figure.getField () < (Constants.NUM_PLAYERS * 
          Constants.NUM_FIGURES)) {
        //check if figure can exit the base
        if ((diceroll == Constants.MAX_DICEROLL) && (
            (getFigureOnField (startField) == null) || (
            getFigureOnField (startField).getPlayer ()
            != players[currentPlayer]))) {
          movableFigures[numMovableFigures] = figure;
          numMovableFigures++;
        } else {
          numBaseFigures++;
        }
        continue;
        //check if figure is in finish
      } else if (figure.getField () >= ((Constants.NUM_PLAYERS * 
                 Constants.NUM_FIGURES) + Constants.NUM_FIELDS)) {
        continue;
        //check if figure would move past the end of the finish
      } else if (getDestination (figure.getFigureId ()) >= (
                 Constants.NUM_PLAYERS * Constants.NUM_FIGURES) + 
                 Constants.NUM_FIELDS + ((currentPlayer + 1) * 
                 Constants.NUM_FIGURES)) {
        continue;
      } else {
        int destination = getDestination (figure.getFigureId());
        //check if field is occupied
        if ((getFigureOnField (destination) == null) ||
            getFigureOnField (destination).getPlayer () !=
            players[currentPlayer]) {
          movableFigures[numMovableFigures] = figure;
          numMovableFigures++;
        }
      }
    }
    /* if all figures are in the base and can't move, roll again up to 3 
     * times in total
     */
    if ((numBaseFigures == Constants.NUM_FIGURES) && 
        (numMovableFigures == 0) && (numDicerolls < 3)) {
      players[currentPlayer].rollDice ();
    } else {
      numDicerolls = 0;
      /* save the game here if no figures can be moved and thus save
       * will not be called in moveFigure
       */
      if (numMovableFigures == 0) {
        save (currentPlayer * (-1));
      }
      players[currentPlayer].selectFigure (movableFigures);
    }
  }
  
  /**
   * Informs the GUI that the specified figures can be moved.
   * @param movableFigures figures the player can move
   */
  public void showMovableFigures (Figure[] movableFigures) {
    //convert figures to IDs
    int[] figureIds = new int[movableFigures.length];
    for(int i = 0; i < movableFigures.length; i++) {
      if (movableFigures[i] == null) {
        figureIds[i] = -1;
      } else {
        figureIds[i] = movableFigures[i].getFigureId ();
      }
    }
    coordinator.showMovableFigures (figureIds);
  }
  
  /**
   * @inheritDoc 
   */
  @Override
  public void moveFigure (int figureId) {
    Figure reset = null;                //figure to be reset
    /* after this calculation the ID always corresponds to the 
     * position of the figure in the player's array
     */
    int figureNumber = figureId % Constants.NUM_FIGURES;
    Figure figure = players[currentPlayer].getFigures()[figureNumber];
    
    int destination = getDestination(figure.getFigureId());
    //reset figure on destination field if necessary
    if (getFigureOnField (destination) != null) {
      reset = getFigureOnField (destination);
      resetFigure(reset);
    }
    System.out.println ("Figure " + figure.getFigureId () + 
                        " moved from Field " + figure.getField() +
                        " to Field " + destination);
    figure.setField(destination);
    
    //skip saving if a game is currently being loaded
    if(!loading) {
      save (figureId);
    }
    
    if (!skipAnimations) {
      //inform gui
      if (reset == null) {
        coordinator.showFigureMovement (figureId, figure.getField());
      } 
      //additionally pass figure to reset
      else {                      
        coordinator.showFigureMovement (figureId, figure.getField(),
                                        reset.getFigureId(),
                                        reset.getField());
      }
    }
  }
  
  /**
   * Moves the specified figure back to its owner's base.
   * @param figure figure to be reset
   */
  private void resetFigure (Figure figure) {
    int position = getPlayerPosition (figure.getPlayer ());

    /* check player's base fields backwards and place the figure on the 
     * first empty one.
     */
    for (int i = ((position + 1) * Constants.NUM_PLAYERS) - 1;
         i >= position * Constants.NUM_PLAYERS; i--) {
      if (getFigureOnField (i) == null) {
        figure.setField (i);
        System.out.println ("Figure " + figure.getFigureId() + " reset");
        break;
      }
    }
  }
  
  /**
   * @inheritDoc 
   */
  @Override
  public void endTurn () {
    //check if the current player has won the game
    boolean gameWon = true;
    for (Figure figure : players[currentPlayer].getFigures ()) {
      if (figure.getField () < ((Constants.NUM_PLAYERS * 
          Constants.NUM_FIGURES) + Constants.NUM_FIELDS)) {
        gameWon = false;
        break;
      }
    }
    if (gameWon) {
      DataManager.saveReplay ();
      coordinator.victory (players[currentPlayer].getName ());
    } else {
      //change current player, skipping empty positions
      do {
        currentPlayer = (currentPlayer + 1) % Constants.NUM_PLAYERS;
      } while (players[currentPlayer] == null);
      coordinator.changeTurn(players[currentPlayer].getName());
      players[currentPlayer].rollDice();
    }
  }
  
  /**
   * Returns Figure standing on the specified field or null if the field
   * is empty.
   * @param field field whose figure is to be determined
   * @return figure standing on the specified field
   */
  public Figure getFigureOnField (int field) {
    for (Player player : players) {
      if(player == null) {
        continue;
      }
      for (Figure figure : player.getFigures()) {
        if (figure.getField () == field) {
          return figure;
        }
      }
    }
    return null;
  }
  
  /**
   * Generates a unique (integer-) ID to be give to a figure.
   * @return unique ID for the figure
   */
  public int[] generateFigureIds (int position) {
    //ID based on player position and number of player's figures
    int[] figureIds = new int[Constants.NUM_FIGURES];
    for(int i = 0; i < figureIds.length; i++) {
      figureIds[i] = (Constants.NUM_FIGURES * position) + i;
    }
    return figureIds;
  }
  
  /**
   * Saves the current state of the game.
   */
  public void save (int movedFigureId) {
    DataManager.save (diceroll, movedFigureId);
  }
  
  /**
   * @inheritDoc 
   */
  @Override
  public void load (String savegame) {
    this.loading = true;
    this.skipAnimations = !DataManager.isReplay (savegame);
    String[] playerNames = DataManager.loadPlayers (savegame);
    int[][] turns = DataManager.loadTurns (savegame);
    
    //create Players
    for (int i = 0; i < playerNames.length; i++) {
      if (!playerNames[i].equals ("")) {
        //check if Player is a bot
        if (playerNames[i].startsWith ("\n")) {
          //remove \n from bot name
          addPlayer (playerNames[i].substring (2), i, true);
        } else {
          addPlayer (playerNames[i], i, false);
        }
      }
    }
    
    //move figures turn by turn
    for (int[] turn : turns) {
      //check if diceroll or figureId is out of bounds
      if ((turn[1] < 0) || (turn[1] > (Constants.NUM_PLAYERS * 
          Constants.NUM_FIGURES)) || (turn[0] < Constants.MIN_DICEROLL)
          || (turn[0] > Constants.MAX_DICEROLL)) {
        continue;
      }
      this.diceroll = turn[0];
      moveFigure (turn[1]);
    }
    
    //set current Player based on the last turn's player
    currentPlayer = (turns[turns.length][1] + 1) % 
                    Constants.NUM_PLAYERS;
    
    //skip empty positions
    while(players[currentPlayer] == null) {
      currentPlayer = (currentPlayer + 1) % Constants.NUM_PLAYERS;
    }
    
    if (skipAnimations) {
      //inform gui to move all figures to their new position
      for (Player player : players) {
        for (Figure figure : player.getFigures ()) {
          coordinator.showFigureMovement (figure.getFigureId (),
                                          figure.getField ());
        }
      }
    }
    
    this.loading = false;
    //continue the game if necessary
    if(this.skipAnimations) {
      this.skipAnimations = false;
      players[currentPlayer].rollDice ();
    }
  }
  
  /**
   * @inheritDoc
   */
  @Override
  public int getDestination(int figureId) {
    return getDestination(figureId, diceroll);
  }
  
  /**
   * Determines which field the specified figure moves to given the 
   * specified distance.
   * @param figureId ID of the figure to be moved
   * @param distance distance the figure is to be moved
   * @return destination field of the figure
   */
  public int getDestination(int figureId, int distance) {
      
    figureId %= Constants.NUM_FIGURES;
    Figure figure = players[currentPlayer].getFigures()[figureId];
    
    int destination = figure.getField();
    //check if figure is standing in base
    if (figure.getField () < (Constants.NUM_PLAYERS *
      Constants.NUM_FIGURES)) {
      //return player's start field
      return (Constants.NUM_PLAYERS * Constants.NUM_FIGURES) +
             (currentPlayer * (Constants.NUM_FIELDS /
             Constants.NUM_PLAYERS));
    }
    //move figure forward one field at a time
    for (int i = 1; i <= distance; i++) {
      //check if the first field has been reached
      if ((destination + i) == (Constants.NUM_PLAYERS * 
          Constants.NUM_FIGURES + Constants.NUM_FIELDS)) {
        //reset number of field to the beginning (e.g. 56 -> 16)
        destination -= Constants.NUM_FIELDS;
      }
      //check if start field has been reached
      if ((destination + i) == ((Constants.NUM_PLAYERS * 
          Constants.NUM_FIGURES) + (currentPlayer *
          (Constants.NUM_FIELDS / Constants.NUM_PLAYERS)))) {
        //move figure to respective finish
        destination += (Constants.NUM_FIELDS - (currentPlayer * 
                       (Constants.NUM_FIELDS / Constants.NUM_PLAYERS))
                       + (currentPlayer * Constants.NUM_FIGURES));
      }
    }
    return (destination + distance);
  }
  
  /**
   * Returns the position of the specified player.
   * @param player player whose position is to be determined
   * @return position of the player
   */
  private int getPlayerPosition (Player player) {
    for (int i = 0; i < Constants.NUM_PLAYERS; i++) {
      if (player == players[i]) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Returns the current diceroll.
   * @return current diceroll
   */
  public int getDiceroll () {
    return diceroll;
  }
  
}
