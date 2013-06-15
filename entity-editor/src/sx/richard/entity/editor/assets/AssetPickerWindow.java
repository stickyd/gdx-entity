
package sx.richard.entity.editor.assets;

import sx.richard.entity.editor.Assets;
import sx.richard.entity.editor.Editor;
import sx.richard.entity.editor.assets.AssetGrid.AssetListListener;
import sx.richard.entity.editor.window.StageWindow;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/** Displays the assets, with a callback to what is selected
 * @author Richard Taylor */
public class AssetPickerWindow extends StageWindow implements AssetListListener {
	
	/** Listens into the end of the {@link AssetPickerWindow}
	 * @author Richard Taylor */
	public interface AssetPickerListener {
		
		/** Invoked when the screen is canceled */
		public void cancel ();
		
		/** Invoked when an asset is picked
		 * @param file the {@link FileHandle} */
		public void ok (FileHandle file);
		
	}
	
	private TextButton cancel, ok;
	private AssetGrid list;
	private final AssetPickerListener listener;
	private FileHandle selected;
	private final AssetType type;
	
	public AssetPickerWindow (AssetType type, AssetPickerListener listener) {
		this.type = type;
		this.listener = listener;
	}
	
	@Override
	public void destroy () {
		super.destroy();
		list.dispose();
	}
	
	@Override
	public void onCreate () {
		root.add(new Table() {
			
			{
				add(new ScrollPane(list = new AssetGrid(type, AssetPickerWindow.this))).expand().fill();
			}
		}).expand().fill();
		root.row();
		root.add(new Table() {
			
			{
				defaults().fill().uniform();
				add(cancel = new TextButton("Cancel", Assets.skin));
				add(ok = new TextButton("Ok", Assets.skin));
			}
		}).expandX().right();
		list.setPath(getPath());
		cancel.addListener(new ClickListener() {
			
			@Override
			public void clicked (InputEvent e, float x, float y) {
				if (listener != null) {
					listener.cancel();
				}
				Editor.pop(AssetPickerWindow.this);
			}
		});
		ok.addListener(new ClickListener() {
			
			@Override
			public void clicked (InputEvent e, float x, float y) {
				if (listener != null) {
					listener.ok(selected);
				}
				Editor.pop(AssetPickerWindow.this);
			}
		});
		update();
	}
	
	@Override
	public void selected (FileHandle fileHandle) {
		selected = fileHandle;
		update();
	}
	
	private FileHandle getPath () {
		return Gdx.files.absolute("C:/Users/Richard/Desktop/demoproject/assets/textures");
	}
	
	private void update () {
		ok.setDisabled(selected == null);
		ok.setColor(ok.isDisabled() ? new Color(0.5f, 0.5f, 0.5f, 1f) : Color.WHITE);
	}
	
}
