package robo.model.event.comm;

import robo.comm.Telegraf;
import robo.model.event.EventPriority;

public class AuthenticationFailedEvent extends
    CommunicationEvent< Telegraf< ?, ?>> {

  public AuthenticationFailedEvent(Telegraf< ?, ?> source) {
    super(source, EventPriority.VERY_HIGH);
  }
}
