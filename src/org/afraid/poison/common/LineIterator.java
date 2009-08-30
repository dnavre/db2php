/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.afraid.poison.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import org.openide.util.Exceptions;

/**
 *
 * @author schnaiter
 */
public class LineIterator implements Iterable<CharSequence>, Iterator<CharSequence> {
	private BufferedReader reader;
	private String line;

	public LineIterator(BufferedReader reader) {
		this.reader=reader;
	}

	public LineIterator(Reader in) {
		this(new BufferedReader(in));
	}

	public LineIterator(File file) throws FileNotFoundException {
		this(new FileReader(file));
	}

	public LineIterator(String filename) {
		
	}


	@Override
	public Iterator<CharSequence> iterator() {
		return this;
	}

	@Override
	public boolean hasNext() {
		if (null==line) {
			try {
				line=reader.readLine();
			} catch (IOException ex) {
				Exceptions.printStackTrace(ex);
			}
		}
		return null==line;
	}

	@Override
	public CharSequence next() {
		try {
			return line;
		} finally {
			line=null;
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void close() {
		IOUtil.closeQuietly(reader);
	}

}
