package com.github.glennchiang.wavefunctioncollapse;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

public class TileMapRenderer {
    private final Rectangle grid;
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch spriteBatch;
    private final BitmapFont font = new BitmapFont();

    public TileMapRenderer(int x, int y, int width, int height, ShapeRenderer renderer, SpriteBatch spriteBatch) {
        this.shapeRenderer = renderer;
        this.spriteBatch = spriteBatch;

        // Create rectangle for grid frame
        grid = new Rectangle(x, y, width, height);
    }

    // Create cell rectangles based on the given tileMap's dimensions
    public Rectangle[][] createCellRectangles(TileMap tileMap) {
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
        Rectangle[][] cellRectangles = createCellRectangles(tileMap);

        for (int i = 0; i < tileMap.getRows(); i++) {
            for (int j = 0; j < tileMap.getCols(); j++) {
                Rectangle cellRect = cellRectangles[i][j];

                Cell cell = tileMap.getCell(i, j);

                if (cell != null && cell.tile() != null && cell.collapsed()) {
                    spriteBatch.begin();
                    Tile tile = cell.tile();
                    spriteBatch.draw(tile.image, cellRect.x, cellRect.y,
                            cellRect.width / 2f, cellRect.height / 2f,
                            cellRect.width, cellRect.height, 1, 1, tile.rotation);
                    spriteBatch.end();
                // Draw cell outline if there is no tile at this position or the tile has not collapsed
                } else {
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    shapeRenderer.setColor(Color.WHITE);
                    shapeRenderer.rect(cellRect.x, cellRect.y, cellRect.width, cellRect.height);
                    shapeRenderer.end();

                    // Show the entropy of the cell
                    if (cell != null) {
                        GlyphLayout glyphLayout = new GlyphLayout();
                        String entropyText = String.valueOf(cell.getEntropy());
                        glyphLayout.setText(font, entropyText);

                        spriteBatch.begin();
                        font.draw(spriteBatch, glyphLayout,
                                cellRect.x + cellRect.width / 2f - glyphLayout.width / 2,
                                cellRect.y + cellRect.height / 2f + glyphLayout.height / 2);
                        spriteBatch.end();
                    }
                }
//                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//                shapeRenderer.setColor(Color.WHITE);
//                shapeRenderer.rect(cellRect.x, cellRect.y, cellRect.width, cellRect.height);
//                shapeRenderer.end();
            }
        }

        // Draw grid outline
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(grid.x, grid.y, grid.width, grid.height);
        shapeRenderer.end();
    }
}
