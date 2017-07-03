package robo.model.event;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import robo.model.Entity;
import robo.model.PhysicalAgent;

public abstract class Event< S> extends Entity {
  // real time of the event occurrence
  private long occurrenceTime = -1;
  // the priority level of the event
  private EventPriority priority = EventPriority.MEDIUM;
  // the source that generated the event
  // NOTE: source can be null for external events
  private S source = null;

  public Event(S source) {
    super();
    this.source = source;
    this.occurrenceTime = (new Date()).getTime();
  }

  public Event(S source, EventPriority priority) {
    this(source);
    this.priority = priority;
  }

  public long getOccurrenceTime() {
    return this.occurrenceTime;
  }

  public void setOccurrenceTime(long occurrenceTime) {
    this.occurrenceTime = occurrenceTime;
  }

  public EventPriority getPriority() {
    return this.priority;
  }

  public void setPriority(EventPriority priority) {
    this.priority = priority;
  }

  public S getSource() {
    return source;
  }

  public void setSource(S source) {
    this.source = source;
  }

  public String toString() {
    return "{" + Event.class.getSimpleName() + ": priority = "
        + this.priority.name() + ", occurrenceTime = " + this.occurrenceTime
        + "}";
  }

  public JSONObject toJson() throws JSONException {
    JSONObject result = super.toJson();
    result.remove("id");
    result.put("occurrenceTime", this.occurrenceTime);
    if (this.source != null && this.source instanceof PhysicalAgent) {
      PhysicalAgent source = ((PhysicalAgent) this.source);
      String sourceName = source.getName();
      result.put("sourceId", source.getId());
      if (sourceName != null && sourceName.length() > 0) {
        result.put("sourceName", sourceName);
      }
    }
    return result;
  }
}