package robo.model;

public interface MeasurementUnit {
  public enum Generic {
    // no unit
    NONE,
    // percent
    PERCENT
  }

  /**
   * The available measurement units for electrics.
   * 
   * @author Mircea Diaconescu
   * @date Apr 8, 2014, 11:06:57 PM
   * 
   */
  public interface Electric {
    public enum Voltage {
      // micro-Volts
      uV,
      // milli-volts
      mV,
      // volts
      V,
      // kilo-Volts
      kV
    }

    public enum Current {
      // micro-Amperes
      uA,
      // milli-Amperes
      mA,
      // Amperes
      A,
      // kilo-Amperes
      kA
    }

    public enum Power {
      // micro-Watts
      uW,
      // milli-Watts
      mW,
      // Watts
      W,
      // kilo-watts
      kW
    }

    public enum Resistance {
      // milli-ohm
      mOhm,
      // Ohm
      Ohm,
      // Kilo-Ohm
      KOhm,
      // Mega-Ohm
      MOhm
    }
  }

  /**
   * Measurement units used for mechanics.
   * 
   * @author Mircea Diaconescu
   * @date May 20, 2014, 12:06:57
   * 
   */
  public interface Mechanic {

    /**
     * The available measurement units for distance.
     * 
     * @author Mircea Diaconescu
     * @date Apr 8, 2014, 23:06:57
     * 
     */
    public enum Distance {
      // no unit
      NONE,
      // pico-meter
      pm,
      // nano-meter
      nm,
      // micro-meter
      um,
      // milli-meter
      mm,
      // centi-meter
      cm,
      // deci-meter
      dm,
      // meter
      m,
      // deca-meter
      dam,
      // hecto-meter
      hm,
      // kilo-meter
      km
    }
  }

  interface Thermic {

    /**
     * The available measurement units for temperature.
     * 
     * @author Mircea Diaconescu
     * @date May 15, 2014, 11:06:57 PM
     * 
     */
    public enum Temperature {
      // no unit
      NONE,
      // Celsius degrees
      Celsius,
      // Fahrenheit degrees
      Fahrenheit
    }
  }

}
