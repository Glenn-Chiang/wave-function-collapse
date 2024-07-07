package com.github.glennchiang.wavefunctioncollapse;

import java.util.*;

// The actual WFC algorithm
public class WaveFunctionCollapseAlgorithm {
    // Directions in which the wave can propagate
    private int[][] directions = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    public WaveFunctionCollapseAlgorithm() {}

    public void generate(TileSet tileSet, TileMap tileMap) {
        // Initialize a SuperTile at each cell in the grid
        for (int i = 0; i < tileMap.ROWS; i++) {
            for (int j = 0; j < tileMap.COLS; j++) {
                // Set the initial state of the SuperTile to include all tiles in the tileset
                tileMap.setTile(i, j, new SuperTile(i, j, tileSet.getTiles()));
            }
        }

        // Loop until all tiles have collapsed
        while (!tileMap.collapsed()) {
            // Among the tiles that have not collapsed, find the tile with the lowest entropy
            SuperTile currentTile = tileMap.getTiles().stream().filter(tile -> !tile.collapsed())
                    .min(Comparator.comparingInt(SuperTile::getEntropy)).orElse(null);
            currentTile.collapse();

            // Get neighbors of current tile
            for (int[] dir: directions) {
                SuperTile neighbor = tileMap.getTile(currentTile.row + dir[0], currentTile.col + dir[1]);
                if (neighbor == null || neighbor.collapsed()) continue;

                // Reduce the states of each neighbor to only include states
                // that are allowed to be adjacent to the current collapsed tile
                List<Tile> allowedNeighbors = currentTile.collapsedTile().allowedNeighbors;
                neighbor.reduce(allowedNeighbors);
            }

        }
    }
}
