package org.afraid.poison.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;

public class FileUtil {

	public static FileOutputStream openOutputStream(File file) throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException(new StringBuilder("File '").append(file).append("' is directory").toString());
			}
			if (file.canWrite()==false) {
				throw new IOException(new StringBuilder("File '").append(file).append("' is not writable").toString());
			}
		} else {
			File parent=file.getParentFile();
			if (parent!=null&&parent.exists()==false) {
				if (parent.mkdirs()==false) {
					throw new IOException(new StringBuilder("File '").append(file).append("' could not be created").toString());
				}
			}
		}
		return new FileOutputStream(file);
	}

	public static void write(String data, File file) throws IOException {
		OutputStream out=openOutputStream(file);
		IOUtil.copy(new StringReader(data), out);
		IOUtil.closeQuietly(out);
	}
}
