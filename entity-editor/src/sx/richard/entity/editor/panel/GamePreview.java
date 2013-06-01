
package sx.richard.entity.editor.panel;

import sx.richard.entity.Engine;
import sx.richard.entity.World;

/** Renders the {@link Engine} in game mode, using the active world camera
 * @author Richard Taylor */
public class GamePreview extends WorldPanel {
	
	public GamePreview (World world) {
		super(world, false);
	}
	
}
