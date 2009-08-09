/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.afraid.poison.db2php.generator;

import java.util.Set;

/**
 *
 * @author poison
 */
public class Index {
	private String name;
	private int type;
	private Set<Field> fields;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name=name;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type=type;
	}

	/**
	 * @return the fields
	 */
	public Set<Field> getFields() {
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(Set<Field> fields) {
		this.fields=fields;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj==null) {
			return false;
		}
		if (getClass()!=obj.getClass()) {
			return false;
		}
		final Index other=(Index) obj;
		if ((this.name==null) ? (other.name!=null) : !this.name.equals(other.name)) {
			return false;
		}
		if (this.type!=other.type) {
			return false;
		}
		if (this.fields!=other.fields&&(this.fields==null||!this.fields.equals(other.fields))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash=5;
		hash=53*hash+(this.name!=null ? this.name.hashCode() : 0);
		hash=53*hash+this.type;
		hash=53*hash+(this.fields!=null ? this.fields.hashCode() : 0);
		return hash;
	}

	
}
