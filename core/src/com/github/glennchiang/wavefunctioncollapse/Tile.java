package com.github.glennchiang.wavefunctioncollapse;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

public class Tile {
    public final String name;
    public final Texture image;
    public final List<Tile> allowedNeighbors = new ArrayList<>();

    public Tile(String name, Texture image) {
        this.name = name;
        this.image = image;
        // TODO: How to determine allowed neighbors?
        allowedNeighbors.add(this);
    }
}
