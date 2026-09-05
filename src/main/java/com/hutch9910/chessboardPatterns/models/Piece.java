package com.hutch9910.chessboardPatterns.models;

import com.hutch9910.chessboardPatterns.utility.Point;
import com.hutch9910.chessboardPatterns.variousEnum.Team;
import com.hutch9910.chessboardPatterns.variousEnum.TypeOfPiece;

public abstract class Piece {

    protected Team team;
    protected Point boardPosition;
    protected TypeOfPiece typeOfPiece;

    // Constructors
    public Piece(Team team, Point position) {
        this.team = team;
        this.boardPosition = position;
    }

    // Getters
    public Point getBoardPosition() {
        return boardPosition;
    }
    public Team getTeam() {
        return team;
    }
    public TypeOfPiece getTypeOfPiece() {
        return typeOfPiece;
    }

    public abstract Point[] getAttacksList();

    @Override
    public String toString() {
        return "" +boardPosition;
    }
}
