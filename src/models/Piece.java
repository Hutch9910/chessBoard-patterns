package models;

import utility.Point;
import variousEnum.Team;
import variousEnum.TypeOfPiece;

public abstract class Piece {

    protected Team team;
    protected Point position;
    protected TypeOfPiece typeOfPiece;

    // Constructors
    public Piece(Team team, Point position) {
        this.team = team;
        this.position = position;
    }

    // Getters
    public Point getPosition() {
        return position;
    }
    public Team getTeam() {
        return team;
    }

    public abstract Point[] getAttacksList();

    @Override
    public String toString() {
        return "" +position;
    }
}
