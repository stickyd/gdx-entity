
package sx.richard.entity.enginetasks;

import sx.richard.entity.Engine;
import sx.richard.entity.EngineTask;

import com.badlogic.gdx.Gdx;

/** Renders the debug elements (origins / bounding boxes)
 * @author Richard Taylor */
public class RenderDebugScene implements EngineTask {
	
	@Override
	public void execute (Engine engine) {
		engine.getScene().renderDebug(engine, Gdx.gl20);
	}
	
}
