package com.hutch9910.chessboardPatterns.visualisation;

import com.hutch9910.chessboardPatterns.models.Board;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class VisualFrame extends JFrame {

    private static int screenSide = 1000;

    // Constructors
    public VisualFrame() {
        super();
    }

    // Getters
    public static int getScreenSide() {
        return screenSide;
    }

    // Board visualisation
    public void createBoardPanel(Board board) {
        BoardPanel boardPanel = new BoardPanel(board);
        this.setContentPane(boardPanel);

        this.setTitle("ChessBoard Patterns");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(true);
        this.pack();

        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
        SwingUtilities.invokeLater(boardPanel::fitBoardToView);
    }
}
