/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.camelcase;

import org.afraid.poison.common.string.StringOccurrence;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.afraid.poison.camelcase.Dictionary.Descriptor;
import org.afraid.poison.common.StringUtil;

/**
 * The CamelCase fairy will sprinkle magic dust on strings.
 * If you believe hard enough in it they will be correctly converted to CamelCase.
 *
 * @author poison
 */
public class CamelCaseFairy {

	private Dictionary.Descriptor descriptor;
	private Set<String> wordList=null;
	private Map<String, String> cache=new HashMap<String, String>();

	/**
	 * CTOR
	 *
	 * @param descriptor the descriptor to use
	 */
	public CamelCaseFairy(Descriptor descriptor) {
		this.descriptor=descriptor;
	}

	/**
	 * get wordList data instance
	 *
	 * @return the wordList data
	 */
	private synchronized Set<String> getWordList() {
		if (null==wordList) {
			wordList=descriptor.getWordList();
		}
		return wordList;
	}

	/**
	 * get occurrences of words from word list in string
	 * 
	 * @param s the string to search for occurrences
	 * @return occurrences of words from dictionary
	 */
	public Set<StringOccurrence> getWordOccurrences(String s) {
		List<StringOccurrence> allContained=new ArrayList<StringOccurrence>();
		String remaining=new String(s);
		Set<StringOccurrence> wordOccurrences;
		for (String word : getWordList()) {
			if (remaining.contains(word)) {
				wordOccurrences=StringUtil.findOccurrences(word, s);
				if (!wordOccurrences.isEmpty()) {
					for (StringOccurrence ac : allContained) {
						ac.removeSubSequences(wordOccurrences);
						if (wordOccurrences.isEmpty()) {
							break;
						}
					}
					allContained.addAll(wordOccurrences);
					remaining=remaining.replaceAll(Pattern.quote(word), "");
					if (0==remaining.length()) {
						break;
					}
				}
			}
		}

		Collections.sort(allContained, new StringOccurrence.PositionComparator());
		return new LinkedHashSet<StringOccurrence>(allContained);
	}

	/**
	 * convert passed string to CamelCase using magic
	 *
	 * @param s the string to convert to CamelCase
	 * @return the CamelCase string
	 */
	public String toCamelCase(String s) {
		if (0==s.length()||null==s) {
			return s;
		}
		s=s.toLowerCase();
		String cr=cache.get(s);
		if (null!=cr) {
			return cr;
		}
		Set<StringOccurrence> allContained=getWordOccurrences(s);
		int lastEnd=0;
		StringBuffer sb=new StringBuffer(s);
		for (StringOccurrence cw : allContained) {
			if (lastEnd!=cw.getStart()) {
				// CamelCase for gaps
				sb.replace(lastEnd, cw.getStart(), StringUtil.firstCharToUpperCase(s.substring(lastEnd, cw.getStart())));
			}
			sb.replace(cw.getStart(), cw.getEnd(), StringUtil.firstCharToUpperCase(cw.getString()));
			lastEnd=cw.getEnd();
		}
		if (lastEnd!=sb.length()) {
			sb.replace(lastEnd, sb.length(), StringUtil.firstCharToUpperCase(s.substring(lastEnd, sb.length())));
		}
		cache.put(s, sb.toString());
		return sb.toString();
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
		if(split.length<2 && null==camelCaseFairy) {
			if (1==split.length && 0!=split[0].length() && split[0].equals(split[0].toUpperCase())) {
				return split[0].toLowerCase();
			}
			return str;
		}
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
