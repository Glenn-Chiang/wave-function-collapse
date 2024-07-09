package com.github.glennchiang.wavefunctioncollapse;
import com.github.glennchiang.wavefunctioncollapse.utils.RandomUtils;
import java.util.*;

// A Cell contains multiple potential Tiles that it can be
// In fancier terms, it is a "superposition of Tile states"
public class Cell {
    public final int row;
    public final int col;
    private final Map<Tile, Float> states;

    public Cell(int row, int col, Map<Tile, Float> states) {
        this.row = row;
        this.col = col;
        this.states = states;
    }

    // Creates a copy of a Cell
    public Cell(Cell cell) {
        this.row = cell.row;
        this.col = cell.col;
        this.states = new HashMap<>(cell.states);
    }

    public void collapse() {
        if (collapsed()) return;
        // Randomly select one of the possible Tiles based on their weights,
        // then remove all other tiles to reduce the Cell to a single state
        Tile selectedTile = RandomUtils.weightedRandomSelect(states);
        states.clear();
        states.put(selectedTile, 1f);
    }

    // Remove states from the current set of states that are not also in the set of allowed states
    public void reduce(Map<Tile, Float> allowedStates) {
        states.keySet().retainAll(allowedStates.keySet());
    }

    // The Cell is considered to be collapsed when it has been reduced to a single state
    public boolean collapsed() {
        return states.size() == 1;
    }

    // The entropy of a Cell refers to the number of states/superpositions it currently has
    public int getEntropy() {
        return states.size();
    }

    // If the Cell has collapsed, return the single Tile state that it has been reduced to
    public Tile tile() {
        if (collapsed()) {
            return states.entrySet().iterator().next().getKey();
        } else {
            return null;
        }
    }
}
