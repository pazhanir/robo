package robo.model.event.hid;

import org.json.JSONException;
import org.json.JSONObject;

import robo.model.event.EventPriority;
import robo.model.hid.Hid;

public class HidOutputEvent extends HidEvent {
  private JSONObject data;

  public HidOutputEvent(Hid source, JSONObject data, EventPriority priority) {
    super(source);
    this.data = data;
  }

  public HidOutputEvent(Hid source, JSONObject data) {
    super(source);
    this.data = data;
  }
  
  public HidOutputEvent(Hid source) {
    super(source);
  }

  public JSONObject toJson() throws JSONException {
    JSONObject result = super.toJson();
    if (this.data != null) {
      result.put("data", this.data);
    }
    return result;
  }
}