package robo.model;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The super class of any entity used by the system. Any specific entity class
 * must extend this!
 * 
 * @author Mircea Diaconescu
 * @date Apr 8, 2014, 10:48:46 PM
 * 
 */
public abstract class Entity {
  private String id;
  protected String name;

  public Entity() {
    this.id = UUID.randomUUID().toString();
    this.name = "";
  }

  public String getId() {
    return this.id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public JSONObject toJson() throws JSONException {
    JSONObject result = new JSONObject();
    result.put("type", this.getClass().getSimpleName());
    result.put("id", this.id);
    if (this.name != null && this.name.length() > 0) {
      result.put("name", this.name);
    }
    return result;
  }
}