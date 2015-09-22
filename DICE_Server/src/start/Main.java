package start;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import network.ClientConnection;

public class Main {
	
	private static boolean running = true;
	private ArrayList<ClientConnection> connections = new ArrayList<>();

	public static void main(String[] args) 
	{
		new Main();
		
	}
	
	public Main(){
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

}
