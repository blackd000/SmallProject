package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

	private Socket socket; // to communicate with the server (ClientHandler)
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private String username;

	public ChatClient(Socket socket,  String username) {
		try {
			this.socket = socket;
			this.username = username;
			
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			closeEverything(socket, bufferedReader, bufferedWriter);
		}
	}
	
	private void sendMessage() {
		try {
			bufferedWriter.write(username); // will go to constructor of ClientHandler
			bufferedWriter.newLine();
			bufferedWriter.flush();
			
			Scanner scanner = new Scanner(System.in);
			while (socket.isConnected()) {
				String message = scanner.nextLine();
				bufferedWriter.write(username + ": " + message);
				bufferedWriter.newLine();
				bufferedWriter.flush();
			}
		} catch (IOException e) {
			
		}
	}

	private void closeEverything(Socket socket,
			BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
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







