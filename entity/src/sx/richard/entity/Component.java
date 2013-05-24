
package sx.richard.entity;

import sx.richard.entity.util.Copyable;

/** The base class for all entity components. For the sake of any potential
 * reflection-based editors, a public no-arg constructor should be made
 * available.
 * @author Richard Taylor */
public abstract class Component<T extends Component<T>> implements Copyable<Component<T>> {
	
	private Entity entity;
	
	/** @param componentClass the type of the component
	 * @return another component from the {@link Entity} this is part of */
	public <K extends Component<K>> K getComponent (Class<K> componentClass) {
		K component = null;
		if (entity != null) {
			component = entity.get(componentClass);
		}
		return component;
	}
	
	/** @return the {@link Entity} this component is a part of */
	public Entity getEntity () {
		return entity;
	}
	
	/** @param entity the {@link Entity} to associate with this component */
	void setEntity (Entity entity) {
		this.entity = entity;
	}
	
}
