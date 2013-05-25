
package sx.richard.entity.components.graphics.core;

import sx.richard.entity.Component;
import sx.richard.entity.ComponentAdapter;
import sx.richard.entity.Render;

import com.badlogic.gdx.graphics.GL20;

/** Calls glClearColor, and glClear(GL_COLOR_BUFFER_BIT)
 * @author Richard Taylor */
public class ClearColor extends ComponentAdapter<ClearColor> {
	
	public float r = 0.2f, g = 0.2f, b = 0.2f, a = 1;
	
	@Override
	public Component<ClearColor> copy () {
		ClearColor component = new ClearColor();
		component.r = r;
		component.g = g;
		component.b = b;
		component.a = a;
		return component;
	}
	
	@Override
	public void render (GL20 gl, Render render) {
		gl.glClearColor(r, g, b, a);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
}
