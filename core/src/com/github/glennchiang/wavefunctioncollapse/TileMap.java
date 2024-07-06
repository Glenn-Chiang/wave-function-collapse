package com.github.glennchiang.wavefunctioncollapse;

public class TileMap {
    public final int ROWS;
    public final int COLS;
    private final SuperTile[][] grid;

    private TileSet tileSet;

    public TileMap(int rows, int cols) {
        ROWS = rows;
        COLS = cols;
        grid = new SuperTile[rows][cols];
    }

    public void setTileSet(TileSet tileSet) {
        this.tileSet = tileSet;
        // Initialize a SuperTile at each cell in the grid
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                // Set the initial state of the SuperTile to include all tiles in the tileset
                grid[i][j] = new SuperTile(tileSet.getTiles());
            }
        }
    }

    public Tile getTile(int row, int col) {
        return grid[row][col].getTile();
    }
}
