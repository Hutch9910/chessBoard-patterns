package com.hutch9910.chessboardPatterns.models;

import java.util.EnumSet;

import com.hutch9910.chessboardPatterns.utility.Point;
import com.hutch9910.chessboardPatterns.variousEnum.Team;
import com.hutch9910.chessboardPatterns.variousEnum.TypeOfPiece;

public class Board {

    private int boardSide;
    private final Point centre;

    private int numberOfPieces;
    private Piece[] pieces;

    private Piece[][] occupancy;
    private EnumSet<Team>[][] attacks;

    private int numberOfTeams;
    private Point[] lastChangedPosition;

    // Constructors
    public Board(int boardSide, int numberOfTeams) {
        setBoardSide(boardSide); // round to the nearest larger odd number
        this.centre = new Point(boardSide /2, boardSide /2);

        numberOfPieces = 0;
        setPieces();

        setOccupancy();
        setAttacks();

        setNumberOfTeams(numberOfTeams);
        setLastChangedPosition();
    }

    // Getters
    public int getBoardSide() {
        return boardSide;
    }
    public Piece[][] getOccupancy() {
        return occupancy;
    }
    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    // Setters
    public void setBoardSide(int boardSide) {
        if (boardSide %2 == 0) {
            boardSide++;
        }
        this.boardSide = boardSide;
    }
    public void setPieces() {
        this.pieces = new Piece[this.boardSide * this.boardSide];
    }
    public void setOccupancy() {
        this.occupancy = new Piece[this.boardSide][this.boardSide];
    }
    public void setAttacks() {
        this.attacks = new EnumSet[this.boardSide][this.boardSide];
        for (int y = 0; y < this.boardSide; y++) {
            for (int x = 0; x < this.boardSide; x++) {
                attacks[y][x] = EnumSet.noneOf(Team.class);
            }
        }
    }
    public void setNumberOfTeams(int numberOfTeams) {
        if (numberOfTeams <= 1) {
            numberOfTeams = 2;
        }
        this.numberOfTeams = numberOfTeams;
    }
    public void setLastChangedPosition() {
        lastChangedPosition = new Point[Team.values().length];
        for (int i = 0; i < Team.values().length; i++) {
            lastChangedPosition[i] = new Point(this.centre.getX(), this.centre.getY());
        }
    }

    // Main method
    public void addPiece(Team team, TypeOfPiece typeOfPiece) {

        Point position = (numberOfPieces == 0) ? centre : findAvailablePosition(team);

        if (position == null) {
            return;
        }

        pieces[numberOfPieces] = typeOfPiece.buildPiece(team, position);
        addAttackedPositions(pieces[numberOfPieces]);
        occupancy[position.getY()][position.getX()] = pieces[numberOfPieces];
        numberOfPieces++;
    }

    // Spiral logic
    public Point findAvailablePosition(Team team) {

        int code = team.getCode();
        int x = lastChangedPosition[code].getX();
        int y = lastChangedPosition[code].getY();
        int dX = x - centre.getX(); // X coordinates relative to the center (distance from the center)
        int dY = y - centre.getY(); // Y coordinates relative to the center (distance from the center)
        int r = Math.max(Math.abs(dX), Math.abs(dY)); // current ring radius calculation

        // loop that iterates the spiral until it finds an available position
        boolean positionFound = false;
        while (!positionFound) {
            if (dX == r && dY == r) { // new ring
                r++;
                x++;
                dX = x - centre.getX();
                if (x >= occupancy[0].length) { // check if it has come off the board
                    return null;
                }
                if (isPositionSafe(team, x, y)) {
                    lastChangedPosition[code].setX(x);
                    lastChangedPosition[code].setY(y);
                    positionFound = true;
                }
            }
            else if (dX == r && dY > -r) { // right side -> up
                y--;
                dY = y - centre.getY();
                if (isPositionSafe(team, x, y)) {
                    lastChangedPosition[code].setX(x);
                    lastChangedPosition[code].setY(y);
                    positionFound = true;
                }
            }
            else if (dX > -r && dY == -r) { // top side -> left
                x--;
                dX = x - centre.getX();
                if (isPositionSafe(team, x, y)) {
                    lastChangedPosition[code].setX(x);
                    lastChangedPosition[code].setY(y);
                    positionFound = true;
                }
            }
            else if (dX == -r && dY < r) { // left side -> down
                y++;
                dY = y - centre.getY();
                if (isPositionSafe(team, x, y)) {
                    lastChangedPosition[code].setX(x);
                    lastChangedPosition[code].setY(y);
                    positionFound = true;
                }
            }
            else if (dX < r && dY == r) { // bottom side -> right
                x++;
                dX = x - centre.getX();
                if (isPositionSafe(team, x, y)) {
                    lastChangedPosition[code].setX(x);
                    lastChangedPosition[code].setY(y);
                    positionFound = true;
                }
            }
        }
        return new Point(x, y);
    }

    public boolean isPositionSafe(Team team, int x, int y) {
        boolean attacksSafe = attacks[y][x].isEmpty()
                || (attacks[y][x].contains(team) && attacks[y][x].size() == 1);
        boolean occupancySafe = occupancy[y][x] == null;
        return attacksSafe && occupancySafe;
    }

    public void addAttackedPositions(Piece piece) {

        Point[] attackedPositions = piece.getAttacksList();

        for (Point attackedPosition : attackedPositions) {
            int x = attackedPosition.getX();
            int y = attackedPosition.getY();

            if (x < attacks[0].length && x >= 0 && y < attacks.length && y >= 0) { // check if the attacked position is inside the board
                attacks[y][x].add(piece.team);
            }
        }
    }
}
