package com.hutch9910.chessboardPatterns.web;

import java.util.List;

import com.hutch9910.chessboardPatterns.models.Piece;

public class BoardView {

    private final int side;
    private final List<Cell> pieces;

    public BoardView(SparseBoard board) {
        side = board.getSide();
        pieces = board.getPieces().stream().map(Cell::new).toList();
    }

    public int getSide() {
        return side;
    }

    public List<Cell> getPieces() {
        return pieces;
    }

    public static class Cell {
        private final Piece piece;

        private Cell(Piece piece) {
            this.piece = piece;
        }

        public int getRow() {
            return piece.getBoardPosition().getY();
        }

        public int getColumn() {
            return piece.getBoardPosition().getX();
        }

        public boolean isOccupied() {
            return true;
        }

        public String getSymbol() {
            return piece == null ? "" : piece.getTypeOfPiece().getSymbol();
        }

        public String getColor() {
            return piece == null ? "" : piece.getTeam().getCssColor();
        }

        public String getLabel() {
            return piece == null ? "Empty square" : piece.getTeam().getDisplayName() + " " + piece.getTypeOfPiece().getDisplayName();
        }
    }
}