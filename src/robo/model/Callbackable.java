package robo.model;

/**
 * An interface for a callback-like function emulation. It allows to define an
 * operation which is invoked as a callback.
 * 
 * @author Mircea Diaconescu
 * @date May 26, 2014, 11:06:27
 * 
 */
public interface Callbackable <T>{
  public void callback(T arg);
}