
package sx.richard.entity.editor.assets;

import sx.richard.entity.assets.AssetType;
import sx.richard.entity.editor.Assets;
import sx.richard.entity.editor.Editor;
import sx.richard.entity.editor.MainEditor;
import sx.richard.entity.editor.assets.AssetGrid.AssetListListener;
import sx.richard.entity.editor.ui.DirectoryTree;
import sx.richard.entity.editor.ui.DirectoryTree.DirectoryTreeListener;
import sx.richard.entity.editor.window.StageWindow;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
	private Label selectedLabel;
	private final AssetType type;
	
	public AssetPickerWindow (AssetType type, FileHandle selected, AssetPickerListener listener) {
		this.type = type;
		this.selected = selected;
		this.listener = listener;
		rootFile = MainEditor.project.getAssets();
	}
	
	@Override
	public void destroy () {
		super.destroy();
		list.dispose();
	}
	
	@Override
	public void directorySelected (FileHandle directory, boolean clicked) {
		list.setPath(directory);
		if (clicked) {
			selected(null);
		}
	}
	
	@Override
	public InputProcessor getInputProcessor () {
		return new InputMultiplexer(super.getInputProcessor(), new InputAdapter() {
			
			@Override
			public boolean keyTyped (char c) {
				if (c == '\n' || c == '\r') {
					ok();
					return true;
				}
				return false;
			}
		});
	}
	
	@Override
	public int getPadding () {
		return 50;
	}
	
	@Override
	public void onCreate () {
		list = new AssetGrid(type, this);
		selectedLabel = new Label("", Assets.skin);
		root.setBackground(Assets.skin.newDrawable("white", new Color(0.1f, 0.1f, 0.1f, 1f)));
		root.add(new Table() {
			
			{
				add(new DirectoryTree(rootFile, selected != null ? selected.parent() : null, AssetPickerWindow.this)).expand().fill();
			}
		}).width(400).expandY().fill().padLeft(5).padTop(5).padRight(5);
		root.add(new Table() {
			
			{
				add(new ScrollPane(list)).expand().fillX().top();
			}
		}).expand().fill().padRight(5).padTop(5);
		root.row();
		root.add(new Table() {
			
			{
				add(new Table() {
					
					{
						add(selectedLabel).expand().left();
						add(new Table() {
							
							{
								defaults().fill().uniform();
								add(cancel = new TextButton("Cancel", Assets.skin));
								add(ok = new TextButton("Ok", Assets.skin));
							}
						}).expandY().right().fillY();
					}
				}).expand().fill();
			}
		}).expandX().fill().colspan(2).pad(5);
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
				ok();
			}
		});
		if (selected != null) {
			list.setPath(selected.parent());
			list.select(selected);
		}
		update();
	}
	
	@Override
	public void selected (FileHandle fileHandle) {
		selected = fileHandle;
		selectedLabel.setText(fileHandle != null ? fileHandle.path() : "");
		update();
	}
	
	private void ok () {
		if (!ok.isDisabled()) {
			if (listener != null) {
				listener.ok(selected);
			}
			Editor.pop(AssetPickerWindow.this);
		}
	}
	
	private void update () {
		ok.setDisabled(selected == null);
		ok.setColor(ok.isDisabled() ? new Color(0.5f, 0.5f, 0.5f, 1f) : Color.WHITE);
	}
	
}
