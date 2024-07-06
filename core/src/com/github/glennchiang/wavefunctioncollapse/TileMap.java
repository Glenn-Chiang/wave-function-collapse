package com.github.glennchiang.wavefunctioncollapse;

public class TileMap {
    public final int ROWS;
    public final int COLS;
    private final Tile[][] grid;

    public TileMap(int rows, int cols) {
        ROWS = rows;
        COLS = cols;
        grid = new Tile[rows][cols];

    }
}
