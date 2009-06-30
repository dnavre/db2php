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

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.afraid.poison.db2php.generator.databaselayer.DatabaseLayer;
import java.util.Set;
import org.afraid.poison.common.StringUtil;
import org.afraid.poison.common.CollectionUtil;
import org.afraid.poison.common.FileUtil;
import org.afraid.poison.common.IOUtil;
import org.afraid.poison.common.StringMutator;
import org.openide.util.Exceptions;

/**
 * generates PHP CLass code from a table
 *
 * @author Andreas Schnaiter <rc.poison@gmail.com>
 */
public class PhpCodeGenerator {

	private Table table;
	private CodeGeneratorSettings settings=new CodeGeneratorSettings();

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
	 * @return the settings
	 */
	public CodeGeneratorSettings getSettings() {
		return settings;
	}

	/**
	 * @param settings the settings to set
	 */
	public void setSettings(CodeGeneratorSettings settings) {
		this.settings=settings;
	}

	/**
	 * @return the databaseLayer
	 */
	public DatabaseLayer getDatabaseLayer() {
		return getSettings().getDatabaseLayer();
	}

	/**
	 * @param databaseLayer the databaseLayer to set
	 */
	public void setDatabaseLayer(DatabaseLayer databaseLayer) {
		getSettings().setDatabaseLayer(databaseLayer);
	}

	/**
	 * @return the generateChecks
	 */
	public boolean isGenerateChecks() {
		return getSettings().isGenerateChecks();
	}

	/**
	 * @param generateChecks the generateChecks to set
	 */
	public void setGenerateChecks(boolean generateChecks) {
		getSettings().setGenerateChecks(generateChecks);
	}

	/**
	 * @return the trackFieldModifications
	 */
	public boolean isTrackFieldModifications() {
		return getSettings().isTrackFieldModifications();
	}

	/**
	 * @param trackFieldModifications the trackFieldModifications to set
	 */
	public void setTrackFieldModifications(boolean trackFieldModifications) {
		getSettings().setTrackFieldModifications(trackFieldModifications);
	}

	/**
	 * @return the classNamePrefix
	 */
	public String getClassNamePrefix() {
		return getSettings().getClassNamePrefix();
	}

	/**
	 * @param classNamePrefix the classNamePrefix to set
	 */
	public void setClassNamePrefix(String classNamePrefix) {
		getSettings().setClassNamePrefix(classNamePrefix);
	}

	/**
	 * @return the classNameSuffix
	 */
	public String getClassNameSuffix() {
		return getSettings().getClassNameSuffix();
	}

	/**
	 * @param classNameSuffix the classNameSuffix to set
	 */
	public void setClassNameSuffix(String classNameSuffix) {
		getSettings().setClassNameSuffix(classNameSuffix);
	}

	public static boolean isValidVariableName(String name) {
		//return name.matches("[a-zA-Z_\x7f-\xff][a-zA-Z0-9_\x7f-\xff]*");
		return true;
	}

	public String getClassName() {
		return new StringBuilder().append(getClassNamePrefix()).append(StringUtil.firstCharToUpperCase(StringUtil.toCamelCase(getTable().getName()))).append(getClassNameSuffix()).toString();
	}

	public String getFileName() {
		return new StringBuilder(getClassName()).append(".class.php").toString();
	}

	public String getMemberName(Field field) {
		return StringUtil.toCamelCase(field.getName());
	}

	public String getMethodName(Field field) {
		return StringUtil.firstCharToUpperCase(getMemberName(field));
	}

	public String getGetterName(Field field) {
		return new StringBuilder("get").append(getMethodName(field)).toString();
	}

	public String getGetterCall(Field f) {
		return new StringBuilder("$this->").append(getGetterName(f)).append("()").toString();
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

	public String getSetterCall(Field f, String param, String context) {
		return new StringBuilder(context).append("->").append(getSetterName(f)).append("(").append(param).append(")").toString();
	}

	public String getSetterCall(Field f, String param) {
		return getSetterCall(f, param, "$this");
	}

	public String getSetter(Field field) {
		StringBuilder s=new StringBuilder("\tpublic function ").append(getSetterName(field)).append("($").append(getMemberName(field)).append(") {\n");
		if (isTrackFieldModifications()) {
			s.append("\t\t$this->notifyChanged(self::").append(getConstName(field)).append(");\n");
		}
		s.append("\t\t$this->").append(getMemberName(field)).append("=$").append(getMemberName(field)).append(";\n");
		s.append("\t}\n");
		return s.toString();
	}

	public String getFieldList(List<Field> fields) {
		return CollectionUtil.join(fields, ",", new StringMutator() {

			@Override
			public String transform(Object s) {
				return new StringBuilder("$").append(getMemberName((Field) s)).toString();
			}
		});
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
		return new StringBuilder("FIELD_").append(getMemberName(field).toUpperCase()).toString();
	}

	public String getTrackingPristineState() {
		return isTrackFieldModifications() ? new StringBuilder("\t\t$this->notifyPristine();\n").toString() : new String();
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

			@Override
			public String transform(Object s) {
				return new StringBuilder("self::").append(getConstName((Field) s)).toString();
			}
		}));
		s.append(");\n");
		// field id to field name mapping
		s.append("\tprivate static $FIELD_NAMES=array(\n");
		s.append(CollectionUtil.join(getTable().getFields(), ",\n", new StringMutator() {

			@Override
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
		s.append(" (").append(CollectionUtil.join(fields, ",", getTable().getIdentifierQuoteString(), getTable().getIdentifierQuoteString())).append(") VALUES (").append(StringUtil.repeat("?,", fields.size()-1)).append("?)").append("\";\n");

		// update query
		s.append("\tconst SQL_UPDATE=\"UPDATE ").append(getTable().getName());
		s.append(" SET ");
		StringMutator fieldAssign=new StringMutator() {

			@Override
			public String transform(Object s) {
				return new StringBuilder().append(getTable().getIdentifierQuoteString()).append(((Field) s).getName()).append(getTable().getIdentifierQuoteString()).append("=?").toString();
			}
		};
		s.append(CollectionUtil.join(fields, ",", fieldAssign));
		Set<Field> keys=getTable().getPrimaryKeys();
		if (!keys.isEmpty()) {
			s.append(" WHERE ");
			s.append(CollectionUtil.join(keys, " AND ", fieldAssign));
		}
		s.append("\";\n");

		// select by id
		s.append("\tconst SQL_SELECT_PK=\"SELECT * FROM ").append(getTable().getName());
		if (!keys.isEmpty()) {
			s.append(" WHERE ");
			s.append(CollectionUtil.join(keys, " AND ", fieldAssign));
		}
		s.append("\";\n");

		// delete by id
		s.append("\tconst SQL_DELETE_PK=\"DELETE FROM ").append(getTable().getName());
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

			@Override
			public String transform(Object s) {
				Field f=(Field) s;
				return new StringBuilder("\t\t\tself::").append(getConstName(f)).append("=>").append(getGetterCall(f)).toString();
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
		if (isTrackFieldModifications()) {
			try {
				s.append(IOUtil.readString(getClass().getResourceAsStream("snippets/CODE_MODIFICATION_TRACKING.php")).replaceAll("<type>", getClassName()));
			} catch (IOException ex) {
				Exceptions.printStackTrace(ex);
			}
		}
		s.append(getAccessors());
		s.append(getUtilMethodToArray());
		s.append(getUtilMethodgetPrimaryKeysToArray());
		s.append(getDatabaseLayer().getCode(this));
		s.append("}\n");
		s.append("?>");
		return s.toString();
	}

	public File getFile() throws IOException {
		if (null==getSettings().getOutputDirectory()) {
			throw new IOException("no directory configured");
		}
		return new File(getSettings().getOutputDirectory(), getFileName());
	}

	public void writeCode(File file) throws IOException {
		if (file.exists()) {
			throw new IOException("file exists, refusing to overwrite");
		}
		FileUtil.writeString(getCode(), file);
	}

	public void writeCode() throws IOException {
		writeCode(getFile());
	}
}
