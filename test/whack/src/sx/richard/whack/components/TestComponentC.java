
package sx.richard.whack.components;

import sx.richard.entity.Component;
import sx.richard.entity.ComponentAdapter;

public class TestComponentC extends ComponentAdapter<TestComponentC> {
	
	@Override
	public Component<TestComponentC> copy () {
		return null;
	}
	
	@Override
	public Class<?>[] getDependencies () {
		return null;
	}
	
}
