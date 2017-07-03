package robo.model.event.comm;

import robo.comm.Telegraf;
import robo.model.event.EventPriority;

public class DisconnectedEvent extends CommunicationEvent< Telegraf< ?, ?>> {
  public DisconnectedEvent(Telegraf< ?, ?> source) {
    super(source, EventPriority.VERY_HIGH);
  }
}
