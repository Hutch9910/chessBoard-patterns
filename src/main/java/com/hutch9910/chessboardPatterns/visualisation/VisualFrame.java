package com.hutch9910.chessboardPatterns.visualisation;

import com.hutch9910.chessboardPatterns.models.Board;

import javax.swing.JFrame;

public class VisualFrame extends JFrame {

    private JFrame frame;
    private static int screenSide = 1000;

    // Constructors
    public VisualFrame() {
        frame = new JFrame();
        frame.setTitle("ChessBoard Patterns");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }

    // Getters
    public static int getScreenSide() {
        return screenSide;
    }

    // Start panel
    public void createStartPanel() {
        frame.add(new StartPanel());
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Board visualisation
    public void createBoardPanel(Board board) {
        frame.setContentPane(new BoardPanel(board));
        frame.pack();

        frame.setVisible(true);
    }
}
