/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.db2php.camelcase;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author poison
 */
public class CamelCaseFairy {

	
	private Set<String> dictionary=null;

	private Set<String> getDictionary() {
		if (null==dictionary) {
			BufferedReader in=null;
			dictionary=new LinkedHashSet<String>();
			try {
				// aspell dump master english|grep -Pi '^[a-z]{2,}$'|tr [A-Z] [a-z]|sort|uniq|awk '{ print length(), $0 | "sort -rn" }'|awk '{ print $2}'
				in=new BufferedReader(new FileReader("/home/andreas.schnaiter/files/www/wordlist.en"));
				String word;
				while (null!=(word=in.readLine())) {
					dictionary.add(word);
				}
			} catch (FileNotFoundException ex) {
				Logger.getLogger(CamelCaseFairy.class.getName()).log(Level.SEVERE, null, ex);
			} catch (IOException ex) {
				Logger.getLogger(CamelCaseFairy.class.getName()).log(Level.SEVERE, null, ex);
			} finally {
				try {
					in.close();
				} catch (IOException ex) {
					Logger.getLogger(CamelCaseFairy.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		return dictionary;
	}

	public String toCamelCase(String s) {
		ArrayList<String> allContained=new ArrayList<String>();
		int pos;
		StringBuilder sb=new StringBuilder(s);
		for (String word : getDictionary()) {
			pos=sb.indexOf(word);
			if (-1!=pos) {
				allContained.add(word);
				sb.replace(pos, pos+word.length()-1, "");
				if (0==sb.length()) {
					break;
				}
			}
		}

		return s;
	}
}
