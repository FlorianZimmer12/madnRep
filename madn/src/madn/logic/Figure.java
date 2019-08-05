/* Represents and provides methods to manipulate a figure.
 * 
 * File: Figure.java               Author:  Philipp Keller 
 * Date: 14.11.18                  
 * 
 */ 
package madn.logic;

public class Figure {
  
  private Player player;
  private int figureId;
  private int field;
  
  /**
   * Creates a figure assigned to the specified player using the
   * specified ID.
   * @param player owner of the figure
   * @param figureId ID of the figure
   */
  public Figure (Player player, int figureId) {
    this.player = player;
    this.figureId = figureId;
  }
  /**
   * Returns this figure's assigned player.
   * @return figure's assigned player
   */
  public Player getPlayer () {
    return player;
  }
   /**
    * Returns the field this figure is standing on.
    * @return field this figure is standing on
    */
  public int getField () {
    return field;
  }
  
  /**
   * Moves the figure to the specified field.
   * @param field destination field
   * @throws IllegalArgumentException if the passed value is too big or
   * to small given the size of the gameboard
   */
  public void setField (int field) throws IllegalArgumentException {
    //check if field exists
    if (field < 0 || field >= ((Constants.NUM_PLAYERS *
        Constants.NUM_FIGURES) * 2 + Constants.NUM_FIELDS)) {
      throw new IllegalArgumentException("Field is not on gameboard");
    }
    this.field = field;
  }
  
  /**
   * Returns this figure's ID.
   * @return this figure's ID
   */
  public int getFigureId () {
    return figureId;
  }
  
}
