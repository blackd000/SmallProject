package chatApp.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.Channels;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.List;
import java.util.ArrayList;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ServerChat {
  private List<PrintWriter> clientWriterList = new ArrayList<>();

  public ServerChat() {
    connection();
  }

  public void connection() {
    try (ServerSocketChannel sChannel = ServerSocketChannel.open()) {
      sChannel.bind(new InetSocketAddress(10_000));

      while (sChannel.isOpen()) {
        SocketChannel clientSocket = sChannel.accept();

        ExecutorService threadPool = Executors.newCachedThreadPool();
        threadPool.submit(new ClientHandle(clientSocket));

        clientWriterList.add(new PrintWriter(Channels.newWriter(clientSocket, UTF_8)));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private class ClientHandle implements Runnable {
    private BufferedReader reader;

    public ClientHandle(SocketChannel clientSocket) {
      reader = new BufferedReader(Channels.newReader(clientSocket, UTF_8));
    }

    @Override
    public void run() {
      try {
        String message;
        while ((message = reader.readLine()) != null) {
          for (PrintWriter eachClientWriter : clientWriterList) {
            eachClientWriter.println(message);
            eachClientWriter.flush();
          }
        }

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    new ServerChat();
  }
}
