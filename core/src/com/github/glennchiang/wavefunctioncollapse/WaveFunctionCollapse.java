package com.github.glennchiang.wavefunctioncollapse;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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

	private Grid grid;
	private GridDisplay gridDisplay;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
		shapeRenderer = new ShapeRenderer();

		int gridRows = 8;
		int gridCols = 8;
		grid = new Grid(gridRows, gridCols);

		int gridWidth = 600;
		int gridHeight = 600;
		gridDisplay = new GridDisplay((SCREEN_WIDTH - gridWidth) / 2, (SCREEN_HEIGHT - gridHeight) / 2,
				gridWidth, gridHeight, grid, shapeRenderer);

	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		gridDisplay.render();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void dispose () {
		shapeRenderer.dispose();
	}
}
