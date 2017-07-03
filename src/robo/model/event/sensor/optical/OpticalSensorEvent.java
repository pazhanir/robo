package robo.model.event.sensor.optical;

import robo.model.event.sensor.SensorEvent;
import robo.model.sensor.optical.OpticalSensor;

/**
 * This is a optical sensor event base class. Specific Optical sensors may
 * require to add specific optical event types based on the data they contain.
 * Very basic sensors may even trigger this event without extending it, as for
 * example a sensor that just detects light on or of states and triggers events
 * when light goes on or off. However, in general this class is extended by
 * specific optical event classes.
 * 
 * @author Mircea Diaconescu
 * @date May 20, 2014, 15:36:22
 * 
 */
public class OpticalSensorEvent extends SensorEvent {
  public OpticalSensorEvent(OpticalSensor source) {
    super(source);
  }
}
