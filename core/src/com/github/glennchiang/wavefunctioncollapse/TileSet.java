package com.github.glennchiang.wavefunctioncollapse;
import java.util.*;

public class TileSet {
    public final String name;
    public final Map<Tile, Float> tiles = new HashMap<>(); // Map of Tiles to their initial weights

    public TileSet(String name) {
        this.name = name;
    }

    public void addTile(Tile tile, float initialWeight) {
        tiles.put(tile, initialWeight);
    }
}
