package org.afraid.poison.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;

public class FileUtil {

	public static FileInputStream openInputStream(File file) throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException(new StringBuilder("File '").append(file).append("' is directory").toString());
			}
			if (file.canRead()==false) {
				throw new IOException(new StringBuilder("File '").append(file).append("' is not readable").toString());
			}
		} else {
			throw new FileNotFoundException(new StringBuilder("File '").append(file).append("' does not exist").toString());
		}
		return new FileInputStream(file);
	}

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

	public static void writeString(String data, File file) throws IOException {
		OutputStream out=openOutputStream(file);
		IOUtil.copy(new StringReader(data), out);
		IOUtil.closeQuietly(out);
	}

	public static String readString(File file) throws IOException {
		InputStream in=openInputStream(file);
		String s=IOUtil.readString(in);
		IOUtil.closeQuietly(in);
		return s;
	}
}
