
package sx.richard.entity;

import sx.richard.entity.system.Scene;
import sx.richard.entity.util.Updatable;

/** The engine contains the current {@link Scene} and the core
 * {@link ComponentSystem}s (additional systems may be included per-scene)
 * @author Richard Taylor */
public abstract class AbstractEngine implements WorldListener, Updatable {
	
	private AbstractWorld world;
	
	/** Adds a system of a given type, this should throw an
	 * {@link IllegalStateException} if a system of the same time already exists
	 * in the {@link AbstractEngine}
	 * @param system the {@link ComponentSystem} */
	public abstract void add (ComponentSystem<? extends Component<?>> system);
	
	/** @param the componentType component {@link Class}
	 * @return the system, <code>null</code> if it doesn't exist */
	public abstract <T extends Component<?>> ComponentSystem<?> get (Class<T> componentType);
	
	/** @return the {@link AbstractWorld} */
	public final AbstractWorld getWorld () {
		return world;
	}
	
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
