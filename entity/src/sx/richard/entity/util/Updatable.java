
package sx.richard.entity.util;

/** Implies that an object should be updated over time
 * @author Richard Taylor */
public interface Updatable {
	
	/** Invoked periodically
	 * @param delta the time since the last updated */
	public void update (float delta);
	
}
