/* Contains the variables that depict a savegame. Also provides the 
 * getters/setters for these variables.
 *
 * File: SaveGame.java Author: Florian Zimmer
 * Date: 25.12.2019 Version: 1.0
 *
 */
package madn.gui;

import javafx.beans.property.SimpleStringProperty;

public class SaveGame {

  SimpleStringProperty name;
  SimpleStringProperty date;

  public SaveGame(String _name, String _date) {
    this.name = new SimpleStringProperty(_name);
    this.date = new SimpleStringProperty(_date);
  }

  public SimpleStringProperty nameProperty() {
    return name;
  }

  public SimpleStringProperty dateProperty() {
    return date;
  }

  public void setName(String _name) {
    name.set(_name);
  }

  public void setDate(String _date) {
    date.set(_date);
  }

  public void getName() {
    name.get();
  }

  public void getDate() {
    date.get();
  }
}
