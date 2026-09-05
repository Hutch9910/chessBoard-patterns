package com.hutch9910.chessboardPatterns.web;

import java.util.List;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class BoardGenerationService {

    private static final long MAX_MATERIALIZED_PIECES = 250_000L;

    public SparseBoard generate(VisualizationSetup setup) {
        int boardSide = Math.max(3, Math.min(5001, setup.getBoardSide()));
        SparseBoard board = new SparseBoard(boardSide);
        List<PieceChoice> sequence = setup.getSequence();

        if (sequence == null || sequence.isEmpty()) {
            return board;
        }

        long squareCount = Math.min((long) board.getSide() * board.getSide(), MAX_MATERIALIZED_PIECES);
        for (long index = 0; index < squareCount; index++) {
            PieceChoice choice = sequence.get((int) (index % sequence.size()));
            if (choice != null && choice.getType() != null && choice.getTeam() != null) {
                board.addPiece(choice.getTeam(), choice.getType());
            }
        }
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
}