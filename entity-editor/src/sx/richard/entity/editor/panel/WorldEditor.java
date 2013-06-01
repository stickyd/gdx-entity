
package sx.richard.entity.editor.panel;

import sx.richard.entity.Engine;
import sx.richard.entity.Entity;
import sx.richard.entity.Render;
import sx.richard.entity.World;
import sx.richard.entity.components.RenderLayer;
import sx.richard.entity.editor.components.DebugGrid;
import sx.richard.entity.util.RenderUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;

/** Renders the {@link Engine} in debug mode, allows manipulation etc.
 * @author Richard Taylor */
public class WorldEditor {
	
	private final SpriteBatch batch;
	private final OrthographicCamera camera;
	private FrameBuffer frameBuffer;
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
	public WorldEditor (World world) {
		this.world = world;
		addDebugEntities();
	}
	
	/** Renders the panel
	 * @return the {@link Texture} once rendered */
	public Texture render () {
		if (frameBuffer == null) {
			if (width <= 0 || height <= 0)
				throw new RuntimeException("EditorPanel width/height must not be zero");
			frameBuffer = new FrameBuffer(Format.RGBA8888, width, height, false);
		}
		frameBuffer.begin();
		Gdx.gl20.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		render.spriteBatch.setProjectionMatrix(camera.combined);
		render.shapes.setProjectionMatrix(camera.combined);
		render.begin();
		RenderUtils.sortRenderGroup(world);
		Engine.debug = true;
		RenderUtils.renderGroup(world, Gdx.gl20, render, transform);
		Engine.debug = false;
		render.end();
		frameBuffer.end();
		return frameBuffer.getColorBufferTexture();
	}
	
	/** @param width the width
	 * @param height the height */
	public void setSize (int width, int height) {
		this.width = width;
		this.height = height;
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
		if (frameBuffer != null) {
			frameBuffer.dispose();
			frameBuffer = null;
		}
	}
	
	private void addDebugEntities () {
		Entity grid = new Entity("_grid");
		grid.add(new DebugGrid());
		grid.get(RenderLayer.class).setLayer(Integer.MAX_VALUE);
		world.add(grid);
	}
}
