/*
 * Copyright (C) 2008 Andreas Schnaiter
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
 * Database Layer type
 *
 * @author Andreas Schnaiter <rc.poison@gmail.com>
 */
public class DatabaseLayer {

	private final String name;
	public static final DatabaseLayer NONE=new DatabaseLayer("none");
	public static final DatabaseLayer INTERFACE=new DatabaseLayer("Simple Interfaces");
	public static final DatabaseLayer MYSQLI=new DatabaseLayer("MySQLi");
	public static final DatabaseLayer ADODB=new DatabaseLayer("ADO DB");
	public static final DatabaseLayer ZEND=new DatabaseLayer("Zend");
	public static final Set<DatabaseLayer> AVAILABLE;

	static {
		AVAILABLE=new LinkedHashSet<DatabaseLayer>();
		AVAILABLE.add(NONE);
		AVAILABLE.add(INTERFACE);
		AVAILABLE.add(ADODB);
		AVAILABLE.add(ZEND);

	}

	public DatabaseLayer(String name) {
		this.name=name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj==null) {
			return false;
		}
		if (getClass()!=obj.getClass()) {
			return false;
		}
		final DatabaseLayer other=(DatabaseLayer) obj;
		if ((this.name==null) ? (other.name!=null) : !this.name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash=7;
		hash=17*hash+(this.name!=null ? this.name.hashCode() : 0);
		return hash;
	}
}
