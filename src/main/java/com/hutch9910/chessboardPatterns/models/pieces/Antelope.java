package com.hutch9910.chessboardPatterns.models.pieces;

import com.hutch9910.chessboardPatterns.models.Piece;
import com.hutch9910.chessboardPatterns.utility.Point;
import com.hutch9910.chessboardPatterns.variousEnum.Team;
import com.hutch9910.chessboardPatterns.variousEnum.TypeOfPiece;

public class Antelope extends Piece {

    // Constructors
    public Antelope(Team team, TypeOfPiece typeOfPiece, Point boardPosition) {
        super(team, boardPosition);
        this.typeOfPiece = typeOfPiece;
    }

    // Getters
    private Point attackPosition0() {
        return new Point((boardPosition.getX() + 4), (boardPosition.getY() + 3));
    }
    private Point attackPosition1() {
        return new Point((boardPosition.getX() + 4), (boardPosition.getY() - 3));
    }
    private Point attackPosition2() {
        return new Point((boardPosition.getX() - 4), (boardPosition.getY() + 3));
    }
    private Point attackPosition3() {
        return new Point((boardPosition.getX() - 4), (boardPosition.getY() - 3));
    }
    private Point attackPosition4() {
        return new Point((boardPosition.getX() + 3), (boardPosition.getY() + 4));
    }
    private Point attackPosition5() {
        return new Point((boardPosition.getX() - 3), (boardPosition.getY() + 4));
    }
    private Point attackPosition6() {
        return new Point((boardPosition.getX() + 3), (boardPosition.getY() - 4));
    }
    private Point attackPosition7() {
        return new Point((boardPosition.getX() - 3), (boardPosition.getY() - 4));
    }

    @Override
    public Point[] getAttacksList() {
        Point[] attacksList = new Point[8];

        attacksList[0] = attackPosition0();
        attacksList[1] = attackPosition1();
        attacksList[2] = attackPosition2();
        attacksList[3] = attackPosition3();
        attacksList[4] = attackPosition4();
        attacksList[5] = attackPosition5();
        attacksList[6] = attackPosition6();
        attacksList[7] = attackPosition7();

        return attacksList;
    }
}
