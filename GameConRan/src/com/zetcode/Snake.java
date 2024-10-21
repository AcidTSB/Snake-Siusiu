package com.zetcode;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Snake extends JFrame {

    private static int delay; 

    public Snake() {
        initUI();
    }

    private void initUI() {
        add(new Board(delay));

        setResizable(false);
        pack();

        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        String[] options = {"Easy", "Medium", "Hard"};
        int x = JOptionPane.showOptionDialog(null, "Select Difficulty Level",
                "Difficulty",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (x) {
            case 0:  // Easy
                delay = 140;  // Slower snake
                break;
            case 1:  // Medium
                delay = 100;  // Normal speed
                break;
            case 2:  // Hard
                delay = 60;  // Faster snake
                break;
            default:
                delay = 100;  // Default to Medium if closed
                break;
        }

        EventQueue.invokeLater(() -> {
            JFrame ex = new Snake();
            ex.setVisible(true);
        });
    }
}
