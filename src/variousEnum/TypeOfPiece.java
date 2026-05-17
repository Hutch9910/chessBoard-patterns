package variousEnum;

import models.*;
import models.pieces.*;
import utility.Point;

public enum TypeOfPiece {
    KNIGHT {
        @Override
        public Piece buildPiece(Team team, Point position) {
            return new Knight(team, KNIGHT, position);
        }
    },
    ELEPHANT {
        @Override
        public Piece buildPiece(Team team, Point position) {
            return new Elephant(team, ELEPHANT, position);
        }
    },
    DROMEDARY {
        @Override
        public Piece buildPiece(Team team, Point position) {
            return new Dromedary(team, DROMEDARY, position);
        }
    },
    ANTELOPE {
        @Override
        public Piece buildPiece(Team team, Point position) {
            return new Antelope(team, ANTELOPE, position);
        }
    },
    DABBABA {
        @Override
        public Piece buildPiece(Team team, Point position) {
            return new Dabbaba(team, DABBABA, position);
        }
    },
    WAZIR {
        @Override
        public Piece buildPiece(Team team, Point position) {
            return new Wazir(team, WAZIR, position);
        }
    },
    ZEBRA {
        @Override
        public Piece buildPiece(Team team, Point position) {
            return new Zebra(team, ZEBRA, position);
        }
    },
    FERZ {
        @Override
        public Piece buildPiece(Team team, Point position) {
            return new Ferz(team, FERZ, position);
        }
    };

    public abstract Piece buildPiece(Team team, Point position);
}
