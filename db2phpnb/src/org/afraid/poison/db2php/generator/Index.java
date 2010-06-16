/*
 * Copyright (C) 2009 Andreas Schnaiter
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */
package org.afraid.poison.db2php.generator;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author poison
 */
public class Index {

	private String name;
	private boolean unique;
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
	 * @return the unique
	 */
	public boolean isUnique() {
		return unique;
	}

	/**
	 * @param unique the unique to set
	 */
	public void setUnique(boolean unique) {
		this.unique=unique;
	}

	/**
	 * @return the fields
	 */
	public synchronized Set<Field> getFields() {
		if (null==fields) {
			fields=new LinkedHashSet<Field>();
		}
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(Set<Field> fields) {
		this.fields=null;
		getFields().addAll(fields);
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
		return true;
	}

	@Override
	public int hashCode() {
		int hash=7;
		hash=23*hash+(this.name!=null ? this.name.hashCode() : 0);
		return hash;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(getName()).append("{").append(getFields()).append("}").toString();
	}


}
