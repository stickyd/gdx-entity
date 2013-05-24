
package sx.richard.entity;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/** A basic {@link AbstractWorld} implemtnation. It allows any entities,
 * restricted to unique Ids, throwing an {@link IllegalStateException} if this
 * condition is not met. Registers as an {@link EntityListener} and processes Id
 * changes.
 * @author Richard Taylor */
public class World extends AbstractWorld implements EntityListener {
	
	private final ObjectMap<String, AbstractEntity> entities;
	private final Array<AbstractEntity> entityArray; // FIXME Wrap Array so that it can't be edited
	
	{
		entities = new ObjectMap<String, AbstractEntity>();
		entityArray = new Array<AbstractEntity>();
	}
	
	@Override
	public void add (AbstractEntity entity) {
		String id = entity.getId();
		if (has(id))
			throw new IllegalStateException("An entity with this Id already exists");
		entities.put(id, entity);
		entity.addListener(this);
		entityAdded(this, entity);
		entityArray.add(entity);
	}
	
	@Override
	public void componentAdded (AbstractEntity entity, Component<?> component) {}
	
	@Override
	public void componentRemoved (AbstractEntity entity, Component<?> component) {}
	
	@Override
	public void entityIdChanged (AbstractEntity entity, String previousId) {
		String id = entity.getId();
		if (!previousId.equals(id)) {
			AbstractEntity otherEntity = get(id);
			if (otherEntity != null && otherEntity != entity)
				throw new IllegalStateException("Entity changed ID, but another entity already exists id=" + id);
			entities.remove(previousId);
			entities.put(id, entity);
		}
	}
	
	@Override
	public AbstractEntity get (String id) {
		return entities.get(id);
	}
	
	@Override
	public Array<AbstractEntity> getEntities () {
		return entityArray;
	}
	
	@Override
	public boolean has (String id) {
		return entities.containsKey(id);
	}
	
	@Override
	public void remove (AbstractEntity entity) {
		String id = entity.getId();
		if (entities.containsKey(id)) {
			entities.remove(id);
			entityArray.removeValue(entity, true);
			entity.removeListener(this);
			entityRemoved(this, entity);
		}
	}
	
	@Override
	public void remove (String id) {
		AbstractEntity entity = get(id);
		if (entity != null) {
			remove(entity);
		}
	}
	
}
