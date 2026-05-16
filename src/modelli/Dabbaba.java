package modelli;

import utility.Point;
import variEnum.Team;
import variEnum.TipoPedina;

public class Dabbaba extends Pedina {

    // Costruttori
    public Dabbaba(Team team, TipoPedina tipoPedina, Point posizione) {
        super(team, posizione);
        this.tipoPedina = tipoPedina;
    }

    // Getters
    private Point posizioneAttacco0() {
        return new Point((posizione.getX() + 2), (posizione.getY()));
    }
    private Point posizioneAttacco1() {
        return new Point((posizione.getX() - 2), (posizione.getY()));
    }
    private Point posizioneAttacco2() {
        return new Point((posizione.getX()), (posizione.getY() + 2));
    }
    private Point posizioneAttacco3() {
        return new Point((posizione.getX()), (posizione.getY() - 2));
    }

    @Override
    public Point[] getListaAttacchi() {
        Point[] listaAttacchi = new Point[8];

        listaAttacchi[0] = posizioneAttacco0();
        listaAttacchi[1] = posizioneAttacco1();
        listaAttacchi[2] = posizioneAttacco2();
        listaAttacchi[3] = posizioneAttacco3();

        return listaAttacchi;
    }
}
