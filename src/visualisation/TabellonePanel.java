package visualisation;

import modelli.Pedina;
import modelli.Tabellone;
import variEnum.Team;
import javax.swing.*;
import java.awt.*;

public class TabellonePanel extends JPanel {

    private Tabellone tabellone;

    // Coordinate
    private int latoTabellone;
    Pedina[][] occupazione;

    // Grandezze Pannello
    private final int GrandezzaTile;
    private final int latoSchermo;

    // Costruttori
    public TabellonePanel(Tabellone tabellone) {
        setTabellone(tabellone);
        this.latoTabellone = tabellone.getLatoTabellone();
        this.occupazione = tabellone.getOccupazione();

        GrandezzaTile = 1000 / latoTabellone; // lato schermo finale = 1000
        latoSchermo = GrandezzaTile * latoTabellone;

        this.setPreferredSize(new Dimension(this.latoSchermo, this.latoSchermo));
        this.setBackground(Color.white);
    }

    // Setters
    public void setTabellone(Tabellone tabellone) {
        this.tabellone = tabellone;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        for (int i = 0; i < occupazione.length; i++) {
            for (Pedina p : occupazione[i]) {

                if (p == null) {
                    continue;
                }

                int x = p.getPosizione().getX() * GrandezzaTile;
                int y = p.getPosizione().getY() * GrandezzaTile;

                g2.setColor(getColor(p.getTeam()));

                g2.fillRect(x, y, GrandezzaTile, GrandezzaTile);
            }
        }
    }

    private Color getColor(Team team) {
        return switch (team) {
            case ROSSO -> Color.RED;
            case BLU -> Color.BLUE;
            case VERDE -> Color.GREEN;
            case MAGENTA -> Color.MAGENTA;
            case GIALLO -> Color.YELLOW;
        };
    }
}