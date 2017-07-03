package robo.model.sensor.electric;

import robo.model.MeasurementUnit;

/**
 * The super class of any Voltage Sensor device, no matter if it is a real
 * hardware) one or a simulated one. This class is abstract and it must be
 * extended by specific voltage sensors (real or simulated types).
 * 
 * @author Mircea Diaconescu
 * @date Apr 8, 2014, 11:06:57 PM
 * 
 */
public abstract class VoltageSensor extends ElectricSensor {
  protected double voltage = 0L;
  // this value must be overridden in the implementation class
  protected MeasurementUnit.Electric.Voltage unit = MeasurementUnit.Electric.Voltage.V;

  public MeasurementUnit.Electric.Voltage getUnit() {
    return this.unit;
  }

  public double getVoltage() {
    return this.voltage;
  }

  public VoltageSensor(String name) {
    super(name);
  }

  public VoltageSensor() {
    super();
  }
}