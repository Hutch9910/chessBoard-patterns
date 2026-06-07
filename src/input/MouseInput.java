package input;


import java.awt.event.*;

import javax.swing.JPanel;

import visualisation.BoardPanel;
import visualisation.VisualFrame;

public class MouseInput extends MouseAdapter {

    private final BoardPanel panel;

    private double zoomFactor;

    private double panX = 0;
    private double panY = 0;

    private int lastMouseX;
    private int lastMouseY;

    // Constructors
    public MouseInput(BoardPanel panel) {
        this.panel = panel;
        zoomFactor = (double) VisualFrame.getScreenSide() / panel.getBoardSide() * panel.getTileSize(); // fit the image in the screen
    }

    // Getters
    public double getZoomFactor() {
        return zoomFactor;
    }
    public double getPanX() {
        return panX;
    }
    public double getPanY() {
        return panY;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        lastMouseX = e.getX();
        lastMouseY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        int dx = e.getX() - lastMouseX;
        int dy = e.getY() - lastMouseY;

        panX += dx;
        panY += dy;

        lastMouseX = e.getX();
        lastMouseY = e.getY();

        panel.repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        double oldZoom = zoomFactor;
        double zoomMultiplier = 1.07;

        double mouseX = e.getX();
        double mouseY = e.getY();

        if (e.getPreciseWheelRotation() < 0) {
            zoomFactor *= zoomMultiplier;
        }
        else {
            zoomFactor /= zoomMultiplier;
        }

        zoomFactor = Math.max(0.05, Math.min(100.0, zoomFactor)); // setting max(100.0) and min(0.05) zoom

        panX = mouseX - (mouseX - panX) * (zoomFactor / oldZoom);
        panY = mouseY - (mouseY - panY) * (zoomFactor / oldZoom);

        panel.repaint();
    }
}