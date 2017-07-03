package robo.model.hid;

/**
 * Generic implementation of a simple tactile button. Such types of buttons are
 * default open, and become closed only when it is pressed, while after it is
 * released it becomes again open.
 * 
 * This is just a base class and it must be sub-classed by using real hardware
 * considerations.
 * 
 * @author Mircea Diaconescu
 * @date May 16, 2014, 11:04:23 PM
 * 
 */
public class TactileButton extends Hid {
  public TactileButton(String name) {
    super(name);
  }

  public TactileButton() {
    super();
  }
}
