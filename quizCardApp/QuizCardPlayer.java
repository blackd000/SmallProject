package OOP.c16_fileIO.NIOApproach;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.ArrayList;
// import java.util.Arrays;

public class QuizCardPlayer {
  private JFrame frame;
  private JTextArea displayQuestion;
  private JTextArea displayAnswer;
  private JButton returnButton;
  private JButton showButton;
  private JButton nextButton;
  // private QuizCard currenCard;
  private List<QuizCard> cardList;
  private int currentCardIndex;
  // private int howManyCorrect;

  public QuizCardPlayer() {
  }

  public void buildGUI() {
    frame = new JFrame("Quiz Card Player");

    Font font = new Font(Font.SERIF, Font.PLAIN, 20);

    JPanel mainPanel = new JPanel();

    // create some textArea
    displayQuestion = createTextArea(font);
    JScrollPane qScroller = createScrollPane(displayQuestion);
    displayAnswer = createTextArea(font);
    JScrollPane aScroller = createScrollPane(displayAnswer);

    // create some buttons
    returnButton = new JButton("Return");
    returnButton.setFont(font);
    returnButton.addActionListener(event -> {
      new QuizCardMain().buildGUI();
      frame.dispose();
    });

    showButton = new JButton("Show Card");
    showButton.setFont(font);
    showButton.addActionListener(event -> showCard());

    nextButton = new JButton("Next");
    nextButton.setFont(font);
    nextButton.addActionListener(event -> nextCard());

    // mainPanel add Component
    mainPanel.add(new JLabel("Question"));
    mainPanel.add(qScroller);
    mainPanel.add(new JLabel("Answer"));
    mainPanel.add(aScroller);
    mainPanel.add(returnButton);
    mainPanel.add(showButton);
    mainPanel.add(nextButton);

    // create MenuBar for frame
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");

    JMenuItem openMenuItem = new JMenuItem("Open");
    openMenuItem.addActionListener(event -> openFile());

    fileMenu.add(openMenuItem);

    menuBar.add(fileMenu);

    frame.setJMenuBar(menuBar);

    // frame add Component
    frame.getContentPane().add(BorderLayout.CENTER, mainPanel);

    frame.setSize(600, 680);
    frame.setVisible(true);
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private JTextArea createTextArea(Font font) {
    JTextArea textArea = new JTextArea(9, 34);
    textArea.setFont(font);
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    textArea.setTabSize(1);
    textArea.setEditable(false);

    return textArea;
  }

  private JScrollPane createScrollPane(JTextArea textArea) {
    JScrollPane scroller = new JScrollPane(textArea);
    scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    return scroller;
  }

  private void openFile() {
    cardList = new ArrayList<>();
    currentCardIndex = 0;

    String pathQuizCard = "./quizCardApp/SavedFileQuizCard";
    JFileChooser fileChooser = new JFileChooser(pathQuizCard);
    fileChooser.showOpenDialog(frame);

    // // ********** io approach **********
    // try {
    //   BufferedReader bufferedReader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));

    //   String line;
    //   while ((line = bufferedReader.readLine()) != null) {
    //     String[] questionAnswer = line.split("///");
    //     cardList.add(new QuizCard(questionAnswer[0], questionAnswer[1]));
    //     System.out.println(cardList);
    //   }

    //   displayQuestion.setText(cardList.get(currentCardIndex).getQuestion());

    //   bufferedReader.close();
    // } catch (IOException e) {
    //   e.printStackTrace();
    // }

    // ********** nio approach **********
    try {
      Files.lines(Path.of(fileChooser.getSelectedFile().getAbsolutePath()))
          .forEach(line -> {
            /**
             * If the limit is positive then the pattern will be applied at most limit - 1 times, the
             * array's length will be no greater than limit, and the array's last entry will contain all
             * input beyond the last matched delimiter.
             * 
             * If the limit is zero then the pattern will be applied as many times as possible, the array
             * can have any length, and trailing empty strings will be discarded.
             * 
             * If the limit is negative then the pattern will be applied as many times as possible and the
             * array can have any length.
             */
            String[] questionAnswer = line.split("///", 2);
            cardList.add(new QuizCard(questionAnswer[0], questionAnswer[1]));
            System.out.println(cardList);
          });
      displayQuestion.setText(cardList.get(currentCardIndex).getQuestion());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void showCard() {
    displayAnswer.setText(cardList.get(currentCardIndex).getAnwser());
    showButton.setEnabled(false);
  }

  private void nextCard() {
    if (++currentCardIndex < cardList.size()) {
      // System.out.println(currentCardIndex + " " + cardList.size());
      displayQuestion.setText(cardList.get(currentCardIndex).getQuestion());
      displayAnswer.setText("");
      showButton.setEnabled(true);
    } else {
      int option = JOptionPane.showConfirmDialog(frame,
          "Finish!! Again or Return to main Panel?",
          "Congratulation!!",
          JOptionPane.YES_NO_OPTION);
      System.out.println(option);
    }
  }

  public static void main(String[] args) {
    QuizCardPlayer quizCardPlayer = new QuizCardPlayer();
    quizCardPlayer.buildGUI();
  }
}
