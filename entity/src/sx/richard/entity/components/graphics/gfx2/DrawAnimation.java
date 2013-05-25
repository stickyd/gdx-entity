
package sx.richard.entity.components.graphics.gfx2;

import sx.richard.entity.Asset;
import sx.richard.entity.Component;
import sx.richard.entity.ComponentAdapter;
import sx.richard.entity.Engine;
import sx.richard.entity.Render;
import sx.richard.entity.assets.RasterAnimation;
import sx.richard.entity.editor.Editable;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/** Draws a {@link RasterAnimation}
 * @author Richard Taylor */
public class DrawAnimation extends ComponentAdapter<DrawAnimation> {
	
	@Editable(type = RasterAnimation.class)
	private Asset asset;
	@Editable
	private float fps = 25f;
	private int index;
	private RasterAnimation rasterAnimation;
	private float stateTime;
	
	@Override
	public Component<DrawAnimation> copy () {
		DrawAnimation component = new DrawAnimation();
		component.asset = asset;
		component.rasterAnimation = rasterAnimation;
		component.fps = fps;
		component.stateTime = stateTime;
		component.index = index;
		return component;
	}
	
	/** @return the {@link Asset} */
	public Asset getAsset () {
		return asset;
	}
	
	@Override
	public Class<?>[] getDependencies () {
		return null;
	}
	
	@Override
	public void render (GL20 gl, Render render) {
		if (rasterAnimation == null) {
			rasterAnimation = Engine.getAssetManager().forceLoad(asset);
		}
		Drawable drawable = rasterAnimation.get(index);
		float width = drawable.getMinWidth();
		float height = drawable.getMinHeight();
		drawable.draw(render.spriteBatch, 0, 0, width, height);
	}
	
	/** @param asset the {@link Asset} */
	public void setAsset (Asset asset) {
		if (this.asset != asset) {
			Engine.getAssetManager().unload(this.asset);
			this.asset = asset;
			Engine.getAssetManager().load(asset);
			rasterAnimation = null;
		}
	}
	
	@Override
	public void update (float delta) {
		stateTime += delta;
		float duration = 1f / fps;
		float totalDuration = duration * rasterAnimation.getSize();
		index = (int)Math.floor(rasterAnimation.getSize() * (stateTime % totalDuration) / totalDuration);
	}
}
