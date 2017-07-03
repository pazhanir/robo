package robo.comm;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import robo.logger.DefaultLogger;
import robo.model.event.Event;
import robo.model.event.StoppedEvent;
import robo.model.event.comm.AuthenticationFailedEvent;
import robo.model.event.comm.AuthenticationRequestEvent;
import robo.model.event.comm.DisableCommunicationEvent;
import robo.model.event.comm.DisconnectedEvent;

/**
 * Implementation of Telegraf: uses Web Sockets for communication.
 * 
 * @author Mircea Diaconescu
 * @date Apr 8, 2014, 10:48:46 PM
 * 
 */
public class WebSocketsTelegraf extends Telegraf< SocketIO, String> implements
    IOCallback {

  public WebSocketsTelegraf(String serverAddress, int serverPort) {
    super(serverAddress, serverPort);
    // initialize the logger
    this.logger = DefaultLogger.getLogger();
  }

  @Override
  public void on(Event< ?> event) {
    if (this.socket == null || event == null) {
      return;
    }
    try {
      if (this.socket.isConnected()) {
        JSONObject jsonEvent = event.toJson();
        this.socket.emit(jsonEvent.getString("type"), jsonEvent);
      }
      // in case that the event type is: CloseCommunicationEvent
      // then the communication with the server will be closed
      if (StoppedEvent.class.isInstance(event)) {
        this.logger.info(this.getClass().getSimpleName(),
            StoppedEvent.class.getSimpleName()
                + " event was received, request server to close connection.");
        this.socket.emit(DisableCommunicationEvent.class.getSimpleName());
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  @Override
  public < T> void trigger(Event< T> event) {
    // TODO: add code to inform listeners about events coming from server
  }

  @Override
  public void connect() throws Exception {
    if (this.socket != null && this.socket.isConnected()) {
      return;
    }
    this.socket = new SocketIO(this.serverAddress + ":" + this.serverPort);
    socket.connect(this);
    this.socket.emit(AuthenticationRequestEvent.class.getSimpleName(),
        (new AuthenticationRequestEvent(this, this.getAccount())).toJson());
  }

  @Override
  public void connect(String clientName) throws Exception {
    if (this.socket != null && this.socket.isConnected()) {
      return;
    }
    this.socket = new SocketIO(this.serverAddress + ":" + this.serverPort);
    socket.connect(this);
    this.socket.emit(AuthenticationRequestEvent.class.getSimpleName(),
        (new AuthenticationRequestEvent(this, this.getAccount(), clientName))
            .toJson());
  }

  @Override
  public void disconnect() {
    if (this.socket != null && this.socket.isConnected()) {
      this.socket.disconnect();
    }
  }

  @Override
  public void send(String data) throws IOException {
    // NOTE: nothing to do for this class implementation.
  }

  @Override
  public void send(String key, String data) throws IOException {
    this.socket.emit(key, data);
  }

  @Override
  public void on(String eventType, IOAcknowledge arg1, Object... arg2) {
    if (eventType == AuthenticationFailedEvent.class.getSimpleName()) {
      try {
        Thread.sleep(100);
        this.socket.disconnect();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } else if (eventType == DisconnectedEvent.class.getSimpleName()) {
      try {
        Thread.sleep(100);
        this.socket.disconnect();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void onConnect() {
    // TODO: actions taken when the connection with server is established
  }

  @Override
  public void onDisconnect() {
    this.logger.info(this.getClass().getSimpleName(),
        "Disconnected from server.");
    // TODO: actions taken when the connection with server is closed
  }

  @Override
  public void onError(SocketIOException exception) {
    // exceptions with messages that ends with +0 means disconnected
    // if (!exception.getMessage().endsWith("+0")) {
    this.logger.error(this.getClass().getSimpleName(), exception.toString());
    // }
  }

  @Override
  public void onMessage(String arg0, IOAcknowledge arg1) {
  }

  @Override
  public void onMessage(JSONObject arg0, IOAcknowledge arg1) {
  }
}