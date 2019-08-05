/* Contains the variables that depict an ingame profile.
 * Also provides the getters/setters for these variables.
 *
 * File: Profile.java Author: Florian Zimmer
 * Date: 25.12.2019 Version: 1.0
 *
 */
package madn.gui;

public class Profile {

  String name;
  String img;
  Boolean bot;

  public Profile(String _name, String img, Boolean bot) {
    this.name = _name;
    this.img = img;
    this.bot = bot;
  }

  public Boolean getBot() {
    return bot;
  }

  public void setBot(Boolean bot) {
    this.bot = bot;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }
}
