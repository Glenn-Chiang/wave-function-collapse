package com.github.glennchiang.wavefunctioncollapse;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class WaveFunctionCollapse extends ApplicationAdapter {
	public final static int SCREEN_WIDTH = 800;
	public final static int SCREEN_HEIGHT = 640;

	private OrthographicCamera camera;
	private Viewport viewport;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch spriteBatch;

	private TileMap tileMap;
	private TileMapDisplayer tileMapDisplayer;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
		shapeRenderer = new ShapeRenderer();
		spriteBatch = new SpriteBatch();

		int mapRows = 8;
		int mapCols = 8;
		tileMap = new TileMap(mapRows, mapCols);

		int mapWidth = 600;
		int mapHeight = 600;
		tileMapDisplayer = new TileMapDisplayer((SCREEN_WIDTH - mapWidth) / 2, (SCREEN_HEIGHT - mapHeight) / 2,
				mapWidth, mapHeight, tileMap, shapeRenderer, spriteBatch);

		TileSetLoader tileSetLoader = new TileSetLoader();
		TileSet activeTileSet = tileSetLoader.getTileSet("overworld");
		tileMap.setTileSet(activeTileSet);
		tileMap.randomize();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		tileMapDisplayer.render();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void dispose () {
		shapeRenderer.dispose();
		spriteBatch.dispose();
	}
}
