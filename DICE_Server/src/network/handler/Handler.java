package network.handler;

import org.json.simple.JSONObject;

import network.NetworkMessage;

public interface Handler {

	boolean handle(NetworkMessage nMessage) throws Exception;
	
}
