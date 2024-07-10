package com.github.glennchiang.wavefunctioncollapse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.*;

// Loads tile sets from the assets directory
public class TileSetLoader {
    private static final String rootPath = "./tilesets"; // Path to directory containing all tilesets
    private final Map<String, TileSet> tileSets = new HashMap<>();

    public TileSetLoader() {
        // Get the directories of all tile sets. Path is relative to assets folder
        FileHandle[] dirs = Gdx.files.internal(rootPath).list();
        // Create a TileSet for each subdirectory in the tilesets directory
        for (FileHandle dir: dirs) {
            TileSet tileSet = loadTileSet(dir.name(), dir.path());
            tileSets.put(tileSet.name, tileSet);
        }
    }

    // Creates a TileSet from a given directory containing the corresponding tile images
    private TileSet loadTileSet(String name, String path) {
        // Get the list of tile image files in the given tileset directory
        // e.g. assets/tilesets/overworld/tiles
        FileHandle[] imageFiles = Gdx.files.internal(path + "/tiles").list();

        // Get the rules for the tile set
        // e.g. assets/tilesets/overworld/rules.json
        JsonReader reader = new JsonReader();
        JsonValue ruleData = reader.parse(Gdx.files.internal(path + "/rules.json"));

        TileSet tileSet = new TileSet(name);

        // Create a Tile from each image file and add it to the list of tiles
        for (FileHandle imageFile: imageFiles) {
            String tileName = imageFile.nameWithoutExtension();
            Texture tileImage = new Texture(Gdx.files.internal(imageFile.path()));

            Tile tile = new Tile(tileName, tileImage);
            // Get the weight value from json
            float weight = ruleData.get(tileName).get("weight").asFloat();
            tileSet.addTile(tile, weight);
        }

        Set<Tile> tiles = tileSet.tiles.keySet();

        for (Tile tile: tiles) {
            // Each edge of the tile is given a string label
            String[] edges = ruleData.get(tile.name).get("edges").asStringArray();

            // For each edge of this tile, find all other tiles whose complementary edge has the same label
            // as this edge
            for (int i = 0; i < edges.length; i++) {
                String edge = edges[i];
                Direction direction = Direction.values()[i];

                for (Tile otherTile: tiles) {
                    String[] otherEdges = ruleData.get(otherTile.name).get("edges").asStringArray();
                    // Get the other tile's edge that is opposite to this edge
                    String complementaryEdge = otherEdges[direction.opposite().ordinal()];

                    // If the opposite edges have the same label, the other tile can be adjacent to this tile
                    // for this direction
                    if (edge.equalsIgnoreCase(complementaryEdge)) {
                        tile.addNeighborOptions(direction, otherTile);
                    }
                }
            }
        }

        return tileSet;
    }

    // Gets a tileset by name
    public TileSet getTileSet(String name) {
        return tileSets.get(name);
    }

    public Set<String> getTileSets() {
        return tileSets.keySet();
    }
}
