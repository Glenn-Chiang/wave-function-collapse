package com.github.glennchiang.wavefunctioncollapse;
import com.github.glennchiang.wavefunctioncollapse.utils.RandomUtils;
import java.util.*;

// A Cell contains multiple potential Tiles that it can be
// In fancier terms, it is a "superposition of Tile states"
public class Cell {
    public final int row;
    public final int col;
    private final Set<Tile> states;
    private boolean collapsed = false;

    public Cell(int row, int col, Set<Tile> states) {
        this.row = row;
        this.col = col;
        this.states = states;
    }

    // Creates a copy of a Cell
    public Cell(Cell cell) {
        this.row = cell.row;
        this.col = cell.col;
        this.states = new HashSet<>(cell.states);
        this.collapsed = cell.collapsed;
    }

    // Collapse the cell into a single Tile
    public void collapse(Map<Tile, Float> weightMap) {
        if (collapsed) return;

        if (getEntropy() == 1) {
            collapsed = true;
        }

        // Based on the given probability map, create a map of each of the Cell's possible states
        // to the probability of selecting that state
        Map<Tile, Float> stateProbabilities = new HashMap<>(weightMap);
        stateProbabilities.keySet().retainAll(states);
        Tile selectedTile = RandomUtils.weightedRandomSelect(stateProbabilities);
        states.clear();
        states.add(selectedTile);
        collapsed = true;
    }

    // Remove states from the current set of states that are not also in the set of allowed states
    public void reduce(Set<Tile> allowedStates) {
        states.retainAll(allowedStates);
    }

    // The Cell is considered to be collapsed when it has been reduced to a single state
    public boolean collapsed() {
        return collapsed;
    }

    // The entropy of a Cell refers to the number of states/superpositions it currently has
    public int getEntropy() {
        return states.size();
    }

    // If the Cell has collapsed, return the single Tile state that it has been reduced to
    public Tile tile() {
        if (collapsed()) {
            return states.iterator().next(); // Get the only Tile in the set
        } else {
            return null;
        }
    }
}
