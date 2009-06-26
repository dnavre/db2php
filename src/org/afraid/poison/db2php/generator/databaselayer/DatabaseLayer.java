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
package org.afraid.poison.db2php.generator.databaselayer;

import org.afraid.poison.db2php.generator.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Database Layer type
 *
 * @author Andreas Schnaiter <rc.poison@gmail.com>
 */
abstract public class DatabaseLayer {

	public static final DatabaseLayer NONE=new DatabaseLayerNone();
	public static final DatabaseLayer INTERFACE=new DatabaseLayerInterface();
	public static final DatabaseLayer PDO=new DatabaseLayerPdo();
	public static final DatabaseLayer MYSQLI=new DatabaseLayerMySQLi();
	public static final DatabaseLayer ADODB=new DatabaseLayerAdoDb();
	public static final DatabaseLayer ZEND=new DatabaseLayerZend();
	public static final Set<DatabaseLayer> AVAILABLE;

	private static final String METHOD_SELECT_ID_NAME="getById";
	private static final String METHOD_UPDATE_NAME="updateToDatabase";
	private static final String METHOD_INSERT_NAME="insertIntoDatabase";
	private static final String METHOD_DELETE_NAME="deleteFromDatabase";

	static {
		AVAILABLE=new LinkedHashSet<DatabaseLayer>();
		AVAILABLE.add(NONE);
		//AVAILABLE.add(INTERFACE);
		AVAILABLE.add(PDO);
		//AVAILABLE.add(MYSQLI);
		//AVAILABLE.add(ADODB);
		//AVAILABLE.add(ZEND);

	}

	private DatabaseLayer() {
	}

	/**
	 * @return the name
	 */
	abstract public String getName();


	abstract public String getSelectCode(PhpCodeGenerator generator);

	abstract public String getInsertCode(PhpCodeGenerator generator);

	abstract public String getUpdateCode(PhpCodeGenerator generator);

	abstract public String getDeleteCode(PhpCodeGenerator generator);


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
		if ((this.getName()==null)?(other.getName()!=null):!this.getName().equals(other.getName())) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash=7;
		hash=17*hash+(this.getName()!=null?this.getName().hashCode():0);
		return hash;
	}

	private static class DatabaseLayerNone extends DatabaseLayer {

		@Override
		public String getName() {
			return "none";
		}

		@Override
		public String getSelectCode(PhpCodeGenerator generator) {
			return null;
		}

		@Override
		public String getInsertCode(PhpCodeGenerator generator) {
			return null;
		}

		@Override
		public String getUpdateCode(PhpCodeGenerator generator) {
			return null;
		}

		@Override
		public String getDeleteCode(PhpCodeGenerator generator) {
			throw new UnsupportedOperationException("Not supported yet.");
		}
	}

	private static class DatabaseLayerInterface extends DatabaseLayer {

		@Override
		public String getName() {
			return "Simple Interface";
		}

		@Override
		public String getSelectCode(PhpCodeGenerator generator) {
			throw new UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public String getInsertCode(PhpCodeGenerator generator) {
			throw new UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public String getUpdateCode(PhpCodeGenerator generator) {
			throw new UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public String getDeleteCode(PhpCodeGenerator generator) {
			throw new UnsupportedOperationException("Not supported yet.");
		}
	}

	private static class DatabaseLayerPdo extends DatabaseLayer {

		@Override
		public String getName() {
			return "PHP Data Objects";
		}

		@Override
		public String getSelectCode(PhpCodeGenerator generator) {
			StringBuilder s=new StringBuilder();
			throw new UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public String getInsertCode(PhpCodeGenerator generator) {
			throw new UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public String getUpdateCode(PhpCodeGenerator generator) {
			throw new UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public String getDeleteCode(PhpCodeGenerator generator) {
			throw new UnsupportedOperationException("Not supported yet.");
		}
	}

	private static class DatabaseLayerMySQLi extends DatabaseLayer {

		@Override
		public String getName() {
			return "MySQLi";
		}

		@Override
		public String getSelectCode(PhpCodeGenerator generator) {
			throw new UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public String getInsertCode(PhpCodeGenerator generator) {
			throw new UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public String getUpdateCode(PhpCodeGenerator generator) {
			throw new UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public String getDeleteCode(PhpCodeGenerator generator) {
			throw new UnsupportedOperationException("Not supported yet.");
		}
	}

	private static class DatabaseLayerAdoDb extends DatabaseLayer {

		@Override
		public String getName() {
			return "ADO DB";
		}

		@Override
		public String getSelectCode(PhpCodeGenerator generator) {
			throw new UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public String getInsertCode(PhpCodeGenerator generator) {
			throw new UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public String getUpdateCode(PhpCodeGenerator generator) {
			throw new UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public String getDeleteCode(PhpCodeGenerator generator) {
			throw new UnsupportedOperationException("Not supported yet.");
		}
	}

	private static class DatabaseLayerZend extends DatabaseLayer {

		@Override
		public String getName() {
			return "Zend";
		}

		@Override
		public String getSelectCode(PhpCodeGenerator generator) {
			throw new UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public String getInsertCode(PhpCodeGenerator generator) {
			throw new UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public String getUpdateCode(PhpCodeGenerator generator) {
			throw new UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public String getDeleteCode(PhpCodeGenerator generator) {
			throw new UnsupportedOperationException("Not supported yet.");
		}
	}
}
