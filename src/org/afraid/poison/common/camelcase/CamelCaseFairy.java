/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.common.camelcase;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.afraid.poison.common.CollectionUtil;
import org.afraid.poison.common.FileUtil;
import org.afraid.poison.common.IOUtil;
import org.afraid.poison.common.StringMutator;
import org.afraid.poison.common.StringUtil;

/**
 * The CamelCase fairy will sprinkle magic dust on strings.
 * If you believe hard enough in it they will be correctly converted to CamelCase.
 *
 * @author poison
 */
public class CamelCaseFairy {

	private Set<String> dictionary=null;

	private synchronized Set<String> getDictionary() {
		if (null==dictionary) {
			dictionary=new LinkedHashSet<String>();
			InputStream in=null;
			BufferedReader br=null;
			try {
				// aspell dump master english|grep -Pi '^[a-z]{2,}$'|tr [A-Z] [a-z]|sort|uniq|awk '{ print length(), $0 | "sort -rn" }'|awk '{ print $2}'
				String path=new StringBuilder(FileUtil.getPackagePath(getClass())).append("/wordlist.en").toString();
				System.err.println(path);
				in=getClass().getResourceAsStream(path);

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
		}
		return dictionary;
	}

	public String toCamelCase(String s) {
		if (0==s.length() || null==s) {
			return s;
		}
		s=s.toLowerCase();
		ArrayList<String> allContained=new ArrayList<String>();
		StringBuilder sb=new StringBuilder(s);
		int pos;
		for (String word : getDictionary()) {
			pos=sb.indexOf(word);
			if (-1!=pos) {
				allContained.add(word);
				sb.delete(pos, pos+word.length());
				if (0==sb.length()) {
					break;
				}
			}
		}
		if (0!=sb.length()) {
			String regex=CollectionUtil.join(allContained, "|", new StringMutator() {

				@Override
				public String transform(Object input) {
					return Pattern.quote(input.toString());
				}
			});
			allContained.addAll(CollectionUtil.fromArray(sb.toString().split(regex)));
		}
		for (String cw : allContained) {
			/*
			pos=s.indexOf(cw);
			if (0!=pos) {
			s=s.replace(cw, StringUtil.firstCharToUpperCase(cw));
			}
			 */
			s=s.replace(cw, StringUtil.firstCharToUpperCase(cw));
		}
		System.err.println(allContained);

		return s;
	}


		/**
	 * tries to convert input string to camel case it it is all upper case or contains _
	 *
	 * @param str the string containing _
	 * @param camelCaseFairy camel case fairy, should you believe in magic
	 * @return the camel case string with the first character lower case
	 */
	public static String toCamelCase(String str, CamelCaseFairy camelCaseFairy) {
		String[] split=str.split("[^a-zA-Z0-9]+");
		StringBuilder res=new StringBuilder();
		boolean first=true;
		for (String s : split) {
			if (null!=camelCaseFairy) {
				s=camelCaseFairy.toCamelCase(s);
				if (first) {
					first=false;
					res.append(StringUtil.firstCharToLowerCase(s));
				} else {
					res.append(s);
				}
			} else {
				if (first) {
					res.append(s.toLowerCase());
					first=false;
				} else {
					res.append(StringUtil.capitalize(s));
				}
			}
		}
		return res.toString();
	}
}
