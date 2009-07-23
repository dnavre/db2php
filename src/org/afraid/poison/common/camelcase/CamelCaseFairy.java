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
import org.afraid.poison.common.FileUtil;
import org.afraid.poison.common.IOUtil;
import org.afraid.poison.common.StringUtil;

/**
 * The CamelCase fairy will sprinkle magic dust on strings.
 * If you believe hard enough in it they will be correctly converted to CamelCase.
 *
 * @author poison
 */
public class CamelCaseFairy {

	private Set<String> dictionary=null;

	private Set<String> getDictionary() {
		if (null==dictionary) {
			dictionary=new LinkedHashSet<String>();
			InputStream in=null;
			BufferedReader br=null;
			try {
				// aspell dump master english|grep -Pi '^[a-z]{2,}$'|tr [A-Z] [a-z]|sort|uniq|awk '{ print length(), $0 | "sort -rn" }'|awk '{ print $2}'
				String path=new StringBuilder(FileUtil.getPackagePath(getClass())).append("/wordlist.en").toString();
				//String path=new StringBuilder("/").append(getClass().getPackage().getName().replace(".", "/")).append("/wordlist.en").toString();
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
		ArrayList<String> allContained=new ArrayList<String>();
		StringBuilder sb=new StringBuilder(s.toLowerCase());
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
}
