
package sx.richard.entity.editor.components;

import sx.richard.entity.Component;
import sx.richard.entity.ComponentAdapter;
import sx.richard.entity.Engine;
import sx.richard.entity.Render;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/** Renders a debug grid, variable size
 * @author Richard Taylor */
public class DebugGrid extends ComponentAdapter<DebugGrid> {
	
	private int size = 32;
	
	@Override
	public Component<DebugGrid> copy () {
		DebugGrid component = new DebugGrid();
		component.size = size;
		return component;
	}
	
	@Override
	public Class<?>[] getDependencies () {
		return null;
	}
	
	/** @return the grid size */
	public int getSize () {
		return size;
	}
	
	@Override
	public void render (GL20 gl, Render render) {
		if (Engine.debug) {
			render.spriteBatch.end();
			render.shapes.begin(ShapeType.Line);
			render.shapes.setColor(0.15f, 0.15f, 0.15f, 1f);
			for (int x = -size * 32; x < size * 32; x += size) {
				render.shapes.line(x, -size * 32, x, size * 32);
			}
			for (int y = -size * 32; y < size * 32; y += size) {
				render.shapes.line(-size * 32, y, size * 32, y);
			}
			render.shapes.end();
			render.spriteBatch.begin();
		}
	}
	
	/** @param size the grid size */
	public void setSize (int size) {
		if (size <= 0)
			throw new IllegalArgumentException("Size must not be <= 0");
		this.size = size;
	}
	
}
