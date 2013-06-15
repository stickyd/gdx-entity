
package sx.richard.entity.editor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/** Contains the play/pause/stop buttons, keeps state
 * @author Richard Taylor */
public class PlayButtons extends Table {
	
	public PlayButtons () {
		defaults().expand().uniform().space(5);
		Drawable play = new TextureRegionDrawable(new TextureRegion(new Texture("play.png")));
		Drawable playDown = new TextureRegionDrawable(new TextureRegion(new Texture("playDown.png")));
		Texture pause = new Texture("pause.png");
		Texture step = new Texture("step.png");
		Texture stop = new Texture("stop.png");
		add(new ImageButton(play, playDown));
		add(new Image(pause));
		add(new Image(step));
		add(new Image(stop));
	}
}
