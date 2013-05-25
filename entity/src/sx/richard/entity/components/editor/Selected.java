
package sx.richard.entity.components.editor;

import sx.richard.entity.Component;
import sx.richard.entity.ComponentAdapter;

/** Assigned to an entity that is selected
 * @author Richard Taylor */
public class Selected extends ComponentAdapter<Selected> {
	
	/** Whether this is selected */
	public boolean on;
	
	@Override
	public Component<Selected> copy () {
		Selected component = new Selected();
		component.on = on;
		return component;
	}
	
	@Override
	public Class<?>[] getDependencies () {
		return null;
	}
	
}
