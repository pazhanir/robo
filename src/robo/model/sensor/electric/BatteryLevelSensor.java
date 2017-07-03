package robo.model.sensor.electric;

import robo.model.MeasurementUnit;

/**
 * The super class of any Battery Level Sensor device, no matter if it is a real
 * hardware) one or a simulated one. This class is abstract and it must be
 * extended by specific battery level sensors (real or simulated types).
 * 
 * @author Mircea Diaconescu
 * @date Apr 8, 2014, 11:06:57 PM
 * 
 */
public abstract class BatteryLevelSensor extends ElectricSensor {
  // the level in percent: range from 0 to 100%
  protected double batteryLevel = 0;
  protected final MeasurementUnit.Generic unit = MeasurementUnit.Generic.PERCENT;

  public double getBatteryLevel() {
    return this.batteryLevel;
  }

  public MeasurementUnit.Generic getUnit() {
    return this.unit;
  }

  public BatteryLevelSensor(String name) {
    super(name);
  }

  public BatteryLevelSensor() {
    super();
  }
}