package com.github.glennchiang.wavefunctioncollapse;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class MapDisplay {
    private final int X_POS;
    private final int Y_POS;
    private final int WIDTH;
    private final int HEIGHT;
    private final TileMap tilemap;
    private final Rectangle[][] cells;
    private final ShapeRenderer renderer;

    public MapDisplay(int x, int y, int width, int height, TileMap tilemap, ShapeRenderer renderer) {
        X_POS = x;
        Y_POS = y;
        WIDTH = width;
        HEIGHT = height;
        this.tilemap = tilemap;
        this.renderer = renderer;

        int cellWidth = width / tilemap.COLS;
        int cellHeight = height / tilemap.ROWS;

        cells = new Rectangle[tilemap.ROWS][tilemap.COLS];
        for (int i = 0; i < tilemap.ROWS; i++) {
            for (int j = 0; j < tilemap.COLS; j++) {
                Rectangle cell = new Rectangle(x + j * cellWidth, y + HEIGHT - (i + 1) * cellHeight,
                        cellWidth, cellHeight);
                cells[i][j] = cell;
            }
        }
    }

    public void render() {
        for (int i = 0; i < tilemap.ROWS; i++) {
            for (int j = 0; j < tilemap.COLS; j++) {
                Rectangle cell = cells[i][j];

                renderer.begin(ShapeRenderer.ShapeType.Line);
                renderer.setColor(Color.WHITE);
                renderer.rect(cell.x, cell.y, cell.width, cell.height);
                renderer.end();
            }
        }
    }
}
