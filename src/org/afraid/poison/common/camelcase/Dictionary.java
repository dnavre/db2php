/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.afraid.poison.common.camelcase;

import org.afraid.poison.common.string.CharSequenceLengthComparator;
import org.afraid.poison.common.*;
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

/**
 *
 * @author schnaiter
 */
public class Dictionary {
	public static abstract class  Language {
		abstract public String[] getLanguageFiles();
		public Set<String> getDictionary() {
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
	
	public static abstract class  LanguageSorted extends Language {
		Comparator<CharSequence> comparator;

		public LanguageSorted(Comparator<CharSequence> comparator) {
			this.comparator=comparator;
		}
		
		@Override
		public Set<String> getDictionary() {
			ArrayList<String> list=new ArrayList<String>(super.getDictionary());
			Collections.sort(list, comparator);
			return new LinkedHashSet<String>(list);
		}
		
	}

	public final static Language EN=new Language() {

		@Override
		public String[] getLanguageFiles() {
			return new String[] {"en"};
		}
	};

	public final static Language DE=new Language() {

		@Override
		public String[] getLanguageFiles() {
			return new String[] {"de"};
		}
	};

	public final static Language DENGLISCH=new LanguageSorted(new CharSequenceLengthComparator(false)) {

		@Override
		public String[] getLanguageFiles() {
			return new String[] {"de", "en"};
		}
	};

	public static LinkedHashSet<String> readDictionary(String lang) {
			LinkedHashSet<String> dictionary=new LinkedHashSet<String>();
			InputStream in=null;
			BufferedReader br=null;
			try {
				// aspell dump master english|grep -Pi '^[a-z]{2,}$'|tr [A-Z] [a-z]|sort|uniq|awk '{ print length(), $0 | "sort -rn" }'|awk '{ print $2}'
				String path=new StringBuilder(FileUtil.getPackagePath(CamelCaseFairy.class)).append("/wordlist.").append(lang).toString();
				in=Dictionary.class.getResourceAsStream(path);

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
				IOUtil.closeQuietly(in);
			}
			return dictionary;
		}
}
