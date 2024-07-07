package com.github.glennchiang.wavefunctioncollapse.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

// Uses WidgetFactory to create widgets
// Positions widgets on the screen and registers event listeners for them
public class WidgetConfig {
    private final Table table;

    public WidgetConfig() {
        WidgetFactory widgetFactory = WidgetFactory.getInstance();

        // Run button
        Button runButton = widgetFactory.createButton("Run", Color.valueOf("#03A9F4"));
        runButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO
            }
        });

        // Reset button
        Button resetButton = widgetFactory.createButton("Reset", Color.RED);
        resetButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO
            }
        });

        // Table to act as container for the other widgets
        table = new Table();
        table.bottom().left().padBottom(8);
        table.add(runButton).width(80).height(25).spaceRight(8);
        table.add(resetButton).width(80).height(25);
    }

    public Cell<Table> addToLayout(Table rootTable) {
        return rootTable.add(table);
    }
}
