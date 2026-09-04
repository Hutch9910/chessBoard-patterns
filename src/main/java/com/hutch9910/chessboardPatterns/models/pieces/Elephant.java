package com.hutch9910.chessboardPatterns.models.pieces;

import com.hutch9910.chessboardPatterns.models.Piece;
import com.hutch9910.chessboardPatterns.utility.Point;
import com.hutch9910.chessboardPatterns.variousEnum.Team;
import com.hutch9910.chessboardPatterns.variousEnum.TypeOfPiece;

public class Elephant extends Piece {

    // Constructors
    public Elephant(Team team, TypeOfPiece typeOfPiece, Point boardPosition) {
        super(team, boardPosition);
        this.typeOfPiece = typeOfPiece;
    }

    // Getters
    private Point attackPosition0() {
        return new Point((boardPosition.getX() + 2), (boardPosition.getY() + 2));
    }
    private Point attackPosition1() {
        return new Point((boardPosition.getX() + 2), (boardPosition.getY() - 2));
    }
    private Point attackPosition2() {
        return new Point((boardPosition.getX() - 2), (boardPosition.getY() + 2));
    }
    private Point attackPosition3() {
        return new Point((boardPosition.getX() - 2), (boardPosition.getY() - 2));
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
