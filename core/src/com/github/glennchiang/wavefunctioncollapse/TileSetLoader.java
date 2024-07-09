package com.github.glennchiang.wavefunctioncollapse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonReader;

import java.util.*;

// Loads tile sets from the assets directory
public class TileSetLoader {
    private static final String rootPath = "./tilesets"; // Path to directory containing all tilesets

    private final Map<String, TileSet> tileSets = new HashMap<>();

    public TileSetLoader() {
        // Get the directories of all tile sets. Path is relative to assets folder.
        FileHandle[] dirs = Gdx.files.internal(rootPath).list();
        // Create a TileSet for each subdirectory in the tilesets directory
        for (FileHandle dir: dirs) {
            TileSet tileSet = loadTileSet(dir.name(), dir.path());
            tileSets.put(tileSet.name, tileSet);
        }
    }

    // Creates a TileSet from a given directory containing the corresponding tile images
    private TileSet loadTileSet(String name, String path) {
        List<Tile> tiles = new ArrayList<>();

        // Get the list of tile image files in the given tileset directory
        FileHandle[] imageFiles = Gdx.files.internal(path).list();

        // Create a Tile from each image file and add it to the list of tiles
        for (FileHandle imageFile: imageFiles) {
            String tileName = imageFile.nameWithoutExtension();
            Texture tileImage = new Texture(Gdx.files.internal(imageFile.path()));
            tiles.add(new Tile(tileName, tileImage));
        }

        // Get the rules for the tile set
        JsonReader reader = new JsonReader();

        return new TileSet(name, tiles);
    }

    // Gets a tileset by name
    public TileSet getTileSet(String name) {
        return tileSets.get(name);
    }

    public Set<String> getTileSets() {
        return tileSets.keySet();
    }
}
