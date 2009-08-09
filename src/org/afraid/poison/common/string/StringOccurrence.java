/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.common.string;

import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author poison
 */
public class StringOccurrence {

	private String string;
	private int start;

	/**
	 * Compares the positions of the occurrences
	 */
	public static class PositionComparator implements java.util.Comparator<StringOccurrence> {

		@Override
		public int compare(StringOccurrence o1, StringOccurrence o2) {
			return o1.getStart()-o2.getStart();
		}
	}

	/**
	 * CTOR
	 *
	 * @param word the word
	 * @param start the start position
	 */
	public StringOccurrence(String word, int start) {
		this.string=word;
		this.start=start;
	}

	/**
	 * @return the string
	 */
	public String getString() {
		return string;
	}

	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @return the end
	 */
	public int getEnd() {
		return getStart()+getString().length();
	}

	/**
	 * check if the passed occurrence position overlaps with this one
	 *
	 * @param occurrence the occurrence to check for overlapping
	 * @return true if the position overlaps
	 */
	public boolean isInSequence(StringOccurrence occurrence) {
		return isInSequence(occurrence.getStart()) || isInSequence(occurrence.getEnd());
	}

	/**
	 * check if the passed position is ours
	 *
	 * @param pos the string start position to check
	 * @return true if the position overlaps
	 */
	public boolean isInSequence (int pos) {
		return isBetween(pos, getStart(), getEnd());
	}

	/**
	 * check if i is between start and end
	 *
	 * @param i position
	 * @param start start position
	 * @param end end position
	 * @return true if i is between start and end
	 */
	private static boolean isBetween(int i, int start, int end) {
		return i>start && i<end;
	}

	/**
	 * remove all occurrences which overlap this occurrence from the passed collection
	 * 
	 * @param occurrences the occurrences to check for overlapping
	 */
	public void removeSubSequences(Set<StringOccurrence> occurrences) {
		Iterator<StringOccurrence> i=occurrences.iterator();
		StringOccurrence stringOccurrence;
		while (i.hasNext()) {
			stringOccurrence=i.next();
			if (isInSequence(stringOccurrence)) {
				i.remove();
			}
		}
	}



	@Override
	public boolean equals(Object obj) {
		if (obj==null) {
			return false;
		}
		if (getClass()!=obj.getClass()) {
			return false;
		}
		final StringOccurrence other=(StringOccurrence) obj;
		if ((this.string==null) ? (other.string!=null) : !this.string.equals(other.string)) {
			return false;
		}
		if (this.start!=other.start) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash=3;
		hash=79*hash+(this.string!=null ? this.string.hashCode() : 0);
		hash=79*hash+this.start;
		return hash;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(getStart()).append(":").append(getEnd()).append("@").append(getString()).toString();
	}
}
