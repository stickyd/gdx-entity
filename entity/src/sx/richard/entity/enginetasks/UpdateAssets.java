
package sx.richard.entity.enginetasks;

import sx.richard.entity.Engine;
import sx.richard.entity.EngineTask;

/** Updates the asset manager
 * @author Richard Taylor */
public class UpdateAssets implements EngineTask {
	
	@Override
	public void execute (Engine engine) {
		Engine.getAssetManager().update();
	}
	
}
