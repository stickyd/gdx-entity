
package sx.richard.entity.editor.project;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/** Defines the project properties
 * @author Richard Taylor */
public class Project {
	
	private String path;
	
	/** @return the {@link FileHandle} to the assets */
	public FileHandle getAssets () {
		FileHandle assets = Gdx.files.absolute(path + "/assets");
		assets.mkdirs();
		return assets;
	}
	
	public FileHandle getBin () {
		return Gdx.files.absolute(path + "/bin");
	}
	
	/** @return the {@link FileHandle} to the root of the project */
	public FileHandle getFile () {
		return Gdx.files.absolute(path);
	}
	
	/** @return the root path to the project */
	public String getPath () {
		return path;
	}
	
	/** @return the {@link FileHandle} to the source */
	public FileHandle getSource () {
		FileHandle src = Gdx.files.absolute(path + "/src");
		src.mkdirs();
		return src;
	}
	
	/** @param path the path to the project */
	public void setPath (String path) {
		this.path = path;
	}
	
}
