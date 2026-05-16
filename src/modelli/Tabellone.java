package modelli;

import utility.Point;
import variEnum.Team;
import variEnum.TipoPedina;

import java.util.Arrays;
import java.util.EnumSet;

public class Tabellone {

    private int latoTabellone;
    private final Point centro;

    private int numeroDiPedine;
    private Pedina[] pedine;

    private int numeroDiTeams;
    private Pedina[][] occupazione;
    private EnumSet<Team>[][] attacchi;
    //private Enum[][] statoTabellone; // null se la posizione non è sotto attacco

    private static Point ultimaPosizioneCambiata;

    // Costruttori
    public Tabellone(int numeroDiTeams, int latoTabellone) {
        setLatoTabellone(latoTabellone); // arrotonda al numero dispari maggiore più vicino
        this.centro = new Point(latoTabellone/2, latoTabellone/2);

        numeroDiPedine = 0;
        setPedine();

        setNumeroDiTeams(numeroDiTeams); // numero minimo: 2
        setOccupazione();
        setAttacchi();

        setUltimaPosizioneCambiata(this.centro);
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

    public void setNumeroDiTeams(int numeroDiTeams) {
        if (numeroDiTeams <= 1) {
            numeroDiTeams = 2;
        }
        this.numeroDiTeams = numeroDiTeams;
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

    //
    public boolean aggiungiPedina(Team team, TipoPedina tipoPedina) {

        Point posizione = (numeroDiPedine == 0) ? centro : trovaPosizioneDisponibile(team);

        if (posizione == null) {
            return false;
        }

        switch (tipoPedina) {
            case CAVALLO :
                pedine[numeroDiPedine] = new Cavallo(team, tipoPedina, posizione);
                break;
            default :
                System.out.println("Tipo pedina non riconosciuta");
                return false;
        } // case da aggiungere se si aggiungono sottoclassi di Pedina

        aggiungiPosizioniAttaccate(pedine[numeroDiPedine]);
        occupazione[posizione.getY()][posizione.getX()] = pedine[numeroDiPedine];

        // DEBUG
        System.out.print(pedine[numeroDiPedine] + " ");
        System.out.println(pedine[numeroDiPedine].team);

        String lineSeparator = System.lineSeparator();
        StringBuilder sb = new StringBuilder();
        for (int i = attacchi.length - 1; i >= 0; i--) {
            EnumSet<Team>[] row = attacchi[i];
            sb.append(Arrays.toString(row))
                    .append(lineSeparator);
        }
        System.out.println(sb);

        lineSeparator = System.lineSeparator();
        sb = new StringBuilder();
        for (int i = occupazione.length - 1; i >= 0; i--) {
            Pedina[] row = occupazione[i];
            sb.append(Arrays.toString(row))
                    .append(lineSeparator);
        }
        System.out.println(sb);
        System.out.println("Ultima posizione cambiata: " +ultimaPosizioneCambiata);
        // FINE DEBUG

        numeroDiPedine++;
        return true;
    }

    public Point trovaPosizioneDisponibile(Team team) {

        int x = ultimaPosizioneCambiata.getX();
        int y = ultimaPosizioneCambiata.getY();
        int dX = x - centro.getX(); // coordinate X relative dal centro (distanza dal centro)
        int dY = y - centro.getY(); // coordinate Y relative dal centro (distanza dal centro)
        int r = Math.max(Math.abs(dX), Math.abs(dY)); // calcolo del raggio dell'anello attuale

        // loop che itera la spirale fino a quando non trova una posizione disponibile
        boolean posizioneTrovata = false;
        while (!posizioneTrovata) {
            if (dX == r && dY == -r) { // nuovo anello
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
            else if (dX == r && dY < r) { // lato destro -> su
                y++;
                dY = y - centro.getY();
                if (isPosizioneSicura(team, new Point(x, y))) {
                    ultimaPosizioneCambiata.setX(x);
                    ultimaPosizioneCambiata.setY(y);
                    posizioneTrovata = true;
                }
            }
            else if (dX > -r && dY == r) { // lato alto -> sinistra
                x--;
                dX = x - centro.getX();
                if (isPosizioneSicura(team, new Point(x, y))) {
                    ultimaPosizioneCambiata.setX(x);
                    ultimaPosizioneCambiata.setY(y);
                    posizioneTrovata = true;
                }
            }
            else if (dX == -r && dY > -r) { // lato sinistro -> giù
                y--;
                dY = y - centro.getY();
                if (isPosizioneSicura(team, new Point(x, y))) {
                    ultimaPosizioneCambiata.setX(x);
                    ultimaPosizioneCambiata.setY(y);
                    posizioneTrovata = true;
                }
            }
            else if (dX < r && dY == -r) { // lato basso -> destra
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

        Point[] posizioniAttaccate = switch (pedina.tipoPedina) {
                                        case CAVALLO -> ((Cavallo)pedina).getListaAttacchi();
                                    }; // case da aggiungere se si aggiungono sottoclassi di Pedina

        for (int i = 0; i < posizioniAttaccate.length; i++) {
            int x = posizioniAttaccate[i].getX();
            int y = posizioniAttaccate[i].getY();

            if (x < attacchi[0].length && x >= 0 && y < attacchi.length && y >= 0) { // controlla se la posizione attaccata sia dentro il tabellone
                attacchi[y][x].add(pedina.team);
            }
        }
    }
}
