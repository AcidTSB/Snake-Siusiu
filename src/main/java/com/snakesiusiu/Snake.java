package com.snakesiusiu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

public class Snake extends JFrame {

    public Snake() {
        initUI();
    }

    private void initUI() {
        ScorePanel scorePanel = new ScorePanel(); // Create ScorePanel instance
        Board board = new Board(scorePanel); // Pass ScorePanel to Board

        // Create a container panel with BorderLayout
        JPanel container = new JPanel(new BorderLayout());
        
        // Add a border to the ScorePanel
        scorePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));

        // Add ScorePanel to the container at the top
        container.add(scorePanel, BorderLayout.NORTH);
        
        // Add Board to the container at the center
        container.add(board, BorderLayout.CENTER);

        // Add a white border around the entire container
        container.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));

        // Add the container to the frame
        add(container);

        setResizable(false);
        pack();

        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame ex = new Snake();
            ex.setVisible(true);
        });
    }
}