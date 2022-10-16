package chatApp.src;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ClientChat {
  private String name = "Anonymous";
  private PrintWriter writer;
  private BufferedReader reader;
  private Font font = new Font(Font.SERIF, Font.PLAIN, 18);
  private JTextField outgoingTextField;
  private JTextArea incomingTextArea;

  public ClientChat() {
    connection();
    setupGUI();
  }

  public void connection() {
    try {
      SocketChannel serverChannel = 
          SocketChannel.open(new InetSocketAddress("127.0.0.1", 10_000));

      reader = new BufferedReader(Channels.newReader(serverChannel, UTF_8));
      writer = new PrintWriter(Channels.newWriter(serverChannel, UTF_8));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void setupGUI() {
    JFrame frame = new JFrame("Simple chat client");

    JPanel panel = new JPanel();

    JScrollPane scroller = createScrollableTextArea();

    outgoingTextField = new JTextField(30);
    outgoingTextField.setFont(font);

    JButton sendButton = new JButton("Send");
    sendButton.setFont(font);
    sendButton.addActionListener(event -> sendMessage());

    ExecutorService executor = Executors.newSingleThreadExecutor();
    executor.execute(() -> readMessage());

    panel.add(scroller);
    panel.add(outgoingTextField);
    panel.add(sendButton);

    frame.getContentPane().add(BorderLayout.CENTER, panel);

    frame.setVisible(true);
    frame.setSize(550, 600);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    outgoingTextField.requestFocus();
  }

  private JScrollPane createScrollableTextArea() {
    incomingTextArea = new JTextArea(20, 35);
    incomingTextArea.setFont(font);
    incomingTextArea.setLineWrap(true);
    incomingTextArea.setWrapStyleWord(true);
    incomingTextArea.setEditable(false);

    JScrollPane scroller = new JScrollPane(incomingTextArea);
    scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    return scroller;
  }

  private void sendMessage() {
    writer.println(outgoingTextField.getText());
    writer.flush();
    
    outgoingTextField.setText("");
    outgoingTextField.requestFocus();
  }

  private void readMessage() {
    try {
      String message;
      while ((message = reader.readLine()) != null) {
        incomingTextArea.append(name + ": " + message + "\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    new ClientChat();
  }
}
