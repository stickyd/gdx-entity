
package sx.richard.entity;

/** Describes an asset, this is immutable
 * @author Richard Taylor */
public final class Asset {
	
	/** The path to the asset */
	public final String path;
	/** The type of asset */
	public final Class<?> type;
	
	/** @param path the path, must not be <code>null</code>
	 * @param type the type, must not be <code>null</code> */
	public Asset (String path, Class<?> type) {
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
		Asset asset = (Asset)object;
		return asset.path.equals(path);
	}
	
	@Override
	public String toString () {
		return "[Asset type=" + type.getSimpleName() + " path=" + path + "]";
	}
}
