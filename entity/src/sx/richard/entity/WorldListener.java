
package sx.richard.entity;

/** Allows observsation of the {@link World}
 * @author Richard Taylor */
public interface WorldListener {
	
	/** Invoked after an entity is added to the world
	 * @param entity the {@link Entity} */
	public void entityAdded (World world, Entity entity);
	
	/** Invoked after an entity is removed from the world
	 * @param entity the {@link Entity} */
	public void entityRemoved (World world, Entity entity);
	
}
