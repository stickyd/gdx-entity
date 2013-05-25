
package sx.richard.entity.components;

import sx.richard.entity.Component;
import sx.richard.entity.ComponentAdapter;

/** Indicates the layer for the component rendering
 * @author Richard Taylor */
public class RenderLayer extends ComponentAdapter<RenderLayer> {
	
	private int layer;
	
	@Override
	public Component<RenderLayer> copy () {
		RenderLayer updateLayer = new RenderLayer();
		updateLayer.layer = layer;
		return updateLayer;
	}
	
	@Override
	public Class<?>[] getDependencies () {
		return null;
	}
	
	/** @return the layer */
	public int getLayer () {
		return layer;
	}
	
	/** @param layer the layer */
	public void setLayer (int layer) {
		this.layer = layer;
	}
	
}
