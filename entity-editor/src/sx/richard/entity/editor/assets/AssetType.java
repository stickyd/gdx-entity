
package sx.richard.entity.editor.assets;

import com.badlogic.gdx.files.FileHandle;

/** Defines the type of asset, for searching etc.
 * @author Richard Taylor */
public enum AssetType {
	
	/** A font */
	FONT("fnt"),
	/** A JSON file */
	JSON("json"),
	/** A texture */
	TEXTURE("png", "jpg", "jpeg", "cim", "etc1");
	
	private final String[] extensions;
	
	private AssetType (String ... extensions) {
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
