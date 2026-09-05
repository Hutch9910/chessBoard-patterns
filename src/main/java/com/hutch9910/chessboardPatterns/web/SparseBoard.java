package com.hutch9910.chessboardPatterns.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hutch9910.chessboardPatterns.models.Piece;
import com.hutch9910.chessboardPatterns.utility.Point;
import com.hutch9910.chessboardPatterns.variousEnum.Team;

public class SparseBoard {

    private final int side;
    private final int centre;
    private final Map<Long, Piece> occupancy = new HashMap<>();
    private final Map<Long, Integer> attacks = new HashMap<>();
    private final Point[] lastChangedPosition;
    private final List<Piece> pieces = new ArrayList<>();

    public SparseBoard(int side) {
        this.side = side % 2 == 0 ? side + 1 : side;
        centre = this.side / 2;
        lastChangedPosition = new Point[Team.values().length];
        for (int index = 0; index < lastChangedPosition.length; index++) {
            lastChangedPosition[index] = new Point(centre, centre);
        }
    }

    public int getSide() {
        return side;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void addPiece(Team team, com.hutch9910.chessboardPatterns.variousEnum.TypeOfPiece type) {
        Point position = pieces.isEmpty() ? new Point(centre, centre) : findAvailablePosition(team);
        if (position == null) {
            return;
        }
        Piece piece = type.buildPiece(team, position);
        pieces.add(piece);
        occupancy.put(key(position.getX(), position.getY()), piece);
        for (Point attack : piece.getAttacksList()) {
            if (inside(attack.getX(), attack.getY())) {
                long key = key(attack.getX(), attack.getY());
                attacks.merge(key, 1 << team.getCode(), (current, value) -> current | value);
            }
        }
    }

    private Point findAvailablePosition(Team team) {
        int code = team.getCode();
        Point last = lastChangedPosition[code];
        int x = last.getX();
        int y = last.getY();
        int deltaX = x - centre;
        int deltaY = y - centre;
        int radius = Math.max(Math.abs(deltaX), Math.abs(deltaY));
        while (true) {
            if (deltaX == radius && deltaY == radius) {
                radius++;
                x++;
                deltaX = x - centre;
                if (!inside(x, y)) {
                    return null;
                }
                if (isSafe(team, x, y)) {
                    return remember(last, x, y);
                }
            } else if (deltaX == radius && deltaY > -radius) {
                y--;
                deltaY = y - centre;
                if (inside(x, y) && isSafe(team, x, y)) {
                    return remember(last, x, y);
                }
            } else if (deltaX > -radius && deltaY == -radius) {
                x--;
                deltaX = x - centre;
                if (inside(x, y) && isSafe(team, x, y)) {
                    return remember(last, x, y);
                }
            } else if (deltaX == -radius && deltaY < radius) {
                y++;
                deltaY = y - centre;
                if (inside(x, y) && isSafe(team, x, y)) {
                    return remember(last, x, y);
                }
            } else if (deltaX < radius && deltaY == radius) {
                x++;
                deltaX = x - centre;
                if (inside(x, y) && isSafe(team, x, y)) {
                    return remember(last, x, y);
                }
            }
        }
    }

    private Point remember(Point last, int x, int y) {
        last.setX(x);
        last.setY(y);
        return new Point(x, y);
    }

    private boolean isSafe(Team team, int x, int y) {
        Integer attackedBy = attacks.get(key(x, y));
        boolean attacksSafe = attackedBy == null || attackedBy == (1 << team.getCode());
        return attacksSafe && !occupancy.containsKey(key(x, y));
    }

    private boolean inside(int x, int y) {
        return x >= 0 && x < side && y >= 0 && y < side;
    }

    private long key(int x, int y) {
        return ((long) y << 32) | (x & 0xffffffffL);
    }
}