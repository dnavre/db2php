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
import org.afraid.poison.common.CollectionUtil;
import org.afraid.poison.common.StringMutator;
import org.afraid.poison.common.StringUtil;

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
	protected static final String METHOD_SELECT_ID_NAME="getById";
	protected static final String METHOD_UPDATE_NAME="updateToDatabase";
	protected static final String METHOD_INSERT_NAME="insertIntoDatabase";
	protected static final String METHOD_DELETE_NAME="deleteFromDatabase";

	public static final Set<DatabaseLayer> AVAILABLE;
	static {
		AVAILABLE=new LinkedHashSet<DatabaseLayer>();
		AVAILABLE.add(NONE);
		//AVAILABLE.add(INTERFACE);
		AVAILABLE.add(PDO);
		//AVAILABLE.add(MYSQLI);
		//AVAILABLE.add(ADODB);
		//AVAILABLE.add(ZEND);

	}

	protected DatabaseLayer() {
	}

	/**
	 * @return the name
	 */
	abstract public String getName();

	abstract public String getCodeSelect(CodeGenerator generator);

	abstract public String getCodeInsert(CodeGenerator generator);

	abstract public String getCodeUpdate(CodeGenerator generator);

	abstract public String getCodeDelete(CodeGenerator generator);

	abstract public String getSnippet();

	public String getCode(CodeGenerator generator) {
		return new StringBuilder()
				.append(getSnippet())
				.append(getCodeSelect(generator))
				.append(getCodeInsert(generator))
				.append(getCodeUpdate(generator))
				.append(getCodeDelete(generator)).toString();
	}

	public String getEscapeCode(String parameter) {
		StringBuilder s=new StringBuilder();
		return s.toString();
	}

	public String getSqlUpdate(final CodeGenerator generator)  {
		StringBuilder s=new StringBuilder();
        Set<Field> fields=generator.getTable().getFields();
        Set<Field> keys=generator.getTable().getPrimaryKeys();
       // update query
		s.append("\tconst SQL_UPDATE='UPDATE ").append(generator.getTable().getName());
		s.append(" SET ");
		StringMutator fieldAssign=new StringMutatorFieldAssign(generator);
		s.append(CollectionUtil.join(fields, ",", fieldAssign));
		if (!keys.isEmpty()) {
			s.append(" WHERE ");
			s.append(CollectionUtil.join(keys, " AND ", fieldAssign));
		}
		s.append("';\n");




		return s.toString();
	}

	public String getSqlSelect(CodeGenerator generator)  {
		StringBuilder s=new StringBuilder();
        Set<Field> fields=generator.getTable().getFields();
        Set<Field> keys=generator.getTable().getPrimaryKeys();
        // select by id
		s.append("\tconst SQL_SELECT_PK='SELECT * FROM ").append(generator.getTable().getName());
        StringMutator fieldAssign=new StringMutatorFieldAssign(generator);
		if (!keys.isEmpty()) {
			s.append(" WHERE ");
			s.append(CollectionUtil.join(keys, " AND ", fieldAssign));
		}
		s.append("';\n");
		return s.toString();
	}

	public String getSqlInsert(CodeGenerator generator)  {
		StringBuilder s=new StringBuilder();
        Set<Field> fields=generator.getTable().getFields();
        Set<Field> keys=generator.getTable().getPrimaryKeys();
        // insert query
		s.append("\tconst SQL_INSERT='INSERT INTO ").append(generator.getTable().getName());
		s.append(" (").append(CollectionUtil.join(fields, ",", generator.getIdentifierQuoteString(), generator.getIdentifierQuoteString())).append(") VALUES (").append(StringUtil.repeat("?,", fields.size()-1)).append("?)").append("';\n");


		return s.toString();
	}

	public String getSqlDelete(CodeGenerator generator)  {
		StringBuilder s=new StringBuilder();
        Set<Field> fields=generator.getTable().getFields();
        Set<Field> keys=generator.getTable().getPrimaryKeys();
        // delete by id
		s.append("\tconst SQL_DELETE_PK='DELETE FROM ").append(generator.getTable().getName());
        StringMutator fieldAssign=new StringMutatorFieldAssign(generator);
		if (!keys.isEmpty()) {
			s.append(" WHERE ");
			s.append(CollectionUtil.join(keys, " AND ", fieldAssign));
		}
		s.append("';\n");
		return s.toString();
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

    private static class StringMutatorFieldAssign implements StringMutator {

        private final CodeGenerator generator;

        public StringMutatorFieldAssign(CodeGenerator generator) {
            this.generator=generator;
        }

        @Override
        public String transform(Object s) {
            return new StringBuilder().append(generator.getIdentifierQuoteString()).append(((Field) s).getName()).append(generator.getIdentifierQuoteString()).append("=?").toString();
        }
    }
}
