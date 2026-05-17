package visualisation;

import models.Board;
import models.Piece;
import variousEnum.Team;
import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    private Board board;

    // Coordinates
    private int boardSide;
    Piece[][] occupancy;

    // Panel Sizes
    private int tileSize;
    private int screenSide;

    // Constructors
    public BoardPanel(Board board) {
        setBoard(board);
        this.boardSide = board.getBoardSide();
        this.occupancy = board.getOccupancy();

        tileSize = 1000 / boardSide; // final screen side = 1000
        screenSide = tileSize * boardSide;

        this.setPreferredSize(new Dimension(this.screenSide, this.screenSide));
        this.setBackground(Color.white);
    }

    // Setters
    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        for (Piece[] pieces : occupancy) {
            for (Piece p : pieces) {

                if (p == null) {
                    continue;
                }

                int x = p.getPosition().getX() * tileSize;
                int y = p.getPosition().getY() * tileSize;

                g2.setColor(getColor(p.getTeam()));

                g2.fillRect(x, y, tileSize, tileSize);
            }
        }
    }

    private Color getColor(Team team) {
        return switch (team) {
            case BLACK -> Color.BLACK;
            case RED -> Color.RED;
            case BLUE -> Color.BLUE;
            case MAGENTA -> Color.MAGENTA;
            case GREEN -> Color.GREEN;
            case YELLOW -> Color.YELLOW;
        };
    }
}