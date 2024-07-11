package com.github.glennchiang.wavefunctioncollapse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.*;

// Loads tile sets from the assets directory
public class TileSetLoader {
    private static final String rootPath = "./tilesets"; // Path to directory containing all tilesets
    public final Map<String, TileSet> tileSets = new HashMap<>();

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

            JsonValue tileData = ruleData.get(tileName);

            // Get the weight value from json
            float weight = tileData.get("weight").asFloat();

            // Get the edge labels from json
            String[] edges = tileData.get("edges").asStringArray();
            // Get all distinct rotation configurations for this tile
            List<String[]> configurations = getConfigurations(edges);

            // Create a unique Tile object for each configuration of this tile asset
            for (int i = 0; i < configurations.size(); i++) {
                String[] configuration = configurations.get(i);
                float rotation = - (float) i / edges.length * 360;
                Tile tile = new Tile(tileImage, configuration, rotation);
                tileSet.addTile(tile, weight);
            }
        }

        Set<Tile> tiles = tileSet.tiles.keySet();

        for (Tile tile: tiles) {
            // For each edge of this tile, find all other tiles whose complementary edge has the same label as this edge
            for (int i = 0; i < tile.edges.length; i++) {
                String edge = tile.edges[i];
                Direction direction = Direction.values()[i];

                for (Tile otherTile: tiles) {
                    // Get the other tile's edge that is opposite to this edge
                    String complementaryEdge = otherTile.edges[direction.opposite().ordinal()];

                    // If the opposite edges have the same label, the other tile can be adjacent to this tile
                    // for this direction
                    String reversedComplementaryEdge = new StringBuilder(complementaryEdge).reverse().toString();
                    if (edge.equalsIgnoreCase(reversedComplementaryEdge)) {
                        tile.addNeighborOptions(direction, otherTile);
                    }
                }
            }
        }

        return tileSet;
    }

    // Get all distinct; rotation configurations of the edges array
    private List<String[]> getConfigurations(String[] edges) {
        List<String[]> configurations = new ArrayList<>();
        for (int i = 0; i < edges.length; i++) {
            String[] configuration = rotateEdges(edges, i);
            if (!containsConfiguration(configurations, configuration)) {
                configurations.add(configuration);
            }
        }
        return configurations;
    }

    // Check if a list of configurations already contains a configuration that is equal to the given configuration
    private boolean containsConfiguration(List<String[]> configurations, String[] configuration) {
        for (String[] config: configurations) {
            // Check if all values are equal
            if (Arrays.equals(config, configuration)) {
                return true;
            }
        }
        return false;
    }

    // Move each edge by k places
    private String[] rotateEdges(String[] edges, int k) {
        String[] rotatedEdges = new String[edges.length];
        for (int i = 0; i < edges.length; i++) {
            rotatedEdges[(i + k) % edges.length] = edges[i];
        }
        return rotatedEdges;
    }

    // Gets a tileset by name
    public TileSet getTileSet(String name) {
        return tileSets.get(name);
    }

}
