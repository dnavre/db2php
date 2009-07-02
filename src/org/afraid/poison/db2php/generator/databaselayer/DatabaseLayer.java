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

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import org.afraid.poison.common.CollectionUtil;
import org.afraid.poison.common.IOUtil;
import org.afraid.poison.common.StringMutator;
import org.afraid.poison.db2php.generator.CodeGenerator;
import org.afraid.poison.db2php.generator.Field;
import org.openide.util.Exceptions;

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
		AVAILABLE.add(INTERFACE);
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

	abstract public String getDbTypeName();

	abstract public String getCodeSelect(CodeGenerator generator);

	abstract public String getCodeInsert(CodeGenerator generator);

	abstract public String getCodeUpdate(CodeGenerator generator);

	abstract public String getCodeDelete(CodeGenerator generator);

	abstract public String getSnippet();

	public String getCode(CodeGenerator generator) {
		return new StringBuilder().append(getSnippet()).append(getCodeSelect(generator)).append(getCodeInsert(generator)).append(getCodeUpdate(generator)).append(getCodeDelete(generator)).toString();
	}

	public String getEscapeCode(String parameter) {
		StringBuilder s=new StringBuilder();
		s.append("$db->escapeValue(").append(parameter).append(")");
		return s.toString();
	}

	/**
	 * get php function that assigns fields by hash
	 *
	 * @param generator instance of a code generator
	 * @return the php function that assigns fields by hash
	 */
	public String getAssignByHash(CodeGenerator generator) {
		StringBuilder s=new StringBuilder();
		// assign data from hash
		s.append("\tpublic function ").append("assignByHash").append("($result) {\n");
		String rAccess;
		for (Field f : generator.getTable().getFields()) {
			rAccess=new StringBuilder("$result['").append(f.getName()).append("']").toString();
			s.append("\t\t").append(generator.getSetterCall(f, rAccess)).append(";\n");
		}
		s.append("\t}\n");
		return s.toString();
	}

	protected String getSqlFieldAssign(CodeGenerator generator, Field f) {
		return new StringBuilder("'").append(generator.quoteIdentifier(f)).append("=' . ").append(getEscapeCode(generator.getGetterCall(f))).toString();
	}

	/**
	 * get where part of query
	 *
	 * @param generator instance of a code generator
	 * @param keys the keys to use
	 * @return
	 */
	private String getSqlWhere(CodeGenerator generator, Set<Field> keys) {
		StringBuilder s=new StringBuilder();
		StringMutator fieldAssign=new StringMutatorFieldAssign(generator);
		if (!keys.isEmpty()) {
			s.append(" . ' WHERE ' . ");
			s.append(CollectionUtil.join(keys, " . ' AND ' . ", fieldAssign));
		}
		return s.toString();
	}

	/**
	 * get sql query for update
	 *
	 * @param generator instance of a code generator
	 * @return update sql query
	 */
	public String getSqlUpdate(final CodeGenerator generator) {
		StringBuilder s=new StringBuilder();
		Set<Field> fields=generator.getTable().getFields();
		Set<Field> keys=generator.getTable().getFieldsIdentifiers();
		// update query
		s.append("'UPDATE ").append(generator.getTable().getName());
		s.append(" SET '\n\t\t. ");
		StringMutator fieldAssign=new StringMutatorFieldAssign(generator);
		s.append(CollectionUtil.join(fields, " . ','\n\t\t . ", fieldAssign));
		s.append(getSqlWhere(generator, keys));
		return replaceUnneededConcat(s.toString());
	}

	/**
	 * get sql for select query
	 *
	 * @param generator instance of a code generator
	 * @return select sql query
	 */
	public String getSqlSelect(CodeGenerator generator) {
		StringBuilder s=new StringBuilder();
		Set<Field> keys=generator.getTable().getFieldsIdentifiers();
		// select by id
		s.append("'SELECT * FROM ").append(generator.getTable().getName()).append("'");

		s.append(getSqlWhere(generator, keys));
		return replaceUnneededConcat(s.toString());
	}

	/**
	 * get sql for insert query
	 *
	 * @param generator instance of a code generator
	 * @return insert sql query
	 */
	public String getSqlInsert(final CodeGenerator generator) {
		StringBuilder s=new StringBuilder();
		Set<Field> fields=generator.getTable().getFields();
		// insert query
		s.append("'INSERT INTO ").append(generator.getTable().getName());
		s.append(" (").append(CollectionUtil.join(fields, ",", generator.getIdentifierQuoteString(), generator.getIdentifierQuoteString())).append(") VALUES ('\n\t\t . ");
		s.append(CollectionUtil.join(fields, ". ','\n\t\t . ", new StringMutator() {

			@Override
			public String transform(Object input) {
				return getEscapeCode(generator.getGetterCall(((Field) input)));
			}
		}));
		s.append(" . ')'");
		return replaceUnneededConcat(s.toString());
	}

	/**
	 * get sql for delete query
	 *
	 * @param generator instance of a code generator
	 * @return delete sql query
	 */
	public String getSqlDelete(CodeGenerator generator) {
		StringBuilder s=new StringBuilder();
		Set<Field> keys=generator.getTable().getFieldsIdentifiers();
		// delete by id
		s.append("'DELETE FROM ").append(generator.getTable().getName()).append("'");

		s.append(getSqlWhere(generator, keys));
		return replaceUnneededConcat(s.toString());
	}

	protected String getReturnResult() {
		return new StringBuilder("\t\treturn $affected;\n").toString();
	}

	/**
	 * replaces unneded concat operations in PHP code which are there simply because it would be too much work to avoid them in the first place
	 * @param s the string to clean up
	 * @return php code without unneeded concats
	 */
	protected String replaceUnneededConcat(String s) {
		return s.replaceAll("\\s*'\\s*\\.\\s*'\\s*", " ");
	}

	private class StringMutatorFieldAssign implements StringMutator {

		private final CodeGenerator generator;

		public StringMutatorFieldAssign(CodeGenerator generator) {
			this.generator=generator;
		}

		@Override
		public String transform(Object s) {
			return getSqlFieldAssign(generator, (Field) s);
		}
	}

	/**
	 * read a snippet and replace '<type>' by the class name
	 *
	 * @param generator instance of a code generator
	 * @param resource path to the resource
	 * @return snippet with replaced '<type>'
	 */
	protected String getSnippetFromFile(CodeGenerator generator, String fileName) {
		StringBuilder s=new StringBuilder();
		try {
			String contents=IOUtil.readString(getClass().getResourceAsStream(new StringBuilder("/org/afraid/poison/db2php/generator/snippets/").append(fileName).toString()));
			s.append(contents.replace("<type>", generator.getClassName()).replace("<dbType>", getDbTypeName()));
		} catch (IOException ex) {
			Exceptions.printStackTrace(ex);
		}
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
		if ((this.getName()==null) ? (other.getName()!=null) : !this.getName().equals(other.getName())) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash=7;
		hash=17*hash+(this.getName()!=null ? this.getName().hashCode() : 0);
		return hash;
	}
}
