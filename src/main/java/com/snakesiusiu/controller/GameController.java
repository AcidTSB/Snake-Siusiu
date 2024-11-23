package com.snakesiusiu.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

import com.snakesiusiu.model.Direction;
import com.snakesiusiu.model.GameModel;
import com.snakesiusiu.view.GameView;

public class GameController {
    private final GameModel model;
    private final GameView view;
    private Timer timer;

    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
        initController();
    }

    private void initController() {
        view.addStartButtonListener(e -> startGame());
        view.addRestartButtonListener(e -> restartGame());
        view.addResumeButtonListener(e -> resumeGame());
        view.addMainMenuButtonListener(e -> showMainMenu());

        view.getBoardPanel().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e.getKeyCode());
            }
        });
    }

    private void startGame() {
        String difficulty = view.getSelectedDifficulty();
        switch (difficulty) {
            case "Easy":
                model.setDelay(240);
                break;
            case "Normal":
                model.setDelay(160);
                break;
            case "Hard":
                model.setDelay(80);
                break;
        }
        model.initGame();
        view.showGame();
        startTimer();
    }

    private void restartGame() {
        model.initGame();
        view.showGame();
        startTimer();
    }

    private void resumeGame() {
        model.setPaused(false);
        view.showGame();
        timer.start();
    }

    private void showMainMenu() {
        if (timer != null) {
            timer.stop();
        }
        model.setPaused(false);
        view.showMainMenu();
    }

    public void handleKeyPress(int keyCode) {
        if (keyCode == KeyEvent.VK_ESCAPE) {
            if (model.isInGame()) {
                if (model.isPaused()) {
                    resumeGame();
                } else {
                    pauseGame();
                }
            }
            return;
        }

        if (!model.isPaused() && model.isInGame()) {
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    model.setDirection(Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    model.setDirection(Direction.RIGHT);
                    break;
                case KeyEvent.VK_UP:
                    model.setDirection(Direction.UP);
                    break;
                case KeyEvent.VK_DOWN:
                    model.setDirection(Direction.DOWN);
                    break;
            }
        }
    }

    private void pauseGame() {
        model.setPaused(true); // Đánh dấu game là đang tạm dừng
        if (timer != null) {
            timer.stop(); // Dừng bộ đếm thời gian
        }
        view.showPauseMenu(); // Hiển thị menu Pause
    }

    private void startTimer() {
        if (timer != null) {
            timer.stop();
        }

        timer = new Timer(model.getDelay(), e -> {
            if (model.isInGame() && !model.isPaused()) {
                model.checkApple();
                model.checkCollision();
                model.move();

                view.updateScore(model.getScore());
                view.updateHighScore(model.getHighScore());

                if (!model.isInGame()) {
                    timer.stop();
                    view.showGameOver();
                }
            }
            view.getBoardPanel().repaint();
        });

        timer.start();
    }
}
