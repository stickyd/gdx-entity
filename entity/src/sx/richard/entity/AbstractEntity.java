
package sx.richard.entity;

import java.util.ArrayList;
import java.util.List;

/** An entity is a basic container that holds many {@link Component}s; these are
 * mapped against their type. Use {@link AbstractEntity#get(Class)} to fetch a component
 * of that type. The components are held in a sorted list, so may be (depending
 * on implementation) executed in a predictably ordered way. Components are
 * sorted by index, in ascending order. These may have duplicates, no order is
 * guaranteed between matching components with indices.
 * <p>
 * 
 * All implementations must be sure to properly invoke the
 * {@link EntityListener}s, by calling the listener methods on {@link AbstractEntity}
 * itself.
 * @author Richard Taylor */
public abstract class AbstractEntity implements EntityListener {
	
	private String id;
	protected final List<EntityListener> listeners;
	
	{
		listeners = new ArrayList<EntityListener>();
	}
	
	/** @param id the entity Id */
	public AbstractEntity (String id) {
		setId(id);
	}
	
	/** @param id the entity Id
	 * @param entity the {@link AbstractEntity} */
	public <T extends Component<T>> AbstractEntity (String id, AbstractEntity entity) {
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
	public abstract <T extends Component<T>> void add (T component);
	
	/** Adds a new component to this entity, this will fail (throwing an
	 * {@link IllegalStateException}) if a component of the same type already
	 * exists.
	 * @param component the component, must not be <code>null</code>
	 * @param index the index to put this component at, matching indices will be
	 *           sorted arbitrarily */
	public abstract <T extends Component<T>> void add (T component, int index);
	
	/** @param listener the {@link EntityListener} to add */
	public void addListener (EntityListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void componentAdded (AbstractEntity entity, Component<?> component) {
		for (int i = 0, n = listeners.size(); i < n; i++) {
			listeners.get(i).componentAdded(this, component);
		}
	}
	
	@Override
	public void componentRemoved (AbstractEntity entity, Component<?> component) {
		for (int i = 0, n = listeners.size(); i < n; i++) {
			listeners.get(i).componentRemoved(this, component);
		}
	}
	
	@Override
	public void entityIdChanged (AbstractEntity entity, String previousId) {
		for (int i = 0, n = listeners.size(); i < n; i++) {
			listeners.get(i).entityIdChanged(this, previousId);
		}
	}
	
	/** Gets the component of a particular type
	 * @param componentClass the component type
	 * @return the component, <code>null</code> if it doesn't exist */
	public abstract <T extends Component<T>> T get (Class<T> componentClass);
	
	/** @param index the component index (not necessarily the sorting index; but
	 *           the internal index)
	 * @return the component type */
	public abstract <T extends Component<T>> Class<T> get (int index);
	
	/** @return the number of components */
	public abstract int getComponentCount ();
	
	/** @return an iterable list of the component types */
	public abstract Iterable<Class<? extends Component<?>>> getComponents ();
	
	/** @return the Id */
	public final String getId () {
		return id;
	}
	
	/** @param index the component index
	 * @return the sorting index of the component */
	public abstract <T extends Component<T>> int getIndex (int index);
	
	/** @param componentClass the component type
	 * @return whether this entity has a component of a particular type; may be
	 *         faster than an <code>{@link AbstractEntity#get(Class)} != null</code> */
	public abstract <T extends Component<T>> boolean has (Class<T> componentClass);
	
	/** Removes the component of a given type, does nothing if the component
	 * doesn't exist
	 * @param componentClass the component type */
	public abstract <T extends Component<T>> void remove (Class<T> componentClass);
	
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
	
	@Override
	public String toString () {
		return "<Entity id=" + id + "/>";
	}
	
}
