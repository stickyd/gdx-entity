
package sx.richard.entity.editor.panel;

import sx.richard.entity.Engine;
import sx.richard.entity.World;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/** Renders the {@link Engine} in game mode, using the active world camera
 * @author Richard Taylor */
public class GamePreview extends WorldPanel {
	
	public GamePreview (World world) {
		super(world, false);
	}
	
	@Override
	public void addInput () {
		addListener(new ClickListener() {
			
			@Override
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				getStage().setKeyboardFocus(GamePreview.this);
				getStage().setScrollFocus(GamePreview.this);
				return true;
			}
		});
	}
	
}
