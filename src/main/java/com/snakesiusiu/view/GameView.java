package com.snakesiusiu.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.snakesiusiu.controller.GameController;
import com.snakesiusiu.model.GameModel;

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

    public GameView(GameModel model) {
        this.model = model;
        scorePanel = new ScorePanel();
        boardPanel = new BoardPanel(model);

        startButton = new JButton("Start");
        restartButton = new JButton("Restart");
        resumeButton = new JButton("Resume");
        mainMenuButton = new JButton("Main Menu");

        String[] difficulties = { "Easy", "Normal", "Hard" };
        difficultyBox = new JComboBox<>(difficulties);

        setupLayout();
        initUI();
        initKeyListener();
        showMainMenu(); // Hiển thị menu chính lúc đầu
    }

    private void setupLayout() {
        JPanel container = new JPanel(new BorderLayout());

        scorePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        container.add(scorePanel, BorderLayout.NORTH);

        container.add(boardPanel, BorderLayout.CENTER);

        JPanel gamePanel = new JPanel(new BorderLayout());
        gamePanel.add(boardPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.add(startButton);
        buttonPanel.add(restartButton);
        buttonPanel.add(resumeButton);
        buttonPanel.add(mainMenuButton);
        buttonPanel.add(difficultyBox);

        gamePanel.add(buttonPanel, BorderLayout.SOUTH);
        container.add(gamePanel, BorderLayout.CENTER);

        container.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        add(container);
    }

    private void initUI() {
        setTitle("Snake Game");
        setSize(330, 535);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                controller.handleKeyPress(e.getKeyCode());
            }
        });
        setFocusable(true);
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    // Hiển thị menu chính lúc bắt đầu
    public void showMainMenu() {
        startButton.setVisible(true);
        difficultyBox.setVisible(true);
        restartButton.setVisible(false);
        resumeButton.setVisible(false);
        mainMenuButton.setVisible(false);
    }

    // Hiển thị giao diện chơi game
    public void showGame() {
        startButton.setVisible(false);
        difficultyBox.setVisible(false);
        restartButton.setVisible(false);
        resumeButton.setVisible(false);
        mainMenuButton.setVisible(false);
    }

    public void showPauseMenu() {
        resumeButton.setVisible(true); // Hiển thị nút Resume
        mainMenuButton.setVisible(true); // Hiển thị nút Main Menu
        startButton.setVisible(false); // Ẩn nút Start
        difficultyBox.setVisible(false); // Ẩn ComboBox
        restartButton.setVisible(false); // Ẩn nút Restart
    }

    // Hiển thị menu khi game kết thúc
    public void showGameOver() {
        startButton.setVisible(false);
        difficultyBox.setVisible(false);
        restartButton.setVisible(true);
        resumeButton.setVisible(false);
        mainMenuButton.setVisible(true);
    }

    // Cập nhật điểm số
    public void updateScore(int score) {
        scorePanel.setScore(score);
    }

    // Cập nhật điểm cao
    public void updateHighScore(int highScore) {
        scorePanel.setHighScore(highScore);
    }

    // Thêm sự kiện cho nút Start
    public void addStartButtonListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }

    // Thêm sự kiện cho nút Restart
    public void addRestartButtonListener(ActionListener listener) {
        restartButton.addActionListener(listener);
    }

    // Thêm sự kiện cho nút Resume
    public void addResumeButtonListener(ActionListener listener) {
        resumeButton.addActionListener(listener);
    }

    // Thêm sự kiện cho nút Main Menu
    public void addMainMenuButtonListener(ActionListener listener) {
        mainMenuButton.addActionListener(listener);
    }

    // Lấy mức độ đã chọn
    public String getSelectedDifficulty() {
        return (String) difficultyBox.getSelectedItem();
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }
}
