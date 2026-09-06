package com.hutch9910.chessboardPatterns.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import com.hutch9910.chessboardPatterns.models.Board;
import com.hutch9910.chessboardPatterns.models.Piece;
import com.hutch9910.chessboardPatterns.utility.Point;
import com.hutch9910.chessboardPatterns.variousEnum.Team;

public class BoardView {

    private final int side;
    private final List<Cell> pieces;
    private final List<Attack> attacks;

    public BoardView(Board board) {
        side = board.getBoardSide();
        pieces = board.getPieces().stream()
                .map(piece -> new Cell(piece, side))
                .toList();

        List<Attack> attackCells = new ArrayList<>();

        for (int row = 0; row < side; row++) {
            for (int column = 0; column < side; column++) {
                EnumSet<Team> teams = board.getAttacks()[row][column];

                if (!teams.isEmpty()) {
                    attackCells.add(new Attack(row, column, teams));
                }
            }
        }

        attacks = attackCells;
    }

    public int getSide() {
        return side;
    }

    public List<Cell> getPieces() {
        return pieces;
    }

    public List<Attack> getAttacks() {
        return attacks;
    }

    public static class Cell {
        private final Piece piece;
        private final List<Position> attacks;

        private Cell(Piece piece, int boardSide) {
            this.piece = piece;

            attacks = Arrays.stream(piece.getAttacksList())
                    .filter(point -> point.getX() >= 0
                            && point.getX() < boardSide
                            && point.getY() >= 0
                            && point.getY() < boardSide)
                    .map(Position::new)
                    .toList();
        }

        public int getRow() {
            return piece.getBoardPosition().getY();
        }

        public int getColumn() {
            return piece.getBoardPosition().getX();
        }

        public String getSymbol() {
            return piece.getTypeOfPiece().getSymbol();
        }

        public String getColor() {
            return piece.getTeam().getCssColor();
        }

        public String getLabel() {
            return piece.getTeam().getDisplayName()
                    + " "
                    + piece.getTypeOfPiece().getDisplayName();
        }

        public List<Position> getAttacks() {
            return attacks;
        }
    }

    public static class Attack {
        private final int row;
        private final int column;
        private final List<String> teams;

        private Attack(int row, int column, EnumSet<Team> attackingTeams) {
            this.row = row;
            this.column = column;
            this.teams = attackingTeams.stream()
                    .map(Enum::name)
                    .toList();
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        public List<String> getTeams() {
            return teams;
        }
    }

    public static class Position {
        private final int row;
        private final int column;

        private Position(Point point) {
            row = point.getY();
            column = point.getX();
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }
    }
}