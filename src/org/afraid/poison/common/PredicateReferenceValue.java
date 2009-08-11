/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.afraid.poison.common;

/**
 *
 * @author poison
 */
abstract public class PredicateReferenceValue implements Predicate {
	private Object referenceValue;

	public PredicateReferenceValue(Object referenceValue) {
		this.referenceValue=referenceValue;
	}

	/**
	 * @return the referenceValue
	 */
	public Object getReferenceValue() {
		return referenceValue;
	}

}
