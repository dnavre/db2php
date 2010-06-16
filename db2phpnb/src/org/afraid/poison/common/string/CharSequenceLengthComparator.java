/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.afraid.poison.common.string;

import java.util.Comparator;

/**
 *
 * @author schnaiter
 */
public class CharSequenceLengthComparator implements Comparator<CharSequence> {
	private boolean ascending;

	public CharSequenceLengthComparator() {
		this(true);
	}

	public CharSequenceLengthComparator(boolean ascending) {
		setAscending(ascending);
	}


	@Override
	public int compare(CharSequence o1, CharSequence o2) {
		if (isAscending()) {
			return o1.length()-o2.length();
		}
		return o2.length()-o1.length();
	}

	/**
	 * @return the ascending
	 */
	public boolean isAscending() {
		return ascending;
	}

	/**
	 * @param ascending the ascending to set
	 */
	public void setAscending(boolean ascending) {
		this.ascending=ascending;
	}

}
