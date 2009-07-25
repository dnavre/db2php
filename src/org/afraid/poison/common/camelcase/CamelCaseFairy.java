/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.common.camelcase;

import org.afraid.poison.common.string.StringOccurrence;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import org.afraid.poison.common.camelcase.Dictionary.Language;
import org.afraid.poison.common.StringUtil;

/**
 * The CamelCase fairy will sprinkle magic dust on strings.
 * If you believe hard enough in it they will be correctly converted to CamelCase.
 *
 * @author poison
 */
public class CamelCaseFairy {

	private Dictionary.Language language;
	private Set<String> dictionary=null;

	public CamelCaseFairy() {
		this(Dictionary.EN);
	}

	public CamelCaseFairy(Language language) {
		this.language=language;
	}

	private synchronized Set<String> getDictionary() {
		if (null==dictionary) {
			dictionary=Dictionary.DENGLISCH.getDictionary();
		}
		return dictionary;
	}

	public String toCamelCase(String s) {
		if (0==s.length()||null==s) {
			return s;
		}
		s=s.toLowerCase();

		List<StringOccurrence> allContained=new ArrayList<StringOccurrence>();
		String remaining=new String(s);
		Set<StringOccurrence> wordOccurrences;
		for (String word : getDictionary()) {
			if (word.equals("database")) {
				System.err.println("WTF?");
			}
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

		Collections.sort(allContained, new StringOccurrence.Comparator());
		int lastEnd=0;
		StringBuffer sb=new StringBuffer(s);
		for (StringOccurrence cw : allContained) {
			System.err.println(""+lastEnd+" - "+cw.toString());
			if (lastEnd!=cw.getStart()) {
				System.err.println("bad boy! you get reward. you get big!:"+s.substring(lastEnd, cw.getStart()));
				sb.replace(lastEnd, cw.getStart(), StringUtil.firstCharToUpperCase(s.substring(lastEnd, cw.getStart())));
			}
			sb.replace(cw.getStart(), cw.getEnd(), StringUtil.firstCharToUpperCase(cw.getString()));
			lastEnd=cw.getEnd();
		}
		System.err.println(allContained);

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
