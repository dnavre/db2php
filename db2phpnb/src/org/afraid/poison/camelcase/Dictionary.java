/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.afraid.poison.camelcase;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.afraid.poison.common.IOUtil;

/**
 *
 * @author schnaiter
 */
public class Dictionary {
	public static abstract class  Descriptor {
		abstract public String[] getLanguageFiles();
		abstract public String getId();
		
		public Set<String> getWordList() {
			if (1==getLanguageFiles().length) {
				return readDictionary(getLanguageFiles()[0]);
			}
			Set<String> dictionary=new LinkedHashSet<String>();
			for (String l : getLanguageFiles()) {
				dictionary.addAll(readDictionary(l));
			}
			return dictionary;
		}
	}
	
	public static abstract class  DescriptorSorted extends Descriptor {
		Comparator<CharSequence> comparator;

		public DescriptorSorted(Comparator<CharSequence> comparator) {
			this.comparator=comparator;
		}
		
		@Override
		public Set<String> getWordList() {
			ArrayList<String> list=new ArrayList<String>(super.getWordList());
			Collections.sort(list, comparator);
			return new LinkedHashSet<String>(list);
		}
		
	}

	public static LinkedHashSet<String> readDictionary(InputStream in) {
		BufferedReader br=null;
		LinkedHashSet<String> dictionary=new LinkedHashSet<String>();
		try {

			// aspell dump master english|grep -Pi '^[a-z]{2,}$'|tr [A-Z] [a-z]|sort|uniq|awk '{ print length(), $0 | "sort -rn" }'|awk '{ print $2}'
			br=new BufferedReader(new InputStreamReader(in));
			String word;
			while (null!=(word=br.readLine())) {
				dictionary.add(word);
			}
		} catch (FileNotFoundException ex) {
			Logger.getLogger(CamelCaseFairy.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(CamelCaseFairy.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			IOUtil.closeQuietly(br);
		}
		return dictionary;
	}

	public static LinkedHashSet<String> readDictionary(String path) {
		LinkedHashSet<String> dictionary=new LinkedHashSet<String>();
		InputStream in=null;
		in=Dictionary.class.getResourceAsStream(path);
		if (null==in) {
			return dictionary;
		}
		dictionary=readDictionary(in);
		IOUtil.closeQuietly(in);
		return dictionary;
	}
}
