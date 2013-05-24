
package sx.richard.entity;

/** Listens for events in the entity, component changes
 * @author Richard Taylor */
public interface EntityListener {
	
	/** Invoked when a component is added to an entity
	 * @param entity the {@link AbstractEntity}
	 * @param component the component that was added */
	public void componentAdded (AbstractEntity entity, Component<?> component);
	
	/** Invoked after a component is removed from an entity
	 * @param entity the {@link AbstractEntity}
	 * @param component the component that was removed */
	public void componentRemoved (AbstractEntity entity, Component<?> component);
	
	/** Invoked after an entities Id changes
	 * @param entity the {@link AbstractEntity}
	 * @param previousId the previous entity Id */
	public void entityIdChanged (AbstractEntity entity, String previousId);
	
}