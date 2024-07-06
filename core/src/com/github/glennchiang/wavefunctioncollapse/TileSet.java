package com.github.glennchiang.wavefunctioncollapse;
import java.util.*;

public class TileSet {
    public final String name;
    public final Map<String, Tile> tiles = new HashMap<>(); // Map of tile names to tile objects

    public TileSet(String name, List<Tile> tiles) {
        this.name = name;
        for (Tile tile: tiles) {
            this.tiles.put(tile.name, tile);
        }
    }

    // Get a random Tile
    public Tile getRandom() {
        List<Tile> tiles = new ArrayList<>(this.tiles.values());
        Random rand = new Random();
        int randomIndex = rand.nextInt(tiles.size());
        return tiles.get(randomIndex);
    }

    public List<Tile> getTiles() {
        return new ArrayList<>(this.tiles.values());
    }
}
