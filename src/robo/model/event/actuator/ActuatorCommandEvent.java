package robo.model.event.actuator;

import robo.model.PhysicalAgent;
import robo.model.event.Event;

/**
 * Super class for all the actuator command events.
 * 
 * @author Mircea Diaconescu
 * @date May 21, 2014, 16:21:19
 * 
 */
public class ActuatorCommandEvent extends Event<PhysicalAgent> {
  private String receiverActuatorName = null;

  /**
   * Create a new event instance when the actuator unique name is known.
   * 
   * @param source
   *          the source component - which triggered the event
   * @param receiverActuatorName
   *          the unique name of the actuator which must execute the command
   */
  public ActuatorCommandEvent(PhysicalAgent source, String receiverActuatorName) {
    super(source);
    this.receiverActuatorName = receiverActuatorName;
  }

  public String getReceiverActuatorName() {
    return this.receiverActuatorName;
  }
}