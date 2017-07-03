package robo.model.event.time;

import robo.model.Entity;
import robo.model.event.Event;
import robo.model.event.EventPriority;

public class PeriodicTimeEvent extends Event< Entity> {
  public PeriodicTimeEvent(Entity source) {
    super(source, EventPriority.VERY_HIGH);
  }
}
