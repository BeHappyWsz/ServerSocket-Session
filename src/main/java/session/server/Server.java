package session.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {
	public static void main(String[] args) {
		startServer();
	}
	
	public static void startServer(){
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(8888);
			while(true){
				Socket socket = serverSocket.accept();
				ServerThread thread = new ServerThread(socket);
				thread.start();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
