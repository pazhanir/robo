package robo.logger;

import java.util.Properties;

/**
 * Define the Java default implementation of the logger (uses System outputs)
 * 
 * @author Mircea Diaconescu
 * @date Apr 8, 2014, 10:48:46 PM
 * 
 */
public class DefaultLogger extends Logger {
  private static Logger logger = null;

  public static Logger getLogger() {
    Properties p = System.getProperties();
    String runtime = p.getProperty("java.runtime.name");
    String loggerImplementationClass = DefaultLogger.class.getName();
    if (runtime.compareToIgnoreCase("Android Runtime") == 0) {
      loggerImplementationClass = "robo.android.logger.DefaultLogger";
    }
    if (DefaultLogger.logger == null) {
      try {
        DefaultLogger.logger = (Logger) Class
            .forName(loggerImplementationClass).newInstance();
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
    return DefaultLogger.logger;
  }

  @Override
  public void debug(String tag, String message) {
    if (DefaultLogger.LOG_LEVEL == LogLevelEnum.DEBUG) {
      System.out.println("[DEBUG][" + tag + "]>" + message);
    }
  }

  @Override
  public void debug(String message) {
    this.debug(Logger.TAG, message);
  }

  @Override
  public void info(String tag, String message) {
    if (DefaultLogger.LOG_LEVEL.ordinal() <= LogLevelEnum.INFO.ordinal()) {
      System.out.println("[INFO][" + tag + "]>" + message);
    }
  }

  @Override
  public void info(String message) {
    this.info(Logger.TAG, message);
  }

  @Override
  public void warn(String tag, String message) {
    if (DefaultLogger.LOG_LEVEL.ordinal() <= LogLevelEnum.WARN.ordinal()) {
      System.out.println("[WARN][" + tag + "]>" + message);
    }
  }

  @Override
  public void warn(String message) {
    this.warn(Logger.TAG, message);
  }

  @Override
  public void error(String tag, String message) {
    if (DefaultLogger.LOG_LEVEL.ordinal() <= LogLevelEnum.ERROR.ordinal()) {
      System.err.println("[ERROR][" + tag + "]>" + message);
    }
  }

  @Override
  public void error(String message) {
    this.error(Logger.TAG, message);
  }

  @Override
  public void error(String tag, Exception exception) {
    if (DefaultLogger.LOG_LEVEL.ordinal() <= LogLevelEnum.ERROR.ordinal()) {
      System.err.print("[ERROR][" + tag + "]>");
      exception.printStackTrace();
    }
  }

  @Override
  public void error(Exception exception) {
    this.error(Logger.TAG, exception);
  }

  @Override
  public void fatal(String tag, String message) {
    if (DefaultLogger.LOG_LEVEL.ordinal() <= LogLevelEnum.FATAL.ordinal()) {
      System.err.println("[FATAL][" + tag + "]>" + message);
    }
  }

  @Override
  public void fatal(String message) {
    this.fatal(Logger.TAG, message);
  }

  @Override
  public void fatal(String tag, Exception exception) {
    if (DefaultLogger.LOG_LEVEL.ordinal() <= LogLevelEnum.FATAL.ordinal()) {
      System.err.print("[FATAL][" + tag + "]>");
      exception.printStackTrace();
    }
  }

  @Override
  public void fatal(Exception exception) {
    this.fatal(Logger.TAG, exception);
  }
}