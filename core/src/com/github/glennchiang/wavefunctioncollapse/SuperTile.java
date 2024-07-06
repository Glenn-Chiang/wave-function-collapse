package com.github.glennchiang.wavefunctioncollapse;

import java.util.List;
import java.util.Random;

// A SuperTile contains multiple potential Tiles that it can be
public class SuperTile {
    private List<Tile> states;

    public SuperTile(List<Tile> states) {
        this.states = states;
    }

    // Randomly select one of the remaining states,
    // then remove all other tiles to reduce the SuperTile to a single state
    public void collapse() {
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

    // The SuperTile is collapsed when it is reduced to a single state
    private boolean collapsed() {
        return states.size() == 1;
    }

    // If the SuperTile has collapsed, return the single Tile state that it has been reduced to
    public Tile getTile() {
        if (collapsed()) {
            return states.get(0);
        } else {
            return null;
        }
    }
}
