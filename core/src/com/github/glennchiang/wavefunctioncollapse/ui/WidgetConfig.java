package com.github.glennchiang.wavefunctioncollapse.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.github.glennchiang.wavefunctioncollapse.TileSet;
import com.github.glennchiang.wavefunctioncollapse.TileSetLoader;
import com.github.glennchiang.wavefunctioncollapse.VisualizationController;

import java.util.Map;

// Uses WidgetFactory to create widgets
// Positions widgets on the screen and registers event listeners for them
public class WidgetConfig {
    private final Table table;

    public WidgetConfig(TileSetLoader tileSetLoader, VisualizationController visualizer, int[][] gridDimensions) {
        WidgetFactory widgetFactory = WidgetFactory.getInstance();

        // Select box for tile sets
        Array<String> tileSets = new Array<>();

        for (Map.Entry<String, TileSet> entry: tileSetLoader.tileSets.entrySet()) {
            tileSets.add(entry.getKey());
        }
        SelectBox<String> tileSetSelectBox = widgetFactory.createSelectBox(tileSets);
        tileSetSelectBox.setSelected(visualizer.getTileSet().name);
        tileSetSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                visualizer.setTileSet(tileSetLoader.getTileSet(tileSetSelectBox.getSelected()));
            }
        });
        Label tileSetLabel = widgetFactory.createLabel("Tile set");

        // Select box for grid dimensions
        Array<String> gridOptions = new Array<>();
        for (int[] dimensions: gridDimensions) {
            String dimensionsLabel = dimensions[0] + " x " + dimensions[1];
            gridOptions.add(dimensionsLabel);
        }
        SelectBox<String> gridSelectBox = widgetFactory.createSelectBox(gridOptions);
        gridSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int[] selectedDimensions = gridDimensions[gridSelectBox.getSelectedIndex()];
                visualizer.tileMap.setDimensions(selectedDimensions[0], selectedDimensions[1]);
            }
        });
        Label gridLabel = widgetFactory.createLabel("Grid");

        // Select box for visualizer speed
        Array<String> speedOptions = new Array<>();
        for (int i = 0; i < visualizer.stepIntervals.length; i++) {
            String speedLabel = (i + 1) + "x";
            speedOptions.add(speedLabel);
        }
        SelectBox<String> speedSelectBox = widgetFactory.createSelectBox(speedOptions);
        speedSelectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                visualizer.setStepInterval(speedSelectBox.getSelectedIndex());
            }
        });
        Label speedLabel = widgetFactory.createLabel("Speed");

        // Run button
        Button runButton = widgetFactory.createButton("Run", Color.valueOf("#58D68D"));
        runButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                visualizer.run();
                // User cannot run visualizer or change grid dimensions when visualizer is already running
                runButton.setDisabled(true);
                gridSelectBox.setDisabled(true);
                tileSetSelectBox.setDisabled(true);
            }
        });

        // Reset button
        Button resetButton = widgetFactory.createButton("Reset", Color.RED);
        resetButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                visualizer.reset();
                runButton.setDisabled(false);
                gridSelectBox.setDisabled(false);
                tileSetSelectBox.setDisabled(false);
            }
        });

        // Table to act as container for the other widgets
        table = new Table();
//        table.setDebug(true);
        table.bottom().left().padBottom(8);

        table.add(tileSetLabel).height(32).expandX();
        table.add(tileSetSelectBox).height(32).expandX().fill();

        table.add(gridLabel).height(32).expandX();
        table.add(gridSelectBox).height(32).expandX().fill();

        table.add(speedLabel).height(32).expandX();
        table.add(speedSelectBox).height(32).expandX().fill();

        table.add(runButton).width(80).height(32).expandX().fill().right();
        table.add(resetButton).width(80).height(32).expandX().fill().right();

    }

    public Cell<Table> addToLayout(Table rootTable) {
        return rootTable.add(table);
    }
}
