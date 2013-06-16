
package sx.richard.entity.components.graphics.gfx2;

import sx.richard.entity.Component;
import sx.richard.entity.ComponentAdapter;
import sx.richard.entity.Engine;
import sx.richard.entity.Render;
import sx.richard.entity.assets.Asset;
import sx.richard.entity.editor.Editable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

/** Draws a texture
 * @author Richard Taylor */
public class DrawTexture extends ComponentAdapter<DrawTexture> {
	
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
	/** The X position offset, additional to the transform */
	@Editable
	public float x;
	/** The Y position offset, additional to the transform */
	@Editable
	public float y;
	@Editable
	private Asset<Texture> asset;
	/** The {@link AtlasRegion} */
	private transient AtlasRegion region;
	
	/** Constructor, no asset set */
	public DrawTexture () {}
	
	/** Constructor, using an asset
	 * @param asset the {@link Asset} */
	public DrawTexture (Asset<Texture> asset) {
		this.asset = asset;
	}
	
	@Override
	public Component<DrawTexture> copy () {
		DrawTexture component = new DrawTexture();
		component.asset = asset;
		component.region = region == null ? null : new AtlasRegion(region);
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
	
	@Override
	public void edited () {
		region = null;
	}
	
	/** @return the {@link Asset} */
	public Asset<Texture> getAsset () {
		return asset;
	}
	
	@Override
	public Class<?>[] getDependencies () {
		return null;
	}
	
	@Override
	public void render (GL20 gl, Render render) {
		if (region == null) {
			Texture texture = Engine.getAssetManager().forceLoad(asset);
			region = new AtlasRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
		}
		if (region != null) {
			int width = region.getRegionWidth();
			int height = region.getRegionHeight();
			float x = this.x - width / 2f;
			float y = this.y - height / 2f;
			float originX = this.originX * width;
			float originY = this.originY * height;
			if (color != null) {
				render.spriteBatch.setColor(color);
			}
			render.spriteBatch.draw(region.getTexture(), x, y, originX, originY, width, height, scaleX, scaleY, 0, 0, 0, width,
				height, flipX, flipY);
			render.spriteBatch.setColor(Color.WHITE);
		}
	}
	
	/** @param asset the texture {@link Asset} */
	public void setTextureAsset (Asset<Texture> asset) {
		if (this.asset != asset) {
			Engine.getAssetManager().unload(this.asset);
			this.asset = asset;
			region = null;
			Engine.getAssetManager().load(asset);
			edited();
		}
	}
}
