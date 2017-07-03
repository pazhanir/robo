package robo.model.actuator;

import java.util.List;

import robo.model.DigitalMode;
import robo.model.Rule;
import robo.model.event.Event;
import robo.model.event.actuator.DisableRelayEvent;
import robo.model.event.actuator.EnableRelayEvent;
import robo.model.event.actuator.RelayDisabledEvent;
import robo.model.event.actuator.RelayEnabledEvent;

/**
 * The super class of any relay actuator, being a real or simulated component.
 * 
 * @author Mircea Diaconescu
 * @date May 21, 2014, 16:04:13
 * 
 */
public class RelayActuator extends Actuator {
  // the digital mode required to enable the relay
  protected final DigitalMode enableMode;
  // the relay state: enabled (true) or disabled (false).
  protected boolean enabled = false;

  /**
   * Create a relay instance when the enable mode is known.
   * 
   * @param enableMode
   *          the digital mode used to enable the relay
   */
  public RelayActuator(DigitalMode enableMode) {
    super();
    if (enableMode == DigitalMode.PULL_DOWN
        || enableMode == DigitalMode.PULL_UP) {
      this.enableMode = enableMode;
      // add the enable rule triggered by the EnableRelayEvent
      this.addRule(new Rule< RelayActuator, EnableRelayEvent>() {
        @Override
        protected List< Event< ?>> execute() {
          try {
            if (!enabled) {
              enable();
            }
          } catch (Exception e) {
            this.logger.error(e);
          }
          return null;
        }
      });
      // add the disable rule triggered by the DisableRelayEvent
      this.addRule(new Rule< RelayActuator, DisableRelayEvent>() {
        @Override
        protected List< Event< ?>> execute() {
          try {
            if (enabled) {
              disable();
            }
          } catch (Exception e) {
            this.logger.error(e);
          }
          return null;
        }
      });
    } else {
      throw new IllegalArgumentException(
          "Only PULL_UP or PULL_DOWN are allowed as enable mode for a relay!");
    }
  }

  /**
   * Create a relay instance when the enable mode is known and a specific
   * component name must be given.
   * 
   * @param name
   *          a unique name for this component
   * @param enableMode
   *          the digital mode used to enable the relay
   */
  public RelayActuator(String name, DigitalMode enableMode) {
    this(enableMode);
    this.setName(name);
  }

  /**
   * Give an enable command to the relay. This will result in setting the mode
   * as defined by the <code>enableMode</code> property. Subclasses must call
   * this method when the enable action succeeded, this being after the relay
   * was physically enabled.
   */
  public void enable() throws Exception {
    this.enabled = true;
    this.trigger(new RelayEnabledEvent(this));
  };

  /**
   * Give an enable command to the relay. This will result in setting the mode
   * to the complement of the <code>enableMode</code> property value. Subclasses
   * must call this method when the disable action succeeded, this being after
   * the relay was physically disabled.
   */
  public void disable() throws Exception {
    this.enabled = false;
    this.trigger(new RelayDisabledEvent(this));
  };

  /**
   * Get the current relay state (enabled = true, disabled = false);
   * 
   * @return true if relay is enabled, false otherwise
   */
  public boolean isEnabled() {
    return this.enabled;
  }
}