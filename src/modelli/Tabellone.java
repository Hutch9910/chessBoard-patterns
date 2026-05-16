package modelli;

import utility.Point;
import variEnum.Team;
import variEnum.TipoPedina;
import java.util.EnumSet;

public class Tabellone {

    private int latoTabellone;
    private final Point centro;

    private int numeroDiPedine;
    private Pedina[] pedine;

    private Pedina[][] occupazione;
    private EnumSet<Team>[][] attacchi;

    private static Point ultimaPosizioneCambiata;

    // Costruttori
    public Tabellone(int latoTabellone) {
        setLatoTabellone(latoTabellone); // arrotonda al numero dispari maggiore più vicino
        this.centro = new Point(latoTabellone/2, latoTabellone/2);

        numeroDiPedine = 0;
        setPedine();

        setOccupazione();
        setAttacchi();

        setUltimaPosizioneCambiata(this.centro);
    }

    // Getters
    public int getLatoTabellone() {
        return latoTabellone;
    }
    public Pedina[][] getOccupazione() {
        return occupazione;
    }

    // Setters
    public void setLatoTabellone(int latoTabellone) {
        if (latoTabellone %2 == 0) {
            latoTabellone++;
        }
        this.latoTabellone = latoTabellone;
    }
    public void setPedine() {
        this.pedine = new Pedina[this.latoTabellone * this.latoTabellone];
    }
    public void setOccupazione() {
        this.occupazione = new Pedina[this.latoTabellone][this.latoTabellone];
    }
    public void setAttacchi() {
        this.attacchi = new EnumSet[this.latoTabellone][this.latoTabellone];
        for (int y = 0; y < this.latoTabellone; y++) {
            for (int x = 0; x < this.latoTabellone; x++) {
                attacchi[y][x] = EnumSet.noneOf(Team.class);
            }
        }
    }
    public static void setUltimaPosizioneCambiata(Point centro) {
        Tabellone.ultimaPosizioneCambiata = new Point(centro.getX(), centro.getY());
    }

    // Metodo principale
    public void aggiungiPedina(Team team, TipoPedina tipoPedina) {

        Point posizione = (numeroDiPedine == 0) ? centro : trovaPosizioneDisponibile(team);

        if (posizione == null) {
            return;
        }

        pedine[numeroDiPedine] = tipoPedina.creaPedina(team, posizione);
        aggiungiPosizioniAttaccate(pedine[numeroDiPedine]);
        occupazione[posizione.getY()][posizione.getX()] = pedine[numeroDiPedine];
        numeroDiPedine++;
    }

    // Logica spirale
    public Point trovaPosizioneDisponibile(Team team) {

        int x = ultimaPosizioneCambiata.getX();
        int y = ultimaPosizioneCambiata.getY();
        int dX = x - centro.getX(); // coordinate X relative dal centro (distanza dal centro)
        int dY = y - centro.getY(); // coordinate Y relative dal centro (distanza dal centro)
        int r = Math.max(Math.abs(dX), Math.abs(dY)); // calcolo del raggio dell'anello attuale

        // loop che itera la spirale fino a quando non trova una posizione disponibile
        boolean posizioneTrovata = false;
        while (!posizioneTrovata) {
            if (dX == r && dY == r) { // nuovo anello
                r++;
                x++;
                dX = x - centro.getX();
                if (x >= occupazione[0].length) { // controllo se sono uscito dal tabellone
                    return null;
                }
                if (isPosizioneSicura(team, new Point(x, y))) {
                    ultimaPosizioneCambiata.setX(x);
                    ultimaPosizioneCambiata.setY(y);
                    posizioneTrovata = true;
                }
            }
            else if (dX == r && dY > -r) { // lato destro -> su
                y--;
                dY = y - centro.getY();
                if (isPosizioneSicura(team, new Point(x, y))) {
                    ultimaPosizioneCambiata.setX(x);
                    ultimaPosizioneCambiata.setY(y);
                    posizioneTrovata = true;
                }
            }
            else if (dX > -r && dY == -r) { // lato alto -> sinistra
                x--;
                dX = x - centro.getX();
                if (isPosizioneSicura(team, new Point(x, y))) {
                    ultimaPosizioneCambiata.setX(x);
                    ultimaPosizioneCambiata.setY(y);
                    posizioneTrovata = true;
                }
            }
            else if (dX == -r && dY < r) { // lato sinistro -> giù
                y++;
                dY = y - centro.getY();
                if (isPosizioneSicura(team, new Point(x, y))) {
                    ultimaPosizioneCambiata.setX(x);
                    ultimaPosizioneCambiata.setY(y);
                    posizioneTrovata = true;
                }
            }
            else if (dX < r && dY == r) { // lato basso -> destra
                x++;
                dX = x - centro.getX();
                if (isPosizioneSicura(team, new Point(x, y))) {
                    ultimaPosizioneCambiata.setX(x);
                    ultimaPosizioneCambiata.setY(y);
                    posizioneTrovata = true;
                }
            }
        }
        return new Point(x, y);
    }

    public boolean isPosizioneSicura(Team team, Point posizione) {
        return attacchi[posizione.getY()][posizione.getX()].isEmpty()
                || (attacchi[posizione.getY()][posizione.getX()].contains(team)
                    && attacchi[posizione.getY()][posizione.getX()].size() == 1);
    }

    public void aggiungiPosizioniAttaccate(Pedina pedina) {

        Point[] posizioniAttaccate = pedina.getListaAttacchi();

        for (int i = 0; i < posizioniAttaccate.length; i++) {
            int x = posizioniAttaccate[i].getX();
            int y = posizioniAttaccate[i].getY();

            if (x < attacchi[0].length && x >= 0 && y < attacchi.length && y >= 0) { // controlla se la posizione attaccata sia dentro il tabellone
                attacchi[y][x].add(pedina.team);
            }
        }
    }
}
