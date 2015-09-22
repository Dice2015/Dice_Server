package start;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import network.ClientConnection;
import Logic.handler.Handler;
import Logic.handler.loginHandler;

public class Main {
	
	private static boolean running = true;
	private ArrayList<ClientConnection> connections = new ArrayList<>();
	private ArrayList<Handler> handlerChain = new ArrayList<>();
	
	public static void main(String[] args) 
	{
		
		new Main();
		
	}
	
	public Main(){
		handlerChain.add(new loginHandler());
		logicThread.start();
		try {
			ServerSocket serverSocket = new ServerSocket(12345);
			while (running )
			{
				Socket socket = serverSocket.accept();
				ClientConnection cc = new ClientConnection(socket, Main.this);
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
	
	private LinkedBlockingQueue <String> lBQread = new LinkedBlockingQueue<String>();

	public void addtoLogicThread(String read) {
		// TODO Auto-generated method stub
		try {
			lBQread.put(read);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 

	
	private Thread logicThread = new Thread(){
		@Override
		public void run(){
			String s="";
			try {
			while(running ){
					s = lBQread.take();
					for(Handler h : handlerChain){
						if(h.handle()){
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
	
}
