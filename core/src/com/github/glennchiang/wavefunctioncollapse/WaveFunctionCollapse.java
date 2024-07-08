package com.github.glennchiang.wavefunctioncollapse;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.glennchiang.wavefunctioncollapse.ui.WidgetConfig;

public class WaveFunctionCollapse extends ApplicationAdapter {
	public final static int SCREEN_WIDTH = 800;
	public final static int SCREEN_HEIGHT = 800;

	private OrthographicCamera camera;
	private Viewport viewport;
	private ShapeRenderer shapeRenderer;
	private SpriteBatch spriteBatch;
	private Stage stage;

	private TileMap tileMap;
	private TileMapRenderer tileMapDisplay;
	private VisualizationController visualizer;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
		shapeRenderer = new ShapeRenderer();
		spriteBatch = new SpriteBatch();

		// Set up stage
		stage = new Stage(viewport);
		Gdx.input.setInputProcessor(stage);
		Table rootTable = new Table();
		rootTable.setFillParent(true);
		stage.addActor(rootTable);

		// Set up tile map with default dimensions 16 x 16
		int[][] tileMapDimensions = {{8, 8}, {16, 16}, {32, 32}, {64, 64}};
		tileMap = new TileMap(tileMapDimensions[0][0], tileMapDimensions[0][0]);

		// Set up tile map display
		int mapWidth = 640;
		int mapHeight = 640;
		tileMapDisplay = new TileMapRenderer((SCREEN_WIDTH - mapWidth) / 2, (SCREEN_HEIGHT - mapHeight) / 2,
				mapWidth, mapHeight, shapeRenderer, spriteBatch);

		// Load tile sets
		TileSetLoader tileSetLoader = new TileSetLoader();
		TileSet tileSet = tileSetLoader.getTileSet("overworld");

		visualizer = new VisualizationController(tileMap, tileMapDisplay);
		visualizer.setTileSet(tileSet);

		// Set up widgets
		WidgetConfig widgetConfig = new WidgetConfig(tileSetLoader, visualizer, tileMapDimensions);
		widgetConfig.addToLayout(rootTable).expand().top().width(mapWidth).height((SCREEN_HEIGHT - mapHeight) / 2);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		camera.update();

		visualizer.update();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

	}

	@Override
	public void resize(int width, int height) {
		shapeRenderer.setProjectionMatrix(camera.combined);
		spriteBatch.setProjectionMatrix(camera.combined);
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void dispose () {
		shapeRenderer.dispose();
		spriteBatch.dispose();
		stage.dispose();
	}
}
