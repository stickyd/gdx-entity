
package sx.richard.entity.editor;

import sx.richard.entity.editor.assets.AssetPickerWindow;
import sx.richard.entity.editor.assets.AssetType;
import sx.richard.entity.editor.window.Window;
import sx.richard.eventbus.EventBus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.utils.Array;

public class Editor extends ApplicationAdapter {
	
	public static EventBus events;
	
	private static int width, height;
	
	private static final Array<Window> windows = new Array<Window>();
	
	static {
		events = new EventBus();
	}
	
	/** Clears a {@link Window} */
	public static void clear () {
		Gdx.app.postRunnable(new Runnable() {
			
			@Override
			public void run () {
				windows.clear();
			}
		});
	}
	
	public static void main (String[] args) {
		new LwjglApplication(new Editor(), "Editor", 1280, 720, true);
	}
	
	public static void pop () {
		Gdx.app.postRunnable(new Runnable() {
			
			@Override
			public void run () {
				Window window = windows.get(windows.size - 1);
				window.destroy();
				windows.removeValue(window, true);
				updateInput();
			}
		});
	}
	
	public static void pop (final Window window) {
		Gdx.app.postRunnable(new Runnable() {
			
			@Override
			public void run () {
				window.destroy();
				windows.removeValue(window, true);
				updateInput();
			}
		});
	}
	
	/** Pushes a new {@link Window}
	 * @param window */
	public static void push (final Window window) {
		Gdx.app.postRunnable(new Runnable() {
			
			@Override
			public void run () {
				windows.add(window);
				window.create();
				window.resize(width, height);
				updateInput();
			}
		});
	}
	
	private static void updateInput () {
		Gdx.input.setInputProcessor(windows.get(windows.size - 1).getInputProcessor());
	}
	
	@Override
	public void create () {
		push(new MainWindow());
		push(new AssetPickerWindow(AssetType.TEXTURE, null));
	}
	
	@Override
	public void render () {
		float delta = Gdx.graphics.getDeltaTime();
		for (Window window : windows) {
			window.update(delta);
		}
		for (Window window : windows) {
			window.render();
		}
	}
	
	@Override
	public void resize (int width, int height) {
		Editor.width = width;
		Editor.height = height;
		for (Window window : windows) {
			window.resize(width, height);
		}
	}
	
}
