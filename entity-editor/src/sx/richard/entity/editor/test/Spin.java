
package sx.richard.entity.editor.test;

import sx.richard.entity.Component;
import sx.richard.entity.ComponentAdapter;
import sx.richard.entity.components.Transform2;
import sx.richard.entity.editor.Editable;

public class Spin extends ComponentAdapter<Spin> {
	
	@Editable
	private float rate;
	
	@Override
	public Component<Spin> copy () {
		Spin component = new Spin();
		component.rate = rate;
		return component;
	}
	
	@Override
	public Class<?>[] getDependencies () {
		return null;
	}
	
	@Override
	public void update (float delta) {
		get(Transform2.class).rotate(rate * delta);
	}
}
