package variEnum;

import modelli.Pedina;
import modelli.Cavallo;
import utility.Point;

public enum TipoPedina {
    CAVALLO {
        @Override
        public Pedina creaPedina(Team team, Point posizione) {
            return new Cavallo(team, CAVALLO, posizione);
        }
    };

    public abstract Pedina creaPedina(Team team, Point posizione);
}
