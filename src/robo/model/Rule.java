package robo.model;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import robo.logger.DefaultLogger;
import robo.logger.Logger;
import robo.model.event.Event;

/**
 * Represents an agent rule. The generic type T refers to the agent type that
 * contains this rule.
 * 
 * @author Mircea Diaconescu
 * @date Apr 8, 2014, 10:48:46 PM
 * 
 * @param <T>
 *          the generic type of the agent that will contain this rule
 */
public abstract class Rule< AgentRefType extends PhysicalAgent, TriggeringEventType extends Event< ? extends PhysicalAgent>> {
  // reference to the event that currently triggered the rule
  protected TriggeringEventType triggeringEvent;
  // the context reference of the agent that has this rule
  protected AgentRefType agent;
  // debugger and logger
  protected Logger logger;

  public Rule() {
    this.agent = null;
    this.triggeringEvent = null;
    // initialize the logger
    this.logger = DefaultLogger.getLogger();
  }

  /**
   * Create a rule instance.
   * 
   * @param agentSubjectRef
   *          the reference to the agent subject
   */
  public Rule(AgentRefType agentSubjectRef) {
    this.agent = agentSubjectRef;
  }

  /**
   * Allow to do initialization job for the rule. This method is called JUST
   * once, right before the agent is started. The initialization method will be
   * able to use the agent reference and all its components in case that this is
   * required (e.g. get reference to agent components, property values, etc).
   * This method is not abstract because not every rule requires an
   * initialization, and in this case there is no need to implement it in the
   * subclass.
   * 
   * @throws Exception
   */
  protected void initialize() throws Exception {
    // nothing to do here, code must be added in the subclass
  }

  /**
   * This method is used to check if the condition of the rule is or is not
   * satisfied. The method is normally implemented in the subclasses of Rule. If
   * not implemented in a subclass, then the condition is supposed to be true
   * all the time.
   * 
   * @return true if condition is satisfied, false otherwise
   */
  protected boolean checkCondition() {
    return true;
  }

  /**
   * This method is used to perform the actions of the rule in case that no
   * condition is available for the rule. The method is usually implemented in
   * the subclasses of Rule.
   * 
   * @param currentEvent
   *          reference to the current event that triggers the rule
   * 
   * @return a list of Event instances that are result of this rule, NULL in
   *         case that the rule actions does not generate new events.
   */
  protected List< Event< ?>> executeDoActions() {
    return null;
  }

  /**
   * This method is used to perform the actions of the rule in case that the
   * triggering event was correct and the condition was satisfied. The method is
   * usually implemented in the subclasses of Rule.
   * 
   * @param currentEvent
   *          reference to the current event that triggers the rule
   * 
   * @return a list of Event instances that are result of this rule, NULL in
   *         case that the rule actions does not generate new events.
   */
  protected List< Event< ?>> executeThenActions() {
    return null;
  }

  /**
   * This method is used to perform the actions of the rule in case that the
   * triggering event was correct but the condition was not satisfied. The
   * method have to be implemented in the subclasses of Rule. In case that is
   * not implemented in a subclass, the method will return null (this is the
   * case on which an ELSE branch is not required for the rule).
   * 
   * @param currentEvent
   *          reference to the current event that triggers the rule
   * 
   * @return a list of Event instances that are result of this rule, NULL in
   *         case that the rule actions does not generate new events.
   */
  protected List< Event< ?>> executeElseActions() {
    return null;
  }

  /**
   * Execute the rule for the current received event.
   * 
   * NOTE: this method can be overridden direct by the Rule subclasses, so a
   * complete freedom is allowed regarding the code of this method. For the
   * simple rules, the subclass can also implement the structured methods:
   * <code>executeDoActions</code>, <code>checkCondition</code>,
   * <code>executeThenActions</code>, <code>executeElseActions</code> as an
   * alternative to rule execution model.
   * 
   * @return the list of possible resulting events as consequence of executing
   *         the rule (might be NULL if no event was created by this rule)
   */
  protected List< Event< ?>> execute() {
    List< Event< ?>> result = null;
    result = this.executeDoActions();
    if (this.checkCondition()) {
      if (result == null) {
        result = this.executeThenActions();
      } else {
        result.addAll(this.executeThenActions());
      }
    } else {
      if (result == null) {
        result = this.executeElseActions();
      } else {
        result.addAll(this.executeElseActions());
      }
    }
    return result;
  }

  /**
   * Return the Class (type) of the event which can trigger this rule.
   * 
   * @return Class (type) of the event which can trigger this rule.
   */
  @SuppressWarnings("unchecked")
  public Class< TriggeringEventType> getTriggeringEventType() {
    return (Class< TriggeringEventType>) ((ParameterizedType) this.getClass()
        .getGenericSuperclass()).getActualTypeArguments()[1];
  }

  /**
   * Set the actual instance of the event which currently triggers this rule.
   * 
   * @param triggeringEvent
   *          the instance of the triggering event which fired this rule
   */
  public void setTriggeringEvent(TriggeringEventType triggeringEvent) {
    if (triggeringEvent != null) {
      this.triggeringEvent = triggeringEvent;
    }
  }

  /**
   * Set the instance of the agent which contains assigned this rule. This will
   * allow access to the agent properties inside the rule (e.g. for condition
   * checks, update values, etc).
   * 
   * @param agent
   *          the agent to which this rule belongs to.
   */
  @SuppressWarnings("unchecked")
  void setAgent(PhysicalAgent agent) {
    this.agent = (AgentRefType) agent;
  }
}