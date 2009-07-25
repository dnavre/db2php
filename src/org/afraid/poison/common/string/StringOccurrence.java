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

	public static class Comparator implements java.util.Comparator<StringOccurrence> {

		public int compare(StringOccurrence o1, StringOccurrence o2) {
			return o1.getStart()-o2.getStart();
		}
	}

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

	public boolean isInSequence(StringOccurrence occurrence) {
		return isInSequence(occurrence.getStart()) || isInSequence(occurrence.getEnd());
	}

	public boolean isInSequence (int pos) {
		return isBetween(pos, getStart(), getEnd());
	}
	
	private static boolean isBetween(int i, int start, int end) {
		return i>start && i<end;
	}

	public void removeSubSequences(Set<StringOccurrence> occurrences) {
		Iterator<StringOccurrence> i=occurrences.iterator();
		StringOccurrence stringOccurrence;
		while (i.hasNext()) {
			stringOccurrence=i.next();
			if (isInSequence(stringOccurrence)) {
				System.err.println("removing:" + stringOccurrence.toString() + " because of:" + toString());
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
