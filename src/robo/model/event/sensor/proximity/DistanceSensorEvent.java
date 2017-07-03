package robo.model.event.sensor.proximity;

import robo.model.MeasurementUnit;
import robo.model.sensor.proximity.DistanceSensor;

/**
 * This is a distance sensor event that considers the distance to the nearest
 * object in range (distance is -1 if nothing is in the sensor's range) of the
 * proximity sensor. Extensions of this event type may consider multiple
 * information (e.g. like in case of a radar, multiple detected points are
 * available).
 * 
 * @author Mircea Diaconescu
 * @date April 8, 2014, 11:06:57 PM
 * 
 */
public class DistanceSensorEvent extends ProximitySensorEvent {
  // the distance to the detected object(s)
  // NOTE: distance = -1 means that no object is in the range of the sensor.
  private double distance = -1L;
  protected MeasurementUnit.Mechanic.Distance unit = MeasurementUnit.Mechanic.Distance.m;

  public double getDistance() {
    return distance;
  }

  public MeasurementUnit.Mechanic.Distance getUnit() {
    return unit;
  }

  public DistanceSensorEvent(DistanceSensor source, double distance,
      MeasurementUnit.Mechanic.Distance unit) {
    super(source);
    this.distance = distance;
    this.unit = unit;
  }
}