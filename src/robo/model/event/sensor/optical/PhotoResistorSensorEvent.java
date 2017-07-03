package robo.model.event.sensor.optical;

import robo.model.MeasurementUnit;
import robo.model.sensor.optical.OpticalSensor;

/**
 * This event is triggered by photo-resistor sensors which has a variable
 * resistance value based on the level of light. The information that this
 * sensor carries is the resistance value which the sensor has at the moment of
 * triggering the event.
 * 
 * @author mircea
 * 
 */
public class PhotoResistorSensorEvent extends OpticalSensorEvent {
  protected double resistance = -1L;
  // the light intensity value in LUX - only available for some cases, since
  // not all sensors are able to do such conversion!!!
  protected double lightIntensity = -1L;
  protected MeasurementUnit.Electric.Resistance unit = MeasurementUnit.Electric.Resistance.Ohm;

  public PhotoResistorSensorEvent(OpticalSensor source, double resistance,
      MeasurementUnit.Electric.Resistance unit) {
    super(source);
    this.resistance = resistance;
    this.unit = unit;
  }

  public PhotoResistorSensorEvent(OpticalSensor source, double lightIntensity,
      double resistance, MeasurementUnit.Electric.Resistance unit) {
    this(source, resistance, unit);
    this.lightIntensity = lightIntensity;
  }

  public double getResistance() {
    return this.resistance;
  }

  public double getLightIntensity() {
    return this.lightIntensity;
  }

  public MeasurementUnit.Electric.Resistance getUnit() {
    return this.unit;
  }
}
