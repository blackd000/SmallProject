package OOP.c16_fileIO.NIOApproach;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class QuizCardBuilder {
  private List<QuizCard> cardList = new ArrayList<>();
  private JFrame frame;
  private JTextArea questionTextArea;
  private JTextArea answerTextArea;

  public QuizCardBuilder() {
  }

  public void buildGUI() {
    frame = new JFrame("Quiz Card Builder");

    JPanel mainPanel = new JPanel();

    Font font = new Font(Font.SERIF, Font.PLAIN, 20);

    // textArea
    questionTextArea = createTextArea(font);
    JScrollPane qScroller = createScroller(questionTextArea);
    answerTextArea = createTextArea(font);
    JScrollPane aScroller = createScroller(answerTextArea);

    JButton returnButton = new JButton("Return");
    returnButton.setFont(font);
    returnButton.addActionListener(event -> {
      new QuizCardMain().buildGUI();
      frame.dispose();
    });

    JButton nextButton = new JButton("Add Card");
    nextButton.setFont(font);
    nextButton.addActionListener(event -> addCard());

    // mainPanel add stuff
    mainPanel.add(new JLabel("Question"));
    mainPanel.add(qScroller);
    mainPanel.add(new JLabel("Answer"));
    mainPanel.add(aScroller);
    mainPanel.add(returnButton);
    mainPanel.add(nextButton);

    // MenuBar
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");

    JMenuItem newMenuItem = new JMenuItem("New");
    newMenuItem.addActionListener(event -> clearAll());

    JMenuItem saveMenuItem = new JMenuItem("Save");
    saveMenuItem.addActionListener(event -> saveCard());

    fileMenu.add(newMenuItem);
    fileMenu.add(saveMenuItem);

    menuBar.add(fileMenu);

    frame.setJMenuBar(menuBar);

    frame.getContentPane().add(BorderLayout.CENTER, mainPanel);

    // frame.pack();
    frame.setSize(600, 680);
    frame.setVisible(true);
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private JTextArea createTextArea(Font font) {
    JTextArea textArea = new JTextArea(9, 34);
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    textArea.setTabSize(1);
    textArea.setFont(font);

    return textArea;
  }

  private JScrollPane createScroller(JTextArea textArea) {
    JScrollPane scroller = new JScrollPane(textArea);
    scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    return scroller;
  }

  // add the current card to the list and clear the text areas
  private void addCard() {
    if (questionTextArea.getText().equals("")) {
      JOptionPane.showMessageDialog(frame,
          "What's the question boi",
          "Warning!!!",
          JOptionPane.WARNING_MESSAGE);
    } else {
      cardList.add(new QuizCard(questionTextArea.getText(), answerTextArea.getText()));
      System.out.println(cardList);
      // cardList.forEach(eachQuizCard -> System.out.println(eachQuizCard));
      clearCard();
    }
  }

  // bring up a file dialog box let the user name and save the set (save all the
  // cards in the current list)
  private void saveCard() {
    if (!questionTextArea.getText().equals("")) {
      cardList.add(new QuizCard(questionTextArea.getText(), answerTextArea.getText()));
      clearCard();
    }
    JFileChooser fileSave = new JFileChooser();
    fileSave.showSaveDialog(frame);
    saveFile(fileSave.getSelectedFile());
  }

  private void saveFile(File file) {
    // // Old
    // BufferedWriter writer = null;
    // try {
    //   // chain BufferedWriter to FileWriter to make writing more efficient
    //   writer = new BufferedWriter(new FileWriter(file, true));

    //   for (QuizCard eachCard : cardList) {
    //     writer.write(eachCard.getQuestion() + "///");
    //     writer.write(eachCard.getAnwser() + "\n");
    //   }

    //   writer.close();
    // } catch (IOException e) {
    //   System.out.println("Couldn't wirte: " + e.getMessage());
    // } finally {
    //   try {
    //     writer.close();
    //   } catch (IOException e) {
    //     System.out.println("Couldn't wirte: " + e.getMessage());
    //   }
    // }

    // New
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      for (QuizCard eachCard : cardList) {
        writer.write(eachCard.getQuestion() + "///");
        writer.write(eachCard.getAnwser() + "\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // clear all cards in the set
  private void clearAll() {
    clearCard();
    cardList.clear();
  }

  // clear the text area when the user chooses 'New' from the File menu or moves
  // to the next card
  private void clearCard() {
    questionTextArea.setText("");
    answerTextArea.setText("");
    questionTextArea.requestFocus();
  }

  public static void main(String[] args) {
    QuizCardBuilder quizCardBuilder = new QuizCardBuilder();
    quizCardBuilder.buildGUI();
  }
}
