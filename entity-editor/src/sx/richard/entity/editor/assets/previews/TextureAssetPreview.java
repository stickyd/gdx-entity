
package sx.richard.entity.editor.assets.previews;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;

/** The {@link AssetPreview} for textures
 * @author Richard Taylor */
public class TextureAssetPreview extends AssetPreview {
	
	private Texture texture;
	
	/** @see AssetPreview#AssetPreview(AssetPreviewListener, FileHandle) */
	public TextureAssetPreview (AssetPreviewListener listener, FileHandle fileHandle) {
		super(listener, fileHandle);
	}
	
	@Override
	public void dispose () {
		if (texture != null) {
			texture.dispose();
			texture = null;
		}
	}
	
	@Override
	protected Actor createPreview () {
		Image image = new Image(texture = new Texture(fileHandle));
		image.setScaling(Scaling.fit);
		return image;
	}
	
}
