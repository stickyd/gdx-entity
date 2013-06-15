
package sx.richard.entity.editor.panel;

import sx.richard.entity.Engine;
import sx.richard.entity.Entity;
import sx.richard.entity.Render;
import sx.richard.entity.World;
import sx.richard.entity.components.RenderLayer;
import sx.richard.entity.editor.components.DebugGrid;
import sx.richard.entity.util.EntityUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Actor;

/** Renders the {@link World} in a window
 * @author Richard Taylor */
public abstract class WorldPanel extends Actor {
	
	protected final OrthographicCamera camera;
	private final SpriteBatch batch;
	private final boolean debug;
	private FrameBuffer frameBuffer;
	private TextureRegion region;
	private final Render render;
	private final Matrix4 transform;
	private int width, height;
	private final World world;
	
	{
		batch = new SpriteBatch();
		render = new Render(batch);
		camera = new OrthographicCamera();
		transform = new Matrix4();
	}
	
	/** @param engine the {@link Engine} */
	public WorldPanel (World world, boolean debug) {
		this.world = world;
		this.debug = debug;
		if (debug) {
			addDebugEntities();
			addInput();
		} else {
			addInput();
		}
	}
	
	public abstract void addInput ();
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
		int width = Math.round(getWidth());
		int height = Math.round(getHeight());
		if (width != this.width || height != this.height || frameBuffer == null) {
			if (frameBuffer != null) {
				frameBuffer.dispose();
				frameBuffer = null;
			}
			if (width <= 0 || height <= 0)
				return;
			this.width = width;
			this.height = height;
			frameBuffer = new FrameBuffer(Format.RGBA8888, width, height, false);
			region = new TextureRegion(frameBuffer.getColorBufferTexture());
			region.flip(false, true);
		}
		batch.end();
		frameBuffer.begin();
		Gdx.gl20.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
		render.spriteBatch.setProjectionMatrix(camera.combined);
		render.shapes.setProjectionMatrix(camera.combined);
		render.begin();
		EntityUtils.sortRenderGroup(world);
		if (debug) {
			Engine.debug = true;
			EntityUtils.renderGroup(world, Gdx.gl20, render, transform);
			Engine.debug = false;
		} else {
			EntityUtils.renderGroup(world, Gdx.gl20, render, transform);
		}
		render.end();
		frameBuffer.end();
		batch.begin();
		batch.draw(region, getX(), getY(), width, height);
		batch.end();
		render.shapes.setTransformMatrix(batch.getTransformMatrix());
		render.shapes.setProjectionMatrix(batch.getProjectionMatrix());
		render.shapes.begin(ShapeType.Rectangle);
		render.shapes.setColor(0.6f, 0.6f, 0.6f, 1f);
		render.shapes.rect(getX(), getY(), width, height);
		render.shapes.end();
		batch.begin();
	}
	
	private void addDebugEntities () {
		if (!world.has("_grid")) {
			Entity grid = new Entity("_grid");
			grid.add(new DebugGrid());
			grid.get(RenderLayer.class).setLayer(Integer.MAX_VALUE);
			world.add(grid);
		}
	}
}
