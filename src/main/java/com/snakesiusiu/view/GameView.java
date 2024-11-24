package com.snakesiusiu.view;

import com.snakesiusiu.controller.GameController;
import com.snakesiusiu.model.GameModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameView extends JFrame {
    private GameController controller;
    private GameModel model;
    private final BoardPanel boardPanel;
    private final ScorePanel scorePanel;
    private final JButton startButton;
    private final JButton restartButton;
    private final JButton resumeButton;
    private final JButton mainMenuButton;
    private final JComboBox<String> difficultyBox;

    public void updateFromModel() {
        updateScore(model.getScore());
        updateHighScore(model.getHighScore());
        boardPanel.repaint();
    }

    public void handleKeyEvent(KeyEvent e) {
        controller.handleKeyPress(e.getKeyCode());
    }

    private void initKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyEvent(e);
            }
        });
        setFocusable(true);
    }

    public GameView(GameModel model) {
        this.model = model;
        scorePanel = new ScorePanel();
        boardPanel = new BoardPanel(model);
        
        startButton = new JButton("Start");
        restartButton = new JButton("Restart");
        resumeButton = new JButton("Resume");
        mainMenuButton = new JButton("Main Menu");
        
        String[] difficulties = {"Easy", "Normal", "Hard"};
        difficultyBox = new JComboBox<>(difficulties);
        
        setupLayout();
        initUI();
        initKeyListener();

        showMainMenu();
        requestFocusInWindow();
    }
    
    private void setupLayout() {
        JPanel container = new JPanel(new BorderLayout());
        
        scorePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        container.add(scorePanel, BorderLayout.NORTH);
        
        container.add(boardPanel, BorderLayout.CENTER);
        
        JPanel gamePanel = new JPanel(new BorderLayout());
 
        JPanel buttonPanel = new JPanel();
        buttonPanel.setSize(300, 300);
        buttonPanel.setLocation(0, 150);
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(startButton);
        buttonPanel.add(restartButton);
        buttonPanel.add(resumeButton);
        buttonPanel.add(mainMenuButton);
        buttonPanel.add(difficultyBox);
        
        gamePanel.add(buttonPanel, BorderLayout.CENTER);
        gamePanel.add(boardPanel, BorderLayout.CENTER);
        
        container.add(gamePanel, BorderLayout.CENTER);
        
        container.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        add(container);
    }
    
    private void initUI() {
        setTitle("Snake Game");
        setSize(327, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }
    
    // Methods to update view
    public void updateScore(int score) {
        scorePanel.setScore(score);
    }
    
    public void updateHighScore(int highScore) {
        scorePanel.setHighScore(highScore);
    }
    
    public void showMainMenu() {
        startButton.setVisible(true);
        difficultyBox.setVisible(true);
        restartButton.setVisible(false);
        resumeButton.setVisible(false);
        mainMenuButton.setVisible(false);
    }
    
    public void showGame() {
        startButton.setVisible(false);
        difficultyBox.setVisible(false);
        restartButton.setVisible(false);
        resumeButton.setVisible(false);
        mainMenuButton.setVisible(false);
    }
    
    public void showPauseMenu() {
        resumeButton.setVisible(true);
        mainMenuButton.setVisible(true);
    }
    
    public void showGameOver() {
        restartButton.setVisible(true);
        mainMenuButton.setVisible(true);
        requestFocusInWindow();
    }
    
    // Add listeners
    public void addStartButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }
    
    public void addRestartButtonListener(ActionListener listener) {
        restartButton.addActionListener(listener);
    }
    
    public void addResumeButtonListener(ActionListener listener) {
        resumeButton.addActionListener(listener);
    }
    
    public void addMainMenuButtonListener(ActionListener listener) {
        mainMenuButton.addActionListener(listener);
    }
    
    public String getSelectedDifficulty() {
        return (String) difficultyBox.getSelectedItem();
    }
    
    public BoardPanel getBoardPanel() {
        return boardPanel;
    }
}