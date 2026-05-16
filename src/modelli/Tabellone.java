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

    private int numeroDiTeams;
    private Point[] ultimaPosizioneCambiata;

    // Costruttori
    public Tabellone(int latoTabellone, int numeroDiTeams) {
        setLatoTabellone(latoTabellone); // arrotonda al numero dispari maggiore più vicino
        this.centro = new Point(latoTabellone/2, latoTabellone/2);

        numeroDiPedine = 0;
        setPedine();

        setOccupazione();
        setAttacchi();

        setNumeroDiTeams(numeroDiTeams);
        setUltimaPosizioneCambiata();
    }

    // Getters
    public int getLatoTabellone() {
        return latoTabellone;
    }
    public Pedina[][] getOccupazione() {
        return occupazione;
    }
    public int getNumeroDiTeams() {
        return numeroDiTeams;
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
    public void setNumeroDiTeams (int numeroDiTeams) {
        if (numeroDiTeams <= 1) {
            numeroDiTeams = 2;
        }
        this.numeroDiTeams = numeroDiTeams;
    }
    public void setUltimaPosizioneCambiata() {
        ultimaPosizioneCambiata = new Point[this.numeroDiTeams];
        for (int i = 0; i < this.numeroDiTeams; i++) {
            ultimaPosizioneCambiata[i] = new Point(this.centro.getX(), this.centro.getY());
        }
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

        int codice = team.getCode();
        int x = ultimaPosizioneCambiata[codice].getX();
        int y = ultimaPosizioneCambiata[codice].getY();
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
                if (isPosizioneSicura(team, x, y)) {
                    ultimaPosizioneCambiata[codice].setX(x);
                    ultimaPosizioneCambiata[codice].setY(y);
                    posizioneTrovata = true;
                }
            }
            else if (dX == r && dY > -r) { // lato destro -> su
                y--;
                dY = y - centro.getY();
                if (isPosizioneSicura(team, x, y)) {
                    ultimaPosizioneCambiata[codice].setX(x);
                    ultimaPosizioneCambiata[codice].setY(y);
                    posizioneTrovata = true;
                }
            }
            else if (dX > -r && dY == -r) { // lato alto -> sinistra
                x--;
                dX = x - centro.getX();
                if (isPosizioneSicura(team, x, y)) {
                    ultimaPosizioneCambiata[codice].setX(x);
                    ultimaPosizioneCambiata[codice].setY(y);
                    posizioneTrovata = true;
                }
            }
            else if (dX == -r && dY < r) { // lato sinistro -> giù
                y++;
                dY = y - centro.getY();
                if (isPosizioneSicura(team, x, y)) {
                    ultimaPosizioneCambiata[codice].setX(x);
                    ultimaPosizioneCambiata[codice].setY(y);
                    posizioneTrovata = true;
                }
            }
            else if (dX < r && dY == r) { // lato basso -> destra
                x++;
                dX = x - centro.getX();
                if (isPosizioneSicura(team, x, y)) {
                    ultimaPosizioneCambiata[codice].setX(x);
                    ultimaPosizioneCambiata[codice].setY(y);
                    posizioneTrovata = true;
                }
            }
        }
        return new Point(x, y);
    }

    public boolean isPosizioneSicura(Team team, int x, int y) {
        boolean attacchiSicura = attacchi[y][x].isEmpty()
                || (attacchi[y][x].contains(team) && attacchi[y][x].size() == 1);
        boolean occupazioneSicura = occupazione[y][x] == null;
        return attacchiSicura && occupazioneSicura;
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
