package OOP.c17_connection.gameSnake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class MainSnake {
  private JFrame frame;
  private MainPanel mainPanel;
  private JPanel subPanel;
  private JLabel scoreLabel;
  private static final int MAINPANEL_WIDTH = 1050;
  private static final int MAINPANEL_HEIGHT = 660;
  private static final int SNAKE_BIG = 30;
  private final int[] xPosition = new int[300];
  private final int[] yPosition = new int[300];
  private int xFood;
  private int yFood;
  private int snakeSize = 4;
  private boolean isMoving = false;
  private char direction = 'R';
  private int score;
  private Timer timer;

  public MainSnake() {

  }

  public void buildGUI() {
    frame = new JFrame("Snake Game");

    String pathTitleIcon = "./gameSnake/somePics/snake.jpg";
    frame.setIconImage(new ImageIcon(pathTitleIcon).getImage());

    Font font = new Font(Font.SERIF, Font.BOLD, 25);

    JPanel background = new JPanel(new BorderLayout());

    mainPanel = new MainPanel();
    mainPanel.setPreferredSize(new Dimension(MAINPANEL_WIDTH, MAINPANEL_HEIGHT));

    subPanel = new JPanel();
    subPanel.setBackground(Color.lightGray);
    subPanel.setPreferredSize(new Dimension(280, 50));

    scoreLabel = new JLabel();
    scoreLabel.setFont(font);

    JLabel messageLabel = new JLabel("Press F3 to Restart");
    messageLabel.setFont(font);

    subPanel.add(scoreLabel);
    subPanel.add(messageLabel);

    background.add(BorderLayout.CENTER, mainPanel);
    background.add(BorderLayout.EAST, subPanel);

    frame.getContentPane().add(BorderLayout.CENTER, background);
    frame.addKeyListener(new MultiKeyPressListener());

    frame.setVisible(true);
    frame.pack();
    frame.setResizable(false);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    timer = new Timer(80, event -> gameStart());
    initGame();
  }

  private void initGame() {
    isMoving = true;
    direction = 'R';
    snakeSize = 4;
    score = 0;
    scoreLabel.setText("Score: " + score);
    initSnake();
    makeFood();
    timer.start();
  }

  private void gameStart() {
    if (isMoving) {
      move();
      checkReachEnd();
      checkFood();
      checkGameOver();
    }
    mainPanel.repaint();
  }

  private void initSnake() {
    int initXPosition = 0, initYPosition = 0;

    for (int i = snakeSize - 1; i >= 0; i--) {
      xPosition[i] = initXPosition;
      yPosition[i] = initYPosition;

      initXPosition += SNAKE_BIG;
    }
  }

  private void move() {
    for (int i = snakeSize; i > 0; i--) {
      xPosition[i] = xPosition[i - 1];
      yPosition[i] = yPosition[i - 1];
    }

    switch (direction) {
      case 'L':
        xPosition[0] -= SNAKE_BIG;
        break;
      case 'R':
        xPosition[0] += SNAKE_BIG;
        break;
      case 'U':
        yPosition[0] -= SNAKE_BIG;
        break;
      case 'D':
        yPosition[0] += SNAKE_BIG;
    }
  }

  private void makeFood() {
    Random random = new Random();

    xFood = random.nextInt((int) (MAINPANEL_WIDTH / SNAKE_BIG)) * SNAKE_BIG;
    yFood = random.nextInt((int) (MAINPANEL_HEIGHT / SNAKE_BIG)) * SNAKE_BIG;

    System.out.println(xFood + " " + yFood);
  }

  private void checkFood() {
    if (xPosition[0] == xFood && yPosition[0] == yFood) {
      snakeSize++;
      scoreLabel.setText("Score: " + ++score);
      makeFood();
    }
  }

  private void checkReachEnd() {
    if (xPosition[0] < 0) {
      xPosition[0] = MAINPANEL_WIDTH;
    } else if (xPosition[0] > MAINPANEL_WIDTH - 1) {
      xPosition[0] = 0;
    } else if (yPosition[0] < 0) {
      yPosition[0] = MAINPANEL_HEIGHT;
    } else if (yPosition[0] > MAINPANEL_HEIGHT - 1) {
      yPosition[0] = 0;
    }
  }

  private void checkGameOver() {
    for (int i = 3; i < snakeSize; i++) {
      if (xPosition[0] == xPosition[i] && yPosition[0] == yPosition[i]) {
        System.out.println(xPosition[0] + " -> " + xPosition[i] + " -> " + i);
        System.out.println(yPosition[0] + " -> " + yPosition[i] + " -> " + i);
        isMoving = false;
      }
    }
  }

  private class MainPanel extends JPanel {
    @Override
    public void paintComponent(Graphics g) {
      if (isMoving) {
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        g.setColor(Color.green);
        g.fillOval(xFood, yFood, SNAKE_BIG, SNAKE_BIG);

        for (int i = 0; i < snakeSize; i++) {
          if (i == 0) {
            g.setColor(Color.cyan);
            g.fillRect(xPosition[i], yPosition[i], SNAKE_BIG, SNAKE_BIG);
          } else {
            g.setColor(Color.orange);
            g.fillRect(xPosition[i], yPosition[i], SNAKE_BIG, SNAKE_BIG);
          }
        }
      } else {
        g.setColor(Color.black);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        String pathGameOverIcon = "./gameSnake/somePics/crying-boy.gif";
        Image image = new ImageIcon(pathGameOverIcon).getImage();
        g.drawImage(image,
            (MAINPANEL_WIDTH - image.getWidth(frame)) / 2,
            (MAINPANEL_HEIGHT - image.getHeight(frame)) / 2,
            frame);

        g.setColor(Color.red);
        g.setFont(new Font(Font.SERIF, Font.BOLD, 40));
        FontMetrics finalWord = getFontMetrics(g.getFont());
        g.drawString("Game Over",
            (MAINPANEL_WIDTH - finalWord.stringWidth("Game Over")) / 2,
            MAINPANEL_HEIGHT - 100);
        g.drawString("Press F3 to play again",
            (MAINPANEL_WIDTH - finalWord.stringWidth("Press F3 to play again")) / 2,
            MAINPANEL_HEIGHT - 50);

        timer.stop();
      }
    }
  }

  private class MultiKeyPressListener implements KeyListener {
    // Set of currently pressed keys
    // private final Set<Integer> pressedKeys = new HashSet<>();

    @Override
    public void keyPressed(KeyEvent e) {
      // pressedKeys.add(e.getKeyCode());

      switch (e.getKeyCode()) {
        case KeyEvent.VK_W:
        case KeyEvent.VK_UP:
          if (direction != 'D') {
            direction = 'U';
          }
          break;
        case KeyEvent.VK_A:
        case KeyEvent.VK_LEFT:
          if (direction != 'R') {
            direction = 'L';
          }
          break;
        case KeyEvent.VK_S:
        case KeyEvent.VK_DOWN:
          if (direction != 'U') {
            direction = 'D';
          }
          break;
        case KeyEvent.VK_D:
        case KeyEvent.VK_RIGHT:
          if (direction != 'L') {
            direction = 'R';
          }
          break;
        case KeyEvent.VK_F3:
          if (timer.isRunning()) {
            timer.stop();
          }
          initGame();
          break;
        case KeyEvent.VK_ENTER:
          break;
      }
    }

    @Override
    public void keyReleased(KeyEvent e) {
      // not use
    }

    @Override
    public void keyTyped(KeyEvent e) {
      // not use
    }
  }

  public static void main(String[] args) {
    new MainSnake().buildGUI();
  }
}