package robo.model.event.sensor.electric;

import org.json.JSONException;
import org.json.JSONObject;

import robo.model.MeasurementUnit;
import robo.model.sensor.electric.VoltageSensor;

public class VoltageSensorEvent extends ElectricSensorEvent {
  protected double voltage = 0.0;
  protected MeasurementUnit.Electric.Voltage unit = MeasurementUnit.Electric.Voltage.V;

  public VoltageSensorEvent(VoltageSensor source, double voltage,
      MeasurementUnit.Electric.Voltage unit) {
    super(source);
    this.voltage = voltage;
    this.unit = unit;
  }

  public double getVoltage() {
    return this.voltage;
  }

  public JSONObject toJson() throws JSONException {
    JSONObject result = super.toJson();
    result.put("unit", this.unit);
    result.put("voltage", this.voltage);
    return result;
  }
}