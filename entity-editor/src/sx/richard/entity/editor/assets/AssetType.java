
package sx.richard.entity.editor.assets;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ObjectMap;

/** Defines the type of asset, for searching etc.
 * @author Richard Taylor */
public enum AssetType {
	
	/** A font */
	FONT(BitmapFont.class, "fnt"),
	/** A JSON file */
	JSON(ObjectMap.class, "json"),
	/** A texture */
	TEXTURE(Texture.class, "png", "jpg", "jpeg", "cim", "etc1");
	
	/** @param type the type
	 * @return the {@link AssetType} */
	public static AssetType from (Class<?> type) {
		for (AssetType assetType : values()) {
			if (assetType.type == type)
				return assetType;
		}
		return null;
	}
	
	private final String[] extensions;
	
	private final Class<?> type;
	
	private AssetType (Class<?> type, String ... extensions) {
		this.type = type;
		this.extensions = extensions;
	}
	
	/** @param file the {@link FileHandle}
	 * @return whether the extension matches this type */
	public boolean matching (FileHandle file) {
		return matching(file.name());
	}
	
	/** @param fileName the file name or path
	 * @return whether the extension matches this type */
	public boolean matching (String fileName) {
		if (fileName.contains(".")) {
			String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
			for (String e : extensions) {
				if (e.equals(extension))
					return true;
			}
		}
		return false;
	}
	
}
