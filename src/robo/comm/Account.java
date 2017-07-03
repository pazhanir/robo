package robo.comm;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Account object, required for login/logout operations.
 * 
 * @author Mircea Diaconescu
 * @date Apr 8, 2014, 10:48:46 PM
 */
public class Account {
  private String username = null;
  private String password = null;

  public Account(String username, String password) {
    this.setUsername(username);
    this.setPassword(password);
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    if (username != null && username.length() > 4) {
      this.username = username;
    }
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    if (password != null && password.length() > 4) {
      this.password = password;
    }
  }

  public JSONObject toJson() throws JSONException {
    JSONObject result = new JSONObject();
    result.put("username", this.username);
    if (this.password != null && this.password.length() > 0) {
      result.put("password", this.password);
    }
    return result;
  }
}
