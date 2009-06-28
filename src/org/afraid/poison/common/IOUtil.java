package org.afraid.poison.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public class IOUtil {

	private static final int BUFFER_SIZE=8192;

	public static void closeQuietly(Reader input) {
		try {
			if (input!=null) {
				input.close();
			}
		} catch (IOException ex) {
		}
	}

	public static void closeQuietly(Writer output) {
		try {
			if (output!=null) {
				output.close();
			}
		} catch (IOException ex) {
		}
	}

	public static void closeQuietly(InputStream input) {
		try {
			if (input!=null) {
				input.close();
			}
		} catch (IOException ex) {
		}
	}

	public static void closeQuietly(OutputStream output) {
		try {
			if (output!=null) {
				output.close();
			}
		} catch (IOException ex) {
		}
	}

	public static long copy(InputStream input, OutputStream output) throws IOException {
		byte[] buffer=new byte[BUFFER_SIZE];
		long cnt=0;
		int n=0;
		while (-1!=(n=input.read(buffer))) {
			output.write(buffer, 0, n);
			cnt+=n;
		}
		return cnt;
	}

	public static void copy(InputStream input, Writer output) throws IOException {
		InputStreamReader in=new InputStreamReader(input);
		copy(in, output);
	}

	public static long copy(Reader input, Writer output) throws IOException {
		char[] buffer=new char[BUFFER_SIZE];
		long cnt=0;
		int n=0;
		while (-1!=(n=input.read(buffer))) {
			output.write(buffer, 0, n);
			cnt+=n;
		}
		return cnt;
	}

	public static void copy(Reader input, OutputStream output) throws IOException {
		OutputStreamWriter out=new OutputStreamWriter(output);
		copy(input, out);
		out.flush();
	}

	public static String readString(InputStream input) throws IOException {
		StringWriter sw=new StringWriter();
		copy(input, sw);
		return sw.toString();
	}
}
