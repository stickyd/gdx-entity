
package sx.richard.entity;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/** A basic {@link AbstractEntity}
 * @author Richard Taylor */
public final class Entity extends AbstractEntity {
	
	private final ObjectMap<Class<? extends Component<?>>, Integer> componentIndices;
	private final ObjectMap<Class<? extends Component<?>>, Component<?>> components;
	private final Array<Class<? extends Component<?>>> componentTypes;
	
	{
		components = new ObjectMap<Class<? extends Component<?>>, Component<?>>();
		componentIndices = new ObjectMap<Class<? extends Component<?>>, Integer>();
		componentTypes = new Array<Class<? extends Component<?>>>();
	}
	
	/** Creates an empty Entity
	 * @param id this entites Id, must not be <code>null</code> */
	public Entity (String id) {
		super(id);
	}
	
	public Entity (String id, Entity entity) {
		super(id, entity);
	}
	
	@Override
	public <T extends Component<T>> void add (T component) {
		add(component, 0);
	}
	
	@Override
	public <T extends Component<T>> void add (T component, int index) {
		// Type safe by method parameter
		@SuppressWarnings("unchecked")
		Class<T> componentClass = (Class<T>)component.getClass();
		if (components.containsKey(componentClass))
			throw new IllegalStateException("Entity already contains component for " + componentClass + ", " + this);
		else {
			components.put(componentClass, component);
			componentIndices.put(componentClass, Integer.valueOf(index));
			componentTypes.add(componentClass);
			sort();
			componentAdded(this, component);
		}
	}
	
	@Override
	public <T extends Component<T>> T get (Class<T> componentClass) {
		// Type safe by logic
		@SuppressWarnings("unchecked")
		T component = (T)components.get(componentClass);
		return component;
	}
	
	@Override
	public <T extends Component<T>> Class<T> get (int index) {
		// Type safe from insertion
		@SuppressWarnings("unchecked")
		Class<T> componentType = (Class<T>)componentTypes.get(index);
		return componentType;
	}
	
	@Override
	public int getComponentCount () {
		return components.size;
	}
	
	@Override
	public Iterable<Class<? extends Component<?>>> getComponents () {
		return new Array<Class<? extends Component<?>>>(componentTypes);
	}
	
	@Override
	public <T extends Component<T>> int getIndex (int index) {
		Class<T> componentType = get(index);
		return componentIndices.get(componentType);
	}
	
	@Override
	public <T extends Component<T>> boolean has (Class<T> componentClass) {
		return components.containsKey(componentClass);
	}
	
	@Override
	public <T extends Component<T>> void remove (Class<T> componentClass) {
		Component<?> component = components.remove(componentClass);
		componentIndices.remove(componentClass);
		componentTypes.removeValue(componentClass, true);
		if (component != null) {
			componentRemoved(this, component);
		}
	}
	
	/** Sorts the componentTypes list by index */
	private void sort () {
		//FIXME Sort by index
	}
	
}
