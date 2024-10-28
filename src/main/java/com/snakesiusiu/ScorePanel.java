package com.snakesiusiu;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

public class ScorePanel extends JPanel {

    private int score;
    private int highScore;

    public ScorePanel() {
        setPreferredSize(new Dimension(300, 50));
        setBackground(Color.black);
    }

    public void setScore(int score) {
        this.score = score;
        repaint();
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawScores(g);
    }

    private void drawScores(Graphics g) {
        String scoreMsg = "Score: " + score;
        String highScoreMsg = "High Score: " + highScore;
        Font small = new Font("Helvetica", Font.BOLD, 14);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(scoreMsg, 10, 20);
        g.drawString(highScoreMsg, 10, 40);
    }
}