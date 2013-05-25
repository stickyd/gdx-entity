
package sx.richard.entity;

/** The engine contains the current {@link Scene} and the core
 * {@link ComponentSystem}s (additional systems may be included per-scene)
 * @author Richard Taylor */
public abstract class AbstractEngine implements WorldListener {
	
	private AbstractWorld world;
	
	/** @return the {@link AbstractWorld} */
	public final AbstractWorld getWorld () {
		return world;
	}
	
	/** Runs the {@link EngineTask}s */
	public abstract void run ();
	
	/** @param world the {@link AbstractWorld} */
	public final void setWorld (AbstractWorld world) {
		if (world == null)
			throw new NullPointerException("World must not be null");
		if (this.world != world) {
			if (this.world != null) {
				this.world.removeListener(this);
			}
			this.world = world;
			world.addListener(this);
		}
	}
	
}
