
package sx.richard.entity.editor.assets;

import sx.richard.entity.assets.AssetType;
import sx.richard.entity.editor.assets.previews.AssetPreview;
import sx.richard.entity.editor.assets.previews.AssetPreview.AssetPreviewListener;
import sx.richard.entity.editor.assets.previews.TextureAssetPreview;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;

/** Lists the assets for selection, callback with {@link AssetListListener}
 * @author Richard Taylor */
public class AssetGrid extends Table implements Disposable, AssetPreviewListener {
	
	/** Listens in for events from {@link AssetGrid}
	 * @author Richard Taylor */
	public interface AssetListListener {
		
		/** Invoked when an item is selected
		 * @param fileHandle the {@link FileHandle}, <code>null</code> if nothing
		 *           is selected */
		public void selected (FileHandle fileHandle);
		
	}
	
	private final AssetListListener listener;
	private FileHandle path;
	private final ObjectMap<String, AssetPreview> previews = new ObjectMap<String, AssetPreview>();
	private AssetPreview selected;
	private final AssetType type;
	
	/** @param type the {@link AssetType} to look for
	 * @param listener the {@link AssetListListener} */
	public AssetGrid (AssetType type, AssetListListener listener) {
		this.type = type;
		this.listener = listener;
	}
	
	@Override
	public void dispose () {
		for (AssetPreview preview : previews.values()) {
			preview.dispose();
		}
		previews.clear();
	}
	
	/** @return the current path */
	public FileHandle getPath () {
		return path;
	}
	
	/** Selects a particular file, if it exists in the current list
	 * @param file the {@link FileHandle} to the file */
	public void select (FileHandle file) {
		AssetPreview preview = previews.get(file.path());
		if (preview != null) {
			preview.select(true);
		}
	}
	
	@Override
	public void selected (AssetPreview assetPreview, FileHandle fileHandle) {
		if (selected != null) {
			selected.select(false);
			selected = null;
		}
		selected = assetPreview;
		listener.selected(fileHandle);
	}
	
	/** Sets the path, unselecting the current item
	 * @param path the {@link FileHandle} to the directory */
	public void setPath (FileHandle path) {
		this.path = path;
		update();
	}
	
	@Override
	public void unselected (AssetPreview assetPreview, FileHandle fileHandle) {
		if (selected == assetPreview) {
			listener.selected(null);
			selected = null;
		}
	}
	
	private AssetPreview createPreview (FileHandle file) {
		switch (type) {
			case FONT:
			case TEXTURE:
				return new TextureAssetPreview(this, file);
			default:
				throw new RuntimeException("Unknown type");
		}
	}
	
	private void update () {
		clear();
		defaults().expandX().fill().uniform().height(150).space(5);
		int columns = 3;
		int idx = 0;
		int count = 0;
		for (FileHandle file : path.list()) {
			if (!file.isDirectory()) {
				if (type.matching(file)) {
					AssetPreview preview = createPreview(file);
					add(preview);
					previews.put(file.path(), preview);
				}
			}
			idx = idx + 1;
			if (idx == columns) {
				idx = 0;
				row();
			}
			count++;
		}
		for (int i = count; i < columns; i++) {
			add();
		}
	}
	
}
