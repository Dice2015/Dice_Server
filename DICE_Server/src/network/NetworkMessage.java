package network;

import org.json.simple.JSONObject;

public class NetworkMessage {

	private ClientConnection connection;
	private JSONObject msg;

	public NetworkMessage(ClientConnection connection, JSONObject msg) {
		this.connection = connection;
		this.msg = msg;
	}
	
	public ClientConnection getConnection() {
		return connection;
	}
	
	public JSONObject getMsg() {
		return msg;
	}	
}
