
package sx.richard.entity.entities;

import sx.richard.entity.Component;
import sx.richard.entity.ComponentAdapter;
import sx.richard.entity.Entity;
import sx.richard.entity.assets.Asset;
import sx.richard.entity.components.graphics.gfx2.DrawText;

import com.badlogic.gdx.Gdx;

/** Displays FPS
 * @author Richard Taylor */
public class FPSEntity {
	
	private static class UpdateFPS extends ComponentAdapter<UpdateFPS> {
		
		@Override
		public Component<UpdateFPS> copy () {
			return this;
		}
		
		@Override
		public Class<?>[] getDependencies () {
			return new Class<?>[] { DrawText.class };
		}
		
		@Override
		public void update (float delta) {
			DrawText drawText = get(DrawText.class);
			drawText.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
		}
		
	}
	
	/** @return an {@link Entity} that renders the FPS */
	public static Entity create (String id) {
		Entity entity = new Entity(id);
		entity.add(new UpdateFPS());
		entity.add(new DrawText(Asset.Defaults.DEBUG_FONT));
		return entity;
	}
}
