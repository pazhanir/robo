package robo.model.sensor;

/**
 * Define Sensor reading modes. It determine when a sensor triggers the event
 * with the collected data.
 * 
 * @author Mircea Diaconescu
 * @date May 5, 2014, 11:34:57 PM
 * 
 */
public enum SensorMode {
  // the sensor is responsible to detect possible changes compared with the
  // latest value and trigger the event with the new values it collected only if
  // that value is different compared with the one it sent last time via event.
  AUTO,
  // a frequency "Readings per Second" value determine the intervals of
  // reading new data. There is no guaranty that the sensor is able to read the
  // data fast enough, it will try to do the best anyway.
  PERIODIC,
  // the sensor will only read data when it is asked to do so. Check the Sensor
  // class for more details on this matter.
  ON_REQUEST
}
