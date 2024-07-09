package com.github.glennchiang.wavefunctioncollapse;

import java.util.*;
import java.util.stream.Collectors;

public class WaveFunctionCollapseAlgorithm {
    public static List<TileMap> generate(TileSet tileSet, TileMap tileMap) {
        // Initialize a SuperTile at each cell in the grid
        for (int i = 0; i < tileMap.getRows(); i++) {
            for (int j = 0; j < tileMap.getCols(); j++) {
                // Set the initial state of the SuperTile to include all tiles in the tileset
                tileMap.setCell(i, j, new Cell(i, j, tileSet.tiles));
            }
        }

        // Save the successive states of the tilemap as the algorithm progresses
        List<TileMap> solutionStates = new ArrayList<>();

        Queue<Cell> queue = new LinkedList<>();
        // Loop until all cells have collapsed
        while (!tileMap.collapsed()) {
            // Retrieve and remove first cell in queue
            Cell currentCell = queue.poll();
            // If queue is empty, select the cell with the lowest entropy among the cells that have not collapsed
            if (currentCell == null) {
                currentCell = tileMap.getCells().stream().filter(cell -> !cell.collapsed())
                        .min(Comparator.comparingInt(Cell::getEntropy)).orElse(null);
                // Collapse the selected tile
                currentCell.collapse();
            }

            for (Direction dir : Direction.values()) {
                int nextRow = currentCell.row + dir.y;
                int nextCol = currentCell.col + dir.x;

                if (!tileMap.inBounds(nextRow, nextCol)) continue;

                Cell neighbor = tileMap.getCell(nextRow, nextCol);
                // Skip neighbors that have already collapsed
                if (neighbor == null || neighbor.collapsed()) continue;

                // Reduce the states of each neighbor to only include states
                // that are allowed to be adjacent to the current collapsed cell
                Map<Tile, Float> allowedNeighbors = currentCell.tile().getNeighborOptions(dir);
                neighbor.reduce(allowedNeighbors);
                // If neighbors was collapsed, add it to the queue to propagate the collapse
                if (neighbor.collapsed()) {
                    queue.add(neighbor);
                }
            }

            // Save a copy of the current state of the tilemap
            solutionStates.add(new TileMap(tileMap));
        }

        System.out.println(solutionStates.size());
        return solutionStates;
    }
}
