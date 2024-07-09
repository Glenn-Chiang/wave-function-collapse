package com.github.glennchiang.wavefunctioncollapse;

import java.util.ArrayList;
import java.util.List;

public class TileMap {
    public int getRows() {
        return grid.length;
    }
    public int getCols() {
        return grid[0].length;
    }
    private Cell[][] grid;

    public TileMap(int rows, int cols) {
        setDimensions(rows, cols);
    }

    // Sets new number of rows and columns, which also resets the grid
    public void setDimensions(int rows, int cols) {
        grid = new Cell[rows][cols];
    }

    // Set every cell to null
    public void clear() {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                grid[i][j] = null;
            }
        }
    }

    // Create a deep copy of the TileMap
    public TileMap(TileMap tileMap) {
        this.grid = new Cell[tileMap.getRows()][tileMap.getCols()];
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                // Create copy of each SuperTile if not null
                this.grid[i][j] = tileMap.getCell(i, j) == null ? null : new Cell(tileMap.getCell(i, j));
            }
        }
    }

    // Check if all SuperTiles in the grid have collapsed
    public boolean collapsed() {
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                if (!grid[i][j].collapsed()) {
                    return false;
                }
            }
        }
        return true;
    }

    // Set the cell at the given position on the grid
    public void setCell(int row, int col, Cell cell) {
        grid[row][col] = cell;
    }

    // Get list of all cells in the grid
    public List<Cell> getCells() {
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                if (grid[i][j] != null) {
                    cells.add(grid[i][j]);
                }
            }
        }
        return cells;
    }

    // Get cell at given position if position is in grid bounds
    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    // Check if given position is in grid bounds
    public boolean inBounds(int row, int col) {
        return row >= 0 && col >= 0 && row < getRows() && col < getCols();
    }
}
