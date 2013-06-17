
package sx.richard.whack.components;

import sx.richard.entity.Component;
import sx.richard.entity.ComponentAdapter;

public class TestComponentE extends ComponentAdapter<TestComponentE> {
	
	@Override
	public Component<TestComponentE> copy () {
		return null;
	}
	
	@Override
	public Class<?>[] getDependencies () {
		return null;
	}
	
}
