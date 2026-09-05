package com.hutch9910.chessboardPatterns.variousEnum;

import java.awt.Color;

public enum Team {
    BLACK(0),
    RED(1),
    BLUE(2),
    MAGENTA(3),
    GREEN(4),
    YELLOW(5);

    private int code;

    Team(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public Color getColor() {
        return switch (this) {
            case BLACK -> Color.BLACK;
            case RED -> Color.RED;
            case BLUE -> Color.BLUE;
            case MAGENTA -> Color.MAGENTA;
            case GREEN -> Color.GREEN;
            case YELLOW -> Color.YELLOW;
        };
    }

    public String getDisplayName() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }

    public String getCssColor() {
        return switch (this) {
            case BLACK -> "#172026";
            case RED -> "#d94f4f";
            case BLUE -> "#3b82c4";
            case MAGENTA -> "#c04bb3";
            case GREEN -> "#3f9d68";
            case YELLOW -> "#d6a72b";
        };
    }
}