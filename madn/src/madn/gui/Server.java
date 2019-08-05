/* Contains the variables that depict a game server. Also provides the 
 * getters/setters for these variables.
 *
 * File: Server.java Author: Florian Zimmer
 * Date: 25.12.2019 Version: 1.0
 *
 */
package madn.gui;

import javafx.beans.property.SimpleStringProperty;

public class Server {

  SimpleStringProperty name;
  SimpleStringProperty ip;
  SimpleStringProperty password;

  public Server(String _name, String _ip, String _password) {
    this.name = new SimpleStringProperty(_name);
    this.ip = new SimpleStringProperty(_ip);
    this.password = new SimpleStringProperty(_password);
  }

  public SimpleStringProperty nameProperty() {
    return name;
  }

  public SimpleStringProperty ipProperty() {
    return ip;
  }

  public SimpleStringProperty passwordProperty() {
    return password;
  }

  public void setName(String _name) {
    name.set(_name);
  }

  public void setIp(String _ip) {
    ip.set(_ip);
  }

  public void setPassword(String _password) {
    password.set(_password);
  }

  public void getName() {
    name.get();
  }

  public void getIp() {
    ip.get();
  }

  public void getPassword() {
    password.get();
  }

}
