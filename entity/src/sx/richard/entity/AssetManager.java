
package sx.richard.entity;

import sx.richard.entity.assets.Asset;

/** Manages the assets
 * @author Richard Taylor */
public class AssetManager {
	
	private final com.badlogic.gdx.assets.AssetManager assetManager;
	
	{
		assetManager = new com.badlogic.gdx.assets.AssetManager();
	}
	
	/** Finishes the loading, blocks up this thread until complete */
	public void finishLoading () {
		assetManager.finishLoading();
	}
	
	/** Loads an asset, and waits until it's ready
	 * @param asset the {@link Asset} */
	public <T> T forceLoad (Asset<?> asset) {
		return forceLoad(asset.path, asset.type);
	}
	
	/** Loads an asset, and waits until it's loaded to continue
	 * @param path the path to the asset
	 * @param type the type of asset
	 * @return the asset, fails if it doesn't get loaded */
	public <T> T forceLoad (String path, Class<?> type) {
		assetManager.load(path, type);
		if (!assetManager.isLoaded(path)) {
			assetManager.finishLoading();
			if (!assetManager.isLoaded(path))
				throw new RuntimeException("Forced load, but it's still not there path=" + path);
		}
		return get(path);
	}
	
	/** @param asset the asset -- this may be <code>null</code>, it will be ignored
	 * @return the asset that is loaded, <code>null</code> if it's not loaded */
	// This warning is okay
	@SuppressWarnings("unchecked")
	public <T> T get (Asset<?> asset) {
		T object = null;
		if (assetManager.isLoaded(asset.path)) {
			object = (T)assetManager.get(asset.path);
		}
		return object;
	}
	
	/** @param path the path to the asset
	 * @param type the asset loaded to this path, of the given type; may be
	 *           <code>null</code> if not loaded yet */
	// Warning is okay, we want to fail if this is wrong
	@SuppressWarnings("unchecked")
	public <T> T get (String path) {
		T object = null;
		if (assetManager.isLoaded(path)) {
			object = (T)assetManager.get(path);
		}
		return object;
	}
	
	/** Loads an {@link Asset}
	 * @param asset the {@link Asset} -- this may be <code>null</code>, it will
	 *           be ignored */
	public void load (Asset<?> asset) {
		if (asset != null) {
			load(asset.path, asset.type);
		}
	}
	
	/** Sends an asset to be loaded, this may or not already be loaded
	 * @param path the path to the asset
	 * @param type the type */
	public void load (String path, Class<?> type) {
		assetManager.load(path, type);
	}
	
	/** Unloads an asset
	 * @param asset the {@link Asset} -- this may be <code>null</code>, it will
	 *           be ignored */
	public void unload (Asset<?> asset) {
		if (asset != null) {
			unload(asset.path);
		}
	}
	
	/** Unlods an asset
	 * @param path the path to the asset */
	public void unload (String path) {
		assetManager.unload(path);
	}
	
	/** Updates the asset loading queue */
	public void update () {
		assetManager.update();
	}
	
	/** Updates the asset loading queue, stops after a given time
	 * @param time the maximum time to block for, in milliseconds */
	public void update (int time) {
		assetManager.update(time);
	}
	
}
