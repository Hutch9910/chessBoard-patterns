package com.hutch9910.chessboardPatterns.web;

import com.hutch9910.chessboardPatterns.variousEnum.Team;
import com.hutch9910.chessboardPatterns.variousEnum.TypeOfPiece;

public class PieceChoice {

    private TypeOfPiece type = TypeOfPiece.ELEPHANT;
    private Team team = Team.BLACK;

    public TypeOfPiece getType() {
        return type;
    }

    public void setType(TypeOfPiece type) {
        this.type = type;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}