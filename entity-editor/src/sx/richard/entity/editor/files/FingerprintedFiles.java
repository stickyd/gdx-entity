
package sx.richard.entity.editor.files;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.utils.Array;

/** Contains a collection of {@link FileFingerprint}s, locates modified files
 * @author Richard Taylor */
public class FingerprintedFiles {
	
	private final HashMap<File, FileFingerprint> fingerprints = new HashMap<File, FileFingerprint>();
	
	/** Adds all the files in a directory
	 * @param file the {@link File} to the directory
	 * @param subdirectories whether to include files in subdirectories */
	public void addDirectory (File file, boolean subdirectories) throws IOException {
		if (file.isDirectory()) {
			for (File child : file.listFiles()) {
				if (!child.isDirectory()) {
					addFile(child);
				} else if (subdirectories) {
					addDirectory(child, true);
				}
			}
		} else
			throw new IllegalArgumentException("File must include subdirectories");
	}
	
	/** Adds a file to the collection, updating the fingerprint if it already
	 * exists
	 * @param file the {@link File} */
	public void addFile (File file) throws IOException {
		fingerprints.put(file, new FileFingerprint(file));
	}
	
	/** @return the files that are modified */
	public Array<File> getModifiedFiles () throws IOException {
		Array<File> files = new Array<File>();
		for (Entry<File, FileFingerprint> entry : fingerprints.entrySet()) {
			File file = entry.getKey();
			if (!entry.getValue().matches(entry.getKey())) {
				files.add(file);
			}
		}
		return files;
	}
	
}
