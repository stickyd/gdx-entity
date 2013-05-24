
package sx.richard.entity.executors;

import sx.richard.entity.AbstractEntity;
import sx.richard.entity.AbstractWorld;
import sx.richard.entity.Component;
import sx.richard.entity.ComponentSystem;
import sx.richard.entity.Engine;
import sx.richard.entity.EngineTask;

/** Runs the update method on entity components and systems
 * @author Richard Taylor */
public class UpdateComponents implements EngineTask {
	
	// Warnings are safe here
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void execute (Engine engine) {
		AbstractWorld world = engine.getWorld();
		for (AbstractEntity entity : world.getEntities()) {
			for (int i = 0, n = entity.getComponentCount(); i < n; i++) {
				Class componentClass = entity.get(i);
				ComponentSystem system = engine.get(componentClass);
				if (system != null) {
					Component component = entity.get(componentClass);
					system.update(component);
				}
			}
		}
	}
}
