
package sx.richard.entity.editor.window;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;

/** A window that is being displayed
 * @author Richard Taylor */
public interface Window {
	
	public static final Color FADE = new Color(0f, 0f, 0f, 0.5f);
	
	/** Invoked when the {@link Window} is created */
	public void create ();
	
	/** Invoked when the screen is destroyed */
	public void destroy ();
	
	/** @return the {@link InputProcessor} */
	public InputProcessor getInputProcessor ();
	
	/** Renders the frame */
	public void render ();
	
	/** @param width the width
	 * @param height the height */
	public void resize (int width, int height);
	
	/** Invoked each update
	 * @param delta the time since the last update */
	public void update (float delta);
	
}
