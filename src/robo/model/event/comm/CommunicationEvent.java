package robo.model.event.comm;

import robo.model.event.Event;
import robo.model.event.EventPriority;

public abstract class CommunicationEvent< T> extends Event< T> {
  public CommunicationEvent(T source) {
    /**
     * By default, a communication event has a very low priority event (it must
     * only be processed after all the other events were processed). If the
     * priority must be different then use the constructor with priority
     * parameter instead.
     */
    super(source, EventPriority.VERY_LOW);
  }

  public CommunicationEvent(T source, EventPriority priority) {
    super(source, priority);
  }
}
