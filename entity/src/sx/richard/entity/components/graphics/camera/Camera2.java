
package sx.richard.entity.components.graphics.camera;

import sx.richard.entity.Component;
import sx.richard.entity.ComponentAdapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

/** A 2D orthographic camera
 * @author Richard Taylor */
public class Camera2 extends ComponentAdapter<Camera2> {
	
	private final OrthographicCamera camera;
	
	/** @param width the viewport width
	 * @param height the viewport height */
	public void setViewport (float width, float height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
	}
	
	/** @return the viewport width */
	public float getWidth () {
		return camera.viewportWidth;
	}
	
	/** @return the viewport height */
	public float getHeight () {
		return camera.viewportHeight;
	}
	
	/** @param width the viewport width
	 * @param height the viewport height */
	public Camera2 (int width, int height) {
		camera = new OrthographicCamera(width, height);
	}
	
	/** Uses the graphics width/height */
	public Camera2 () {
		this(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	@Override
	public Component<Camera2> copy () {
		Camera2 component = new Camera2();
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void update (float delta) {
		camera
	}
}
