
package sx.richard.entity.components.graphics.camera;

import sx.richard.entity.Component;
import sx.richard.entity.ComponentAdapter;
import sx.richard.entity.components.Transform2;
import sx.richard.entity.components.editor.Debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;

/** A 2D orthographic camera, requires a {@link Transform2}
 * @author Richard Taylor */
public class Camera2 extends ComponentAdapter<Camera2> {
	
	private final OrthographicCamera camera;
	
	/** Uses the graphics width/height */
	public Camera2 () {
		this(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	/** @param width the viewport width
	 * @param height the viewport height */
	public Camera2 (int width, int height) {
		camera = new OrthographicCamera(width, height);
	}
	
	@Override
	public Component<Camera2> copy () {
		Camera2 component = new Camera2();
		component.camera.viewportWidth = camera.viewportWidth;
		component.camera.viewportHeight = camera.viewportHeight;
		return component;
	}
	
	/** @return the combined projection/view matrix */
	public Matrix4 getCombinedMatrix () {
		return camera.combined;
	}
	
	@Override
	public Class<?>[] getDependencies () {
		return new Class<?>[] { Transform2.class };
	}
	
	/** @return the viewport height */
	public float getHeight () {
		return camera.viewportHeight;
	}
	
	/** @return the cameras projection matrix */
	public Matrix4 getProjectionMatrix () {
		return camera.projection;
	}
	
	/** @return the view matrix */
	public Matrix4 getViewMatrix () {
		return camera.view;
	}
	
	/** @return the viewport width */
	public float getWidth () {
		return camera.viewportWidth;
	}
	
	/** @param width the viewport width
	 * @param height the viewport height */
	public void setViewport (float width, float height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
	}
	
	@Override
	public void started () {
		get(Debug.class).render = false;
	}
	
	/** Updates the camera matrices */
	public void update () {
		camera.update(true);
	}
	
	@Override
	public void update (float delta) {
		update();
	}
}
