package com.github.glennchiang.wavefunctioncollapse;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class TileMapDisplayer {
    private final int X_POS;
    private final int Y_POS;
    private final int WIDTH;
    private final int HEIGHT;
    private final TileMap tilemap;
    private final Rectangle[][] cells;
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch spriteBatch;

    public TileMapDisplayer(int x, int y, int width, int height, TileMap tilemap, ShapeRenderer renderer, SpriteBatch spriteBatch) {
        X_POS = x;
        Y_POS = y;
        WIDTH = width;
        HEIGHT = height;
        this.tilemap = tilemap;
        this.shapeRenderer = renderer;
        this.spriteBatch = spriteBatch;

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

    // Should be called every frame in main render loop
    public void render() {
        for (int i = 0; i < tilemap.ROWS; i++) {
            for (int j = 0; j < tilemap.COLS; j++) {
                Rectangle cell = cells[i][j];

                // Draw tile image
                Tile tile = tilemap.getTile(i, j);
                if (tile != null) {
                    spriteBatch.begin();
                    spriteBatch.draw(tile.image, cell.x, cell.y, cell.width, cell.height);
                    spriteBatch.end();
                }

                // Draw cell border
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(Color.WHITE);
                shapeRenderer.rect(cell.x, cell.y, cell.width, cell.height);
                shapeRenderer.end();

            }
        }
    }
}
