
package sx.richard.entity.enginetasks;

import sx.richard.entity.Engine;
import sx.richard.entity.EngineTask;
import sx.richard.entity.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/** Runs the render method on all the entities
 * @author Richard Taylor */
public class RenderComponents implements EngineTask {
	
	@Override
	public void execute (Engine engine) {
		GL20 gl = Gdx.gl20;
		Scene<?> scene = engine.getScene();
		scene.render(engine, gl);
	}
}
