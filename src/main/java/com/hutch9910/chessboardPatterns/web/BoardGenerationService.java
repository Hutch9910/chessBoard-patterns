package com.hutch9910.chessboardPatterns.web;

import java.util.List;

import com.hutch9910.chessboardPatterns.models.Board;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class BoardGenerationService {

    private static final long MAX_MATERIALIZED_PIECES = 250_000L;

    public Board generate(VisualizationSetup setup) {
        int boardSide = normalizeBoardSide(setup.getBoardSide());
        Board board = new Board(boardSide);
        populate(setup, board.getBoardSide(), MAX_MATERIALIZED_PIECES,
                choice -> board.addPiece(choice.getTeam(), choice.getType()));
        return board;
    }

    public Board emptyBoard(VisualizationSetup setup) {
        return new Board(normalizeBoardSide(setup.getBoardSide()));
    }

    public Board generateDesktop(VisualizationSetup setup) {
        int boardSide = normalizeBoardSide(setup.getBoardSide());
        Board board = new Board(boardSide);
        populate(setup, board.getBoardSide(), (long) board.getBoardSide() * board.getBoardSide(),
                choice -> board.addPiece(choice.getTeam(), choice.getType()));
        return board;
    }

    public int countTeams(VisualizationSetup setup) {
        Set<com.hutch9910.chessboardPatterns.variousEnum.Team> teams = new HashSet<>();
        if (setup.getSequence() != null) {
            for (PieceChoice choice : setup.getSequence()) {
                if (choice != null && choice.getTeam() != null) {
                    teams.add(choice.getTeam());
                }
            }
        }
        return Math.max(1, teams.size());
    }

    private void populate(VisualizationSetup setup, int boardSide, long maxPieces,
            java.util.function.Consumer<PieceChoice> addPiece) {
        List<PieceChoice> sequence = setup.getSequence();
        if (sequence == null || sequence.isEmpty()) {
            return;
        }

        long squareCount = Math.min((long) boardSide * boardSide, maxPieces);
        for (long index = 0; index < squareCount; index++) {
            PieceChoice choice = sequence.get((int) (index % sequence.size()));
            if (choice != null && choice.getType() != null && choice.getTeam() != null) {
                addPiece.accept(choice);
            }
        }
    }

    private int normalizeBoardSide(int boardSide) {
        return Math.max(3, Math.min(10001, boardSide));
    }
}