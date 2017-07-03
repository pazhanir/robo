package robo.model.event.actuator;

import robo.model.actuator.Actuator;

/**
 * Event type triggered by an Enable-Relay command.
 * 
 * @author Mircea Diaconescu
 * @date May 21, 2014, 16:26:42
 * 
 */
public class RelayEnabledEvent extends ActuatorStateChangeEvent {
  public RelayEnabledEvent(Actuator source) {
    super(source);
  }
}
