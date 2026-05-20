package models;

import utility.Point;
import variousEnum.Team;
import variousEnum.TypeOfPiece;

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

    public abstract Point[] getAttacksList();

    @Override
    public String toString() {
        return "" +boardPosition;
    }
}
