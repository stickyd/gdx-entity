
package sx.richard.entity.editor.panel;

import sx.richard.entity.Engine;
import sx.richard.entity.World;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/** Renders the {@link Engine} in debug mode, allows manipulation etc.
 * @author Richard Taylor */
public class WorldEditor extends WorldPanel {
	
	public WorldEditor (World world) {
		super(world, true);
	}
	
	@Override
	public void addInput () {
		final GestureAdapter adapter = new GestureAdapter() {
			
			@Override
			public boolean pan (float x, float y, float deltaX, float deltaY) {
				camera.position.sub(deltaX * camera.zoom, deltaY * camera.zoom, 0);
				return false;
			}
			
		};
		final GestureDetector detector = new GestureDetector(adapter);
		addListener(new ClickListener() {
			
			private boolean dragging;
			
			@Override
			public boolean scrolled (InputEvent e, float x, float y, int amount) {
				camera.zoom = MathUtils.clamp(camera.zoom + camera.zoom * (amount * 0.1f), 0.01f, 1000f);
				return true;
			}
			
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if (button == 2) {
					detector.touchDown(x, y, pointer, button);
					dragging = true;
				}
				getStage().setKeyboardFocus(WorldEditor.this);
				getStage().setScrollFocus(WorldEditor.this);
				return true;
			}
			
			@Override
			public void touchDragged (InputEvent event, float x, float y, int pointer) {
				if (dragging) {
					detector.touchDragged(x, y, pointer);
				}
			}
			
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				if (button == 2) {
					detector.touchUp(x, y, pointer, button);
				}
				dragging = false;
			}
			
		});
	}
}
