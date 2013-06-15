
package sx.richard.entity.editor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/** Contains the play/pause/stop buttons, keeps state
 * @author Richard Taylor */
public class PlayButtons extends Table {
	
	/** Callbacks for running the game, play/pause/step stop
	 * @author Richard Taylor */
	public interface RunGameListener {
		
		/** Invoked when the game should pause */
		public void pause ();
		
		/** Invoked when the game should play */
		public void play ();
		
		/** Invoked when the game should step over */
		public void step ();
		
		/** Invoked when the game should stop */
		public void stop ();
		
		/** Invoked when the game should unpause */
		public void unpause ();
	}
	
	private final RunGameListener listener;
	private ImageButton playButton, pauseButton, unpauseButton, stepButton, stopButton;
	
	public PlayButtons (RunGameListener listener) {
		this.listener = listener;
		defaults().expand().uniform().space(5);
		final Drawable play = new TextureRegionDrawable(new TextureRegion(new Texture("play.png")));
		final Drawable playDown = new TextureRegionDrawable(new TextureRegion(new Texture("playDown.png")));
		final Drawable pause = new TextureRegionDrawable(new TextureRegion(new Texture("pause.png")));
		final Drawable pauseDown = new TextureRegionDrawable(new TextureRegion(new Texture("pauseDown.png")));
		final Drawable step = new TextureRegionDrawable(new TextureRegion(new Texture("step.png")));
		final Drawable stepDown = new TextureRegionDrawable(new TextureRegion(new Texture("stepDown.png")));
		final Drawable stop = new TextureRegionDrawable(new TextureRegion(new Texture("stop.png")));
		final Drawable stopDown = new TextureRegionDrawable(new TextureRegion(new Texture("stopDown.png")));
		stack(playButton = new ImageButton(play, playDown), unpauseButton = new ImageButton(play, playDown));
		add(stepButton = new ImageButton(step, stepDown));
		add(pauseButton = new ImageButton(pause, pauseDown));
		add(stopButton = new ImageButton(stop, stopDown));
		playButton.addListener(play());
		pauseButton.addListener(pause());
		unpauseButton.addListener(unpause());
		stepButton.addListener(step());
		stopButton.addListener(stop());
		pauseButton.setVisible(false);
		stepButton.setVisible(false);
		unpauseButton.setVisible(false);
		stopButton.setVisible(false);
	}
	
	private ClickListener pause () {
		return new ClickListener() {
			
			@Override
			public void clicked (InputEvent e, float x, float y) {
				playButton.setVisible(false);
				pauseButton.setVisible(false);
				unpauseButton.setVisible(true);
				stepButton.setVisible(true);
				stopButton.setVisible(true);
				listener.pause();
			}
		};
	}
	
	private ClickListener play () {
		return new ClickListener() {
			
			@Override
			public void clicked (InputEvent e, float x, float y) {
				playButton.setVisible(false);
				pauseButton.setVisible(true);
				unpauseButton.setVisible(false);
				stepButton.setVisible(false);
				stopButton.setVisible(true);
				listener.play();
			}
		};
	}
	
	private ClickListener step () {
		return new ClickListener() {
			
			@Override
			public void clicked (InputEvent e, float x, float y) {
				listener.step();
			}
		};
	}
	
	private ClickListener stop () {
		return new ClickListener() {
			
			@Override
			public void clicked (InputEvent e, float x, float y) {
				playButton.setVisible(true);
				pauseButton.setVisible(false);
				unpauseButton.setVisible(false);
				stepButton.setVisible(false);
				stopButton.setVisible(false);
				listener.stop();
			}
		};
	}
	
	private ClickListener unpause () {
		return new ClickListener() {
			
			@Override
			public void clicked (InputEvent e, float x, float y) {
				playButton.setVisible(false);
				pauseButton.setVisible(true);
				unpauseButton.setVisible(false);
				stepButton.setVisible(false);
				stopButton.setVisible(true);
				listener.unpause();
			}
		};
	}
}
