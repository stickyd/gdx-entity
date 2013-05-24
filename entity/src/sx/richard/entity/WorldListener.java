
package sx.richard.entity;


/** Allows observsation of the {@link AbstractWorld}
 * @author Richard Taylor */
public interface WorldListener {
	
	/** Invoked after an entity is added to the world
	 * @param entity the {@link AbstractEntity} */
	public void entityAdded (AbstractWorld world, AbstractEntity entity);
	
	/** Invoked after an entity is removed from the world
	 * @param entity the {@link AbstractEntity} */
	public void entityRemoved (AbstractWorld world, AbstractEntity entity);
	
}
