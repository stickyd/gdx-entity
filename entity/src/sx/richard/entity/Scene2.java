
package sx.richard.entity;

import sx.richard.entity.components.Transform2;
import sx.richard.entity.components.editor.Debug;
import sx.richard.entity.components.editor.Selected;
import sx.richard.entity.components.graphics.camera.Camera2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;

/** A basic two-dimension {@link Scene}, requires a {@link Camera2} to render a
 * {@link World}
 * @author Richard Taylor */
public class Scene2 implements Scene<Camera2> {
	
	private static final float DEBUG_SIZE = 10;
	private Camera2 camera;
	
	private final Matrix4 shapeTransform = new Matrix4();
	
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
		Matrix4 transform = render.spriteBatch.getTransformMatrix();
		for (Entity entity : world.getEntities()) {
			for (int i = 0, n = entity.getComponentCount(); i < n; i++) {
				Class componentClass = entity.get(i);
				Component component = entity.get(componentClass);
				component.transform(gl, render, transform);
				render.spriteBatch.setTransformMatrix(transform);
			}
			for (int i = 0, n = entity.getComponentCount(); i < n; i++) {
				Class componentClass = entity.get(i);
				Component component = entity.get(componentClass);
				component.render(gl, render);
			}
			for (int i = entity.getComponentCount() - 1; i >= 0; i--) {
				Class componentClass = entity.get(i);
				Component component = entity.get(componentClass);
				component.untransform(gl, render, transform);
				render.spriteBatch.setTransformMatrix(transform);
			}
		}
		render.end();
	}
	
	@Override
	public void renderDebug (Engine engine, GL20 gl) {
		Render render = engine.getRender();
		ShapeRenderer shapes = render.shapes;
		shapeTransform.idt();
		shapes.begin(ShapeType.Line);
		for (Entity entity : world.getEntities()) {
			Transform2 transform = entity.get(Transform2.class);
			Debug debug = entity.get(Debug.class);
			if (transform != null && debug.render) {
				transform.transform(gl, render, shapeTransform);
				shapes.setTransformMatrix(shapeTransform);
				Selected selected = entity.get(Selected.class);
				shapes.setColor(selected != null && selected.on ? Color.RED : Color.YELLOW);
				float rotation = transform.getRotation();
				shapes.rotate(0, 0, 1, rotation);
				shapes.line(-DEBUG_SIZE, -DEBUG_SIZE, +DEBUG_SIZE, -DEBUG_SIZE);
				shapes.line(+DEBUG_SIZE, -DEBUG_SIZE, +DEBUG_SIZE, +DEBUG_SIZE);
				shapes.line(+DEBUG_SIZE, +DEBUG_SIZE, -DEBUG_SIZE, +DEBUG_SIZE);
				shapes.line(-DEBUG_SIZE, +DEBUG_SIZE, -DEBUG_SIZE, -DEBUG_SIZE);
				shapes.line(0, -DEBUG_SIZE, 0, DEBUG_SIZE);
				shapes.line(-DEBUG_SIZE, 0, 0, DEBUG_SIZE);
				shapes.line(DEBUG_SIZE, 0, 0, DEBUG_SIZE);
				shapes.rotate(0, 0, 1, -rotation);
				transform.untransform(gl, render, shapeTransform);
				shapes.setTransformMatrix(shapeTransform);
			}
		}
		shapes.end();
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
