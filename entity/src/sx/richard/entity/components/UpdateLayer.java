
package sx.richard.entity.components;

import sx.richard.entity.Component;

/** Indicates the layer for the component updates
 * @author Richard Taylor */
public class UpdateLayer extends Component<UpdateLayer> {
	
	private int layer;
	
	@Override
	public Component<UpdateLayer> copy () {
		UpdateLayer updateLayer = new UpdateLayer();
		updateLayer.layer = layer;
		return updateLayer;
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
