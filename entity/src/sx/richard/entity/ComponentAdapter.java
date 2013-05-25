
package sx.richard.entity;

import com.badlogic.gdx.graphics.GL20;

/** A basic {@link Component} adapter, does not provide a copy method
 * @author Richard Taylor */
public abstract class ComponentAdapter<T extends Component<T>> extends Component<T> {
	
	@Override
	public void added () {}
	
	@Override
	public Class<?>[] getDependencies () {
		return null;
	}
	
	@Override
	public void removed () {}
	
	@Override
	public void render (GL20 gl, Render render) {}
	
	@Override
	public void started () {}
	
	@Override
	public void update (float delta) {}
	
}
