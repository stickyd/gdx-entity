
package sx.richard.entity;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.utils.Array;

/** A {@link AbstractWorld} contains the {@link AbstractEntity}s, and allows
 * {@link WorldListener}s to observe entity changes. All implementations should
 * invoke {@link WorldListener} methods on the {@link AbstractWorld} itself to
 * notify listeners.
 * @author Richard Taylor */
public abstract class AbstractWorld implements WorldListener {
	
	private final List<WorldListener> listeners;
	
	{
		listeners = new ArrayList<WorldListener>();
	}
	
	/** Adds an {@link AbstractEntity} to the world
	 * @param entity the {@link AbstractEntity} */
	public abstract void add (AbstractEntity entity);
	
	/** @param listener the {@link WorldListener} to add */
	public void addListener (WorldListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void entityAdded (AbstractWorld world, AbstractEntity entity) {
		for (int i = 0, n = listeners.size(); i < n; i++) {
			listeners.get(i).entityAdded(world, entity);
		}
	}
	
	@Override
	public void entityRemoved (AbstractWorld world, AbstractEntity entity) {
		for (int i = 0, n = listeners.size(); i < n; i++) {
			listeners.get(i).entityRemoved(world, entity);
		}
	}
	
	/** @param id the {@link AbstractEntity} Id
	 * @return the {@link AbstractEntity} in this world with the given Id,
	 *         <code>null</code> if it doesn't exist */
	public abstract AbstractEntity get (String id);
	
	/** @return an array containing the the entities */
	public abstract Array<AbstractEntity> getEntities ();
	
	/** @param id the {@link AbstractEntity} Id
	 * @return whether this world contains an entity with the given Id, this may
	 *         be faster than using {@link AbstractWorld#get(String)} */
	public abstract boolean has (String id);
	
	/** Removes an {@link AbstractEntity} from the world
	 * @param entity the entity */
	public abstract void remove (AbstractEntity entity);
	
	/** Removes an {@link AbstractEntity} from the world by Id
	 * @param id the entity's Id */
	public abstract void remove (String id);
	
	/** @param listener the {@link WorldListener} to remove */
	public void removeListener (WorldListener listener) {
		listeners.remove(listener);
	}
	
}
