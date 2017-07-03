package robo.model.event.actuator;

import robo.model.actuator.Actuator;
import robo.model.event.WoTComponentEvent;

/**
 * Super class for all the actuator state change events. Such events results
 * from state changes actions of the actuators.
 * 
 * @author Mircea Diaconescu
 * @date May 21, 2014, 17:14:38
 * 
 */
public class ActuatorStateChangeEvent extends WoTComponentEvent {
  public ActuatorStateChangeEvent(Actuator source) {
    super(source);
  }
}
