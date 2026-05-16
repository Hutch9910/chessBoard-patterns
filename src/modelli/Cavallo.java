package modelli;

import utility.Point;
import variEnum.Team;
import variEnum.TipoPedina;

public class Cavallo extends Pedina{

    // Costruttori
    public Cavallo(Team team, TipoPedina tipoPedina, Point posizione) {
        super(team, posizione);
        this.tipoPedina = tipoPedina;
    }

    // Getters
    private Point posizioneAttacco0() {
        return new Point((posizione.getX() + 1), (posizione.getY() + 2));
    }
    private Point posizioneAttacco1() {
        return new Point((posizione.getX() + 1), (posizione.getY() - 2));
    }
    private Point posizioneAttacco2() {
        return new Point((posizione.getX() - 1), (posizione.getY() + 2));
    }
    private Point posizioneAttacco3() {
        return new Point((posizione.getX() - 1), (posizione.getY() - 2));
    }
    private Point posizioneAttacco4() {
        return new Point((posizione.getX() + 2), (posizione.getY() + 1));
    }
    private Point posizioneAttacco5() {
        return new Point((posizione.getX() + 2), (posizione.getY() - 1));
    }
    private Point posizioneAttacco6() {
        return new Point((posizione.getX() - 2), (posizione.getY() + 1));
    }
    private Point posizioneAttacco7() {
        return new Point((posizione.getX() - 2), (posizione.getY() - 1));
    }

    @Override
    public Point[] getListaAttacchi() {
        Point[] listaAttacchi = new Point[8];

        listaAttacchi[0] = posizioneAttacco0();
        listaAttacchi[1] = posizioneAttacco1();
        listaAttacchi[2] = posizioneAttacco2();
        listaAttacchi[3] = posizioneAttacco3();
        listaAttacchi[4] = posizioneAttacco4();
        listaAttacchi[5] = posizioneAttacco5();
        listaAttacchi[6] = posizioneAttacco6();
        listaAttacchi[7] = posizioneAttacco7();

        return listaAttacchi;
    }
}
