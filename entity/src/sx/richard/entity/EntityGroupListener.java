
package sx.richard.entity;

/** Allows observsation of the {@link World}
 * @author Richard Taylor */
public interface EntityGroupListener {
	
	/** Invoked after an entity is added to the world
	 * @param entity the {@link Entity} */
	public void entityAdded (EntityGroup group, Entity entity);
	
	/** Invoked after an entity is removed from the world
	 * @param entity the {@link Entity} */
	public void entityRemoved (EntityGroup group, Entity entity);
	
}
