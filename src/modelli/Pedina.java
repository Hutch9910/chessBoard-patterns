package modelli;

import utility.Point;
import variEnum.Team;
import variEnum.TipoPedina;

public abstract class Pedina {

    protected Team team;
    protected Point posizione;
    protected TipoPedina tipoPedina;

    // Costruttori
    public Pedina(Team team, Point posizione) {
        this.team = team;
        this.posizione = posizione;
    }

    // Getters
    public Point getPosizione() {
        return posizione;
    }
    public Team getTeam() {
        return team;
    }

    public abstract Point[] getListaAttacchi();

    @Override
    public String toString() {
        return "" +posizione;
    }
}
