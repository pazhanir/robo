package robo.model.event.sensor;

import robo.model.event.WoTComponentEvent;
import robo.model.sensor.Sensor;

/**
 * The super class of all the events triggered by sensors. This class is to be
 * implemented in specific sensor event types.
 * 
 * @author Mircea Diaconescu
 * @date May 20, 2014, 15:39:57
 * 
 */
public abstract class SensorEvent extends WoTComponentEvent {
  public SensorEvent(Sensor source) {
    super(source);
  }
}
