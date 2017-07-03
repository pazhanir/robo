package robo.model.sensor.weather;

import robo.model.MeasurementUnit;
import robo.model.sensor.Sensor;

/**
 * The super class of any Temperature Sensor device, no matter if it is a real
 * hardware) one or a simulated one. This class is abstract and it must be
 * extended by specific temperature sensors (real or simulated types).
 * 
 * @author Mircea Diaconescu
 * @date May 15, 2014, 11:06:57 PM
 * 
 */
public abstract class TemperatureSensor extends Sensor {
  // temperature = -275 (below zero absolute) => no real value was read
  // (uninitialized value) from the sensor
  protected double temperature = -275;
  // this value can be overridden in the implementation class
  protected MeasurementUnit.Thermic.Temperature unit = MeasurementUnit.Thermic.Temperature.Celsius;

  public MeasurementUnit.Thermic.Temperature getUnit() {
    return this.unit;
  }

  public double getTemperature() {
    return this.temperature;
  }

  public TemperatureSensor(String name) {
    super(name);
  }

  public TemperatureSensor() {
    super();
  }
}