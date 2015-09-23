package start;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Logic.User;
import network.ClientConnection;
import network.handler.Handler;
import network.handler.LoginHandler;

public class Main {
	
	private static boolean running = true;
	private ArrayList<ClientConnection> connections = new ArrayList<>();
	private ArrayList<Handler> handlerChain = new ArrayList<>();
	private static ArrayList<User> users = new ArrayList<>();
	
	public static void main(String[] args) 
	{
		
		new Main();
		
	}
	
	public Main(){
		handlerChain.add(new LoginHandler());
		logicThread.start();
		try {
			ServerSocket serverSocket = new ServerSocket(12345);
			while (running )
			{
				Socket socket = serverSocket.accept();
				ClientConnection cc = new ClientConnection(socket, Main.this);
				cc.start();
				connections.add(cc);
			}
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ClientConnection> getConnections() {
		return connections;
	}
	
	private LinkedBlockingQueue <String> readQueue = new LinkedBlockingQueue<String>();

	public void addtoLogicThread(String read) {
		// TODO Auto-generated method stub
		try {
			readQueue.put(read);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 

	
	private Thread logicThread = new Thread(){
		@Override
		public void run(){
			String msg= null;
			try {
				JSONParser parser = new JSONParser();
			while(running ){
					msg = readQueue.take();
					JSONObject jsonMsg = (JSONObject) parser.parse(msg);
					for(Handler h : handlerChain){
						if(h.handle(jsonMsg)){
							break;
						}
					}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	};
	
	public static ArrayList<User> getUsers() {
		return users;
	}
	
}
