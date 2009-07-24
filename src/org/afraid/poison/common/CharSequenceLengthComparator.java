/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.afraid.poison.common;

import java.util.Comparator;

/**
 *
 * @author schnaiter
 */
public class CharSequenceLengthComparator implements Comparator<CharSequence> {

	@Override
	public int compare(CharSequence o1, CharSequence o2) {
		return o1.length()-o2.length();
	}

}
