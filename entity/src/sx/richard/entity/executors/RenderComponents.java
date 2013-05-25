
package sx.richard.entity.executors;

import sx.richard.entity.AbstractEntity;
import sx.richard.entity.AbstractWorld;
import sx.richard.entity.Component;
import sx.richard.entity.Engine;
import sx.richard.entity.EngineTask;
import sx.richard.entity.Render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/** Runs the render method on all the entities
 * @author Richard Taylor */
public class RenderComponents implements EngineTask {
	
	// Warnings are safe here
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void execute (Engine engine) {
		GL20 gl = Gdx.gl20;
		Render render = engine.getRender();
		render.begin();
		AbstractWorld world = engine.getWorld();
		for (AbstractEntity entity : world.getEntities()) {
			for (int i = 0, n = entity.getComponentCount(); i < n; i++) {
				Class componentClass = entity.get(i);
				Component component = entity.get(componentClass);
				component.render(gl, render);
			}
		}
		render.end();
	}
}
