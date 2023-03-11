package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

	// This object will listen for incoming connections (clients) and create a Socket object
	// is used to communication with them
	private ServerSocket serverSocket;
	
	public ChatServer(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	// To keep server running
	public void startServer() {
		try {
			while (!serverSocket.isClosed()) {
				// Just siting here and waiting for clients to connect
				Socket socket = serverSocket.accept(); // can throw IOException when waiting for connection
				System.out.println("A new client has just connected");
				
				// Object of this class will communicating with the client
				ClientHandler clientHandler = new ClientHandler(socket);
				
				Thread thread = new Thread(clientHandler);
				thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void closeServerSocket() {
		try {
			if (serverSocket != null) {
				serverSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		// Will listening for clients that try to connect to this port
		ServerSocket sSocket = new ServerSocket(12345);
		ChatServer chatServer = new ChatServer(sSocket);
		chatServer.startServer();
	}

}











