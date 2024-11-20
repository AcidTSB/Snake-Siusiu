package com.snakesiusiu.view;

import com.snakesiusiu.model.GameModel;
import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {
    private final GameModel model;
    private Image ballImage;
    private Image appleImage;
    private Image headImage;

    public BoardPanel(GameModel model) {
        this.model = model;
        setPreferredSize(new Dimension(model.getBWidth(), model.getBHeight()));
        setBackground(Color.black);
        setFocusable(true);
        loadImages();
    }

    private void loadImages() {
        ImageIcon iid = new ImageIcon("src/main/resources/dot.png");
        ballImage = iid.getImage();

        ImageIcon iia = new ImageIcon("src/main/resources/apple.png");
        appleImage = iia.getImage();

        ImageIcon iih = new ImageIcon("src/main/resources/head.png");
        headImage = iih.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        if (model.isInGame()) {
            drawGame(g);
        } else {
            drawGameOver(g);
        }
    }

    private void drawGame(Graphics g) {
        g.drawImage(appleImage, model.getAppleX(), model.getAppleY(), this);

        for (int z = 0; z < model.getDots(); z++) {
            if (z == 0) {
                g.drawImage(headImage, model.getX()[z], model.getY()[z], this);
            } else {
                g.drawImage(ballImage, model.getX()[z], model.getY()[z], this);
            }
        }

        if (model.isPaused()) {
            drawPauseOverlay(g);
        }
    }

    private void drawPauseOverlay(Graphics g) {
        Color overlayColor = new Color(0, 0, 0, 204); // 80% transparent black
        g.setColor(overlayColor);
        g.fillRect(0, 0, model.getBWidth(), model.getBHeight());
    }

    private void drawGameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (model.getBWidth() - metr.stringWidth(msg)) / 2, model.getBHeight() / 2);
    }
}