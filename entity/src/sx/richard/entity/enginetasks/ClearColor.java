
package sx.richard.entity.enginetasks;

import sx.richard.entity.Engine;
import sx.richard.entity.EngineTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/** Clears the color buffer
 * @author Richard Taylor */
public class ClearColor implements EngineTask {
	
	@Override
	public void execute (Engine engine) {
		Gdx.gl20.glClearColor(0.2f, 0.2f, 0.2f, 1f);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
}
