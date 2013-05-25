
package sx.richard.entity;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/** A basic {@link AbstractEntity}
 * @author Richard Taylor */
public final class Entity implements EntityListener {
	
	private final ObjectMap<Class<? extends Component<?>>, Integer> componentIndices;
	private final ObjectMap<Class<? extends Component<?>>, Component<?>> components;
	private final Array<Class<? extends Component<?>>> componentTypes;
	
	private String id;
	
	protected final List<EntityListener> listeners;
	
	{
		components = new ObjectMap<Class<? extends Component<?>>, Component<?>>();
		componentIndices = new ObjectMap<Class<? extends Component<?>>, Integer>();
		componentTypes = new Array<Class<? extends Component<?>>>();
		listeners = new ArrayList<EntityListener>();
	}
	
	/** Creates an empty Entity
	 * @param id this entites Id, must not be <code>null</code> */
	public Entity (String id) {
		setId(id);
	}
	
	/** @param id the entity Id
	 * @param entity the {@link AbstractEntity} */
	public <T extends Component<T>> Entity (String id, Entity entity) {
		this(id);
		for (int i = 0, n = getComponentCount(); i < n; i++) {
			Class<T> componentClass = get(i);
			T component = get(componentClass);
			int index = getIndex(i);
			add(component, index);
		}
	}
	
	/** Adds a component with an index of 0
	 * @see AbstractEntity#add(Component, int) */
	public <T extends Component<T>> void add (T component) {
		add(component, 0);
	}
	
	/** Adds a new component to this entity, this will fail (throwing an
	 * {@link IllegalStateException}) if a component of the same type already
	 * exists.
	 * @param component the component, must not be <code>null</code>
	 * @param index the index to put this component at, matching indices will be
	 *           sorted arbitrarily */
	public <T extends Component<T>> void add (T component, int index) {
		// Type safe by method parameter
		@SuppressWarnings("unchecked")
		Class<T> componentClass = (Class<T>)component.getClass();
		if (components.containsKey(componentClass))
			throw new IllegalStateException("Entity already contains component for " + componentClass + ", " + this);
		else {
			if (component.getEntity() != null)
				throw new IllegalStateException("This Component is already part of an entity");
			if (component.hasStarted())
				throw new IllegalStateException("This Component has already been started, you cannot re-use it");
			component.setEntity(this);
			component.setStarted();
			components.put(componentClass, component);
			componentIndices.put(componentClass, Integer.valueOf(index));
			componentTypes.add(componentClass);
			sort();
			component.started();
			componentAdded(this, component);
		}
	}
	
	/** @param listener the {@link EntityListener} to add */
	public void addListener (EntityListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void componentAdded (Entity entity, Component<?> component) {
		for (int i = 0, n = listeners.size(); i < n; i++) {
			listeners.get(i).componentAdded(this, component);
		}
	}
	
	@Override
	public void componentRemoved (Entity entity, Component<?> component) {
		for (int i = 0, n = listeners.size(); i < n; i++) {
			listeners.get(i).componentRemoved(this, component);
		}
	}
	
	@Override
	public void entityIdChanged (Entity entity, String previousId) {
		for (int i = 0, n = listeners.size(); i < n; i++) {
			listeners.get(i).entityIdChanged(this, previousId);
		}
	}
	
	/** Gets the component of a particular type
	 * @param componentClass the component type
	 * @return the component, <code>null</code> if it doesn't exist */
	public <T extends Component<?>> T get (Class<T> componentClass) {
		// Type safe by logic
		@SuppressWarnings("unchecked")
		T component = (T)components.get(componentClass);
		return component;
	}
	
	/** @param index the component index (not necessarily the sorting index; but
	 *           the internal index)
	 * @return the component type */
	public <T extends Component<?>> Class<T> get (int index) {
		// Type safe from insertion
		@SuppressWarnings("unchecked")
		Class<T> componentType = (Class<T>)componentTypes.get(index);
		return componentType;
	}
	
	/** @return the number of components */
	public int getComponentCount () {
		return components.size;
	}
	
	/** @return an iterable list of the component types */
	public Iterable<Class<? extends Component<?>>> getComponents () {
		return new Array<Class<? extends Component<?>>>(componentTypes);
	}
	
	/** @return the Id */
	public final String getId () {
		return id;
	}
	
	/** @param index the component index
	 * @return the sorting index of the component */
	public <T extends Component<T>> int getIndex (int index) {
		Class<T> componentType = get(index);
		return componentIndices.get(componentType);
	}
	
	/** @return whether all the {@link Component}s dependencies are satisfied */
	// This warning is safe here, allow it to fail
	@SuppressWarnings("unchecked")
	public ObjectMap<Class<? extends Component<?>>, Array<Class<? extends Component<?>>>> getMissingDependencies () {
		ObjectMap<Class<? extends Component<?>>, Array<Class<? extends Component<?>>>> missing = new ObjectMap<Class<? extends Component<?>>, Array<Class<? extends Component<?>>>>();
		for (Component<?> component : components.values()) {
			Class<? extends Component<?>>[] dependencies = (Class<? extends Component<?>>[])component.getDependencies();
			if (dependencies != null) {
				for (Class<? extends Component<?>> dependency : dependencies) {
					if (!has(dependency)) {
						Class<? extends Component<?>> componentType = (Class<? extends Component<?>>)component.getClass();
						Array<Class<? extends Component<?>>> missingComponents = missing.get(componentType);
						if (missingComponents == null) {
							missingComponents = new Array<Class<? extends Component<?>>>(1);
							missing.put(componentType, missingComponents);
						}
						missingComponents.add(dependency);
					}
				}
			}
		}
		return missing;
	}
	
	/** @param componentClass the component type
	 * @return whether this entity has a component of a particular type; may be
	 *         faster than an
	 *         <code>{@link AbstractEntity#get(Class)} != null</code> */
	public <T extends Component<?>> boolean has (Class<T> componentClass) {
		return components.containsKey(componentClass);
	}
	
	/** Removes the component of a given type, does nothing if the component
	 * doesn't exist
	 * @param componentClass the component type */
	public <T extends Component<T>> void remove (Class<T> componentClass) {
		Component<?> component = components.remove(componentClass);
		componentIndices.remove(componentClass);
		componentTypes.removeValue(componentClass, true);
		if (component != null) {
			componentRemoved(this, component);
		}
	}
	
	/** @param listener the {@link EntityListener} to remove */
	public void removeListener (EntityListener listener) {
		listeners.remove(listener);
	}
	
	/** @param id the Id */
	public final void setId (String id) {
		if (id == null)
			throw new NullPointerException("Id must not be null");
		String previousId = this.id;
		this.id = id;
		entityIdChanged(this, previousId);
	}
	
	/** Sorts the componentTypes list by index */
	private void sort () {
		//FIXME Sort by index
	}
	
	@Override
	public String toString () {
		return "<Entity id=" + id + "/>";
	}
	
}
