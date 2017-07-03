package robo.model.event;

import robo.model.WoTComponent;

public class WoTComponentEvent extends Event< WoTComponent> {
  public WoTComponentEvent(WoTComponent source) {
    super(source);
  }
}