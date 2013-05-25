
package sx.richard.entity.assets;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;

/** A raster animation, is built from a set of {@link Drawable}s
 * @author Richard Taylor */
public class RasterAnimation {
	
	private final Array<Drawable> drawables;
	
	{
		drawables = new Array<Drawable>();
	}
	
	/** @param index the index
	 * @return the {@link Drawable} at the given index */
	public Drawable get (int index) {
		return drawables.get(index);
	}
	
	/** @return the number of frames */
	public int getSize () {
		return drawables.size;
	}
	
	/** @param drawables the {@link Drawable}s */
	public void set (Array<Drawable> drawables) {
		this.drawables.clear();
		this.drawables.addAll(drawables);
	}
	
}
