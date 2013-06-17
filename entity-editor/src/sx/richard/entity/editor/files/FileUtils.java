
package sx.richard.entity.editor.files;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import sx.richard.entity.Component;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ObjectMap;

/** Utility methods for the editor, generic misc stuff
 * @author Richard Taylor */
public class FileUtils {
	
	private static class ProjectClassLoader extends ClassLoader {
		
		Class<?> load (String path, String name) {
			try {
				byte classByte[] = loadClassData(path);
				return defineClass(name, classByte, 0, classByte.length, null);
			} catch (Exception e) {
				System.err.println("Unable to load " + name);
				return null;
			}
		}
		
		private byte[] loadClassData (String className) throws IOException {
			File f = new File(className);
			int size = (int)f.length();
			byte buff[] = new byte[size];
			FileInputStream fis = new FileInputStream(f);
			DataInputStream dis = new DataInputStream(fis);
			dis.readFully(buff);
			dis.close();
			return buff;
		}
		
	}
	
	/** @param bin the root {@link FileHandle}
	 * @return the {@link FileHandle}s of component source files */
	public static ObjectMap<Class<? extends Component<?>>, FileHandle> getComponents (FileHandle bin) {
		ObjectMap<Class<? extends Component<?>>, FileHandle> files = new ObjectMap<Class<? extends Component<?>>, FileHandle>();
		addComponents(files, bin, bin);
		return files;
	}
	
	@SuppressWarnings("unchecked")
	private static void addComponents (ObjectMap<Class<? extends Component<?>>, FileHandle> files, FileHandle root, FileHandle bin) {
		for (FileHandle file : root.list()) {
			if (!file.isDirectory()) {
				if (file.extension().equals("class")) {
					ProjectClassLoader l = new ProjectClassLoader();
					String path = file.path();
					String name = path.substring(bin.path().length() + 1);
					Class<?> c = l.load(path, name.substring(0, name.lastIndexOf(".class")).replace("/", "."));
					if (c != null && Component.class.isAssignableFrom(c)) {
						files.put((Class<? extends Component<?>>)c, file);
					}
				}
			} else {
				addComponents(files, file, bin);
			}
		}
	}
	
}
