
package sx.richard.entity;

import sx.richard.entity.util.Copyable;

import com.badlogic.gdx.graphics.GL20;

/** The base class for all entity components. For the sake of any potential
 * reflection-based editors, a public no-arg constructor should be made
 * available.
 * @author Richard Taylor */
public abstract class Component<T extends Component<T>> implements Copyable<Component<T>> {
	
	private AbstractEntity entity;
	private boolean started;
	private AbstractWorld world;
	
	/** Added when this component is added to the world */
	public abstract void added ();
	
	/** @param componentClass the type of the component
	 * @return another component from the {@link AbstractEntity} this is part of */
	public final <K extends Component<K>> K get (Class<K> componentClass) {
		K component = null;
		if (entity != null) {
			component = entity.get(componentClass);
		}
		return component;
	}
	
	/** @return the {@link Component} types this requires, for editor (may return
	 *         <code>null</code>) */
	public abstract Class<?>[] getDependencies ();
	
	/** @return the {@link Entity} this component is a part of */
	public AbstractEntity getEntity () {
		return entity;
	}
	
	/** @param id the entity Id
	 * @return the entity in this {@link World}, with the given Id */
	public final AbstractEntity getEntity (String id) {
		return world.get(id);
	}
	
	/** @return the world this lives in */
	public AbstractWorld getWorld () {
		return world;
	}
	
	/** @return whether this component has been started */
	boolean hasStarted () {
		return started;
	}
	
	/** Invoked when this component is removed from the world */
	public abstract void removed ();
	
	/** Invoked on render */
	public abstract void render (GL20 gl, Render render);
	
	/** @param entity the {@link AbstractEntity} to associate with this component */
	void setEntity (AbstractEntity entity) {
		this.entity = entity;
	}
	
	/** Marks as started */
	void setStarted () {
		started = true;
	}
	
	/** @param world the {@link World} this component's entity is a part of */
	void setWorld (AbstractWorld world) {
		this.world = world;
	}
	
	/** Invoked when the component is first started; when it is added to an
	 * entity, at this point it may or may not be in the {@link World} */
	public abstract void started ();
	
	/** Invoked each update
	 * @param delta the time since the last update, varies */
	public abstract void update (float delta);
}
