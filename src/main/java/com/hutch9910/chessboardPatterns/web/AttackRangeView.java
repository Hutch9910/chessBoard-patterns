package com.hutch9910.chessboardPatterns.web;

import java.util.ArrayList;
import java.util.List;

import com.hutch9910.chessboardPatterns.utility.Point;
import com.hutch9910.chessboardPatterns.variousEnum.Team;
import com.hutch9910.chessboardPatterns.variousEnum.TypeOfPiece;

public class AttackRangeView {
    private final TypeOfPiece piece;
    private final List<Point> attacks;
    private final int extent;

    public AttackRangeView(TypeOfPiece piece) {
        this.piece = piece;
        Point origin = new Point(0, 0);
        this.attacks = new ArrayList<>();
        for (Point attack : piece.buildPiece(Team.BLACK, origin).getAttacksList()) {
            attacks.add(new Point(attack.getX(), attack.getY()));
        }
        extent = attacks.stream().mapToInt(point -> Math.max(Math.abs(point.getX()), Math.abs(point.getY()))).max().orElse(1);
    }

    public TypeOfPiece getPiece() { return piece; }
    public List<Point> getAttacks() { return attacks; }
    public int getExtent() { return extent; }
    public boolean isAttack(int x, int y) {
        return attacks.stream().anyMatch(point -> point.getX() == x && point.getY() == y);
    }
}