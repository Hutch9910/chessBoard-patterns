package com.hutch9910.chessboardPatterns.web;

import java.util.ArrayList;
import java.util.List;

import com.hutch9910.chessboardPatterns.variousEnum.Team;
import com.hutch9910.chessboardPatterns.variousEnum.TypeOfPiece;

public class VisualizationSetup {

    private int boardSide = 15;
    private List<PieceChoice> sequence = defaultSequence();

    public int getBoardSide() {
        return boardSide;
    }

    public void setBoardSide(int boardSide) {
        this.boardSide = boardSide;
    }

    public List<PieceChoice> getSequence() {
        return sequence;
    }

    public void setSequence(List<PieceChoice> sequence) {
        if (sequence == null || sequence.isEmpty()) {
            List<PieceChoice> fallback = new ArrayList<>();
            fallback.add(defaultSequenceEntry());
            this.sequence = fallback;
            return;
        }
        this.sequence = sequence;
    }

    public static VisualizationSetup defaults() {
        return new VisualizationSetup();
    }

    private static List<PieceChoice> defaultSequence() {
        List<PieceChoice> choices = new ArrayList<>();
        choices.add(defaultSequenceEntry());
        TypeDefaults.add(choices, TypeOfPiece.KNIGHT, Team.RED);
        return choices;
    }

    private static PieceChoice defaultSequenceEntry() {
        PieceChoice choice = new PieceChoice();
        choice.setType(TypeOfPiece.KNIGHT);
        choice.setTeam(Team.BLACK);
        return choice;
    }

    private static final class TypeDefaults {
        private static void add(List<PieceChoice> choices, TypeOfPiece type, Team team) {
            PieceChoice choice = new PieceChoice();
            choice.setType(type);
            choice.setTeam(team);
            choices.add(choice);
        }
    }
}