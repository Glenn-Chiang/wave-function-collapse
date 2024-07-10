package com.github.glennchiang.wavefunctioncollapse;

public enum Direction {
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0);

    int x;
    int y;
    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Direction opposite() {
        for (Direction dir: Direction.values()) {
            if (this == dir) continue;
            if ((x == dir.x && y == -dir.y) || (y == dir.y && x == -dir.x)) {
                return dir;
            }
        }
        return null;
    }
}
