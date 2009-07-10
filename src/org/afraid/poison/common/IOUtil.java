package org.afraid.poison.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 *
 * @author andreas.schnaiter
 */
public class IOUtil {

	private static final int BUFFER_SIZE=8192;

	/**
	 * close passed reader and surpress exception
	 *
	 * @param input the reader
	 */
	public static void closeQuietly(Reader input) {
		try {
			if (input!=null) {
				input.close();
			}
		} catch (IOException ex) {
		}
	}

	/**
	 * close passed writer and surpress exception
	 *
	 * @param output the writer
	 */
	public static void closeQuietly(Writer output) {
		try {
			if (output!=null) {
				output.close();
			}
		} catch (IOException ex) {
		}
	}

	/**
	 * close passed input stream and surpress exception
	 *
	 * @param input the input stream
	 */
	public static void closeQuietly(InputStream input) {
		try {
			if (input!=null) {
				input.close();
			}
		} catch (IOException ex) {
		}
	}

	/**
	 * close passed output stream and surpress exception
	 *
	 * @param output the output stream
	 */
	public static void closeQuietly(OutputStream output) {
		try {
			if (output!=null) {
				output.close();
			}
		} catch (IOException ex) {
		}
	}

	/**
	 * copy everything from input to output
	 *
	 * @param input the input
	 * @param output the output
	 * @return the number of written bytes
	 * @throws IOException
	 */
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

	/**
	 * copy everything from input to output
	 * 
	 * @param input the input
	 * @param output the output
	 * @throws IOException
	 */
	public static void copy(InputStream input, Writer output) throws IOException {
		InputStreamReader in=new InputStreamReader(input);
		copy(in, output);
	}

	/**
	 * copy everything from input to output
	 * 
	 * @param input the input
	 * @param output the output
	 * @return the number of written bytes
	 * @throws IOException
	 */
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

	/**
	 * copy everything from input to output
	 * 
	 * @param input the input
	 * @param output the output
	 * @throws IOException
	 */
	public static void copy(Reader input, OutputStream output) throws IOException {
		OutputStreamWriter out=new OutputStreamWriter(output);
		copy(input, out);
		out.flush();
	}

	/**
	 * read everthing into a string
	 *
	 * @param input the input
	 * @return content of the input
	 * @throws IOException
	 */
	public static String readString(InputStream input) throws IOException {
		StringWriter sw=new StringWriter();
		copy(input, sw);
		return sw.toString();
	}
}
