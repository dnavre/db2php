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

import java.util.Set;
import org.afraid.poison.common.StringUtil;
import org.afraid.poison.common.CollectionUtil;
import org.afraid.poison.common.StringMutator;

/**
 * generates PHP CLass code from a table
 *
 * @author Andreas Schnaiter <rc.poison@gmail.com>
 */
public class PhpCodeGenerator {

	private Table table;
	private DatabaseLayer databaseLayer;
	private boolean generateChecks;
	private boolean trackFieldModifications;
	private String classNamePrefix;
	private String classNameSuffix;

	public PhpCodeGenerator(Table table) {
		setTable(table);
	}

	/**
	 * @return the table
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * @param table the table to set
	 */
	public void setTable(Table table) {
		this.table=table;
	}

	/**
	 * @return the databaseLayer
	 */
	public DatabaseLayer getDatabaseLayer() {
		return databaseLayer;
	}

	/**
	 * @param databaseLayer the databaseLayer to set
	 */
	public void setDatabaseLayer(DatabaseLayer databaseLayer) {
		this.databaseLayer=databaseLayer;
	}

	/**
	 * @return the generateChecks
	 */
	public boolean isGenerateChecks() {
		return generateChecks;
	}

	/**
	 * @param generateChecks the generateChecks to set
	 */
	public void setGenerateChecks(boolean generateChecks) {
		this.generateChecks=generateChecks;
	}

	/**
	 * @return the trackFieldModifications
	 */
	public boolean isTrackFieldModifications() {
		return trackFieldModifications;
	}

	/**
	 * @param trackFieldModifications the trackFieldModifications to set
	 */
	public void setTrackFieldModifications(boolean trackFieldModifications) {
		this.trackFieldModifications=trackFieldModifications;
	}

	/**
	 * @return the classNamePrefix
	 */
	public String getClassNamePrefix() {
		return classNamePrefix;
	}

	/**
	 * @param classNamePrefix the classNamePrefix to set
	 */
	public void setClassNamePrefix(String classNamePrefix) {
		this.classNamePrefix=classNamePrefix;
	}

	/**
	 * @return the classNameSuffix
	 */
	public String getClassNameSuffix() {
		return classNameSuffix;
	}

	/**
	 * @param classNameSuffix the classNameSuffix to set
	 */
	public void setClassNameSuffix(String classNameSuffix) {
		this.classNameSuffix=classNameSuffix;
	}

	public String getClassName() {
		StringBuilder s=new StringBuilder();
		if (null!=getClassNamePrefix()) {
			s.append(getClassNamePrefix());
		}
		s.append(StringUtil.firstCharToUpperCase(getTable().getName()));
		return s.toString();
	}

	public String getMemberName(Field field) {
		return field.getName();
	}

	public String getMethodName(Field field) {
		return StringUtil.firstCharToUpperCase(getMemberName(field));
	}

	public String getGetterName(Field field) {
		return new StringBuilder("get").append(getMethodName(field)).toString();
	}

	public String getGetter(Field field) {
		StringBuilder s=new StringBuilder("\tpublic function ").append(getGetterName(field)).append("() {\n");
		s.append("\t\treturn $this->").append(getMemberName(field)).append(";\n");
		s.append("\t}\n");
		return s.toString();
	}

	public String getSetterName(Field field) {
		return new StringBuilder("set").append(getMethodName(field)).toString();
	}

	public String getSetter(Field field) {
		StringBuilder s=new StringBuilder("\tpublic function ").append(getSetterName(field)).append("($").append(getMemberName(field)).append(") {\n");
		s.append("\t\t$this->").append(getMemberName(field)).append("=$").append(getMemberName(field)).append(";\n");
		if (true||isTrackFieldModifications()) {
			s.append("\t\t$this->notifyChanged(self::").append(getConstName(field)).append(");\n");
		}
		s.append("\t}\n");
		return s.toString();
	}

	public String getAccessors() {
		StringBuilder s=new StringBuilder();
		for (Field f : getTable().getFields()) {
			//s.append("\tconst FIELD_").append(getMethodName(f).toUpperCase()).append("=").append((int) Math.pow(2, i++)).append("\n");
			s.append(getSetter(f)).append(getGetter(f));
		}
		return s.toString();
	}

	public String getConstName(Field field) {
		return new StringBuilder("FIELD_").append(getMethodName(field).toUpperCase()).toString();
	}

	public String getConsts() {
		StringBuilder s=new StringBuilder();
		// field ids for misc use
		int i=0;
		for (Field f : getTable().getFields()) {
			//s.append("\tconst FIELD_").append(getMethodName(f).toUpperCase()).append("=").append((int) Math.pow(2, i++)).append("\n");
			s.append("\tconst ").append(getConstName(f)).append("=").append(i++).append(";\n");
		}
		// list of primary keys
		s.append("\tprivate static $PRIMARY_KEYS=array(");
		s.append(CollectionUtil.join(getTable().getPrimaryKeys(), ",", new StringMutator() {

			public String transform(Object s) {
				return new StringBuilder("self::").append(getConstName((Field) s)).toString();
			}
		}));
		s.append(");\n");
		// field id to field name mapping
		s.append("\tprivate static $FIELD_NAMES=array(\n");
		s.append(CollectionUtil.join(getTable().getFields(), ",\n", new StringMutator() {

			public String transform(Object s) {
				Field f=(Field) s;
				return new StringBuilder("\t\tself::").append(getConstName(f)).append("=>'").append(f.getName()).append("'").toString();
			}
		}));
		s.append(");\n");
		return s.toString();
	}

	public String getMembers() {
		StringBuilder s=new StringBuilder();
		for (Field f : getTable().getFields()) {
			s.append("\tprivate $").append(getMemberName(f)).append(";\n");
		}
		return s.toString();
	}

	public String getPreparedStatements() {
		Set<Field> fields=getTable().getFields();
		StringBuilder s=new StringBuilder();

		// insert query
		s.append("\tconst SQL_INSERT=\"INSERT INTO ").append(getTable().getName());
		s.append(" (").append(CollectionUtil.join(fields, ",")).append(") VALUES (").append(StringUtil.repeat("?,", fields.size()-1)).append("?)").append("\";\n");

		// update query
		s.append("\tconst SQL_UPDATE=\"UPDATE ").append(getTable().getName());
		s.append(" SET ");
		StringMutator fieldAssign=new StringMutator() {

			public String transform(Object s) {
				return new StringBuilder(((Field) s).getName()).append("=").append("?").toString();
			}
		};
		s.append(CollectionUtil.join(fields, ",", fieldAssign));
		Set<Field> keys=getTable().getPrimaryKeys();
		if (!keys.isEmpty()) {
			s.append(" WHERE ");
			s.append(CollectionUtil.join(keys, ",", fieldAssign));
		}
		s.append("\";\n");

		// select by id
		s.append("\tconst SQL_SELECT_PK=\"SELECT * FROM ").append(getTable().getName());
		if (!keys.isEmpty()) {
			s.append(" WHERE ");
			s.append(CollectionUtil.join(keys, " AND ", fieldAssign));
		}
		s.append("\";\n");
		return s.toString();
	}

	public String getUtilMethodToArray() {
		return getUtilMethodArray(getTable().getFields(), "toArray");
	}

	public String getUtilMethodgetPrimaryKeysToArray() {
		return getUtilMethodArray(getTable().getPrimaryKeys(), "getPrimaryKeyValues");
	}

	private String getUtilMethodArray(Set<Field> fields, String methodName) {
		StringBuilder s=new StringBuilder("\tpublic function ").append(methodName).append("() {\n");
		s.append("\t\treturn array(\n");
		s.append(CollectionUtil.join(fields, ",\n", new StringMutator() {

			public String transform(Object s) {
				Field f=(Field) s;
				return new StringBuilder("\t\t\tself::").append(getConstName(f)).append("=>$this->").append(getGetterName(f)).append("()").toString();
			}
		}));
		//s.append("\t\treturn $this->").append(getMemberName(field)).append(";\n");
		s.append(");\n");
		s.append("\t}\n");
		return s.toString();
	}

	public String getCode() {
		StringBuilder s=new StringBuilder("<?php\n");
		s.append("class ").append(getClassName()).append(" {\n");
		s.append(getPreparedStatements());
		s.append(getConsts());
		s.append(getMembers());
		s.append(getAccessors());
		s.append(getUtilMethodToArray());
		s.append(getUtilMethodgetPrimaryKeysToArray());
		s.append("}\n");
		s.append("?>");
		return s.toString();
	}
}
