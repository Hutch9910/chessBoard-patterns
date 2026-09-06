package com.hutch9910.chessboardPatterns.visualisation;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.hutch9910.chessboardPatterns.models.Board;

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
        ImageIcon icon = new ImageIcon(
                getClass().getResource("/static/trayicon.png"));
        this.setIconImage(icon.getImage());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(true);
        this.pack();

        this.setLocationRelativeTo(null);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
        this.setState(JFrame.NORMAL);
        this.setAlwaysOnTop(true);
        this.setAlwaysOnTop(false);
        SwingUtilities.invokeLater(boardPanel::fitBoardToView);
    }
}
