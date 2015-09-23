package network.handler;

import java.nio.file.attribute.UserPrincipalNotFoundException;

import org.json.simple.JSONObject;

import Logic.User;
import start.Main;

public class LoginHandler implements Handler{

	@Override
	public boolean handle(final JSONObject msg) {
		
		String loginMsg = (String) msg.get("type");
		if (loginMsg != null && loginMsg.equals("login"))
		{
			String user = (String) msg.get("user");
			Main.getUsers().add(new User(user));
			
			
			return true;
		}
		
		
		return false;
	}

}
