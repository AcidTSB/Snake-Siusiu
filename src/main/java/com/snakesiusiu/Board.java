package com.snakesiusiu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private final int B_WIDTH = 300;
    private final int B_HEIGHT = 300;
    private final int DOT_SIZE = 10;
    private final int ALL_DOTS = 900;
    private final int RAND_POS = 29;

    private int DELAY = 240; // Default delay

    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];

    private int dots;
    private int apple_x;
    private int apple_y;
    private int score;
    private int highScore;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = false;

    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;
    private JButton startButton;
    private JButton restartButton;    
    private boolean isPaused = false;
    private JButton resumeButton;
    private JButton mainMenuButton;
    private JComboBox<String> difficultyBox;

    public Board() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        loadHighScore();

        startButton = new JButton("Start");
        startButton.setBounds(B_WIDTH / 2 - 50, B_HEIGHT / 2 - 25, 100, 50);
        startButton.addActionListener(e -> startGame());
        add(startButton);

        restartButton = new JButton("Restart");
        restartButton.setBounds(B_WIDTH / 2 - 50, B_HEIGHT / 2 - 25, 100, 50);
        restartButton.addActionListener(e -> restartGame());
        restartButton.setVisible(false);
        add(restartButton);

        String[] difficulties = {"Easy", "Normal", "Hard"};
        difficultyBox = new JComboBox<>(difficulties);
        difficultyBox.setBounds(B_WIDTH / 2 - 50, B_HEIGHT / 2 + 35, 100, 25);
        add(difficultyBox);

        setLayout(null);

        resumeButton = new JButton("Resume");
        resumeButton.setBounds(B_WIDTH / 2 - 50, B_HEIGHT / 2 - 25, 100, 50);
        resumeButton.addActionListener(e -> resumeGame());
        resumeButton.setVisible(false);
        add(resumeButton);

        mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setBounds(B_WIDTH / 2 - 50, B_HEIGHT / 2 + 35, 100, 50);
        mainMenuButton.addActionListener(e -> showMainMenu());
        mainMenuButton.setVisible(false);
        add(mainMenuButton);

        setLayout(null);
    }

    private void loadImages() {
        ImageIcon iid = new ImageIcon("src/main/resources/dot.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("src/main/resources/apple.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("src/main/resources/head.png");
        head = iih.getImage();
    }

    private void pauseGame() {
        isPaused = true;
        timer.stop();
        resumeButton.setVisible(true);
        mainMenuButton.setVisible(true);
        repaint();
    }
    
    private void resumeGame() {
        isPaused = false;
        timer.start();
        resumeButton.setVisible(false);
        mainMenuButton.setVisible(false);
        repaint();
    }
    
    private void showMainMenu() {
        isPaused = false;
        inGame = false;
        startButton.setVisible(true);
        difficultyBox.setVisible(true);
        resumeButton.setVisible(false);
        mainMenuButton.setVisible(false);
    }
    

    private void startGame() {
        String selectedDifficulty = (String) difficultyBox.getSelectedItem();
        switch (selectedDifficulty) {
            case "Easy":
                DELAY = 240;
                break;
            case "Normal":
                DELAY = 160;
                break;
            case "Hard":
                DELAY = 80;
                break;
        }

        startButton.setVisible(false);
        difficultyBox.setVisible(false);
        restartButton.setVisible(false);
        inGame = true;
        score = 0;
        initGame();
    }

    private void restartGame() {
        restartButton.setVisible(false);
        inGame = true;
        score = 0;
        initGame();
    }

    private void initGame() {
        dots = 3;

        for (int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;
        }

        locateApple();

        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    // Modify the doDrawing method to include the pause overlay
    private void doDrawing(Graphics g) {
        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }

            drawScore(g);
            drawHighScore(g);

            if (isPaused) {
                drawPauseOverlay(g);
            }
        } else {
            gameOver(g);
        }
    }

    // Update the drawPauseOverlay method to 80% transparency
    private void drawPauseOverlay(Graphics g) {
        Color overlayColor = new Color(0, 0, 0, 204); // 80% transparent black
        g.setColor(overlayColor);
        g.fillRect(0, 0, B_WIDTH, B_HEIGHT);
    }

    private void drawScore(Graphics g) {
        String msg = "Score: " + score;
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, 10, 20);
    }

    private void drawHighScore(Graphics g) {
        String msg = "High Score: " + highScore;
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, 10, 40);
    }

    private void gameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);

        if (score > highScore) {
            highScore = score;
            saveHighScore();
        }

        restartButton.setVisible(true);
    }

    private void checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            score += 10;
            locateApple();
        }
    }

    private void move() {
        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }

    private void checkCollision() {
        for (int z = dots; z > 0; z--) {
            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] >= B_HEIGHT) {
            inGame = false;
        }

        if (y[0] < 0) {
            inGame = false;
        }

        if (x[0] >= B_WIDTH) {
            inGame = false;
        }

        if (x[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }

    private void locateApple() {
        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));
    }

    private void loadHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader("highscore.txt"))) {
            highScore = Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            highScore = 0;
        }
    }

    private void saveHighScore() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("highscore.txt"))) {
            writer.write(String.valueOf(highScore));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame && !isPaused) {
            checkApple();
            checkCollision();
            move();
        }
    
        repaint();
    }

    // Handle ESC key press to pause the game
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_ESCAPE) {
                if (inGame) {
                    if (isPaused) {
                        resumeGame();
                    } else {
                        pauseGame();
                    }
                }
            }

            // Existing key handling logic...
            if (!isPaused) {
                if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                    leftDirection = true;
                    upDirection = false;
                    downDirection = false;
                }
    
                if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                    rightDirection = true;
                    upDirection = false;
                    downDirection = false;
                }
    
                if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                    upDirection = true;
                    rightDirection = false;
                    leftDirection = false;
                }
    
                if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                    downDirection = true;
                    rightDirection = false;
                    leftDirection = false;
                }
            }
        }
    }
}