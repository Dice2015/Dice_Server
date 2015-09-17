package start;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import network.ClientConnection;

public class Main {
	
	private static boolean running = true;

	public static void main(String[] args) 
	{
		new Main();
		
	}
	
	public Main(){
		try {
			ServerSocket serverSocket = new ServerSocket(12345);
			while (running )
			{
				Socket s = serverSocket.accept();
				new ClientConnection(s, Main.this);
			}
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
