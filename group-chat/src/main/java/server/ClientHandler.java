package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {

	// Whenever a client sent a message, it will broadcast to others
	private static List<ClientHandler> clientHandlers = new ArrayList<>();

	private Socket socket; // to establish a connection between a server and a client
	private BufferedReader bufferedReader; // read data that sent from client
	private BufferedWriter bufferedWriter; // write data to others
	private String clientUsername;

	public ClientHandler(Socket socket) {
		try {
			this.socket = socket;

			//  Read what client is sending
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// Write to others
			// I want to send over characters so I write character stream, get
			// that from byte stream in Socket object that the client pass
			// BufferedWriter for write more effectively
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			// This will wait for username to send over (this is a block operation)
			clientUsername = bufferedReader.readLine(); // read username when client first connected to

			clientHandlers.add(this); // add this client to the list
		} catch (IOException e) {
			closeEverything(socket, bufferedReader, bufferedWriter);
		}
	}

	/**
	 * Listening for messages, means this is a blocking operation, this will stuck
	 * until a message is delivered So I have to make a separate thread just for
	 * listening for messages so the program will continue do other things like send
	 * a message Ultimately, not just until we receive a message so that we can
	 * continue but while we listening for message, we also sending message
	 */
	@Override
	public void run() {
		// As long as the client is still connected, read what ever the client send
		while (socket.isConnected()) {
			try {
				// This will stop program until receive a message from client, so make it
				// in the separate thread (this is a blocking operation)
				String messageFromClient = bufferedReader.readLine();
				// As soon as we receive a message, send it to other clients
				broadcastMessage(messageFromClient);
			} catch (IOException e) {
				closeEverything(socket, bufferedReader, bufferedWriter);
				break; // when client disconnected, break out
			}
		}
	}

	// Broadcast message to other clients
	private void broadcastMessage(String message) {
		for (ClientHandler client : clientHandlers) {
			try {
				if (!client.clientUsername.equals(clientUsername)) {
					client.bufferedWriter.write(message); // doesn't include new line character
					client.bufferedWriter.newLine(); // so has to because others expect new line to read
					client.bufferedWriter.flush(); // immediately send whatever in buffer even it not big enough
				}
			} catch (IOException e) {
				closeEverything(socket, bufferedReader, bufferedWriter);
			}
		}
	}

	// When client disconnected, remove and notify others
	private void removeClientHandlers() {
		clientHandlers.remove(this);
		broadcastMessage(clientUsername + " has left the chat...");
	}

	// This method is called when client left the chat or an error occurs
	private void closeEverything(Socket socket,
			BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
		removeClientHandlers();
		try {
			/**
			 * **Note:
			 * With stream, you only close outer wrapper, the underlying stream are close when you
			 * close the wrapper, so just close bufferedReader and bufferedWriter (not InputStreamReader,
			 * OutputStreamWriter)
			 * Close socket also close SocketInputStream, SocketInputStream
			 */
			if (socket != null) {
				socket.close();
			}
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (bufferedWriter != null) {
				bufferedWriter.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}








