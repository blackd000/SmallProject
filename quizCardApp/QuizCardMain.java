package OOP.c16_fileIO.NIOApproach;

import javax.swing.*;
import java.awt.*;

public class QuizCardMain {
  public void buildGUI() {
    JFrame frame = new JFrame("Quiz Card Main");

    Font font = new Font(Font.SERIF, Font.PLAIN, 20);

    JPanel mainPanel = new JPanel();

    Box buttonBox = new Box(BoxLayout.Y_AXIS);

    JButton builderButton = new JButton("Create Set");
    builderButton.setFont(font);
    builderButton.addActionListener(event -> {
      new QuizCardBuilder().buildGUI();
      frame.dispose();
    });

    JButton playButton = new JButton("Learn Set");
    playButton.setFont(font);
    playButton.addActionListener(event -> {
      new QuizCardPlayer().buildGUI();
      frame.dispose();
    });

    buttonBox.add(builderButton);
    buttonBox.add(playButton);

    mainPanel.add(buttonBox);

    frame.getContentPane().add(BorderLayout.CENTER, mainPanel);

    frame.setSize(600, 680);
    frame.setVisible(true);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public static void main(String[] args) {
    new QuizCardMain().buildGUI();
  }
}
