package robo.model.sensor.optical;

import robo.model.MeasurementUnit;

/**
 * The super class of any Photo Resistor Optical Sensor device, no matter if it
 * is a real hardware) one or a simulated one. This class is abstract and it
 * must be extended by specific photo-resistor optical sensors (real or
 * simulated types).
 * 
 * NOTE1: Such devices are mostly measured by using a voltage divider
 * configuration to determine the resistance of the Photo-Resistor.
 * 
 * NOTE2: Specific implementations of this class can return values in LUX or fc
 * for light levels, and in this case the measurement unit must be overridden in
 * the implementation class.
 * 
 * @author Mircea Diaconescu
 * @date May 20, 2014, 13:22:43 PM
 * 
 */
public abstract class PhotoResistorSensor extends OpticalSensor {
  // initially, -1 value expresses that no readings were made
  protected double resistance = -1;
  // this value can be overridden in the implementation class
  protected MeasurementUnit.Electric.Resistance unit = MeasurementUnit.Electric.Resistance.Ohm;

  // the second resistor value in Ohm (no matter if photor-esistor resistance uses other units!!!)
  // used to create the voltage divider when this method is used to measure the Photo-Resistor value. 
  // If other measurement method is used, then this property can be ignored.
  // NOTE: value >=0 means that voltage divider measurement method is used!
  protected double resistance2 = -1;

  // the value of the supplied voltage in Volts if used in voltage divider
  // configuration. If other measurement method is used then this property can
  // be ignored.
  protected double inputVoltage = 0;

  public PhotoResistorSensor(String name) {
    super(name);
  }

  public PhotoResistorSensor() {
    super();
  }

  public MeasurementUnit.Electric.Resistance getUnit() {
    return this.unit;
  }

  public double getResistance() {
    return this.resistance;
  }

  /**
   * Check if a value was set for <code>resistance2</code> that being able to
   * detect if voltage divider is used as measurement method.
   * 
   * @return
   */
  public boolean isVoltageDividerConfiguration() {
    return this.resistance >= 0;
  }

  public double getResistance2() {
    return resistance2;
  }

  public void setResistance2(double resistance2) {
    if (resistance2 >= 0) {
      this.resistance2 = resistance2;
    } else {
      throw new IllegalArgumentException(
          "Value of R2 must be greather or equal with zero!");
    }
  }

  public double getInputVoltage() {
    return inputVoltage;
  }

  /**
   * This is the voltage applied to the voltage divider in case that this method
   * is used for measure the resistance of the photo-resistor. Positive and
   * negative voltage values are allowed but not null (zero) values.
   * 
   * @param inputVoltage
   */
  public void setInputVoltage(double inputVoltage) {
    if (inputVoltage == 0) {
      throw new IllegalArgumentException(
          "Input voltage values must be either positive or negative values, but not 0!");
    } else {
      this.inputVoltage = inputVoltage;
    }
  }
}