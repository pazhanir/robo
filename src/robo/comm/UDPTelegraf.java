package robo.comm;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import robo.model.event.Event;

/**
 * Implementation of Telegraf: uses Web Sockets for communication.
 * 
 * @author Mircea Diaconescu
 * @date Apr 8, 2014, 10:48:46 PM
 * 
 */
public class UDPTelegraf extends Telegraf< DatagramSocket, InetAddress> {
  public UDPTelegraf(String serverAddress, int serverPort, int clientPort)
      throws UnknownHostException {
    super(InetAddress.getByName(serverAddress), serverPort, clientPort);
  }

  @Override
  public void connect() throws SocketException, UnknownHostException {
    this.socket = new DatagramSocket(this.clientPort);
  }

  @Override
  public void connect(String name) throws Exception {
    // TODO Auto-generated method stub
  }

  @Override
  public < T> void trigger(Event< T> event) {
  }

  @Override
  public void on(Event< ?> event) {
  }

  @Override
  public void send(String data) throws IOException {
    byte[] sendData = data.getBytes();
    DatagramPacket packet = new DatagramPacket(sendData, sendData.length,
        this.serverAddress, this.serverPort);
    this.socket.send(packet);

    byte[] d = new byte[256];
    DatagramPacket pack = new DatagramPacket(d, d.length);
    socket.receive(pack);
    System.out.println(new String(pack.getData(), 0, pack.getLength()));
    System.out.println(pack.getAddress() + ":" + pack.getPort());
  }

  @Override
  public void send(String key, String data) throws IOException {
    // TODO Auto-generated method stub

  }

  @Override
  public void disconnect() {
    // TODO Auto-generated method stub

  }
}
