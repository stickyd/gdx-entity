
package sx.richard.entity;

/** Executes an action on a given entity
 * @author Richard Taylor */
public interface EngineTask {
	
	/** Invoked when these actions should be run on the entity
	 * @param entity the {@link Entity} */
	public void execute (Engine engine);
	
}
