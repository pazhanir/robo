package robo.model.event.comm;

import robo.comm.Telegraf;
import robo.model.event.EventPriority;

/**
 * Built in Event type used to inform the server communication components that
 * they the must disconnect from the server (if a connection with the server
 * exists).
 * 
 * @author Mircea Diaconescu
 * 
 */
public class DisableCommunicationEvent extends
    CommunicationEvent< Telegraf< ?, ?>> {
  public DisableCommunicationEvent(Telegraf< ?, ?> source) {
    super(source, EventPriority.VERY_HIGH);
  }
}
