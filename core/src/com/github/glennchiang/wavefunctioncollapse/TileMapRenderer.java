package com.github.glennchiang.wavefunctioncollapse;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class TileMapRenderer {
    private final Rectangle grid;
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch spriteBatch;

    public TileMapRenderer(int x, int y, int width, int height, ShapeRenderer renderer, SpriteBatch spriteBatch) {
        this.shapeRenderer = renderer;
        this.spriteBatch = spriteBatch;

        // Create rectangle for grid frame
        grid = new Rectangle(x, y, width, height);
    }

    // Create cells based on the given tileMap's dimensions
    public Rectangle[][] createCells(TileMap tileMap) {
        Rectangle[][] cells = new Rectangle[tileMap.getRows()][tileMap.getCols()];
        int cellWidth = grid.width / tileMap.getCols();
        int cellHeight = grid.height / tileMap.getRows();
        for (int i = 0; i < tileMap.getRows(); i++) {
            for (int j = 0; j < tileMap.getCols(); j++) {
                Rectangle cell = new Rectangle(grid.x + j * cellWidth, grid.y + grid.height - (i + 1) * cellHeight,
                        cellWidth, cellHeight);
                cells[i][j] = cell;
            }
        }
        return cells;
    }

    public void render(TileMap tileMap) {
        Rectangle[][] cells = createCells(tileMap);

        for (int i = 0; i < tileMap.getRows(); i++) {
            for (int j = 0; j < tileMap.getCols(); j++) {
                Rectangle cell = cells[i][j];

                SuperTile tile = tileMap.getTile(i, j);
                // Draw cell outline if there is no tile at this position or the tile has not collapsed
                if (tile == null || !tile.collapsed()) {
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    shapeRenderer.setColor(Color.WHITE);
                    shapeRenderer.rect(cell.x, cell.y, cell.width, cell.height);
                    shapeRenderer.end();
                // Draw tile image if tile has collapsed
                } else {
                    spriteBatch.begin();
                    spriteBatch.draw(tile.collapsedTile().image, cell.x, cell.y, cell.width, cell.height);
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
