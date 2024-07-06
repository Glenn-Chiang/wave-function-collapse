package com.github.glennchiang.wavefunctioncollapse;

public class TileMap {
    public final int ROWS;
    public final int COLS;
    private final Tile[][] grid;

    private TileSet tileSet;

    public TileMap(int rows, int cols) {
        ROWS = rows;
        COLS = cols;
        grid = new Tile[rows][cols];
    }

    public void setTileSet(TileSet tileSet) {
        this.tileSet = tileSet;
    }

    // Randomly set a Tile at each cell in the grid
    public void randomize() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                Tile tile = tileSet.getRandom();
                grid[i][j] = tile;
            }
        }
    }

    public Tile getTile(int row, int col) {
        return grid[row][col];
    }
}
