package com.github.glennchiang.wavefunctioncollapse;

import com.badlogic.gdx.Gdx;

import java.util.Iterator;
import java.util.List;

public class VisualizationController {
    private TileSet tileSet;
    public final TileMap tileMap;
    private final TileMapRenderer tileMapRenderer;
    private Iterator<TileMap> stepIterator;
    private TileMap currentStep = null;

    public enum State {
        INACTIVE, RUNNING, COMPLETE
    }

    private State state = State.INACTIVE;

    public VisualizationController(TileMap tileMap, TileMapRenderer tileMapRenderer) {
        this.tileMapRenderer = tileMapRenderer;
        this.tileMap = tileMap;
    }

    public void setTileSet(TileSet tileSet) {
        this.tileSet = tileSet;
    }

    public TileSet getTileSet() {
        return tileSet;
    }

    public void run() {
        if (state == State.RUNNING) {
            reset();
        }
        state = State.RUNNING;
        // Generate a list of tilemap states that progress toward the final generated tilemap
        List<TileMap> solutionStates = WaveFunctionCollapseAlgorithm.generate(tileSet, tileMap);
        stepIterator = solutionStates.iterator();
        currentStep = stepIterator.next();
    }

    public void update() {
        if (state != State.RUNNING) {
            tileMapRenderer.render(tileMap);
            return;
        }

        tileMapRenderer.render(currentStep);

        // Finished visualizing the generated solution
        if (!stepIterator.hasNext()) {
            state = State.COMPLETE;
            return;
        }

        // At every interval, advance to next state
        currentStep = stepIterator.next();
    }

    public void reset() {
        state = State.INACTIVE;
        stepIterator = null;
        currentStep = null;
        tileMap.clear();
    }
}
