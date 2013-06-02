
package sx.richard.entity.editor.events;

import sx.richard.entity.Entity;

import com.badlogic.gdx.scenes.scene2d.Event;

/** Published when an entity is selected
 * @author Richard Taylor */
public class EntitySelected extends Event {
	
	/** The {@link Entity} that was selected, <code>null</code> if unselected */
	public final Entity entity;
	
	/** @param entity the {@link Entity} that was selected, <code>null</code> if
	 *           unselected */
	public EntitySelected (Entity entity) {
		this.entity = entity;
	}
}
