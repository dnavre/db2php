/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.common;

import org.afraid.poison.common.string.StringMutatorPrependAppend;
import org.afraid.poison.common.string.StringMutator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author poison
 */
public class CollectionUtil {

	/**
	 * join elements as string separated by the passed delimiter
	 *
	 * @param c the collection of elements to join
	 * @param delim the delimiter
	 * @return the delimited string
	 */
	public static String join(Collection<?> c, CharSequence delim) {
		return join(c, delim, null);
	}

	/**
	 * join elements as string separated by the passed delimiter and prepend/append passed parameter to each element
	 *
	 * @param c the collection of elements to join
	 * @param delim the delimiter
	 * @param prepend the string to prepend to each element
	 * @param append the string to append to each element
	 * @return the delimited string
	 */
	public static String join(Collection<?> c, CharSequence delim, CharSequence prepend, CharSequence append) {
		return join(c, delim, new StringMutatorPrependAppend(prepend, append));
	}

	/**
	 * join elements as string separated by the passed delimiter
	 *
	 * @param c the collection of elements to join
	 * @param delim the delimiter
	 * @param mutator the string mutator to use
	 * @return the delimited string
	 */
	public static String join(Collection<?> c, CharSequence delim, StringMutator mutator) {
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
	public static int getStringLengthMax(Collection<?> c) {
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
	public static int getStringLengthMin(Collection<?> c) {
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

	/**
	 * create list from array
	 *
	 * @param <T>
	 * @param values
	 * @return array values in list
	 */
	@SuppressWarnings("unchecked")
	public static <T> ArrayList<T> fromArray(T... values) {
		ArrayList<T> list=new ArrayList<T>();
		for (Object o : values) {
			list.add((T) o);
		}
		return list;
	}

	/**
	 * filter list of random types and only return only the matching type
	 *
	 * @param <T>
	 * @param clazz
	 * @param values
	 * @return list of <T>
	 */
	public static <T> ArrayList<T> filterType(Class<T> clazz, Iterable<?> values) {
		ArrayList<T> list=new ArrayList<T>();
		for (Object o : values) {
			if (clazz.isInstance(o)) {
				list.add(clazz.cast(o));
			}
		}
		return list;
	}

	/**
	 * filter list of random types and only return only the matching type
	 *
	 * @param <T>
	 * @param clazz
	 * @param values
	 * @return list of <T>
	 */
	public static <T> ArrayList<T> filterType(Class<T> clazz, Object[] values) {
		return filterType(clazz, Arrays.asList(values));
	}
}
