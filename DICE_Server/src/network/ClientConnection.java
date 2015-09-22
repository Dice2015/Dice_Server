package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import start.Main;

public class ClientConnection extends Thread {

	private Socket socket;
	private OutputStream outputStream;
	private OutputStreamWriter outputStreamWriter;

	public ClientConnection(Socket socket, Main main) throws IOException 
	{
		this.socket = socket;		
		outputStream = socket.getOutputStream();
		outputStreamWriter = new OutputStreamWriter(outputStream);
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
			while (socket.isConnected() && read != null )
		{
				
				read = bR.readLine();
			//TODO handler logic
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String msg) throws InterruptedException{
		lBQ.put(msg);
	}
	
	private void send(String msg) throws IOException
	{
		outputStreamWriter.write(msg + "\n");
		outputStreamWriter.flush();
	}
	
	//eingabeQueue
	private LinkedBlockingQueue <String> lBQ = new LinkedBlockingQueue<String>(); 

	private Thread sendThread = new Thread(){
		@Override
		public void run(){
			while(socket.isConnected()){
				String s;
				try {
					s = lBQ.take();
				    send(s);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};
	
}
