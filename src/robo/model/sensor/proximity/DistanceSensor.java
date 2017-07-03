package robo.model.sensor.proximity;

import robo.model.MeasurementUnit;

/**
 * The super class of any Distance Sensor device, no matter if it is a real
 * hardware) one or a simulated one. This class is abstract and it must be
 * extended by specific proximity sensors (real or simulated types).
 * 
 * NOTE: a Distance Sensor is a kind of proximity sensor that not only detects
 * object in its detection range, but it also knows the distance from the sensor
 * to the object.
 * 
 * @author Mircea Diaconescu
 * @date Apr 8, 2014, 11:06:57 PM
 * 
 */
public abstract class DistanceSensor extends ProximitySensor {
  // the distance from the sensor to the detected object
  // A value of -1 means that no object was detected.
  protected double distance = -1;
  protected MeasurementUnit.Mechanic.Distance unit = MeasurementUnit.Mechanic.Distance.m;
  // the lowest limit (using the unit measurement units) for which the proximity
  // event will be triggered (mainly used in AUTO mode)
  protected double lowLimitDistance = 0;
  // the highest limit (using the unit measurement units) for which the
  // proximity event will be triggered (mainly used in AUTO mode)
  protected double highLimitDistance = Double.MAX_VALUE;

  public DistanceSensor(String name) {
    super(name);
  }

  public DistanceSensor() {
    super();
  }

  /**
   * Get the current measurement unit used for the distance.
   * 
   * @return the current distance measurement unit for this sensor
   */
  public MeasurementUnit.Mechanic.Distance getUnit() {
    return this.unit;
  }

  /**
   * Get the latest stored current distance value.
   * 
   * NOTE1: this value may not be necessarily the current one, it is actually
   * the latest one that was read by the sensor before calling this method.
   * 
   * NOTE2: the method will return -1 in case that the distance is not in the
   * allowed range that was set for this sensor (minimum and maximum ranges can
   * be set).
   * 
   * @return the latest known distance value, -1 if no value was yet read.
   */
  public double getDistance() {
    if (this.distance >= this.lowLimitDistance
        && this.distance <= this.highLimitDistance) {
      return distance;
    } else {
      return -1;
    }
  }

  /**
   * Get the current value for the low limit range of this sensor.
   * 
   * @return the value of <code>lowLimitDistance</code> property.
   */
  public double getLowLimitDistance() {
    return this.lowLimitDistance;
  }

  /**
   * Set new value for the low limit range (value of
   * <code>lowLimitDistance</code> property).
   * 
   * @param lowLimitDistance
   *          the new value for the <code>lowLimitDistance</code> property.
   */
  public void setLowLimitDistance(double lowLimitDistance) {
    if (lowLimitDistance >= 0) {
      this.lowLimitDistance = lowLimitDistance;
    } else {
      this.lowLimitDistance = 0;
    }
  }

  /**
   * Get the current value for the high limit range of this sensor.
   * 
   * @return the value of <code>highLimitDistance</code> property.
   */
  public double getHighLimitDistance() {
    return this.highLimitDistance;
  }

  /**
   * Set new value for the high limit range (value of
   * <code>highLimitDistance</code> property).
   * 
   * @param highLimitDistance
   *          the new value for the <code>highLimitDistance</code> property.
   */
  public void setHighLimitDistance(double highLimitDistance) {
    if (highLimitDistance > 0) {
      this.highLimitDistance = highLimitDistance;
    } else {
      this.highLimitDistance = 0;
    }
  }
}