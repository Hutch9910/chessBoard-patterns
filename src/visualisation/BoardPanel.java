package visualisation;

import models.Board;
import models.Piece;
import input.MouseInput;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BoardPanel extends JPanel {

    private BufferedImage boardImage;

    // Coordinates
    private int boardSide;
    private Piece[][] occupancy;

    // Tile Size
    private int tileSize = 1;    

    // Mouse Zoom
    private final MouseInput mouseInput;

    // Constructors
    public BoardPanel(Board board) {
        boardSide = board.getBoardSide();
        occupancy = board.getOccupancy();
        setPreferredSize(new Dimension(VisualFrame.getScreenSide(), VisualFrame.getScreenSide()));
        setBackground(Color.white);


        buildImage();

        mouseInput = new MouseInput(this);
        addMouseWheelListener(mouseInput);
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
    }

    public int getBoardSide() {
        return boardSide;
    }
    public int getTileSize() {
        return tileSize;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.translate(mouseInput.getPanX(), mouseInput.getPanY());
        g2.scale(mouseInput.getZoomFactor(), mouseInput.getZoomFactor());

        if (boardImage != null) {
            g2.drawImage(boardImage, 0, 0, null);
        }
    }

    public void buildImage() {
        int width = occupancy[0].length * tileSize;
        int height = occupancy.length * tileSize;

        boardImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = boardImage.createGraphics();

        for (int y = 0; y < occupancy.length; y++) {
            for (int x = 0; x < occupancy[y].length; x++) {

                Piece p = occupancy[y][x];

                if (p == null) {
                    continue;
                }

                g2.setColor(p.getTeam().getColor());
                g2.fillRect(
                    x * tileSize,
                    y * tileSize,
                    tileSize, tileSize);
            }
        }

        g2.dispose();
    }
}