
package sx.richard.entity;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.OrderedMap;

/** A basic {@link AbstractEngine} implementation, keeps the systems in an
 * {@link OrderedMap}
 * @author Richard Taylor */
public class Engine extends AbstractEngine {
	
	private final Array<EngineTask> engineTasks;
	private final Array<ComponentSystem<?>> systemArray;
	private final ObjectMap<Class<? extends Component<?>>, ComponentSystem<?>> systems;
	private final Array<Class<? extends Component<?>>> systemTypes;
	
	{
		systems = new ObjectMap<Class<? extends Component<?>>, ComponentSystem<?>>(16);
		systemArray = new Array<ComponentSystem<?>>(true, 16);
		systemTypes = new Array<Class<? extends Component<?>>>(16);
		engineTasks = new Array<EngineTask>();
	}
	
	@Override
	public void add (ComponentSystem<?> system) {
		Class<? extends Component<?>> systemClass = system.getComponentType();
		if (systems.containsKey(systemClass))
			throw new IllegalStateException("System already exists, class=" + systemClass);
		systemTypes.add(systemClass);
		systemArray.add(system);
		systems.put(systemClass, system);
	}
	
	// These warnings are safe
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void entityAdded (AbstractWorld world, AbstractEntity entity) {
		for (int i = 0, n = entity.getComponentCount(); i < n; i++) {
			Class componentType = entity.get(i);
			ComponentSystem componentSystem = get(componentType);
			if (componentSystem != null) {
				Component<?> component = entity.get(componentType);
				componentSystem.added(component);
			}
		}
	}
	
	// These warnings are safe
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void entityRemoved (AbstractWorld world, AbstractEntity entity) {
		for (int i = 0, n = entity.getComponentCount(); i < n; i++) {
			Class componentType = entity.get(i);
			ComponentSystem componentSystem = get(componentType);
			if (componentSystem != null) {
				Component<?> component = entity.get(componentType);
				componentSystem.removed(component);
			}
		}
	}
	
	@Override
	public <T extends Component<?>> ComponentSystem<?> get (Class<T> componentType) {
		return systems.get(componentType);
	}
	
	/** @return the {@link EngineTask}s */
	public Array<EngineTask> getEngineTasks () {
		return new Array<EngineTask>(engineTasks);
	}
	
	/** Sets the engine tasks
	 * @param engineTasks the engine tasks */
	public void setEngineTasks (Array<EngineTask> engineTasks) {
		this.engineTasks.clear();
		this.engineTasks.addAll(engineTasks);
	}
	
	@Override
	public void update (float delta) {
		for (EngineTask engineTask : engineTasks) {
			engineTask.execute(this);
		}
	}
}
