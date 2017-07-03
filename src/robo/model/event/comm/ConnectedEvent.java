package robo.model.event.comm;

import robo.comm.Telegraf;
import robo.model.event.EventPriority;

public class ConnectedEvent extends CommunicationEvent< Telegraf< ?, ?>> {
  public ConnectedEvent(Telegraf< ?, ?> source) {
    super(source, EventPriority.VERY_HIGH);
  }
}
