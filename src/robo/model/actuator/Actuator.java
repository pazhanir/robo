package robo.model.actuator;

import robo.model.WoTComponent;
import robo.model.event.Event;
import robo.model.event.actuator.ActuatorCommandEvent;

/**
 * The super class of any real or simulated actuator component.
 * 
 * @author Mircea Diaconescu
 * @date Apr 8, 2014, 11:04:23
 * 
 */
public abstract class Actuator extends WoTComponent {
  protected static int CURRENT_NUMBER_OF_ACTUATORS = 0;

  /**
   * Create an actuator instance for a specific given name.
   * 
   * @param name
   *          a unique name for the actuator
   */
  public Actuator(String name) {
    super(name);
    Actuator.CURRENT_NUMBER_OF_ACTUATORS++;
  }

  /**
   * Create an actuator instance with an auto-generated name. The name has the
   * syntax: ClassName_currentExistingSensorsNumber.
   */
  public Actuator() {
    this("");
    this.setName(this.getClass().getSimpleName() + "_"
        + Actuator.CURRENT_NUMBER_OF_ACTUATORS);
  }

  @Override
  public synchronized void on(Event< ?> event) {
    // for safety reasons, actuators refuses any command events which are not
    // meant for them, so we avoid playing with the fire by mistake!!!
    if (event != null && event instanceof ActuatorCommandEvent) {
      if (((ActuatorCommandEvent) event).getReceiverActuatorName() == this.name) {
        super.on(event);
      }
    }
  }
}