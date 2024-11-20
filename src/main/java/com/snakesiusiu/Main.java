package com.snakesiusiu;

import com.snakesiusiu.model.GameModel;
import com.snakesiusiu.view.GameView;
import com.snakesiusiu.controller.GameController;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameModel model = new GameModel();
            GameView view = new GameView(model);
            GameController controller = new GameController(model, view);
            view.setController(controller);
            view.setVisible(true);
        });
    }
}