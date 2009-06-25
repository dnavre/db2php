/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.db2php.generator;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author poison
 */
public class DatabaseLayer {

	private final String name;
	public static final DatabaseLayer NONE=new DatabaseLayer("none");
	public static final DatabaseLayer INTERFACE=new DatabaseLayer("Simple Interfaces");
	public static final DatabaseLayer MYSQLI=new DatabaseLayer("MySQLi");
	public static final DatabaseLayer ADODB=new DatabaseLayer("SADO DB");
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
