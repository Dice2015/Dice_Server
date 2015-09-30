package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import start.Main;

public class ClientConnection extends Thread {

	private Socket socket;
	private OutputStream outputStream;
	private OutputStreamWriter outputStreamWriter;

	private Main main;
	public ClientConnection(Socket socket, Main main) throws IOException 
	{
		this.main = main;
		this.socket = socket;		
		outputStream = socket.getOutputStream();
		outputStreamWriter = new OutputStreamWriter(outputStream);
		this.setDaemon(true);
		this.setName(this.getClass().getSimpleName());
	}
	
	@Override
	public void run() 
	{
		sendThread.start();
		try {
			InputStreamReader iSR = new InputStreamReader(socket.getInputStream());
			BufferedReader bR = new BufferedReader(iSR);
			String read = "";
			read = bR.readLine();
			JSONParser parser = new JSONParser();
			while (socket.isConnected() && read != null )
		{
				JSONObject jsonMsg = (JSONObject) parser.parse(read);
				NetworkMessage nMessage = new NetworkMessage(this, jsonMsg);
				main.addtoLogicThread(nMessage);
				read = bR.readLine();
			//TODO handler logic
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String msg) throws InterruptedException{
		sendQueue.put(msg);
	}
	
	private void send(String msg) throws IOException
	{
		outputStreamWriter.write(msg + "\n");
		outputStreamWriter.flush();
	}
	
	//eingabeQueue
	private LinkedBlockingQueue <String> sendQueue = new LinkedBlockingQueue<String>(); 


	private Thread sendThread = new Thread(){
		@Override
		public void run(){
			while(socket.isConnected()){
				String s;
				try {
					s = sendQueue.take();
				    send(s);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};
	
}
