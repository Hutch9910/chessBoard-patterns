package models.pieces;

import models.Piece;
import utility.Point;
import variousEnum.Team;
import variousEnum.TypeOfPiece;

public class Wazir extends Piece {

    // Constructors
    public Wazir(Team team, TypeOfPiece typeOfPiece, Point position) {
        super(team, position);
        this.typeOfPiece = typeOfPiece;
    }

    // Getters
    private Point attackPosition0() {
        return new Point((position.getX() + 1), (position.getY()));
    }
    private Point attackPosition1() {
        return new Point((position.getX() - 1), (position.getY()));
    }
    private Point attackPosition2() {
        return new Point((position.getX()), (position.getY() + 1));
    }
    private Point attackPosition3() {
        return new Point((position.getX()), (position.getY() - 1));
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
