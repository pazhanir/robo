package robo.model.event.comm;

import robo.comm.Telegraf;
import robo.model.event.EventPriority;

public class DisconnectRequestEvent extends
    CommunicationEvent< Telegraf< ?, ?>> {
  public DisconnectRequestEvent(Telegraf< ?, ?> source) {
    super(source, EventPriority.VERY_HIGH);
  }
}
