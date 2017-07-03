package robo.model.event.sensor.electric;

import org.json.JSONException;
import org.json.JSONObject;

import robo.model.MeasurementUnit;
import robo.model.sensor.electric.BatteryLevelSensor;

public class BatteryLevelSensorEvent extends ElectricSensorEvent {
  protected double level = 0.0f;
  protected MeasurementUnit.Generic unit = MeasurementUnit.Generic.PERCENT;

  public BatteryLevelSensorEvent(BatteryLevelSensor source, double level) {
    super(source);
    this.level = level;
  }

  public MeasurementUnit.Generic getUnit() {
    return this.unit;
  }

  public double getBatteryLevel() {
    return this.level;
  }

  public JSONObject toJson() throws JSONException {
    JSONObject result = super.toJson();
    result.put("unit", this.unit);
    result.put("batteryLevel", this.level);
    return result;
  }
}