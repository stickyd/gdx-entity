
package sx.richard.entity.components.editor;

import sx.richard.entity.Component;
import sx.richard.entity.ComponentAdapter;

/** Suggests that debug rendering should be skipped
 * @author Richard Taylor */
public class Debug extends ComponentAdapter<Debug> {
	
	/** Whether to render the debug shapes */
	public boolean render = true;
	
	@Override
	public Component<Debug> copy () {
		Debug component = new Debug();
		component.render = render;
		return component;
	}
	
	@Override
	public Class<?>[] getDependencies () {
		return null;
	}
	
}
