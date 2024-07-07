package com.github.glennchiang.wavefunctioncollapse;

import com.badlogic.gdx.Gdx;

import java.util.Iterator;
import java.util.List;

public class SolutionVisualizer {
    private final TileMapDisplayer tileMapDisplayer;
    private Iterator<TileMap> stepIterator;
    private TileMap currentStep = null;

    private boolean isRunning = false;
    private float stepInterval = 0.05f;
    private float stepTimer = stepInterval;

    public SolutionVisualizer(TileMapDisplayer tileMapDisplayer) {
        this.tileMapDisplayer = tileMapDisplayer;
    }

    public void setSolution(List<TileMap> solutionStates) {
        this.stepIterator = solutionStates.iterator();
        this.currentStep = stepIterator.next();
    }

    public void run() {
        isRunning = true;
        stepTimer = stepInterval; // Reset timer
    }

    // Called every frame in main render loop
    public void update() {
        if (!isRunning) return;

        tileMapDisplayer.render(currentStep);

        // Finished visualizing the generated solution
        if (!stepIterator.hasNext()) {
            return;
        }

        // Update timer each frame
        stepTimer -= Gdx.graphics.getDeltaTime();

        // At every interval, advance to next step
        if (stepTimer <= 0) {
            currentStep = stepIterator.next();
            // Reset timer for next interval
            stepTimer = stepInterval;
        }
    }
}
