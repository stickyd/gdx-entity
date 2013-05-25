
package sx.richard.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.OrderedMap;

/** A basic {@link AbstractEngine} implementation, keeps the systems in an
 * {@link OrderedMap}
 * @author Richard Taylor */
public class Engine extends AbstractEngine {
	
	private final Array<EngineTask> engineTasks;
	private final Render render;
	
	{
		SpriteBatch spriteBatch = new SpriteBatch();
		render = new Render(spriteBatch);
		engineTasks = new Array<EngineTask>();
	}
	
	// These warnings are safe
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void entityAdded (AbstractWorld world, AbstractEntity entity) {
		for (int i = 0, n = entity.getComponentCount(); i < n; i++) {
			Class componentType = entity.get(i);
			Component<?> component = entity.get(componentType);
			component.setWorld(world);
			component.added();
		}
	}
	
	// These warnings are safe
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void entityRemoved (AbstractWorld world, AbstractEntity entity) {
		for (int i = 0, n = entity.getComponentCount(); i < n; i++) {
			Class componentType = entity.get(i);
			Component<?> component = entity.get(componentType);
			component.setWorld(null);
			component.removed();
		}
	}
	
	/** @return the {@link EngineTask}s */
	public Array<EngineTask> getEngineTasks () {
		return new Array<EngineTask>(engineTasks);
	}
	
	/** @return the {@link Render} object */
	public Render getRender () {
		return render;
	}
	
	@Override
	public void run () {
		for (EngineTask engineTask : engineTasks) {
			engineTask.execute(this);
		}
	}
	
	/** Sets the engine tasks
	 * @param engineTasks the engine tasks */
	public void setEngineTasks (Array<EngineTask> engineTasks) {
		this.engineTasks.clear();
		this.engineTasks.addAll(engineTasks);
	}
}
