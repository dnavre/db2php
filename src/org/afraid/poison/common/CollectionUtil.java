/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.common;

import java.util.Collection;

/**
 *
 * @author poison
 */
public class CollectionUtil {

	/**
	 * join elements as string seperated by the passed delimiter
	 *
	 * @param c the collection of elements to join
	 * @param delim the delimiter
	 * @return the delimited string
	 */
	public static String join(Collection c, String delim) {
		return join(c, delim, null);
	}

	/**
	 * join elements as string seperated by the passed delimiter and prepend/append passed parameter to each element
	 *
	 * @param c the collection of elements to join
	 * @param delim the delimiter
	 * @param prepend the string to prepend to each element
	 * @param append the string to append to each element
	 * @return the delimited string
	 */
	public static String join(Collection<Object> c, String delim, String prepend, String append) {
		return join(c, delim, new StringMutatorPrependAppend(prepend, append));
	}

	/**
	 * join elements as string seperated by the passed delimiter
	 *
	 * @param c the collection of elements to join
	 * @param delim the delimiter
	 * @param mutator
	 * @return the delimited string
	 */
	public static String join(Collection c, String delim, StringMutator mutator) {
		StringBuilder s=new StringBuilder();
		boolean first=true;
		for (Object o : c) {
			if (!first) {
				s.append(delim);
			} else {
				first=false;
			}
			if (null!=mutator) {
				s.append(mutator.transform(o));
			} else {
				s.append(o);
			}
		}

		return s.toString();
	}

	/**
	 * get maximum length of contained objects toString()
	 * @param c list of objects
	 * @return the max length
	 */
	public static int getStringLengthMax(Collection c) {
		int max=0;
		int len;
		for (Object o : c) {
			len=o.toString().length();
			if (len>max) {
				max=len;
			}
		}
		return max;
	}

	/**
	 * get minimum length of contained objects toString()
	 * @param c list of objects
	 * @return the minimum length
	 */
	public static int getStringLengthMin(Collection c) {
		int min=0;
		int len;
		for (Object o : c) {
			len=o.toString().length();
			if (len<min) {
				min=len;
			}
		}
		return min;
	}
}
