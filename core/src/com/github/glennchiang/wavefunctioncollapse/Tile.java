package com.github.glennchiang.wavefunctioncollapse;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tile {
    public final String name;
    public final Texture image;
    public final List<Tile> allowedNeighbors = new ArrayList<>();

    private final Map<Direction, WeightedTile[]> neighborOptions = new HashMap<>();

    public Tile(String name, Texture image) {
        this.name = name;
        this.image = image;
    }

    // Add an array of tiles as valid neighbors to this tile for the given direction
    public void addNeighborOptions(Direction direction, WeightedTile[] tiles) {
        neighborOptions.put(direction, tiles);
    }

    // Select a random tile from the neighbor options for the given direction
    // The weight of the WeightedTile determines its probability of being selected
    public Tile selectRandomNeighbor(Direction direction) {
        WeightedTile[] possibleNeighbors = neighborOptions.get(direction);
        // TODO: Weighted random selection
        return possibleNeighbors[0].tile;
    }
}
