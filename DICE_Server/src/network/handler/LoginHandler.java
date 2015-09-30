package network.handler;

import org.json.simple.JSONObject;

import Logic.User;
import network.ClientConnection;
import network.NetworkMessage;
import start.Main;

public class LoginHandler implements Handler{

	@Override
	public boolean handle(final NetworkMessage nMessage) throws Exception {
		
		JSONObject msg = nMessage.getMsg();
		ClientConnection connection = nMessage.getConnection();
		String loginMsg = (String) msg.get("type");
		JSONObject response = new JSONObject();
		if (loginMsg != null && loginMsg.equals("login"))
		{
			String user = (String) msg.get("user");
			Main.getUsers().add(new User(user));
			
			System.out.println("User " + user + " added");
			
			response.put("loginResponse", "LOGIN_OK");
			connection.sendMessage(response.toJSONString());
			
			return true;
		}
		response.put("loginResponse", "LOGIN_ERROR");
		connection.sendMessage(response.toJSONString());
		
		return false;
	}

}
