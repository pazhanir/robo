package robo.model.event.hid;

import robo.model.hid.Hid;

public class ButtonPressedEvent extends HidOutputEvent {
  public ButtonPressedEvent(Hid source) {
    super(source);
  }
}
