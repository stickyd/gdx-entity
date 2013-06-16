
package sx.richard.entity.editor.assets;

import sx.richard.entity.assets.AssetType;
import sx.richard.entity.editor.Assets;
import sx.richard.entity.editor.Editor;
import sx.richard.entity.editor.assets.AssetGrid.AssetListListener;
import sx.richard.entity.editor.ui.DirectoryTree;
import sx.richard.entity.editor.ui.DirectoryTree.DirectoryTreeListener;
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
public class AssetPickerWindow extends StageWindow implements AssetListListener, DirectoryTreeListener {
	
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
	private final FileHandle rootFile;
	private FileHandle selected;
	private final AssetType type;
	
	public AssetPickerWindow (AssetType type, AssetPickerListener listener) {
		this.type = type;
		this.listener = listener;
		rootFile = Gdx.files.absolute("C:/Users/Richard/Desktop/");
	}
	
	@Override
	public void destroy () {
		super.destroy();
		list.dispose();
	}
	
	@Override
	public void directorySelected (FileHandle directory) {
		list.setPath(directory);
	}
	
	@Override
	public int getPadding () {
		return 50;
	}
	
	@Override
	public void onCreate () {
		root.setBackground(Assets.skin.newDrawable("white", new Color(0.1f, 0.1f, 0.1f, 1f)));
		root.add(new Table() {
			
			{
				add(new DirectoryTree(rootFile, rootFile, AssetPickerWindow.this)).expand().fill();
			}
		}).width(400).expandY().fill().padLeft(5).padTop(5).space(5);
		root.add(new Table() {
			
			{
				add(new ScrollPane(list = new AssetGrid(type, AssetPickerWindow.this))).expand().fillX().top();
			}
		}).expand().fill().padRight(5).padTop(5);
		root.row();
		root.add(new Table() {
			
			{
				defaults().fill().uniform();
				add(cancel = new TextButton("Cancel", Assets.skin));
				add(ok = new TextButton("Ok", Assets.skin));
			}
		}).expandX().right().colspan(2).pad(5);
		list.setPath(rootFile);
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
				if (!ok.isDisabled()) {
					if (listener != null) {
						listener.ok(selected);
					}
					Editor.pop(AssetPickerWindow.this);
				}
			}
		});
		update();
	}
	
	@Override
	public void selected (FileHandle fileHandle) {
		selected = fileHandle;
		update();
	}
	
	private void update () {
		ok.setDisabled(selected == null);
		ok.setColor(ok.isDisabled() ? new Color(0.5f, 0.5f, 0.5f, 1f) : Color.WHITE);
	}
	
}
