
package sx.richard.entity.editor.assets;

import sx.richard.entity.assets.AssetType;
import sx.richard.entity.editor.assets.previews.AssetPreview;
import sx.richard.entity.editor.assets.previews.AssetPreview.AssetPreviewListener;
import sx.richard.entity.editor.assets.previews.TextureAssetPreview;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

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
	private final Array<AssetPreview> previews = new Array<AssetPreview>();
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
		for (AssetPreview preview : previews) {
			preview.dispose();
		}
		previews.clear();
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
		int columns = 3;
		int idx = 0;
		for (FileHandle file : path.list()) {
			if (!file.isDirectory()) {
				if (type.matching(file)) {
					AssetPreview preview = createPreview(file);
					add(preview).expand().fill().uniform().space(5);
					previews.add(preview);
				}
			}
			idx = idx + 1;
			if (idx == columns) {
				idx = 0;
				row();
			}
		}
	}
	
}
