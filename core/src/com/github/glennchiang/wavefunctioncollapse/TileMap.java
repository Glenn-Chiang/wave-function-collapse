package com.github.glennchiang.wavefunctioncollapse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TileMap {
    public final int ROWS;
    public final int COLS;
    private final SuperTile[][] grid;

    public TileMap(int rows, int cols) {
        ROWS = rows;
        COLS = cols;
        grid = new SuperTile[rows][cols];
    }

    // Create a deep copy of the TileMap
    public TileMap(TileMap tileMap) {
        this.ROWS = tileMap.ROWS;
        this.COLS = tileMap.COLS;;
        this.grid = new SuperTile[ROWS][COLS];
        for (int i = 0; i < tileMap.ROWS; i++) {
            for (int j = 0; j < tileMap.COLS; j++) {
                // Create copy of each SuperTile
                this.grid[i][j] = new SuperTile(tileMap.grid[i][j]);
            }
        }
    }

    // Check if all SuperTiles in the grid have collapsed
    public boolean collapsed() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (!grid[i][j].collapsed()) {
                    return false;
                }
            }
        }
        return true;
    }

    public int cellCount() {
        return ROWS * COLS;
    }

    public void setTile(int row, int col, SuperTile tile) {
        grid[row][col] = tile;
    }

    public List<SuperTile> getTiles() {
        List<SuperTile> tiles = new ArrayList<>();
        for (int i = 0; i < ROWS; i++) {
            tiles.addAll(Arrays.asList(grid[i]).subList(0, COLS));
        }
        return tiles;
    }

    public SuperTile getTile(int row, int col) {
        if (inBounds(row, col)) {
            return grid[row][col];
        } else {
            return null;
        }
    }

    private boolean inBounds(int row, int col) {
        return row >= 0 && col >= 0 && row < ROWS && col < COLS;
    }
}
