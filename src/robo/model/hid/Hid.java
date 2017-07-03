package robo.model.hid;

import robo.model.WoTComponent;

/**
 * The super class of any Hid device, no matter if it is a real hardware) one or
 * a simulated one.
 * 
 * @author Mircea Diaconescu
 * @date Apr 8, 2014, 11:06:57 PM
 * 
 */
public abstract class Hid extends WoTComponent {
  protected static int CURRENT_NUMBER_OF_HIDS = 0;

  public Hid(String name) {
    super(name);
    Hid.CURRENT_NUMBER_OF_HIDS++;
  }

  public Hid() {
    this("");
    // auto generate name in the for of ClassName_currentCounterValueOfHids
    this.setName(this.getClass().getSimpleName() + "_"
        + Hid.CURRENT_NUMBER_OF_HIDS);
  }
}