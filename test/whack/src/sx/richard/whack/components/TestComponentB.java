
package sx.richard.whack.components;

import sx.richard.entity.Component;
import sx.richard.entity.ComponentAdapter;

public class TestComponentB extends ComponentAdapter<TestComponentB> {
	
	@Override
	public Component<TestComponentB> copy () {
		return null;
	}
	
	@Override
	public Class<?>[] getDependencies () {
		return null;
	}
	
}
