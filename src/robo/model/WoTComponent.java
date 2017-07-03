package robo.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Define a Web of Things Component. Such a component is the base of any real
 * (or simulated) components, such as sensors, actuators, hids and so on. Also,
 * a WoT component can have may sub-components, resulting in a chain of
 * parent-child components.
 * 
 * @author Mircea Diaconescu
 * @date Apr 8, 2014, 10:48:46 PM
 * 
 */
public abstract class WoTComponent extends PhysicalAgent {
  private Map< String, WoTComponent> components;

  /**
   * Create a new component. This constructor is only designed to be invoked by
   * the sub-classes.
   * 
   * @param name
   *          the name of the component (must be unique among the sub-components
   *          of the same parent component)
   */
  public WoTComponent(String name) {
    this.setName(name);
    this.components = new HashMap< String, WoTComponent>(1);
  }

  /**
   * Return a component by knowing its name.
   * 
   * @param name
   *          the name of the component
   * @return the component with the given name, null in case that no component
   *         was found for the given name
   */
  @SuppressWarnings("unchecked")
  public < T extends WoTComponent> T getComponentByName(String name) {
    return (T) this.components.get(name);
  }

  /**
   * Add a component to the system. This will automatically register this
   * components parent as listener for all events of the added component.
   * 
   * @param component
   *          the component to be added to the system
   */
  public void addComponent(WoTComponent component) {
    this.addComponent(component, true);
  }

  /**
   * Add a component to the system. This method offers the possibility to allow
   * or dissalow the auto-registering of this components parent as listener for
   * all events of the added component.
   * 
   * @param component
   *          the component to be added to the system
   * @param autoListenerRegistration
   *          if true, this components parent will be registered automatically
   *          to all events generated by this component, while if
   *          <code>false</code> no auto-registration is made for events so this
   *          must be done manually
   */
  public void addComponent(WoTComponent component,
      boolean autoListenerRegistration) {
    if (component != null) {
      this.components.put(component.getName(), component);
      if (autoListenerRegistration) {
        component.addListener(this);
      }
    }
  }

  @Override
  public void initialize() throws Exception {
    // nothing to do at this point, but this method can be overridden by the
    // subclasses that requires this functionality. This empty method from here
    // allows to not be forced to implement this method in the subclasses that
    // does not need this functionality.
  }

  @Override
  public void onStarted() {
    // start all the subcomponents
    for (String key : this.components.keySet()) {
      WoTComponent component = this.components.get(key);
      if (component != null) {
        component.start();
      }
    }
  }

  @Override
  public void onStopped() {
    // stop all subcomponents
    for (String key : this.components.keySet()) {
      WoTComponent component = this.components.get(key);
      if (component != null) {
        component.stop();
      }
    }
  }
}
