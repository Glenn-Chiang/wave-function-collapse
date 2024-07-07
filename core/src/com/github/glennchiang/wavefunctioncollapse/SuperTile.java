package com.github.glennchiang.wavefunctioncollapse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// A SuperTile contains multiple potential Tiles that it can be
public class SuperTile {
    public final int row;
    public final int col;
    private final List<Tile> states;

    public SuperTile(int row, int col, List<Tile> states) {
        this.row = row;
        this.col = col;
        this.states = states;
    }

    // Creates a copy of a SuperTile
    public SuperTile(SuperTile tile) {
        this.row = tile.row;
        this.col = tile.col;
        this.states = new ArrayList<>(tile.states);
    }

    public void collapse() {
        if (collapsed()) return;
        // Randomly select one of the remaining states,
        // then remove all other tiles to reduce the SuperTile to a single state
        Random rand = new Random();
        int randomIndex = rand.nextInt(states.size());
        Tile selectedTile = states.get(randomIndex);
        states.clear();
        states.add(selectedTile);
    }

    // Reduce the list of states to only include those states that are in the given list of allowed states
    public void reduce(List<Tile> allowedStates) {
        states.removeIf(state -> !allowedStates.contains(state));
    }

    // The SuperTile is considered to be collapsed when it has been reduced to a single state
    public boolean collapsed() {
        return states.size() == 1;
    }

    // The entropy of a SuperTile refers to the number of states/superpositions it currently has
    public int getEntropy() {
        return states.size();
    }

    // If the SuperTile has collapsed, return the single Tile state that it has been reduced to
    public Tile collapsedTile() {
        if (collapsed()) {
            return states.get(0);
        } else {
            return null;
        }
    }
}
