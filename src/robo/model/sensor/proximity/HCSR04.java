package robo.model.sensor.proximity;

import robo.model.MeasurementUnit;
import robo.model.sensor.SensorMode;

/**
 * The generic implementation of the HC-SR04 ultrasonic proximity/distance
 * sensor. Normally, this class must be extended by specific implementations
 * (e.g. depending on the interface device). However, this class is not abstract
 * and can be used to emulate such a sensor when a real hardware sensor is
 * missing. In this case, for example a set of rules that defines its behavior
 * can be used to emulate the sensor or it may override specific
 * <code>PhysicalAgent</code> methods to obtain the desired HCSR04 emulated
 * sensor behavior.
 * 
 * @author Mircea Diaconescu
 * @date Apr 8, 2014, 11:06:57 PM
 * 
 */
public class HCSR04 extends DistanceSensor {
  // the coefficient to be used for distance computation (in centi-meters) based
  // on the sensor "echo" reading
  protected double coefficient = 0;

  /**
   * Create an instance of the HC-SR04 that is in auto-mode, meaning that will
   * auto detect objects entering or exiting its range and will trigger the
   * appropriate events. The result distance will be in centimeters.
   */
  public HCSR04() {
    super();
    this.setUnit(MeasurementUnit.Mechanic.Distance.cm);
    // by default this sensor will be read 30 times/second => ~33 ms delays
    // between two readings. This can be changes in the implementation.
    this.setSensorMode(SensorMode.AUTO, 33);
  }

  /**
   * Create an instance of the HC-SR04 that is in auto-mode, meaning that will
   * auto detect objects entering or exiting its range and will trigger the
   * appropriate events. The result distance will be in centimeters.
   * 
   * @param name
   *          an unique name used to identify this sensor
   */
  public HCSR04(String name) {
    this();
    this.setName(name);
  }

  /**
   * Set periodicity value. For this specific sensor, the maximum number of
   * readings per seconds is about 50 (one read requires about 6 to 18ms time
   * for most of hardware that uses HCSR04 like sensors) and the minimum is
   * "almost" unbounded.
   * 
   * NOTE: specific implementations can change this value according to their
   * necessities, by allowing lower/higher margins.
   * 
   * @param periodicity
   *          the periodicity (number of reads per second)
   */
  public void setPeriodicity(float periodicity) {
    super.setPeriodicity(periodicity);
    if (this.periodicity > 50) {
      this.periodicity = 50;
    }
  }

  /**
   * Set the measurement unit for this specific sensor. This will determine the
   * computation coeficient value.
   */
  public final void setUnit(MeasurementUnit.Mechanic.Distance unit) {
    this.coefficient = 1000000 / 58.2;
    this.unit = unit;
    switch (unit) {
    case pm:
      this.coefficient *= 10000000000L;
      break;
    case nm:
      this.coefficient *= 10000000;
      break;
    case um:
      this.coefficient *= 10000;
      break;
    case mm:
      this.coefficient *= 10;
      break;
    case cm:
      // default value.
      break;
    case dm:
      this.coefficient /= 10;
      break;
    case m:
      this.coefficient /= 100;
      break;
    case dam:
      this.coefficient /= 1000;
      break;
    case hm:
      this.coefficient = 10000;
      break;
    case km:
      this.coefficient = 100000;
      break;
    default:
      // not evaluated unit
      this.coefficient = 1;
      break;
    }
  }

  @Override
  public boolean isInRange() {
    return this.distance >= 0;
  }
}