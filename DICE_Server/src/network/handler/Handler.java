package network.handler;

import org.json.simple.JSONObject;

public interface Handler {

	boolean handle(JSONObject jsonMsg);
	
}
