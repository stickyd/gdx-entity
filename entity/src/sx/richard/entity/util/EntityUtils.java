
package sx.richard.entity.util;

import java.util.Comparator;

import sx.richard.entity.Component;
import sx.richard.entity.Entity;
import sx.richard.entity.EntityGroup;
import sx.richard.entity.Render;
import sx.richard.entity.components.RenderLayer;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;

public class EntityUtils {
	
	private static final Comparator<Entity> renderComparator;
	
	static {
		renderComparator = new Comparator<Entity>() {
			
			@Override
			public int compare (Entity a, Entity b) {
				RenderLayer renderLayerA = a.get(RenderLayer.class);
				RenderLayer renderLayerB = b.get(RenderLayer.class);
				int layerA = renderLayerA == null ? 0 : renderLayerA.getLayer();
				int layerB = renderLayerB == null ? 0 : renderLayerB.getLayer();
				return layerB - layerA;
			}
			
		};
	}
	
	/** Renders the {@link EntityGroup} and its children
	 * @param group the {@link EntityGroup}
	 * @param gl the {@link GL20}
	 * @param render the {@link Render}
	 * @param transform the transform */
	// These warnings are fine here
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void renderGroup (EntityGroup group, GL20 gl, Render render, Matrix4 transform) {
		for (Entity entity : group.getEntities()) {
			for (int i = 0, n = entity.getComponentCount(); i < n; i++) {
				Class componentClass = entity.get(i);
				Component component = entity.get(componentClass);
				component.transform(gl, render, transform);
				render.spriteBatch.setTransformMatrix(transform);
			}
			for (int i = 0, n = entity.getComponentCount(); i < n; i++) {
				Class componentClass = entity.get(i);
				Component component = entity.get(componentClass);
				component.render(gl, render);
			}
			renderGroup(entity, gl, render, transform);
			for (int i = entity.getComponentCount() - 1; i >= 0; i--) {
				Class componentClass = entity.get(i);
				Component component = entity.get(componentClass);
				component.untransform(gl, render, transform);
				render.spriteBatch.setTransformMatrix(transform);
			}
		}
	}
	
	/** Sorts {@link Entity}s in an {@link EntityGroup} by their
	 * {@link RenderLayer}
	 * @param group the {@link EntityGroup} */
	public static void sortRenderGroup (EntityGroup group) {
		Array<Entity> entities = group.getEntities();
		entities.sort(renderComparator);
		for (Entity entity : entities) {
			sortRenderGroup(entity);
		}
	}
	
	/** @param group the {@link EntityGroup}
	 * @param delta the time delta */
	// These warnings are fine here
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void updateGroup (EntityGroup group, float delta) {
		for (Entity entity : group.getEntities()) {
			for (int i = 0, n = entity.getComponentCount(); i < n; i++) {
				Class componentClass = entity.get(i);
				Component component = entity.get(componentClass);
				component.update(delta);
			}
			updateGroup(entity, delta);
		}
	}
}
