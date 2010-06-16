/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.afraid.poison.common.string;

/**
 *
 * @author poison
 */
public interface StringMutator {
	/**
	 * transform input
	 *
	 * @param input the value to transform
	 * @return result of the transformation
	 */
	public String transform(Object input);
}
