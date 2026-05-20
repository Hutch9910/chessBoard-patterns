package visualisation;

import models.Board;
import models.Piece;
import variousEnum.Team;
import input.MouseInput;
import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    private Board board;

    // Coordinates
    private int boardSide;
    private Piece[][] occupancy;

    // Panel Sizes
    private int tileSize = 1;
    private int screenSide;

    // Mouse Zoom
    private final MouseInput camera;

    // Constructors
    public BoardPanel(Board board) {
        setBoard(board);
        this.boardSide = board.getBoardSide();
        this.occupancy = board.getOccupancy();
        
        screenSide = (int) (tileSize * boardSide * 0.5);

        this.setPreferredSize(new Dimension(this.screenSide, this.screenSide));
        this.setBackground(Color.white);

        this.camera = new MouseInput(this);

        addMouseWheelListener(camera);
        addMouseListener(camera);
        addMouseMotionListener(camera);
    }

    // Setters
    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        g2.translate(camera.getPanX(), camera.getPanY());
        g2.scale(camera.getZoomFactor(), camera.getZoomFactor());
        
        for (int y = 0; y < occupancy.length; y++) {
            for (int x = 0; x < occupancy[y].length; x++) {

                Piece p = occupancy[y][x];

                if (p == null) {
                    continue;
                }

                x = x * tileSize;
                y = y * tileSize;

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