package network.handler;

import org.json.simple.JSONObject;

public class LoginHandler implements Handler{

	@Override
	public boolean handle(final JSONObject msg) {
		
		String loginMsg = (String) msg.get("type");
		if (loginMsg != null && loginMsg.equals("login"))
		{
			
			
			
			return true;
		}
		
		
		return false;
	}

}
