package textEditorApp.textEditor;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TextEditor extends JFrame {
  Font defaultFont;
  JTextArea textAria;
  JLabel fontLabel;
  JScrollPane scrollPane;
  JSpinner fontSizeSpinner;

  public TextEditor() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Text editor bois");
    this.setSize(800, 600);
    this.setLayout(new FlowLayout());
    this.setLocationRelativeTo(null); //set display to the middle

    //set default font 
    defaultFont = new Font("Times New Roman", Font.PLAIN, 20);

    textAria = new JTextArea();
    textAria.setLineWrap(true);
    textAria.setWrapStyleWord(true);
    textAria.setFont(defaultFont);

    scrollPane = new JScrollPane(textAria);
    scrollPane.setPreferredSize(new Dimension(750, 500));
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    //scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    fontLabel = new JLabel("Font size: ");
    fontLabel.setFont(defaultFont);

    fontSizeSpinner = new JSpinner();
    fontSizeSpinner.setPreferredSize(new Dimension(50, 25));
    fontSizeSpinner.setValue(20);
    fontSizeSpinner.setFont(defaultFont);
    fontSizeSpinner.addChangeListener(new ChangeListener() {

      @Override
      public void stateChanged(ChangeEvent e) {
        // TODO Auto-generated method stub
        // change size of textAria
        // textAria.getFont().getFamily() get the current font
        textAria.setFont(new Font(textAria.getFont().getFamily(), Font.PLAIN, (int)fontSizeSpinner.getValue()));
      }
      
    });

    this.add(fontLabel);
    this.add(fontSizeSpinner);
    this.add(scrollPane);

    this.setVisible(true);
  }
}
