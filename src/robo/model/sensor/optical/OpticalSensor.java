package robo.model.sensor.optical;

import robo.model.sensor.Sensor;

/**
 * The super class of any Optical Sensor device, no matter if it is a real
 * hardware) one or a simulated one. This class is abstract and it must be
 * extended by specific optical sensors (real or simulated types).
 * 
 * @author Mircea Diaconescu
 * @date May 20, 2014, 13:17:21 PM
 * 
 */
public abstract class OpticalSensor extends Sensor {
  public OpticalSensor(String name) {
    super(name);
  }

  public OpticalSensor() {
    super();
  }
}
