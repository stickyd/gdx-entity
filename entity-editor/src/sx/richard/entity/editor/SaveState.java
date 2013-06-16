
package sx.richard.entity.editor;

import sx.richard.entity.Component;
import sx.richard.entity.Entity;
import sx.richard.entity.World;

import com.badlogic.gdx.utils.Array;

/** Saves the state of a {@link World}
 * @author Richard Taylor */
public class SaveState {
	
	private final Array<Entity> entities;
	private final World world;
	
	{
		entities = new Array<Entity>();
	}
	
	/** @param world the {@link World} */
	public SaveState (World world) {
		this.world = world;
	}
	
	/** Restores the last saved state */
	public void restore () {
		world.removeAll();
		for (Entity entity : entities) {
			world.add(entity);
		}
	}
	
	/** Saves the state */
	public void save () {
		for (Entity entity : world.getEntities()) {
			Entity newEntity = new Entity(entity.getId(), entity);
			entities.add(newEntity);
			for (Class<? extends Component<?>> componentType : newEntity.getComponents()) {
				newEntity.get(componentType).edited();
			}
		}
	}
	
}
