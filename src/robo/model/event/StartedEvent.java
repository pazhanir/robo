package robo.model.event;

import robo.model.PhysicalAgent;

public class StartedEvent extends Event< PhysicalAgent> {
  public StartedEvent(PhysicalAgent source) {
    super(source);
  }
}
