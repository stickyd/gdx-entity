
package sx.richard.entity;

import com.badlogic.gdx.InputProcessor;

/** Allows access to the state of the input
 * @author Richard Taylor */
public final class Input {
	
	private static InputProcessor inputProcessor;
	private static boolean[] keyDown;
	private static boolean[] keyPressed;
	private static boolean[] keyReleased;
	
	static {
		keyPressed = new boolean[256];
		keyReleased = new boolean[256];
		keyDown = new boolean[256];
	}
	
	private static boolean get (int key, boolean[] array) {
		if (key < 0 || key > 255)
			throw new IllegalArgumentException("Key must be >= 0, < 256");
		return array[key];
	}
	
	/** @return the {@link InputProcessor} */
	static InputProcessor getInputProcessor () {
		return inputProcessor = inputProcessor != null ? inputProcessor : new InputProcessor() {
			
			@Override
			public boolean keyDown (int key) {
				keyPressed[key] = true;
				keyDown[key] = true;
				return true;
			}
			
			@Override
			public boolean keyTyped (char key) {
				return true;
			}
			
			@Override
			public boolean keyUp (int key) {
				keyDown[key] = false;
				keyReleased[key] = true;
				return true;
			}
			
			@Override
			public boolean mouseMoved (int arg0, int arg1) {
				return true;
			}
			
			@Override
			public boolean scrolled (int arg0) {
				return true;
			}
			
			@Override
			public boolean touchDown (int arg0, int arg1, int arg2, int arg3) {
				return true;
			}
			
			@Override
			public boolean touchDragged (int arg0, int arg1, int arg2) {
				return true;
			}
			
			@Override
			public boolean touchUp (int arg0, int arg1, int arg2, int arg3) {
				return true;
			}
			
		};
	}
	
	/** @param key the key
	 * @return whether the key is down */
	public static boolean keyDown (int key) {
		return get(key, keyDown);
	}
	
	/** @param key the key
	 * @return whether the key was pressed this update */
	public static boolean keyPressed (int key) {
		return get(key, keyPressed);
	}
	
	/** @param key the key
	 * @return whether the key was released this update */
	public static boolean keyReleased (int key) {
		return get(key, keyReleased);
	}
	
	/** Updates */
	public static void update () {
		for (int i = 0; i < 256; i++) {
			keyPressed[i] = false;
			keyReleased[i] = false;
		}
	}
	
}
