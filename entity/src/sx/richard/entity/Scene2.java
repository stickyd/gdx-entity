
package sx.richard.entity;

import sx.richard.entity.components.graphics.camera.Camera2;

import com.badlogic.gdx.graphics.GL20;

/** A basic two-dimension {@link Scene}, requires a {@link Camera2} to render a
 * {@link World}
 * @author Richard Taylor */
public class Scene2 implements Scene<Camera2> {
	
	private Camera2 camera;
	private World world;
	
	@Override
	public Camera2 getCamera () {
		return camera;
	}
	
	/** @return the {@link World} */
	public World getWorld () {
		return world;
	}
	
	// These warnings are fine here
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void render (Engine engine, GL20 gl) {
		Render render = engine.getRender();
		camera.update();
		render.spriteBatch.setProjectionMatrix(camera.getCombinedMatrix());
		render.shapes.setProjectionMatrix(camera.getCombinedMatrix());
		render.begin();
		for (Entity entity : world.getEntities()) {
			for (int i = 0, n = entity.getComponentCount(); i < n; i++) {
				Class componentClass = entity.get(i);
				Component component = entity.get(componentClass);
				component.render(gl, render);
			}
		}
		render.end();
	}
	
	@Override
	public void setCamera (Camera2 camera) {
		if (camera == null)
			throw new NullPointerException("Camera must not be null");
		this.camera = camera;
	}
	
	/** @param world the {@link World}, must not be <code>null</code> */
	public void setWorld (World world) {
		if (world == null)
			throw new NullPointerException("World must not be null");
		this.world = world;
	}
	
	// These warnings are fine here
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void update (Engine engine, float delta) {
		for (Entity entity : world.getEntities()) {
			for (int i = 0, n = entity.getComponentCount(); i < n; i++) {
				Class componentClass = entity.get(i);
				Component component = entity.get(componentClass);
				component.update(delta);
			}
		}
	}
	
}
