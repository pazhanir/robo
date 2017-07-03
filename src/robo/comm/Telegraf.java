package robo.comm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import robo.logger.Logger;
import robo.model.event.Event;
import robo.model.event.EventListener;
import robo.model.event.EventSource;

/**
 * The communication protocol is implemented by this class. This is the
 * middle-layer between the Agent and the Server with respect to communication.
 * 
 * @author Mircea Diaconescu
 * @date Apr 8, 2014, 10:48:46 PM
 * 
 */
public abstract class Telegraf< S, A> implements EventListener, EventSource {
  // the server address
  protected A serverAddress = null;
  // the UDP port of the server
  protected int serverPort = -1;
  // the UDP port of the client
  protected int clientPort = -1;
  // the socket used for communication with the server
  protected S socket = null;
  // the user object - used only for Telegraf implementations that requires it
  private Account account = null;
  // event listeners organized by event types
  private Map< Class< ? extends Event< ?>>, List< EventListener>> eventListenersByEventType = null;
  // event listeners registered for ALL event types
  private List< EventListener> eventListenersForAllEvents = null;
  // the logger
  protected Logger logger = null;

  /**
   * @param serverAddress
   *          the server address
   */
  public Telegraf(A serverAddress, int serverPort) {
    this.serverAddress = serverAddress;
    this.serverPort = serverPort;
    this.eventListenersByEventType = new HashMap< Class< ? extends Event< ?>>, List< EventListener>>(
        1);
    this.eventListenersForAllEvents = new ArrayList< EventListener>(1);
  }

  /**
   * @param serverAddress
   *          the server address
   * @param serverPort
   *          the port used for communication (server side)
   * @param clientPort
   *          the port used for communication (client side)
   */
  public Telegraf(A serverAddress, int serverPort, int clientPort) {
    this(serverAddress, serverPort);
    this.clientPort = clientPort;
  }

  /**
   * Get the account object.
   * 
   * @return the account object or null if none was set.
   */
  public Account getAccount() {
    return account;
  }

  /**
   * Set a new account object.
   * 
   * @param account
   *          the new account object to be set.
   */
  public void setAccount(Account account) {
    this.account = account;
  }

  /**
   * Establish the communication channel with the server, i.e. connect to the
   * server by using the specific socket implementation.
   * 
   * @throws Exception
   *           exceptions that may be thrown during the connection process.
   */
  public abstract void connect() throws Exception;

  /**
   * Establish the communication channel with the server, i.e. connect to the
   * server by using the specific socket implementation. A client name is
   * specified when this method is used.
   * 
   * @param clientName
   *          the name used to connect to serve (client name)
   * 
   * @throws Exception
   *           exceptions that may be thrown during the connection process.
   */
  public abstract void connect(String clientName) throws Exception;

  /**
   * Disable the communication channel with the server, i.e. disconnect from the
   * server by using the specific socket implementation.
   */
  public abstract void disconnect();

  /**
   * Send data to the server. This method should only be called after the socket
   * was initialized in the implementation class.
   * 
   * @param data
   *          the data packet to send
   * @throws IOException
   */
  public abstract void send(String data) throws IOException;

  /**
   * Send data to the server. This method should only be called after the socket
   * was initialized in the implementation class.
   * 
   * @param key
   *          a key with the purpose of being used as event type
   * @param data
   *          the data packet to send
   * @throws IOException
   */
  public abstract void send(String key, String data) throws IOException;

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
}
