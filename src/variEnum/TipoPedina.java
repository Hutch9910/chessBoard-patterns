package variEnum;

import modelli.*;
import utility.Point;

public enum TipoPedina {
    CAVALLO {
        @Override
        public Pedina creaPedina(Team team, Point posizione) {
            return new Cavallo(team, CAVALLO, posizione);
        }
    },
    ELEFANTE {
        @Override
        public Pedina creaPedina(Team team, Point posizione) {
            return new Elefante(team, ELEFANTE, posizione);
        }
    },
    DROMEDARIO {
        @Override
        public Pedina creaPedina(Team team, Point posizione) {
            return new Dromedario(team, DROMEDARIO, posizione);
        }
    },
    ANTELOPE {
        @Override
        public Pedina creaPedina(Team team, Point posizione) {
            return new Antelope(team, ANTELOPE, posizione);
        }
    },
    DABBABA {
        @Override
        public Pedina creaPedina(Team team, Point posizione) {
            return new Dabbaba(team, DABBABA, posizione);
        }
    },
    VISIR {
        @Override
        public Pedina creaPedina(Team team, Point posizione) {
            return new Visir(team, VISIR, posizione);
        }
    },
    ZEBRA {
        @Override
        public Pedina creaPedina(Team team, Point posizione) {
            return new Zebra(team, ZEBRA, posizione);
        }
    },
    FERZ {
        @Override
        public Pedina creaPedina(Team team, Point posizione) {
            return new Ferz(team, FERZ, posizione);
        }
    };

    public abstract Pedina creaPedina(Team team, Point posizione);
}
