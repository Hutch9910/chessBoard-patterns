package input;


import java.awt.event.*;

import visualisation.BoardPanel;

public class MouseInput extends MouseAdapter implements MouseWheelListener{

    private final BoardPanel panel;

    private double zoomFactor = 1.0;

    private double panX = 0;
    private double panY = 0;

    private int lastMouseX;
    private int lastMouseY;

    // Constructors
    public MouseInput(BoardPanel panel) {
        this.panel = panel;
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
        double zoomMultiplier = 1.1;

        double mouseX = e.getX();
        double mouseY = e.getY();

        if (e.getPreciseWheelRotation() < 0) {
            zoomFactor *= zoomMultiplier;
        }
        else {
            zoomFactor /= zoomMultiplier;
        }

        zoomFactor = Math.max(0.2, Math.min(5.0, zoomFactor));


        panX = mouseX - (mouseX - panX) * (zoomFactor / oldZoom);

        panY = mouseY - (mouseY - panY) * (zoomFactor / oldZoom);

        panel.repaint();
    }

    
}