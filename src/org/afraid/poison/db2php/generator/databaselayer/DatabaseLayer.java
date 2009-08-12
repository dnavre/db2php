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

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.afraid.poison.common.CollectionUtil;
import org.afraid.poison.common.StringUtil;
import org.afraid.poison.common.string.StringMutator;
import org.afraid.poison.db2php.generator.CodeGenerator;
import org.afraid.poison.db2php.generator.Field;

/**
 * Database Layer type
 *
 * @author Andreas Schnaiter <rc.poison@gmail.com>
 */
abstract public class DatabaseLayer {

	/**
	 * No Database Layer
	 */
	public static final DatabaseLayer NONE=new DatabaseLayerNone();
	/**
	 * Simple Interface Database Layer
	 */
	public static final DatabaseLayer INTERFACE=new DatabaseLayerInterface();
	/**
	 * PDO Database Layer
	 */
	public static final DatabaseLayer PDO=new DatabaseLayerPdo();
	/**
	 * MySQLi Database Layer
	 */
	public static final DatabaseLayer MYSQLI=new DatabaseLayerMySQLi();
	/**
	 * ADODB Database Layer
	 */
	public static final DatabaseLayer ADODB=new DatabaseLayerAdoDb();
	/**
	 * ZEND Database Layer
	 */
	public static final DatabaseLayer ZEND=new DatabaseLayerZend();
	/**
	 * name of the method to get by id
	 */
	protected static final String METHOD_SELECT_ID_NAME="findById";
	/**
	 * update method name
	 */
	protected static final String METHOD_UPDATE_NAME="updateToDatabase";
	/**
	 * insert method name
	 */
	protected static final String METHOD_INSERT_NAME="insertIntoDatabase";
	/**
	 * delete method name
	 */
	protected static final String METHOD_DELETE_NAME="deleteFromDatabase";
	/**
	 * the available database layers
	 */
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
	/**
	 * default quote strings
	 */
	public static final String[] DEFAULT_QUOTE_STRINGS=new String[]{" ", "`", "\""};

	/**
	 * CTOR
	 */
	protected DatabaseLayer() {
	}

	/**
	 * @return the name
	 */
	abstract public String getName();

	/**
	 * get database type name (in PHP)
	 *
	 * @return the database type name from PHP
	 */
	abstract public String getDbTypeName();

	/**
	 * get select code
	 *
	 * @param generator a code generator
	 * @return the generated select code
	 */
	abstract public String getCodeSelect(CodeGenerator generator);

	/**
	 * get insert code
	 *
	 * @param generator a code generator
	 * @return the generated insert code
	 */
	abstract public String getCodeInsert(CodeGenerator generator);

	/**
	 *
	 * @param generator a code generator
	 * @return
	 */
	abstract public String getCodeUpdate(CodeGenerator generator);

	/**
	 * get delete code
	 *
	 * @param generator a code generator
	 * @return the generated delete code
	 */
	abstract public String getCodeDelete(CodeGenerator generator);

	/**
	 * get code snippet
	 *
	 * @return the code snippet
	 */
	abstract public String getSnippet();

	/**
	 * get complete db code
	 *
	 * @param generator a code generator
	 * @return the db code
	 */
	public String getCode(CodeGenerator generator) {
		return new StringBuilder().append(getSnippet()).append(getCodeSelect(generator)).append(getCodeInsert(generator)).append(getCodeUpdate(generator)).append(getCodeDelete(generator)).toString();
	}

	/**
	 * get code to escape code for passed field
	 *
	 * @param generator a code generator
	 * @param f the field
	 * @param fromOldValue if to use old value
	 * @return the escape code for the passed field
	 */
	public String getEscapeCode(CodeGenerator generator, Field f, boolean fromOldValue) {
		StringBuilder s=new StringBuilder();
		s.append("$db->escapeValue(");
		if (fromOldValue) {
			s.append(generator.getGetterCallOldInstance(f));
		} else {
			s.append(generator.getGetterCall(f));
		}
		s.append(",").append("self::").append(generator.getConstName(f)).append(")");
		return s.toString();
	}

	/**
	 * get PHP function that assigns fields by hash
	 *
	 * @param generator instance of a code generator
	 * @return the PHP function that assigns fields by hash
	 */
	public String getAssignByHash(CodeGenerator generator) {
		StringBuilder s=new StringBuilder();
		// assign data from hash
		s.append(getSnippetFromFile(generator, "DatabaseLayer.assignByHash.php"));
		s.append("\tpublic function ").append("assignByHash").append("($result) {\n");
		String rAccess;
		for (Field f : generator.getTable().getFields()) {
			rAccess=new StringBuilder("$result['").append(f.getName()).append("']").toString();
			s.append("\t\t").append(generator.getSetterCall(f, rAccess)).append(";\n");
		}
		s.append("\t}\n");
		return s.toString();
	}

	/**
	 * get code to assign field in SQL
	 *
	 * @param generator a code generator
	 * @param f the field
	 * @param fromOldValue weather to use old value
	 * @return code to assign a field in SQL
	 */
	protected String getSqlFieldAssign(CodeGenerator generator, Field f, boolean fromOldValue) {
		StringBuilder s=new StringBuilder();
		s.append("'").append(generator.quoteIdentifier(f)).append("=' . ").append(getEscapeCode(generator, f, fromOldValue)).toString();
		return s.toString();
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
	 * get SQL query for update
	 *
	 * @param generator instance of a code generator
	 * @return update SQL query
	 */
	public String getSqlUpdate(final CodeGenerator generator) {
		StringBuilder s=new StringBuilder();
		Set<Field> fields=generator.getTable().getFields();
		Set<Field> keys=generator.getTable().getFieldsIdentifiers();
		// update query
		s.append("'UPDATE ").append(generator.quoteIdentifier(generator.getTable().getName()));
		s.append(" SET '\n\t\t. ");
		StringMutator fieldAssign=new StringMutatorFieldAssign(generator);
		s.append(CollectionUtil.join(fields, " . ','\n\t\t . ", fieldAssign));
		s.append(getSqlWhere(generator, keys));
		return replaceUnneededConcat(s.toString());
	}

	/**
	 * get SQL for select query
	 *
	 * @param generator instance of a code generator
	 * @return select SQL query
	 */
	public String getSqlSelect(CodeGenerator generator) {
		StringBuilder s=new StringBuilder();
		Set<Field> keys=generator.getTable().getFieldsIdentifiers();
		// select by id
		s.append("'SELECT * FROM ").append(generator.quoteIdentifier(generator.getTable().getName())).append("'");

		s.append(getSqlWhere(generator, keys));
		return replaceUnneededConcat(s.toString());
	}

	/**
	 * get SQL for insert query
	 *
	 * @param generator instance of a code generator
	 * @return insert SQL query
	 */
	public String getSqlInsert(final CodeGenerator generator) {
		// TODO add method which the value gets passed through before beeing assigned to allow handling special types
		StringBuilder s=new StringBuilder();
		Set<Field> fields=generator.getTable().getFields();
		// insert query
		s.append("'INSERT INTO ").append(generator.quoteIdentifier(generator.getTable().getName()));
		s.append(" (").append(CollectionUtil.join(fields, ",", generator.getIdentifierQuoteString(), generator.getIdentifierQuoteString())).append(") VALUES ('\n\t\t . ");
		s.append(CollectionUtil.join(fields, ". ','\n\t\t . ", new StringMutator() {

			@Override
			public String transform(Object input) {
				return getEscapeCode(generator, (Field) input, false);
			}
		}));
		s.append(" . ')'");
		return replaceUnneededConcat(s.toString());
	}

	/**
	 * get SQL for delete query
	 *
	 * @param generator instance of a code generator
	 * @return delete SQL query
	 */
	public String getSqlDelete(CodeGenerator generator) {
		StringBuilder s=new StringBuilder();
		Set<Field> keys=generator.getTable().getFieldsIdentifiers();
		// delete by id
		s.append("'DELETE FROM ").append(generator.quoteIdentifier(generator.getTable().getName())).append("'");

		s.append(getSqlWhere(generator, keys));
		return replaceUnneededConcat(s.toString());
	}

	/**
	 * return result from $stmt->execute()
	 *
	 * @return the result from $stmt->execute()
	 */
	protected String getReturnResult() {
		return new StringBuilder("\t\treturn $affected;\n").toString();
	}

	/**
	 * replaces unneeded concat operations in PHP code which are there simply because it would be too much work to avoid them in the first place
	 * @param s the string to clean up
	 * @return PHP code without unneeded concats
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
			return getSqlFieldAssign(generator, (Field) s, false);
		}
	}

	/**
	 * read a snippet and replace '<type>' by the class name
	 *
	 * @param generator instance of a code generator
	 * @param fileName name of the file
	 * @return snippet with replaced '<type>'
	 */
	protected String getSnippetFromFile(CodeGenerator generator, String fileName) {
		StringBuilder s=new StringBuilder();
		String contents=generator.getSnippetFromFile(fileName);
		Map<CharSequence, CharSequence> replacements=new HashMap<CharSequence, CharSequence>();
		replacements.put("<dbType>", getDbTypeName());
		replacements.put("<tableName>", generator.getTable().getName());
		replacements.put("<tableNameQuoted>", generator.quoteIdentifier(generator.getTable().getName()));
		s.append(StringUtil.replace(contents, replacements));
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
