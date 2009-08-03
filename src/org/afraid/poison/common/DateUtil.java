/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author poison
 */
public class DateUtil {

	private static final DateFormat formatterISO8601=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	/**
	 * return date formatted according to ISO-8601
	 *
	 * @param date date to format
	 * @return date formatted according to ISO-8601
	 */
	public static String formatISO8601(Date date) {
		return formatterISO8601.format(date);
	}
}
