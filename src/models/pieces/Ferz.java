package models.pieces;

import models.Piece;
import utility.Point;
import variousEnum.Team;
import variousEnum.TypeOfPiece;

public class Ferz extends Piece {

    // Constructors
    public Ferz(Team team, TypeOfPiece typeOfPiece, Point boardPosition) {
        super(team, boardPosition);
        this.typeOfPiece = typeOfPiece;
    }

    // Getters
    private Point attackPosition0() {
        return new Point((boardPosition.getX() + 1), (boardPosition.getY() + 1));
    }
    private Point attackPosition1() {
        return new Point((boardPosition.getX() + 1), (boardPosition.getY() - 1));
    }
    private Point attackPosition2() {
        return new Point((boardPosition.getX() - 1), (boardPosition.getY() + 1));
    }
    private Point attackPosition3() {
        return new Point((boardPosition.getX() - 1), (boardPosition.getY() - 1));
    }

    @Override
    public Point[] getAttacksList() {
        Point[] attacksList = new Point[4];

        attacksList[0] = attackPosition0();
        attacksList[1] = attackPosition1();
        attacksList[2] = attackPosition2();
        attacksList[3] = attackPosition3();

        return attacksList;
    }
}
