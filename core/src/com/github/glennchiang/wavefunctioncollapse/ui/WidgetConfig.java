package com.github.glennchiang.wavefunctioncollapse.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.github.glennchiang.wavefunctioncollapse.VisualizationController;

// Uses WidgetFactory to create widgets
// Positions widgets on the screen and registers event listeners for them
public class WidgetConfig {
    private final Table table;

    public WidgetConfig(VisualizationController visualizer) {
        WidgetFactory widgetFactory = WidgetFactory.getInstance();

        // Run button
        Button runButton = widgetFactory.createButton("Run", Color.valueOf("#03A9F4"));
        runButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                visualizer.run();
                runButton.setDisabled(true);
                // TODO: Re-enable once complete
            }
        });

        // Reset button
        Button resetButton = widgetFactory.createButton("Reset", Color.RED);
        resetButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                visualizer.reset();
                runButton.setDisabled(false);
            }
        });

        // Table to act as container for the other widgets
        table = new Table();
        table.bottom().left().padBottom(8);
        table.add(runButton).width(80).height(32).spaceRight(8);
        table.add(resetButton).width(80).height(32);
    }

    public Cell<Table> addToLayout(Table rootTable) {
        return rootTable.add(table);
    }
}
