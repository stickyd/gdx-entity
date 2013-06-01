
package sx.richard.entity.editor.panel;

import sx.richard.entity.Engine;
import sx.richard.entity.World;

/** Renders the {@link Engine} in debug mode, allows manipulation etc.
 * @author Richard Taylor */
public class WorldEditor extends WorldPanel {
	
	public WorldEditor (World world) {
		super(world, true);
	}
	
}
