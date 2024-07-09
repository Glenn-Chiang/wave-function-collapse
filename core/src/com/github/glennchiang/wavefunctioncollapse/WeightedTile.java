package com.github.glennchiang.wavefunctioncollapse;

// Composes a tile with a weight that determines its probability of being selected
public class WeightedTile {
    public final Tile tile;
    public final float weight;
    public WeightedTile(Tile tile, float weight) {
        this.tile = tile;
        this.weight = weight;
    }
}
