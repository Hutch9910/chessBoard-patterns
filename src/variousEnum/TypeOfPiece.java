package variousEnum;

import models.*;
import models.pieces.*;
import utility.Point;

public enum TypeOfPiece {
    KNIGHT {
        @Override
        public Piece buildPiece(Team team, Point boardPosition) {
            return new Knight(team, KNIGHT, boardPosition);
        }
    },
    ELEPHANT {
        @Override
        public Piece buildPiece(Team team, Point boardPosition) {
            return new Elephant(team, ELEPHANT, boardPosition);
        }
    },
    DROMEDARY {
        @Override
        public Piece buildPiece(Team team, Point boardPosition) {
            return new Dromedary(team, DROMEDARY, boardPosition);
        }
    },
    ANTELOPE {
        @Override
        public Piece buildPiece(Team team, Point boardPosition) {
            return new Antelope(team, ANTELOPE, boardPosition);
        }
    },
    DABBABA {
        @Override
        public Piece buildPiece(Team team, Point boardPosition) {
            return new Dabbaba(team, DABBABA, boardPosition);
        }
    },
    WAZIR {
        @Override
        public Piece buildPiece(Team team, Point boardPosition) {
            return new Wazir(team, WAZIR, boardPosition);
        }
    },
    ZEBRA {
        @Override
        public Piece buildPiece(Team team, Point boardPosition) {
            return new Zebra(team, ZEBRA, boardPosition);
        }
    },
    FERZ {
        @Override
        public Piece buildPiece(Team team, Point boardPosition) {
            return new Ferz(team, FERZ, boardPosition);
        }
    };

    public abstract Piece buildPiece(Team team, Point boardPosition);
}
