package modelli;

import utility.Point;
import variEnum.Team;
import variEnum.TipoPedina;

public class Pedina {

    protected Team team;
    protected Point posizione;
    protected TipoPedina tipoPedina;

    // Costruttori
    public Pedina(Team team, Point posizione) {
        this.team = team;
        this.posizione = posizione;
    }

    @Override
    public String toString() {
        return "" +posizione;
//        return "Pedina{" +
//                "team=" + team +
//                ", posizione=" + posizione +
//                ", tipoPedina=" + tipoPedina +
//                '}';
    }
}
