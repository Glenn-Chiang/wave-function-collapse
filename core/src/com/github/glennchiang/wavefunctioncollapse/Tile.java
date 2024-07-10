package com.github.glennchiang.wavefunctioncollapse;

import com.badlogic.gdx.graphics.Texture;

import java.util.*;

public class Tile {
    public final String name;
    public final Texture image;

    // Store the possible neighbors to this Tile for each direction
    // For each direction, each possible neighbor has a corresponding probability weight
    private final Map<Direction, Set<Tile>> neighborOptions = new HashMap<>();

    public Tile(String name, Texture image) {
        this.name = name;
        this.image = image;
        // Initialize empty hashmap for each direction
        for (Direction direction: Direction.values()) {
            neighborOptions.put(direction, new HashSet<>());
        }
    }

    // Add a tile to the set of possible neighbors for the given direction, as well as its corresponding weight
    public void addNeighborOptions(Direction direction, Tile tile) {
        neighborOptions.get(direction).add(tile);
    }

    // Get the possible neighbors for the given direction, along with their weights
    public Set<Tile> getNeighborOptions(Direction direction) {
        return neighborOptions.get(direction);

    }
}
