package com.github.glennchiang.wavefunctioncollapse;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

import java.awt.*;

public class GridDisplay {
    private final int X_POS;
    private final int Y_POS;
    private final int WIDTH;
    private final int HEIGHT;
    private final Grid grid;
    private final Rectangle[][] cells;
    private final ShapeRenderer renderer;

    public GridDisplay(int x, int y, int width, int height, Grid grid, ShapeRenderer renderer) {
        X_POS = x;
        Y_POS = y;
        WIDTH = width;
        HEIGHT = height;
        this.grid = grid;
        this.renderer = renderer;

        int cellWidth = width / grid.COLS;
        int cellHeight = height / grid.ROWS;

        cells = new Rectangle[grid.ROWS][grid.COLS];
        for (int i = 0; i < grid.ROWS; i++) {
            for (int j = 0; j < grid.COLS; j++) {
                Rectangle cell = new Rectangle(x + j * cellWidth, y + HEIGHT - (i + 1) * cellHeight,
                        cellWidth, cellHeight);
                cells[i][j] = cell;
            }
        }
    }

    public void render() {
        for (int i = 0; i < grid.ROWS; i++) {
            for (int j = 0; j < grid.COLS; j++) {
                Rectangle cell = cells[i][j];

                renderer.begin(ShapeRenderer.ShapeType.Line);
                renderer.setColor(Color.WHITE);
                renderer.rect(cell.x, cell.y, cell.width, cell.height);
                renderer.end();
            }
        }
    }
}
