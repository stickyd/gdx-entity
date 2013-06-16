
package sx.richard.entity.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/** Describes an asset, this is immutable
 * @author Richard Taylor */
public final class Asset<T extends Object> {
	
	/** Default assets
	 * @author Richard Taylor */
	public static final class Defaults {
		
		/** The debug {@link BitmapFont} font, at "debug.fnt" */
		public static final Asset<BitmapFont> DEBUG_FONT = new Asset<BitmapFont>("debug.fnt", BitmapFont.class);
		
	}
	
	/** The path to the asset */
	public final String path;
	/** The type of asset */
	public final Class<T> type;
	
	/** @param path the path, must not be <code>null</code>
	 * @param type the type, must not be <code>null</code> */
	public Asset (String path, Class<T> type) {
		if (path == null)
			throw new NullPointerException("Path must not be null");
		if (type == null)
			throw new NullPointerException("Path must not be null");
		this.path = path;
		this.type = type;
	}
	
	@Override
	public boolean equals (Object object) {
		if (object == null)
			return false;
		Asset<?> asset = (Asset<?>)object;
		return asset.path.equals(path);
	}
	
	/** @return the {@link FileHandle} for this path */
	public FileHandle file () {
		return Gdx.files.internal(path);
	}
	
	@Override
	public String toString () {
		return "[Asset type=" + type.getSimpleName() + " path=" + path + "]";
	}
}
