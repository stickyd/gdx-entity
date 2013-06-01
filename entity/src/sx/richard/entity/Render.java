
package sx.richard.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/** Contains objects required for rendering
 * @author Richard Taylor */
public class Render {
	
	/** The {@link ShapeRenderer} */
	public final ShapeRenderer shapes;
	/** The {@link SpriteBatch}, this should always be left in 'begin' mode */
	public final SpriteBatch spriteBatch;
	
	{
		shapes = new ShapeRenderer();
	}
	
	public Render (SpriteBatch spriteBatch) {
		this.spriteBatch = spriteBatch;
	}
	
	/** Begins the {@link Render} */
	public void begin () {
		spriteBatch.begin();
	}
	
	/** Ends the {@link Render} */
	public void end () {
		spriteBatch.end();
	}
	
}
