package robo.model.event.comm;

import org.json.JSONException;
import org.json.JSONObject;

import robo.comm.Account;
import robo.comm.Telegraf;
import robo.model.event.EventPriority;

public class AuthenticationRequestEvent extends
    CommunicationEvent< Telegraf< ?, ?>> {
  private Account account = null;

  public AuthenticationRequestEvent(Telegraf< ?, ?> source, Account account) {
    super(source, EventPriority.VERY_HIGH);
    this.account = account;
  }

  public AuthenticationRequestEvent(Telegraf< ?, ?> source, Account account,
      String clientName) {
    super(source, EventPriority.VERY_HIGH);
    this.account = account;
    this.setName(clientName);
  }

  public Account getAccount() {
    return this.account;
  }

  public JSONObject toJson() throws JSONException {
    JSONObject result = super.toJson();
    if (this.account != null) {
      result.put("account", this.account.toJson());
    }
    return result;
  }
}
