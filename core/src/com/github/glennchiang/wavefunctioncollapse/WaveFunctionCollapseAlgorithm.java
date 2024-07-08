package com.github.glennchiang.wavefunctioncollapse;

import java.util.*;

// The actual WFC algorithm
public class WaveFunctionCollapseAlgorithm {
    // Directions in which the wave can propagate
    private static int[][] directions = {{0, 1}, {-1, 0}, {0, -1}, {1, 0}};

    public static List<TileMap> generate(TileSet tileSet, TileMap tileMap) {
        // Initialize a SuperTile at each cell in the grid
        for (int i = 0; i < tileMap.getRows(); i++) {
            for (int j = 0; j < tileMap.getCols(); j++) {
                // Set the initial state of the SuperTile to include all tiles in the tileset
                tileMap.setTile(i, j, new SuperTile(i, j, tileSet.getTiles()));
            }
        }

        // Save the successive states of the tilemap as the algorithm progresses
        List<TileMap> tileMapStates = new ArrayList<>();

        // Queue of tiles to propagate
        Queue<SuperTile> queue = new LinkedList<>();

        // Loop until all tiles have collapsed
        while (!tileMap.collapsed()) {
            // Retrieve and remove first tile in queue
            SuperTile currentTile = queue.poll();
            // If queue is empty, select the tile with the lowest entropy among the tiles that have not collapsed
            if (currentTile == null) {
                currentTile = tileMap.getTiles().stream().filter(tile -> !tile.collapsed())
                        .min(Comparator.comparingInt(SuperTile::getEntropy)).orElse(null);
                // Collapse the selected tile
                currentTile.collapse();
            }

            for (int[] dir : directions) {
                int nextRow = currentTile.row + dir[0];
                int nextCol = currentTile.col + dir[1];

                if (!tileMap.inBounds(nextRow, nextCol)) continue;

                SuperTile neighbor = tileMap.getTile(nextRow, nextCol);
                // Skip neighbors that have already collapsed
                if (neighbor == null || neighbor.collapsed()) continue;

                // Reduce the states of each neighbor to only include states
                // that are allowed to be adjacent to the current collapsed tile
                List<Tile> allowedNeighbors = currentTile.collapsedTile().allowedNeighbors;
                neighbor.reduce(allowedNeighbors);
                // If neighbors was collapsed, add it to the queue to propagate the collapse
                if (neighbor.collapsed()) {
                    queue.add(neighbor);
                }
            }

            // Save a copy of the current state of the tilemap
            TileMap savedState = new TileMap(tileMap);
            tileMapStates.add(savedState);
        }

        return tileMapStates;
    }
}
