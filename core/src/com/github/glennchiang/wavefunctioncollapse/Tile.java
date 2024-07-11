package com.github.glennchiang.wavefunctioncollapse;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.*;

public class Tile {
    public final TextureRegion image;
    public final float rotation;
    public final String[] edges;

    // Store the possible neighbors to this Tile for each direction
    // For each direction, each possible neighbor has a corresponding probability weight
    private final Map<Direction, Set<Tile>> neighborOptions = new HashMap<>();

    public Tile(Texture texture, String[] edges, float rotation) {
        this.image = new TextureRegion(texture);
        this.edges = edges;
        this.rotation = rotation;
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
