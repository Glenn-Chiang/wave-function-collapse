package com.github.glennchiang.wavefunctioncollapse;
import java.util.List;

public class TileSet {
    public final String name;
    public final List<Tile> tiles;

    public TileSet(String name, List<Tile> tiles) {
        this.name = name;
        this.tiles = tiles;
    }

}
