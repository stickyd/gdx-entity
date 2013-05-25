
package sx.richard.entity.components.graphics.camera;

import sx.richard.entity.Component;
import sx.richard.entity.ComponentAdapter;
import sx.richard.entity.components.Transform2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

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
	
	@Override
	public Class<?>[] getDependencies () {
		return new Class<?>[] { Transform2.class };
	}
	
	/** @return the viewport height */
	public float getHeight () {
		return camera.viewportHeight;
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
	public void update (float delta) {
		camera.update();
	}
}
