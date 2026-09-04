package com.hutch9910.chessboardPatterns.utility;

public class Point {
    private int x;
    private int y;

    // Constructors
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getters
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    // Setters
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return ("(" + x + "," + y + ")");
    }
}
