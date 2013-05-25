
package sx.richard.entity;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;

/** A {@link Scene} can update, and render. It may contain any behavior inside
 * that, nothing is expected. Parameterized by the type of component that should
 * be required for the render camera.
 * @author Richard Taylor */
public interface Scene<T extends Component<?>> {
	
	/** @return the active camera */
	public T getCamera ();
	
	/** Renders the scene */
	public void render (Engine engine, GL20 gl);
	
	/** Sets the active
	 * @param camera the {@link Camera} */
	public void setCamera (T camera);
	
	/** Updates the scene
	 * @param engine the {@link Engine}
	 * @param delta the time since the last update */
	public void update (Engine engine, float delta);
	
}
