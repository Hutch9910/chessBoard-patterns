package variousEnum;

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
}