
package sx.richard.entity.enginetasks;

import sx.richard.entity.Engine;
import sx.richard.entity.EngineTask;
import sx.richard.entity.Scene;

import com.badlogic.gdx.Gdx;

/** Runs the update method on entity components and systems
 * @author Richard Taylor */
public class UpdateScene implements EngineTask {
	
	@Override
	public void execute (Engine engine) {
		float delta = Gdx.graphics.getDeltaTime();
		Scene<?> scene = engine.getScene();
		scene.update(engine, delta);
	}
}
