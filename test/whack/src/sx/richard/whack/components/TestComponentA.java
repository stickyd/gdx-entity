
package sx.richard.whack.components;

import sx.richard.entity.Component;
import sx.richard.entity.ComponentAdapter;

public class TestComponentA extends ComponentAdapter<TestComponentA> {
	
	@Override
	public Component<TestComponentA> copy () {
		return null;
	}
	
	@Override
	public Class<?>[] getDependencies () {
		return null;
	}
	
}
