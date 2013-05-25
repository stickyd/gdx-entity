
package sx.richard.entity;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/** Contains all the {@link Asset}s associated with the game; {@link Asset}s are
 * only ever added once
 * @author Richard Taylor */
public final class AssetCollection {
	
	private final ObjectMap<String, Asset> assets;
	
	{
		assets = new ObjectMap<String, Asset>();
	}
	
	/** Adds an asset to the collection
	 * @param asset */
	public void add (Asset asset) {
		String path = asset.path;
		Asset existingAsset = assets.get(path);
		if (existingAsset != null) {
			if (existingAsset.type != asset.type)
				throw new IllegalStateException("Two assets share the same path, but not matching types. path=" + path + " existing="
					+ existingAsset.type + " new=" + asset.type);
		} else {
			assets.put(path, asset);
		}
	}
	
	/** Finds all the assets of a specific type
	 * @param type the type
	 * @return an array containing all the assets of that type, never
	 *         <code>null</code> */
	public Array<Asset> getAssets (Class<?> type) {
		Array<Asset> assets = new Array<Asset>();
		for (Asset asset : this.assets.values()) {
			if (asset.type == type) {
				assets.add(asset);
			}
		}
		return assets;
	}
	
	/** @param path the path to the asset
	 * @return the type of the asset, <code>null</code> if it doesn't exist */
	public Class<?> getType (String path) {
		Class<?> type = null;
		Asset asset = assets.get(path);
		if (asset != null) {
			type = asset.type;
		}
		return type;
	}
}
