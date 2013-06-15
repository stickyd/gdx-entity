
package sx.richard.entity.editor.assets.previews;

import sx.richard.entity.editor.Assets;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;

public abstract class AssetPreview extends Table implements Disposable {
	
	/** The listener for selecting asset previews
	 * @author Richard Taylor */
	public interface AssetPreviewListener {
		
		/** Invoked when a preview is selected
		 * @param assetPreview the preview
		 * @param fileHandle the {@link FileHandle} */
		public void selected (AssetPreview assetPreview, FileHandle fileHandle);
		
		/** Invoked when a preview is unselected
		 * @param assetPreview the preview
		 * @param fileHandle the {@link FileHandle} */
		public void unselected (AssetPreview assetPreview, FileHandle fileHandle);
		
	}
	
	protected final FileHandle fileHandle;
	protected final AssetPreviewListener listener;
	protected boolean selected;
	
	public AssetPreview (final AssetPreviewListener listener, final FileHandle fileHandle) {
		this.listener = listener;
		this.fileHandle = fileHandle;
		setBackground(Assets.skin.newDrawable("white", new Color(0.2f, 0.2f, 0.2f, 1f)));
		add(createPreview()).expand();
		row();
		add(new Label(fileHandle.name(), Assets.skin));
		setTouchable(Touchable.enabled);
		addListener(new ClickListener() {
			
			@Override
			public void clicked (InputEvent e, float x, float y) {
				select(!selected);
			}
		});
	}
	
	@Override
	public float getMinHeight () {
		return 150;
	}
	
	/** Selects or unselects this cell, invokes the {@link AssetPreviewListener}
	 * @param select whether this is selected */
	public void select (boolean select) {
		if (select) {
			setBackground(Assets.skin.newDrawable("white", new Color(0.3f, 0.2f, 0.2f, 1f)));
			listener.selected(AssetPreview.this, fileHandle);
		} else {
			setBackground(Assets.skin.newDrawable("white", new Color(0.2f, 0.2f, 0.2f, 1f)));
			listener.unselected(AssetPreview.this, fileHandle);
		}
		selected = select;
	}
	
	/** @return the {@link Actor} for the preview cell */
	protected abstract Actor createPreview ();
	
}