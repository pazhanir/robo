package robo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import robo.logger.DefaultLogger;
import robo.logger.Logger;
import robo.model.event.Event;
import robo.model.event.EventListener;
import robo.model.event.EventPriority;
import robo.model.event.EventSource;
import robo.model.event.StartedEvent;
import robo.model.event.StoppedEvent;

public abstract class PhysicalAgent extends PhysicalObject implements
    EventSource, EventListener, Runnable {
  // flag to determine if the Agent is emulated or physically exists.
  // By default an agent is emulated, but this can be changes.
  private boolean emulated = true;
  // flag that determine if the agent is or not active (this can be used to
  // temporarily deactivate the agent for special cases, (i.e. simulating a
  // defection)
  private boolean active = false;
  // flag that controls when the thread for this agent has to stop
  private boolean threadActive = false;
  // the incoming events Map - the keys are the events priorities and values are
  // the list of events with the same priority
  private Map< EventPriority, List< Event< ?>>> currentEvents = null;
  // event listeners organized by event types
  private Map< Class< ? extends Event< ?>>, List< EventListener>> eventListenersByEventType = null;
  // event listeners registered for ALL event types
  private List< EventListener> eventListenersForAllEvents = null;
  // the map of agent rules - the keys are the triggering event type and values
  // are the list of rules with the same triggering event type
  private Map< Class< ?>, List< Rule< ?, ?>>> rules = null;
  // debugger and logger
  protected Logger logger;

  /**
   * Create a new agent instance and take care of current events initialization
   */
  public PhysicalAgent() {
    this.rules = new HashMap< Class< ?>, List< Rule< ?, ?>>>(4);
    this.currentEvents = new ConcurrentHashMap< EventPriority, List< Event< ?>>>(
        EventPriority.values().length);
    this.eventListenersByEventType = new HashMap< Class< ? extends Event< ?>>, List< EventListener>>(
        1);
    this.eventListenersForAllEvents = new ArrayList< EventListener>(1);
    // by default the agent is active.
    this.setActive(true);
    // initialize the logger
    this.logger = DefaultLogger.getLogger();
  }

  public boolean isEmulated() {
    return emulated;
  }

  public boolean isActive() {
    return active;
  }

  /**
   * Activate or deactivate an agent. If the agent is deactivated, then all the
   * incoming events for it will be ignored, but listeners can still be
   * registered for later usage when the agent becomes again active. Rules can
   * be added or removed also when an agent is inactive.
   * 
   * NOTE: this will NOT stop the thread on which this agent runs (if the thread
   * is already active), but will only temporarily (until this method is called
   * again with a <code>true</code> parameter) deactivate the agent behavior.
   * 
   * @param active
   *          a true value will activate the agent, while a false value will
   *          deactivate it
   */
  public void setActive(boolean active) {
    if (active) {
      for (EventPriority priority : EventPriority.values()) {
        this.currentEvents
            .put(priority, new CopyOnWriteArrayList< Event< ?>>());
      }
    }
    // if the agent becomes inactive, all the current events are no longer
    // processed, so they are deleted
    else {
      this.currentEvents.clear();
    }
    this.active = active;
  }

  /**
   * Add a rule to the agent. The same rule instance can be shared between
   * different agents (even if their type is different) if desired.
   * 
   * @param rule
   *          the rule instance to be used by the agent
   */
  public void addRule(
      Rule< ? extends PhysicalAgent, ? extends Event< ? extends PhysicalAgent>> rule) {
    List< Rule< ? extends PhysicalAgent, ? extends Event< ? extends PhysicalAgent>>> rulesForEventType = null;
    if (rule == null) {
      return;
    }
    rule.setAgent(this);
    rulesForEventType = this.rules.get(rule.getTriggeringEventType());
    if (rulesForEventType != null) {
      rulesForEventType.add(rule);
    } else {
      rulesForEventType = new ArrayList< Rule< ? extends PhysicalAgent, ? extends Event< ? extends PhysicalAgent>>>();
      rulesForEventType.add(rule);
      this.rules.put(rule.getTriggeringEventType(), rulesForEventType);
    }
  }

  @Override
  public synchronized void on(Event< ?> event) {
    if (event == null) {
      return;
    } else if (this.active && this.threadActive) {
      this.currentEvents.get(event.getPriority()).add(event);
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public synchronized < T> void trigger(Event< T> event) {
    if (event == null) {
      return;
    } else if (this.active) {
      if (event.getSource() == null) {
        event.setSource((T) this);
      }
      List< EventListener> listeners = this.eventListenersByEventType.get(event
          .getClass());
      if (listeners != null) {
        for (EventListener listener : listeners) {
          listener.on(event);
        }
      }
      if (this.eventListenersForAllEvents != null) {
        for (EventListener listener : this.eventListenersForAllEvents) {
          listener.on(event);
        }
      }
    }
  }

  @Override
  public boolean addListener(EventListener eventListener) {
    if (eventListener == null) {
      return false;
    }
    this.eventListenersForAllEvents.add(eventListener);
    return true;
  }

  @Override
  public boolean addListenerForEventType(Class< ? extends Event< ?>> eventType,
      EventListener eventListener) {
    if (eventType == null || eventListener == null) {
      return false;
    }
    List< EventListener> listeners = this.eventListenersByEventType
        .get(eventType);
    if (listeners == null) {
      listeners = new ArrayList< EventListener>(1);
      listeners.add(eventListener);
      this.eventListenersByEventType.put(eventType, listeners);
    } else {
      listeners.add(eventListener);
    }
    return true;
  }

  /**
   * Initialize the agent. Do any initialization required before starting the
   * agent. This method is called just once before the thread of this agent
   * starts. The purpose of this method is to be implemented by any subclass
   * that requires this initialization capability.
   * 
   */
  public abstract void initialize() throws Exception;

  /**
   * This method should NOT be directly used. Instead use the <code>start</code>
   * method.
   */
  @Override
  public void run() {
    try {
      this.initialize();
      this.initializeRules();
      this.onStarted();
      this.trigger(new StartedEvent(this));
      this.logger.debug("[" + this.getClass().getSimpleName() + "] Started: "
          + this.getId() + ",  " + this.getClass().getSimpleName() + "( '"
          + this.getName() + "')");
      while (this.threadActive) {
        if (this.active) {
          // freeze this thread for 1ms so other threads can also perform their
          // tasks (Thread.yield may be used, but seems unreliable...).
          Thread.sleep(1);
          this.onCycleStarts();
          processCurrentEvents();
          this.onCycleEnds();
        }
      }
      this.onStopped();
      this.trigger(new StoppedEvent(this));
      this.logger.debug("[" + this.getClass().getSimpleName() + "] Ended: "
          + this.getId() + ",  " + this.getClass().getSimpleName() + "( '"
          + this.getName() + "')");
    } catch (Exception e) {
      this.logger.fatal("[" + this.getClass().getSimpleName() + "] Ended: "
          + this.getId() + ",  " + this.getClass().getSimpleName() + "( '"
          + this.getName() + "') because of the following fatal error: ");
      this.logger.fatal(e);
      e.printStackTrace();
    }
  }

  /**
   * Helper method that will allow to initialize the rules before the agent is
   * started. At the point when this method is called, the agent is already
   * initialized, so the rule initialization can get access to all the agent
   * data (e.g. components, subcomponents, etc).
   */
  private void initializeRules() {
    for (Class< ?> key : this.rules.keySet()) {
      for (Rule< ?, ?> rule : this.rules.get(key)) {
        try {
          rule.initialize();
        } catch (Exception e) {
          this.logger.error(e);
        }
      }
    }
  }

  /**
   * This method acts as a special listener and is called once the system was
   * initialized and just before starting to process anything.
   * 
   * NOTE: this method is a special way to replace or supplement the receival of
   * the asynchronous <code>StartedEvent</code> that has a very similar
   * functionality, only that its receival is asynchronous. Also, the
   * <code>StartedEvent</code> is normally received by the registered listeners,
   * and will not be implicitly received by the agent that trigger it without an
   * explicit registering of such events triggered by itself.
   */
  public abstract void onStarted();

  /**
   * This method acts as a special listener and is called once the system was
   * stopped - this can be used to do cleanup when the system stops.
   * 
   * NOTE: this method is a special way to replace or supplement the receival of
   * the asynchronous <code>StoppedEvent</code> that has a very similar
   * functionality, only that its receival is asynchronous. Also, the
   * <code>StoppedEvent</code> is normally received by the registered listeners,
   * and will not be implicitly received by the agent that trigger it without an
   * explicit registering of such events triggered by itself.
   */
  public abstract void onStopped();

  /**
   * Start the activity of this agent.
   * 
   * NOTE: this will start the thread on which this agent runs.
   */
  public void start() {
    this.logger.debug("[" + this.getClass().getSimpleName()
        + "] Start request for: " + this.getClass().getSimpleName()
        + "( name = '" + this.getName() + "', id='" + this.getId() + "')");
    if (!this.threadActive) {
      this.threadActive = true;
      (new Thread(this)).start();
    }
  }

  /**
   * Stop the activity (thread) of this agent. The method will only stop the
   * agent after the processing of the current "step" ends - that is, all the
   * events from the current events queue are processed then the agent thread
   * (activity) is terminated.
   * 
   * NOTE: after calling this method, no incoming events will be accepted.
   */
  public void stop() {
    this.logger.debug("[" + this.getClass().getSimpleName()
        + "] Stop request for: " + this.getClass().getSimpleName()
        + "( name = '" + this.getName() + "', id='" + this.getId() + "')");
    this.threadActive = false;
  }

  /**
   * Helper method used to process the current incoming events queue, execute
   * the rule and then propagate the possible resulting events to the listeners.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private synchronized void processCurrentEvents() {
    List< Event< ?>> currentEventsByPriority = null;
    List< Rule< ?, ?>> rulesForEventType = null;
    List< Event< ?>> resultingEvents = new ArrayList< Event< ?>>(8);
    // loop through the current events in order of the priorities
    for (EventPriority priority : EventPriority.values()) {
      // get the list of events for the current step priority
      currentEventsByPriority = this.currentEvents.get(priority);
      // loop through the list of events of current priority level
      List< Event< ?>> eventsToRemove = new ArrayList< Event< ?>>();
      for (Event< ?> currentEvent : currentEventsByPriority) {
        eventsToRemove.add(currentEvent);
        // loop through the rules that are triggered by the same type as
        // of the current extracted event type
        rulesForEventType = this.rules.get(currentEvent.getClass());
        if (rulesForEventType == null) {
          continue;
        }
        for (Rule rule : rulesForEventType) {
          rule.setTriggeringEvent(currentEvent);
          List< Event< ?>> ruleResultingEvents = rule.execute();      
          if (ruleResultingEvents != null) {
            resultingEvents.addAll(ruleResultingEvents);
          }
        }
        // remove processed events
        currentEventsByPriority.removeAll(eventsToRemove);
        // allow other processes to get the control (according to Java
        // specification, this may have no effect in some cases result!)
        Thread.yield();
      }
      // propagate the resulting events to the listeners
      for (Event< ?> resultingEvent : resultingEvents) {
        this.trigger(resultingEvent);
      }
      resultingEvents.clear();
    }
  }

  /**
   * This method must be implemented by the subclasses and it must contains any
   * code that is supposed to be executed in each cycle of the thread. For
   * example, this is where the code for sensor reading data must go in the
   * sensor implementation classes. The method is not abstract because not all
   * subclasses may need to implement it.
   * 
   * NOTE: this method is called by the thread, once for each cycle, before
   * processing the current events.
   */
  public void onCycleStarts() {
  }

  /**
   * This method must be implemented by the subclasses and it must contains any
   * code that is supposed to be executed in each cycle of the thread. For
   * example, this is where the code for sensor reading data must go in the
   * sensor implementation classes. The method is not abstract because not all
   * subclasses may need to implement it.
   * 
   * NOTE: this method is called by the thread, once for each cycle, after
   * processing the current events, and it is the last action of the current
   * thread cycle.
   */
  public void onCycleEnds() {
  }
}