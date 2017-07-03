package robo.model.event;

import robo.model.PhysicalAgent;

public class StoppedEvent extends Event< PhysicalAgent> {
  public StoppedEvent(PhysicalAgent source) {
    super(source);
  }
}
