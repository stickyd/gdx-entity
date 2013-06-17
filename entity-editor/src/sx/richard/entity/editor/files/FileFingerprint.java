
package sx.richard.entity.editor.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** Fingerprints a file, for tracking modifications
 * @author Richard Taylor */
public class FileFingerprint {
	
	private static final MessageDigest MD;
	
	static {
		try {
			MD = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	/** Creates a SHA-1 from a {@link File}
	 * @param file the {@link File}
	 * @return the SHA-1 bytes
	 * @throws IOException */
	public static synchronized byte[] sha1 (File file) throws IOException {
		InputStream fis = new FileInputStream(file);
		int n = 0;
		byte[] buffer = new byte[8192];
		while (n != -1) {
			n = fis.read(buffer);
			if (n > 0) {
				MD.update(buffer, 0, n);
			}
		}
		fis.close();
		return MD.digest();
	}
	
	/** The time the file was modified at */
	public final long lastModified;
	private final byte[] sha1;
	
	/** @param file the {@link File} */
	public FileFingerprint (File file) throws IOException {
		if (!file.exists())
			throw new RuntimeException("File does not exist");
		lastModified = file.lastModified();
		sha1 = sha1(file);
	}
	
	@Override
	public boolean equals (Object object) {
		if (object instanceof FileFingerprint) {
			FileFingerprint f = (FileFingerprint)object;
			if (f.lastModified != lastModified)
				return false;
			if (f.sha1.length != sha1.length)
				return false;
			for (int i = 0; i < sha1.length; i++) {
				if (f.sha1[i] != sha1[i])
					return false;
			}
			return true;
		}
		return false;
	}
	
	/** Determines whether a {@link File} matches this one. First by checking
	 * modified date, then checking for changes.
	 * @param file the {@link File}
	 * @return whether the file is the same, <code>false</code> if it changes */
	public boolean matches (File file) throws IOException {
		FileFingerprint fingerprint = new FileFingerprint(file);
		return fingerprint.equals(file);
	}
	
}