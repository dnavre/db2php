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
