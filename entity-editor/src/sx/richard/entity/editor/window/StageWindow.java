
package sx.richard.entity.editor.window;

import sx.richard.entity.editor.Assets;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/** A {@link Window} with a {@link Stage}
 * @author Richard Taylor */
public abstract class StageWindow implements Window {
	
	protected Table root;
	protected Stage stage;
	
	@Override
	public void create () {
		stage = new Stage();
		stage.addActor(root = new Table());
		root.setBackground(Assets.skin.newDrawable("white", Window.FADE));
		onCreate();
	}
	
	@Override
	public void destroy () {
		stage.dispose();
	}
	
	@Override
	public InputProcessor getInputProcessor () {
		return stage;
	}
	
	public abstract void onCreate ();
	
	@Override
	public void render () {
		stage.draw();
	}
	
	@Override
	public void resize (int width, int height) {
		stage.setViewport(width, height, false);
		root.setSize(width, height);
		root.invalidateHierarchy();
	}
	
	@Override
	public void update (float delta) {
		stage.act(delta);
	}
	
}
