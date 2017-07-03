package robo.model.sensor;

import robo.model.WoTComponent;

/**
 * The super class of any Sensor device, no matter if it is a real hardware) one
 * or a simulated one. Every real/simulated sensor implements this class of one
 * of its specialized subclasses.
 * 
 * @author Mircea Diaconescu
 * @date Apr 8, 2014, 11:06:57 
 * 
 */
public abstract class Sensor extends WoTComponent {
  protected static int CURRENT_NUMBER_OF_SENSORS = 0;
  protected SensorMode sensorMode = SensorMode.AUTO;
  // the number of readings per second (Hz)
  // NOTE: this is only estimative and is hardware dependent. Values can be also
  // under unit ( i.e. 0 < periodicity <= 1). A value less or equal with 0
  // (zero) means that the sensor is transfered to ON_REQUEST mode.
  protected float periodicity = 1.0f;
  // It is used to measure the time between two readings in auto mode (e.g. can
  // be a value in milliseconds). It must only be used for internal purposes,
  // and it is declared as protected to not require setters and getters call
  // which will just add execution time without real reasons.
  // The default value is 1000ms to result in a periodicity of 1Hz.
  protected long delay = 1000;

  /**
   * Create a sensor instance for a specific given name.
   * 
   * @param name
   */
  public Sensor(String name) {
    super(name);
    Sensor.CURRENT_NUMBER_OF_SENSORS++;
  }

  /**
   * Create a sensor instance with an auto-generated name. The name has the
   * syntax: ClassName_currentExistingSensorsNumber.
   */
  public Sensor() {
    this("");
    this.setName(this.getClass().getSimpleName() + "_"
        + Sensor.CURRENT_NUMBER_OF_SENSORS);
  }

  /**
   * Get the current value of the <code>sensorMode</code> property.
   * 
   * @return the current <code>sensorMode</code> property value.
   */
  public SensorMode getSensorMode() {
    return sensorMode;
  }

  /**
   * Set new value for <code>sensorMode</code> property. This method can't be
   * used with <code>SensorMode.PERIODIC</code> value, in which case the
   * <code>setSensorMode(SensorMode sensorMode, float periodicity)</code> method
   * must be used.
   * 
   * @param sensorMode
   *          the new value for <code>sensorMode</code> property.
   */
  public void setSensorMode(SensorMode sensorMode)
      throws IllegalArgumentException {
    if (sensorMode == SensorMode.AUTO || sensorMode == SensorMode.ON_REQUEST) {
      this.sensorMode = sensorMode;
      this.periodicity = 0;
    } else {
      throw new IllegalArgumentException(
          "Only SensorMode.AUTO and SensorMode.ON_REQUEST values are allowed "
              + "when calling Sensor.setSensorMode(SensorMode) method! "
              + "Instead, use Sensor.setSensorMode(SensorMode, periodicity).");
    }
  }

  /**
   * Set new values for <code>sensorMode</code> and <code>periodicity</code>
   * properties. This method can be used with <code>SensorMode.PERIODIC</code>,
   * <code>SensorMode.ON_REQUEST</code> and <code>SensorMode.AUTO</code> values
   * for <code>sensorMode</code>.
   * 
   * NOTE1: negative <code>periodicity</code> values triggers exceptions in all
   * cases.
   * 
   * NOTE2: a zero (0) value for <code>periodicity</code> triggers exception if
   * used with <code>SensorMode.PERIODIC</code>.
   * 
   * NOTE3: a positive <code>periodicity</code> value will raise an exception
   * for <code>SensorMode.ON_REQUEST</code> and will set the corresponding delay
   * value for the other cases.
   * 
   * NOTE4: When it is used with <code>SensorMode.AUTO</code>, it is the
   * responsibility of each sensor implementation class what is doing with the
   * <code>periodicity</code> property value and eventually the computed
   * <code>delay</code> value (use it, ignore it or override it with own
   * values).
   * 
   * @param sensorMode
   *          the new value for <code>sensorMode</code> property.
   */
  public void setSensorMode(SensorMode sensorMode, float periodicity)
      throws IllegalArgumentException {
    if (periodicity < 0) {
      throw new IllegalArgumentException(
          "Negative values are not allowed when calling Sensor.setSensorMode(SensorMode, periodicity)!");
    } else if (periodicity == 0) {
      if (sensorMode == SensorMode.PERIODIC) {
        throw new IllegalArgumentException(
            "A zero value is not allowed when calling Sensor.setSensorMode(SensorMode.PERIODIC, periodicity)!");
      } else {
        this.sensorMode = sensorMode;
        this.periodicity = 0;
      }
    } else {
      if (sensorMode == SensorMode.ON_REQUEST) {
        throw new IllegalArgumentException(
            "Only zero is allowed as value of periodicity when calling Sensor.setSensorMode(SensorMode.ON_REQUEST, periodicity)!");
      } else {
        this.setPeriodicity(periodicity);
      }
    }

  }

  /**
   * Get the current <code>periodicity</code> property value. This can be a
   * value in the range [0,1), where 0 means no periodical readings and sub-unit
   * values means readings with a periodicity over one second, or in the range
   * [1, MAX_FLOAT] which is the value in Hz for the periodicity.
   * 
   * @return the current <code>periodicity</code> property value.
   */
  public float getPeriodicity() {
    return periodicity;
  }

  /**
   * Set a new value for the <code>periodicity</code> property. This can be a
   * value in the range [0,1), where 0 means no periodical readings and sub-unit
   * values means readings with a periodicity over one second, or in the range
   * [1, MAX_FLOAT] which is the value in Hz for the periodicity.
   * 
   * NOTE1: negative values raise exceptions for all cases.
   * 
   * NOTE2: a zero (0) value for periodicity is raise exceptions for the case of
   * <code>SensorMode.PERIODIC</code>.
   * 
   * NOTE3: a positive <code>periodicity</code> value will raise an exception
   * for <code>SensorMode.ON_REQUEST</code> and will set the corresponding delay
   * value for the other cases.
   * 
   * @return the current <code>periodicity</code> property value.
   */
  public void setPeriodicity(float periodicity) {
    if (periodicity < 0) {
      throw new IllegalArgumentException(
          "Negative periodicity values are not allowed when calling Sensor.setPeriodicity( periodicity)!");
    } else if (periodicity == 0) {
      if (this.sensorMode == SensorMode.PERIODIC) {
        throw new IllegalArgumentException(
            "The sensor is in SensorMode.PERIODIC mode, periodicity must be values greather than 0!");
      } else {
        this.periodicity = 0;
      }
    } else {
      if (sensorMode == SensorMode.ON_REQUEST) {
        throw new IllegalArgumentException(
            "The sensor is in SensorMode.ON_REQUEST, so only a zero value si allowed as periodicity!");
      } else {
        this.periodicity = periodicity;
        this.delay = (long) (1000 / periodicity);
      }
    }
  }
}