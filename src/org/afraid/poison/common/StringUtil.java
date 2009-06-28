/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author poison
 */
public class StringUtil {

	/**
	 * repeat passed string specified number of times
	 *
	 * @param string the string to repeat
	 * @param times number of tiime to repeat the string
	 * @return the repeated string
	 */
	public static String repeat(String string, int times) {
		StringBuilder str=new StringBuilder();
		for (int i=0; i<times; ++i) {
			str.append(string);
		}
		return str.toString();
	}

	/**
	 * pad passed string with passed padchar until the total length reaches the passed length
	 *
	 * @param string the string to pad
	 * @param padchar the string with which to pad
	 * @param length the desired total length of the string
	 * @return the padded string
	 */
	public static String padLeft(String string, String padchar, int length) {
		int spaces=length-string.length();
		StringBuilder str=new StringBuilder(repeat(padchar, spaces));
		str.append(string);
		return str.toString();
	}

	/**
	 * pad passed string with passed padchar until the total length reaches the passed length
	 *
	 * @param i the int to pad
	 * @param padchar the string with which to pad
	 * @param length the desired total length of the string
	 * @return the padded string
	 */
	public static String padLeft(int i, String padchar, int length) {
		return padLeft(Integer.toString(i), padchar, length);
	}

	/**
	 * pad passed string with passed padchar until the total length reaches the passed length
	 *
	 * @param l the long to pad
	 * @param padchar the string with which to pad
	 * @param length the desired total length of the string
	 * @return the padded string
	 */
	public static String padLeft(long l, String padchar, int length) {
		return padLeft(Long.toString(l), padchar, length);
	}

	/**
	 * pad passed string with passed padchar until the total length reaches the passed length
	 *
	 * @param string the string to pad
	 * @param padchar the string with which to pad
	 * @param length the desired total length of the string
	 * @return the padded string
	 */
	public static String padRight(String string, String padchar, int length) {
		int spaces=length-string.length();
		StringBuilder str=new StringBuilder(string);
		str.append(repeat(padchar, spaces));
		return str.toString();
	}

	/**
	 * pad passed string with passed padchar until the total length reaches the passed length
	 *
	 * @param i the int to pad
	 * @param padchar the string with which to pad
	 * @param length the desired total length of the string
	 * @return the padded string
	 */
	public static String padRight(int i, String padchar, int length) {
		return padRight(Integer.toString(i), padchar, length);
	}

	/**
	 * pad passed string with passed padchar until the total length reaches the passed length
	 *
	 * @param l the long to pad
	 * @param padchar the string with which to pad
	 * @param length the desired total length of the string
	 * @return the padded string
	 */
	public static String padRight(long l, String padchar, int length) {
		return padRight(Long.toString(l), padchar, length);
	}

	/**
	 * split up string at specified lengths
	 *
	 * @param str the string to split
	 * @param len the length
	 * @return the splitted string
	 */
	public static Collection<String> splitToLength(String str, int len) {
		List<String> res=new ArrayList<String>();
		int slen=str.length();
		for (int spos=0; spos<=slen; spos+=len) {
			res.add(
					str.substring(
					spos,
					spos+len>slen ? slen : len));
		}
		return res;
	}

	/**
	 * make first character if the string uppercase and the rest lowercase
	 *
	 * @param str the string to capitalize
	 * @return the capitalized string
	 */
	public static String capitalize(String str) {
		return firstCharToUpperCase(str.toLowerCase());
	}


	/**
	 * make first character if the string uppercase
	 *
	 * @param str the string to work on
	 * @return the resulting string
	 */
	public static String firstCharToUpperCase(String str) {
		return new StringBuilder(str.substring(0, 1).toUpperCase()).append(str.substring(1)).toString();
	}

	/**
	 * tries to convert input string to camel case it it is all upper case or contains _
	 * @param str the tring containing _
	 * @return the camel case string with the first character lower case
	 */
	public static String toCamelCase(String str) {
		if (!(str.contains("_")||str.equals(str.toUpperCase()))) {
			return str;
		}
		String[] split=str.split("_+");
		StringBuilder res=new StringBuilder();
		boolean first=true;
		for (String s : split) {
			if (first) {
				res.append(s.toLowerCase());
				first=false;
			} else {
				res.append(capitalize(s));
			}
		}
		return res.toString();
	}
}
