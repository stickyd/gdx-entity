
package sx.richard.entity;

import sx.richard.entity.util.Copyable;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Matrix4;

/** The base class for all entity components. For the sake of any potential
 * reflection-based editors, a public no-arg constructor should be made
 * available.
 * @author Richard Taylor */
public abstract class Component<T extends Component<T>> implements Copyable<Component<T>> {
	
	private Entity entity;
	private EntityGroup group;
	private boolean started;
	
	/** Added when this component is added to the world */
	public abstract void added ();
	
	/** @param componentClass the type of the component
	 * @return another component from the {@link Entity} this is part of */
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
	public Entity getEntity () {
		return entity;
	}
	
	/** @param id the entity Id
	 * @return the entity in this {@link World}, with the given Id */
	public final Entity getEntity (String id) {
		return group.get(id);
	}
	
	/** @return the world this lives in */
	public EntityGroup getGroup () {
		return group;
	}
	
	/** @return whether this is a hidden component (it will not appear in the
	 *         editor) */
	public abstract boolean isHidden ();
	
	/** Invoked when this component is removed from the world */
	public abstract void removed ();
	
	/** Invoked on render */
	public abstract void render (GL20 gl, Render render);
	
	/** Invoked when the component is first started; when it is added to an
	 * entity, at this point it may or may not be in the {@link World} */
	public abstract void started ();
	
	/** Invoked when rendering in reverse, to undo transforms etc. */
	public abstract void transform (GL20 gl, Render render, Matrix4 transform);
	
	/** Invoked when rendering, before the main render method; apply transforms
	 * etc. */
	public abstract void untransform (GL20 gl, Render render, Matrix4 transform);
	
	/** Invoked each update
	 * @param delta the time since the last update, varies */
	public abstract void update (float delta);
	
	/** @return whether this component has been started */
	boolean hasStarted () {
		return started;
	}
	
	/** @param entity the {@link Entity} to associate with this component */
	void setEntity (Entity entity) {
		this.entity = entity;
	}
	
	/** @param world the {@link World} this component's entity is a part of */
	void setGroup (EntityGroup group) {
		this.group = group;
	}
	
	/** Marks as started */
	void setStarted () {
		started = true;
	}
}
