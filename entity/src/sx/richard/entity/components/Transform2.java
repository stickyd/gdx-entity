
package sx.richard.entity.components;

import sx.richard.entity.Component;
import sx.richard.entity.Render;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/** A basic component with 2D position/orientation transform
 * @author Richard Taylor */
public final class Transform2 extends Component<Transform2> {
	
	private final Vector2 position = new Vector2();
	private float rotation;
	private float scaleX = 1, scaleY = 1;
	
	/** Set to 0,0 */
	public Transform2 () {}
	
	@Override
	public void added () {}
	
	@Override
	public Component<Transform2> copy () {
		Transform2 transform = new Transform2();
		transform.position.set(position);
		transform.rotation = rotation;
		transform.scaleX = scaleX;
		transform.scaleY = scaleY;
		return transform;
	}
	
	@Override
	public Class<?>[] getDependencies () {
		return null;
	}
	
	/** @return the {@link Vector2}, not a deep copy */
	public Vector2 getPosition () {
		return position;
	}
	
	/** @return the rotation, in degrees */
	public float getRotation () {
		return rotation;
	}
	
	/** @return the X scale */
	public float getScaleX () {
		return scaleX;
	}
	
	/** @return the Y scale */
	public float getScaleY () {
		return scaleY;
	}
	
	/** Looks at a given position
	 * @param x the X
	 * @param y the Y */
	public void lookAt (float x, float y) {
		//FIXME Not actually correct
		rotation = MathUtils.radDeg * MathUtils.atan2(y - position.y, x - position.x);
	}
	
	/** Looks at a given transform
	 * @param transform the {@link Transform2} */
	public void lookAt (Transform2 transform) {
		lookAt(transform.position.x, transform.position.y);
	}
	
	/** Looks at a given transform
	 * @param vector the {@link Transform2} */
	public void lookAt (Vector2 vector) {
		lookAt(vector.x, vector.y);
	}
	
	@Override
	public void removed () {}
	
	@Override
	public void render (GL20 gl, Render render) {}
	
	/** Adds a rotation amount to the current value
	 * @param rotation the rotation amount */
	public void rotate (float rotation) {
		this.rotation += rotation;
	}
	
	/** @param vector the {@link Vector2} */
	public void setPosition (Vector2 vector) {
		position.set(vector);
	}
	
	/** @param rotation the rotation, in degrees */
	public void setRotation (float rotation) {
		this.rotation = rotation;
	}
	
	/** @param scaleX the X scale
	 * @param scaleY the Y scale */
	public void setScale (float scaleX, float scaleY) {
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}
	
	/** @param scaleX the X scale */
	public void setScaleX (float scaleX) {
		this.scaleX = scaleX;
	}
	
	/** @param scaleY the Y scale */
	public void setScaleY (float scaleY) {
		this.scaleY = scaleY;
	}
	
	@Override
	public void started () {}
	
	@Override
	public String toString () {
		return "[Transform2 position=" + position + " rot=" + rotation + " sX=" + scaleX + " sY=" + scaleY + "]";
	}
	
	@Override
	public void update (float delta) {}
	
}
