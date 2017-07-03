package robo.logger;

/**
 * Define the structure of the logging system.
 * 
 * @author Mircea Diaconescu
 * @date Apr 8, 2014, 10:48:46 PM
 * 
 */
public abstract class Logger {
  /**
   * The level of the logging - this is static so it is set for any logging
   * method of this interface used everywhere on the code.
   */
  public static LogLevelEnum LOG_LEVEL = LogLevelEnum.DEBUG;
  /**
   * The tag that will be used for all the logging methods that does not uses a
   * tag.
   */
  public static String TAG = Logger.class.getName();

  /**
   * Logging a debug message.
   * 
   * @param tag
   *          the message tag (key)
   * @param message
   *          the message to log
   */
  public abstract void debug(String tag, String message);

  /**
   * Logging a debug message.
   * 
   * @param message
   *          the message to log
   */
  public abstract void debug(String message);

  /**
   * Logging an info message.
   * 
   * @param tag
   *          the message tag (key)
   * @param message
   *          the message to log
   */
  public abstract void info(String tag, String message);

  /**
   * Logging an info message.
   * 
   * @param message
   *          the message to log
   */
  public abstract void info(String message);

  /**
   * Logging a warn message.
   * 
   * @param tag
   *          the message tag (key)
   * @param message
   *          the message to log
   */
  public abstract void warn(String tag, String message);

  /**
   * Logging a warn message.
   * 
   * @param message
   *          the message to log
   */
  public abstract void warn(String message);

  /**
   * Logging an error message.
   * 
   * @param tag
   *          the message tag (key)
   * @param message
   *          the message to log
   */
  public abstract void error(String tag, String message);

  /**
   * Logging an error message.
   * 
   * @param message
   *          the message to log
   */
  public abstract void error(String message);

  /**
   * Logging an exception message where need to observe the exception stack
   * trace.
   * 
   * @param tag
   *          the message tag (key)
   * @param exception
   *          the exception to serialize (log)
   */
  public abstract void error(String tag, Exception exception);

  /**
   * Logging an exception message where need to observe the exception stack
   * trace.
   * 
   * @param exception
   *          the exception to serialize (log)
   */
  public abstract void error(Exception exception);

  /**
   * Logging a fatal message.
   * 
   * @param tag
   *          the message tag (key)
   * @param message
   *          the message to log
   */
  public abstract void fatal(String tag, String message);

  /**
   * Logging a fatal message.
   * 
   * @param message
   *          the message to log
   */
  public abstract void fatal(String message);

  /**
   * Logging afatal exception message where need to observe the exception stack
   * trace.
   * 
   * @param tag
   *          the message tag (key)
   * @param exception
   *          the exception to serialize (log)
   */
  public abstract void fatal(String tag, Exception exception);

  /**
   * Logging a fatal exception message where need to observe the exception stack
   * trace.
   * 
   * @param exception
   *          the exception to serialize (log)
   */
  public abstract void fatal(Exception exception);

  /**
   * Define all possible level logging.
   * 
   * @author Mircea Diaconescu
   * 
   */
  public enum LogLevelEnum {
    // at this level all the messages are logged
    DEBUG,
    // at this level, all but DEBUG messages are logged
    INFO,
    // at this level, all but DEBUG and INFO messages are logged
    WARN,
    // at this level, all but DEBUG, INFO and WARN messages are logged
    ERROR,
    // at this level, all but DEBUG, INFO, WARN and ERROR messages are logged
    FATAL,
    // at this level, no messages are logged
    NONE
  }
}