package robo.model.event.sensor.electric;

import robo.model.event.sensor.SensorEvent;
import robo.model.sensor.electric.ElectricSensor;

/**
 * Super class of all events triggered by electric sensors. This class is to be
 * implemented by specific electric sensor event types.
 * 
 * @author Mircea Diaconescu
 * @date May 20, 2014, 15:46:32
 * 
 */
public abstract class ElectricSensorEvent extends SensorEvent {
  public ElectricSensorEvent(ElectricSensor source) {
    super(source);
  }
}