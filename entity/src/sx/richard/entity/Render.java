
package sx.richard.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** Contains objects required for rendering
 * @author Richard Taylor */
public class Render {
	
	/** The {@link SpriteBatch}, this should always be left in 'begin' mode */
	public final SpriteBatch spriteBatch;
	
	Render (SpriteBatch spriteBatch) {
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
