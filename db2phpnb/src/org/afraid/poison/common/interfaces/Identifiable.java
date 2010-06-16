/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.common.interfaces;

/**
 * for objects Identifiable by a PK
 *
 * @author Andreas Schnaiter <rc.poison@gmail.com>
 */
public interface Identifiable {

	/**
	 * @return the id
	 */
	public Integer getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id);
}
