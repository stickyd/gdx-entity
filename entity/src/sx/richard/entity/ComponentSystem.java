
package sx.richard.entity;


/** An {@link ComponentSystem} is what executes the logic behind a particular
 * component
 * @author Richard Taylor */
public abstract class ComponentSystem<T extends Component<T>> {
	
	private final Class<T> componentType;
	
	/** Tracks a given component type, this system will have a quickly-accessible
	 * list of all of these
	 * @param componentType the component type to operate on, must not be
	 *           <code>null</code> */
	public ComponentSystem (Class<T> componentType) {
		if (componentType == null)
			throw new NullPointerException("Component type must not be null");
		this.componentType = componentType;
	}
	
	/** Invoked when the component is added to the world
	 * @param component the {@link Component} */
	public abstract void added (T component);
	
	/** @return the component type that's operated on by this system */
	public final Class<? extends Component<?>> getComponentType () {
		return componentType;
	}
	
	/** Invoked when the component is removed from the world
	 * @param component the {@link Component} */
	public abstract void removed (T component);
	
	/** Invoked when the component requires executing
	 * @param component the {@link Component} */
	public abstract void update (T component);
	
}
