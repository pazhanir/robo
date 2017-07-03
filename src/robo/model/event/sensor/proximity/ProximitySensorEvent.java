package robo.model.event.sensor.proximity;

import robo.model.event.sensor.SensorEvent;
import robo.model.sensor.proximity.ProximitySensor;

/**
 * This is the most basic proximity sensor event which only makes a reference to
 * the source of event, and does not contain additional data. This event is
 * triggered when proximity sensor detects obstacles in their range.
 * 
 * @author Mircea Diaconescu
 * @date April 8, 2014, 11:06:57 PM
 * 
 */
public class ProximitySensorEvent extends SensorEvent {
  public ProximitySensorEvent(ProximitySensor source) {
    super(source);
  }
}