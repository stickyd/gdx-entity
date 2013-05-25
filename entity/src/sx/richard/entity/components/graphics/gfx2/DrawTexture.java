
package sx.richard.entity.components.graphics.gfx2;

import sx.richard.entity.Asset;
import sx.richard.entity.Component;
import sx.richard.entity.ComponentAdapter;
import sx.richard.entity.Engine;
import sx.richard.entity.Render;
import sx.richard.entity.components.Transform2;
import sx.richard.entity.editor.Editable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/** Draws a texture, requires a {@link Transform2}
 * @author Richard Taylor */
public class DrawTexture extends ComponentAdapter<DrawTexture> {
	
	@Editable(type = { Texture.class, AtlasRegion.class, TextureRegion.class })
	private Asset asset;
	/** The {@link Color}, will use whatever is set before if <code>null</code> */
	@Editable
	public Color color = null;
	/** Whether to flip in the X direction */
	@Editable
	public boolean flipX;
	/** Whether to flip in the Y direction */
	@Editable
	public boolean flipY;
	/** The X origin, as a multiple of the height */
	@Editable
	public float originX = 0.5f;
	/** The Y origin, as a multiple of the width */
	@Editable
	public float originY = 0.5f;
	/** The X scale */
	@Editable
	public float scaleX = 1f;
	/** The Y scale */
	@Editable
	public float scaleY = 1f;
	/** The {@link Texture} */
	private Texture texture;
	/** The X position offset, additional to the transform */
	@Editable
	public float x;
	/** The Y position offset, additional to the transform */
	@Editable
	public float y;
	
	/** Constructor, no asset set */
	public DrawTexture () {}
	
	/** Constructor, using an asset
	 * @param asset the {@link Asset} */
	public DrawTexture (Asset asset) {
		this.asset = asset;
	}
	
	@Override
	public Component<DrawTexture> copy () {
		DrawTexture component = new DrawTexture();
		component.asset = asset;
		component.texture = texture;
		component.color = color == null ? null : new Color(color);
		component.flipX = flipX;
		component.flipY = flipY;
		component.originX = originX;
		component.originY = originY;
		component.scaleX = scaleX;
		component.scaleY = scaleY;
		component.x = x;
		component.y = y;
		return component;
	}
	
	/** @return the {@link Asset} */
	public Asset getAsset () {
		return asset;
	}
	
	@Override
	public Class<?>[] getDependencies () {
		return new Class<?>[] { Transform2.class };
	}
	
	@Override
	public void render (GL20 gl, Render render) {
		if (texture == null) {
			texture = Engine.getAssetManager().forceLoad(asset);
		}
		if (texture != null) {
			Transform2 transform = get(Transform2.class);
			Vector2 position = transform.getPosition();
			int width = texture.getWidth();
			int height = texture.getHeight();
			float x = position.x + this.x - width / 2f;
			float y = position.y + this.y - height / 2f;
			float rotation = transform.getRotation();
			float scaleX = transform.getScaleX() * this.scaleX;
			float scaleY = transform.getScaleY() * this.scaleY;
			float originX = this.originX * width;
			float originY = this.originY * height;
			if (color != null) {
				render.spriteBatch.setColor(color);
			}
			render.spriteBatch.draw(texture, x, y, originX, originY, width, height, scaleX, scaleY, rotation, 0, 0, width, height,
				flipX, flipY);
			render.spriteBatch.setColor(Color.WHITE);
		}
	}
	
	/** @param asset the texture {@link Asset} */
	public void setTextureAsset (Asset asset) {
		if (this.asset != asset) {
			Engine.getAssetManager().unload(this.asset);
			this.asset = asset;
			texture = null;
			Engine.getAssetManager().load(asset);
		}
	}
}
