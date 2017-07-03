package robo.model.sensor.electric;

import robo.model.sensor.Sensor;

/**
 * The super class of any Electric Sensor device, no matter if it is a real
 * hardware) one or a simulated one. This class is abstract and it must be
 * extended by specific electric sensors (real or simulated types).
 * 
 * @author Mircea Diaconescu
 * @date May 22, 2014, 10:06:47
 * 
 */
public abstract class ElectricSensor extends Sensor {
  public ElectricSensor(String name) {
    super(name);
  }

  public ElectricSensor() {
    super();
  }
}