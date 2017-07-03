package robo.model.event.actuator;

import robo.model.PhysicalAgent;

/**
 * Event type which requests a Enable-Relay command.
 * 
 * @author Mircea Diaconescu
 * @date May 21, 2014, 16:26:42
 * 
 */
public class EnableRelayEvent extends ActuatorCommandEvent {
  /**
   * Create a new event instance when the relay actuator unique name is known.
   * 
   * @param source
   *          the source component - which triggered the event
   * @param relayName
   *          the unique name of the relay which must execute the command
   */
  public EnableRelayEvent(PhysicalAgent source, String relayName) {
    super(source, relayName);
  }
}