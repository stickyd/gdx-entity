
package sx.richard.entity.components.graphics.gfx2;

import sx.richard.entity.Component;
import sx.richard.entity.ComponentAdapter;
import sx.richard.entity.Engine;
import sx.richard.entity.Render;
import sx.richard.entity.assets.Asset;
import sx.richard.entity.components.Transform2;
import sx.richard.entity.editor.Editable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

public class DrawText extends ComponentAdapter<DrawText> {
	
	/** The {@link Color} */
	@Editable
	public Color color;
	/** The {@link Asset} */
	@Editable
	private Asset<BitmapFont> asset;
	/** The {@link BitmapFont} */
	private BitmapFont bitmapFont;
	/** The text */
	@Editable
	private String text;
	
	/** Constructor, no asset or text */
	public DrawText () {}
	
	/** @param asset the {@link Asset} */
	public DrawText (Asset<BitmapFont> asset) {
		this(asset, null);
	}
	
	/** @param asset the {@link Asset}
	 * @param text the text */
	public DrawText (Asset<BitmapFont> asset, String text) {
		this.asset = asset;
		this.text = text;
	}
	
	@Override
	public Component<DrawText> copy () {
		DrawText component = new DrawText();
		component.asset = asset;
		component.color = color == null ? null : new Color(color);
		component.text = text;
		return component;
	}
	
	/** @return the {@link Asset} */
	public Asset<BitmapFont> getAsset () {
		return asset;
	}
	
	@Override
	public Class<?>[] getDependencies () {
		return new Class<?>[] { Transform2.class };
	}
	
	/** @returnthe text */
	public String getText () {
		return text;
	}
	
	@Override
	public void render (GL20 gl, Render render) {
		if (bitmapFont == null) {
			bitmapFont = Engine.getAssetManager().forceLoad(asset);
		}
		if (text != null) {
			Transform2 transform = get(Transform2.class);
			Vector2 position = transform.getPosition();
			float x = position.x;
			float y = position.y;
			//FIXME Full transform
			if (color != null) {
				bitmapFont.setColor(color);
			}
			bitmapFont.setScale(transform.getScaleX(), transform.getScaleY());
			bitmapFont.draw(render.spriteBatch, text, x, y);
			bitmapFont.setColor(Color.WHITE);
		}
	}
	
	/** @param asset the {@link Asset} */
	public void setAsset (Asset<BitmapFont> asset) {
		if (this.asset != asset) {
			Engine.getAssetManager().load(this.asset);
			this.asset = asset;
			Engine.getAssetManager().load(asset);
			bitmapFont = null;
		}
	}
	
	/** @param text the text */
	public void setText (String text) {
		this.text = text;
	}
	
}
