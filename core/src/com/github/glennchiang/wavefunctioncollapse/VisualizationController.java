package com.github.glennchiang.wavefunctioncollapse;

import com.badlogic.gdx.Gdx;

import java.util.Iterator;
import java.util.List;

public class VisualizationController {
    private final TileSetLoader tileSetLoader;
    private TileSet currentTileSet;
    private final TileMap tileMap;
    private final TileMapRenderer tileMapRenderer;
    private Iterator<TileMap> stepIterator;
    private TileMap currentStep = null;
    private float stepInterval = 0.05f;
    private float stepTimer = stepInterval;

    public enum State {
        INACTIVE, RUNNING, COMPLETE
    }

    private State state = State.INACTIVE;
    public State getState() {
        return state;
    }

    public VisualizationController(TileSetLoader tileSetLoader, TileMap tileMap, TileMapRenderer tileMapRenderer) {
        this.tileSetLoader = tileSetLoader;
        this.tileMapRenderer = tileMapRenderer;
        currentTileSet = tileSetLoader.getTileSet("overworld");
        this.tileMap = tileMap;
    }

    public void run() {
        state = State.RUNNING;
        stepTimer = stepInterval;
        // Generate a list of tilemap states that progress toward the final generated tilemap
        List<TileMap> solutionStates = WaveFunctionCollapseAlgorithm.generate(currentTileSet, tileMap);
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

        // Update timer each frame
        stepTimer -= Gdx.graphics.getDeltaTime();

        // At every interval, advance to next state
        if (stepTimer <= 0) {
            currentStep = stepIterator.next();
            // Reset timer for next interval
            stepTimer = stepInterval;
        }
    }

    public void reset() {
        state = State.INACTIVE;
        stepTimer = stepInterval; // Reset timer
        stepIterator = null;
        currentStep = null;
        tileMap.clear();
    }
}
