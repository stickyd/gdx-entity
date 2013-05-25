
package sx.richard.entity;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/** A {@link World} contains the {@link Entity}s, and allows
 * {@link WorldListener}s to observe entity changes.
 * @author Richard Taylor */
public final class World implements WorldListener, EntityListener {
	
	private final ObjectMap<String, Entity> entities;
	private final Array<Entity> entityArray; // FIXME Wrap Array so that it can't be edited
	private final List<WorldListener> listeners;
	
	{
		listeners = new ArrayList<WorldListener>();
		entities = new ObjectMap<String, Entity>();
		entityArray = new Array<Entity>();
	}
	
	/** Adds an {@link Entity} to the world
	 * @param entity the {@link Entity} */
	public void add (Entity entity) {
		String id = entity.getId();
		if (has(id))
			throw new IllegalStateException("An entity with this Id already exists");
		entity.addListener(this);
		entities.put(id, entity);
		entityAdded(this, entity);
		entityArray.add(entity);
	}
	
	/** @param listener the {@link WorldListener} to add */
	public void addListener (WorldListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void componentAdded (Entity entity, Component<?> component) {}
	
	@Override
	public void componentRemoved (Entity entity, Component<?> component) {}
	
	@Override
	public void entityAdded (World world, Entity entity) {
		for (int i = 0, n = listeners.size(); i < n; i++) {
			listeners.get(i).entityAdded(world, entity);
		}
	}
	
	@Override
	public void entityIdChanged (Entity entity, String previousId) {
		String id = entity.getId();
		if (!previousId.equals(id)) {
			Entity otherEntity = get(id);
			if (otherEntity != null && otherEntity != entity)
				throw new IllegalStateException("Entity changed ID, but another entity already exists id=" + id);
			entities.remove(previousId);
			entities.put(id, entity);
		}
	}
	
	@Override
	public void entityRemoved (World world, Entity entity) {
		for (int i = 0, n = listeners.size(); i < n; i++) {
			listeners.get(i).entityRemoved(world, entity);
		}
	}
	
	/** @param id the {@link Entity} Id
	 * @return the {@link Entity} in this world with the given Id,
	 *         <code>null</code> if it doesn't exist */
	public Entity get (String id) {
		return entities.get(id);
	}
	
	/** @return an array containing the the entities */
	public Array<Entity> getEntities () {
		return entityArray;
	}
	
	/** @return any invalid entities (bad components, etc.) */
	public Array<Entity> getInvalidEntities () {
		Array<Entity> invalidEntities = new Array<Entity>();
		for (Entity entity : entityArray) {
			if (entity.getMissingDependencies().size > 0) {
				invalidEntities.add(entity);
			}
		}
		return invalidEntities;
	}
	
	/** @param id the {@link Entity} Id
	 * @return whether this world contains an entity with the given Id, this may
	 *         be faster than using {@link World#get(String)} */
	public boolean has (String id) {
		return entities.containsKey(id);
	}
	
	/** Removes an {@link Entity} from the world
	 * @param entity the entity */
	public void remove (Entity entity) {
		String id = entity.getId();
		if (entities.containsKey(id)) {
			entity.removeListener(this);
			entities.remove(id);
			entityArray.removeValue(entity, true);
			entityRemoved(this, entity);
		}
	}
	
	/** Removes an {@link Entity} from the world by Id
	 * @param id the entity's Id */
	public void remove (String id) {
		Entity entity = get(id);
		if (entity != null) {
			remove(entity);
		}
	}
	
	/** @param listener the {@link WorldListener} to remove */
	public void removeListener (WorldListener listener) {
		listeners.remove(listener);
	}
	
}
