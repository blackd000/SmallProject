package textEditorApp.textEditor;

import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JFileChooser;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.ScrollPaneConstants;
import javax.swing.JComboBox;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;

public class TextEditor extends JFrame {
  Font defaultFont;
  JTextArea textAria;
  JLabel fontLabel;
  JScrollPane scrollPane;
  JSpinner fontSizeSpinner;
  JButton colorChooserButton;
  JComboBox fontBox;

  JMenuBar menuBar;
  JMenu menu;
  JMenuItem openItem;
  JMenuItem saveItem;
  JMenuItem exitItem;

  public TextEditor() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Text editor bois");
    this.setSize(800, 600);
    this.setLayout(null);
    this.setLocationRelativeTo(null); //set display to the middle

    // set default font 
    defaultFont = new Font("Arial", Font.PLAIN, 20);

    // set menubar
    menuBar = new JMenuBar();
    menuBar.setBounds(0, 0, 800, 25);
    menu = new JMenu("File");
    menu.setFont(defaultFont);

    openItem = new JMenuItem("Open");
    openItem.setFont(defaultFont);
    openItem.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("/home/keilar/Documents/Vscode/Java/2.SmallProject/textEditorApp"));

        int response = fileChooser.showOpenDialog(null);

        switch (response) {
          case JFileChooser.APPROVE_OPTION: {
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            // Create Scanner to read a file
            Scanner fileIn = null;

            try {
              fileIn = new Scanner(file);

              if (file.isFile()) {
                while (fileIn.hasNextLine()) {
                  String line = fileIn.nextLine() + "\n";
                  textAria.append(line);
                }
              }
            } catch (FileNotFoundException e1) {
              e1.printStackTrace();
            } finally {
              fileIn.close();
            }
            break;
          }
        }
      }
    }); 

    saveItem = new JMenuItem("Save");
    saveItem.setFont(defaultFont);
    saveItem.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("/home/keilar/Documents/Vscode/Java/2.SmallProject/textEditorApp"));

        int response = fileChooser.showSaveDialog(null);

        switch (response) {
          case JFileChooser.APPROVE_OPTION: {
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            PrintWriter fileOut = null;
            
            try {
              fileOut = new PrintWriter(file);
              fileOut.println(textAria.getText());
            } catch (FileNotFoundException e1) {
              e1.printStackTrace();
            } finally {
              fileOut.close();
            }
            break;
          }
        }
      }

    });

    exitItem = new JMenuItem("Exit");
    exitItem.setFont(defaultFont);
    exitItem.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        System.exit(1);
      }

    });

    menu.add(openItem);
    menu.add(saveItem);
    menu.add(exitItem);

    menuBar.add(menu);
    // end of menubar

    // set text aria
    textAria = new JTextArea();
    textAria.setLineWrap(true);
    textAria.setWrapStyleWord(true);
    textAria.setFont(defaultFont);

    scrollPane = new JScrollPane(textAria);
    scrollPane.setBounds(50, 70, 700, 500);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    // scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    // choose what size do you want
    fontLabel = new JLabel("Font size: ");
    fontLabel.setFont(defaultFont);
    fontLabel.setBounds(50, 25, 100, 25);

    fontSizeSpinner = new JSpinner();
    fontSizeSpinner.setBounds(150, 25, 70, 25);
    fontSizeSpinner.setValue(20);
    fontSizeSpinner.setFont(defaultFont);
    fontSizeSpinner.addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent e) {
        // change size of textAria
        // textAria.getFont().getFamily() get the current font
        textAria.setFont(new Font(textAria.getFont().getFamily(), Font.PLAIN, (int)fontSizeSpinner.getValue()));
      }
      
    });

    // choose your fucking color
    colorChooserButton = new JButton("Color");
    colorChooserButton.setFont(defaultFont);
    colorChooserButton.setBounds(250, 25, 100, 25);
    colorChooserButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        Color color = JColorChooser.showDialog(null, "Select", Color.white);

        textAria.setForeground(color);
      }

    });

    // choose your font, this shit is gonna blow your fucking mind
    // get all the fonts that available in java
    String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

    fontBox = new JComboBox(fonts);
    fontBox.setFont(defaultFont);
    fontBox.setBounds(400, 25, 200, 25);
    fontBox.setSelectedItem("Arial");
    fontBox.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        textAria.setFont(new Font((String)fontBox.getSelectedItem(), Font.PLAIN, textAria.getFont().getSize()));
      }

    });
    
    this.add(menuBar);
    this.add(scrollPane);
    this.add(fontLabel);
    this.add(fontSizeSpinner);

    this.add(colorChooserButton);
    this.add(fontBox);

    this.setVisible(true);
  }
}