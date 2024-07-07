package com.github.glennchiang.wavefunctioncollapse;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class TileMapDisplayer {
    private final Rectangle grid;
    private final Rectangle[][] cells;
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch spriteBatch;

    public TileMapDisplayer(int x, int y, int width, int height, int rows, int cols, ShapeRenderer renderer, SpriteBatch spriteBatch) {
        this.shapeRenderer = renderer;
        this.spriteBatch = spriteBatch;

        // Create grid rectangle
        grid = new Rectangle(x, y, width, height);

        // Create cell rectangles
        int cellWidth = width / cols;
        int cellHeight = height / rows;
        cells = new Rectangle[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Rectangle cell = new Rectangle(x + j * cellWidth, y + height - (i + 1) * cellHeight,
                        cellWidth, cellHeight);
                cells[i][j] = cell;
            }
        }
    }

    public void render(TileMap tileMap) {
        for (int i = 0; i < tileMap.ROWS; i++) {
            for (int j = 0; j < tileMap.COLS; j++) {
                Rectangle cell = cells[i][j];

                Tile tile = tileMap.getTile(i, j).collapsedTile();
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
