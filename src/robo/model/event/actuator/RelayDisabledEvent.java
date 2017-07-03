package robo.model.event.actuator;

import robo.model.actuator.Actuator;

/**
 * Event type triggered by an Disable-Relay command.
 * 
 * @author Mircea Diaconescu
 * @date May 21, 2014, 16:26:42
 * 
 */
public class RelayDisabledEvent extends ActuatorStateChangeEvent {
  public RelayDisabledEvent(Actuator source) {
    super(source);
  }
}
