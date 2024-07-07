package com.github.glennchiang.wavefunctioncollapse;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class TileMapDisplayer {
    private final TileMap tilemap;
    private final Rectangle grid;
    private final Rectangle[][] cells;
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch spriteBatch;

    public TileMapDisplayer(int x, int y, int width, int height, TileMap tilemap, ShapeRenderer renderer, SpriteBatch spriteBatch) {
        this.tilemap = tilemap;
        this.shapeRenderer = renderer;
        this.spriteBatch = spriteBatch;

        // Create grid rectangle
        grid = new Rectangle(x, y, width, height);

        // Create cell rectangles
        int cellWidth = width / tilemap.COLS;
        int cellHeight = height / tilemap.ROWS;
        cells = new Rectangle[tilemap.ROWS][tilemap.COLS];
        for (int i = 0; i < tilemap.ROWS; i++) {
            for (int j = 0; j < tilemap.COLS; j++) {
                Rectangle cell = new Rectangle(x + j * cellWidth, y + height - (i + 1) * cellHeight,
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

                Tile tile = tilemap.getTile(i, j).collapsedTile();
                // Draw cell outline if tile has not collapsed
                if (tile == null) {
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    shapeRenderer.setColor(Color.WHITE);
                    shapeRenderer.rect(cell.x, cell.y, cell.width, cell.height);
                    shapeRenderer.end();
                // Draw tile image if tile has collapsed
                } else {
                    spriteBatch.begin();
                    spriteBatch.draw(tile.image, cell.x, cell.y, cell.width, cell.height);
                    spriteBatch.end();
                }
            }
        }

        // Draw grid outline
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(grid.x, grid.y, grid.width, grid.height);
        shapeRenderer.end();
    }
}
