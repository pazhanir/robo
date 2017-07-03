package robo.model.sensor.proximity;

import robo.model.sensor.Sensor;

/**
 * The super class of any Proximity Sensor device, no matter if it is a real
 * hardware) one or a simulated one. This class is abstract and it must be
 * extended by specific proximity sensors (real or simulated types).
 * 
 * NOTE: proximity sensors are only sensing object on their range but does not
 * know the position of the distance to the detected object.
 * 
 * @author Mircea Diaconescu
 * @date Apr 8, 2014, 11:06:57 PM
 * 
 */
public abstract class ProximitySensor extends Sensor {

  public ProximitySensor(String name) {
    super(name);
  }

  public ProximitySensor() {
    super();
  }

  /**
   * This method has to be implemented by specific sensors to detect if an
   * object was detected in the sensor detection range.
   * 
   * @return true if an object is in the sensor detection range, false otherwise
   */
  public abstract boolean isInRange();
}