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
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.afraid.poison.db2php.generator.databaselayer.DatabaseLayer;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.afraid.poison.common.StringUtil;
import org.afraid.poison.common.CollectionUtil;
import org.afraid.poison.common.FileUtil;
import org.afraid.poison.common.IOUtil;
import org.afraid.poison.camelcase.CamelCaseFairy;
import org.afraid.poison.common.string.StringMutator;

/**
 * generates PHP code from a tableName
 *
 * @author Andreas Schnaiter <rc.poison@gmail.com>
 */
public class CodeGenerator {

	private static final String SNIPPET_PATH="/org/afraid/poison/db2php/generator/snippets/";
	private static String db2phpVersion=null;
	private Table table;
	private Settings settings;
	private CamelCaseFairy camelCaseFairy;

	/**
	 * CTOR
	 *
	 * @param tableName the tableName to operate on
	 */
	public CodeGenerator(Table table) {
		setTable(table);
		setSettings(new Settings());
	}

	/**
	 * CTOR
	 *
	 * @param tableName the tableName to operate on
	 * @param settings the settings
	 */
	public CodeGenerator(Table table, Settings settings) {
		setTable(table);
		setSettings(settings);
	}

	/**
	 * Read version from the Manifest
	 *
	 * @return the db2phpVersion
	 */
	public static synchronized String getDb2phpVersion() {
		if (null==db2phpVersion) {
			InputStream is=null;
			try {
				is=CodeGenerator.class.getResourceAsStream("/META-INF/MANIFEST.MF");
				if (null!=is) {
					Properties p=new Properties();
					p.load(is);
					db2phpVersion=p.getProperty("OpenIDE-Module-Specification-Version", "");
				}
			} catch (IOException ex) {
				Logger.getLogger(CodeGenerator.class.getName()).log(Level.SEVERE, null, ex);
			} finally {
				IOUtil.closeQuietly(is);
			}

		}
		return db2phpVersion;
	}

	/**
	 * escape single quotes in passed string
	 *
	 * @param s
	 * @return escaped string for use in single quoted PHP string
	 */
	public static String escapePhpString(String s) {
		return s.replace("'", "\\'");
	}

	/**
	 * quote and escape passed string for use as PHP string
	 *
	 * @param s
	 * @return PHP string
	 */
	public static String getPhpString(String s) {
		return new StringBuilder("'").append(escapePhpString(s)).append("'").toString();
	}

	/**
	 * @return the tableName
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * @param table
	 */
	public void setTable(Table table) {
		this.table=table;
	}

	/**
	 * @return the settings
	 */
	public Settings getSettings() {
		return settings;
	}

	/**
	 * @param settings the settings to set
	 */
	public void setSettings(Settings settings) {
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

	/**
	 * check if passed string is valid PHP variable name
	 *
	 * @param name the variable name
	 * @return true if is valid PHP variable name
	 */
	public static boolean isValidVariableName(String name) {
		return name.matches("^[a-zA-Z_\\x7f-\\xff][a-zA-Z0-9_\\x7f-\\xff]*$");
	}

	/**
	 * get the class name
	 *
	 * @return the class name
	 */
	public String getClassName() {
		return getClassName(getTable().getName());
	}

	/**
	 * get the class name
	 *
	 * @param tableName
	 * @return the class name
	 */
	public String getClassName(String tableName) {
		return new StringBuilder().append(getClassNamePrefix()).append(StringUtil.firstCharToUpperCase(CamelCaseFairy.toCamelCase(tableName, getCamelCaseFairy()))).append(getClassNameSuffix()).toString();
	}

	/**
	 * get the file name
	 *
	 * @return the file name
	 */
	public String getFileName() {
		return new StringBuilder(getClassName()).append(".class.php").toString();
	}

	/**
	 * get the member variable name
	 *
	 * @param field the field for which to get the member name
	 * @return the member name
	 */
	public String getMemberName(Field field) {
		return CamelCaseFairy.toCamelCase(field.getName(), getCamelCaseFairy());
	}

	/**
	 * get the method name
	 *
	 * @param field the field for which to get the method name
	 * @return the method name
	 */
	public String getMethodName(Field field) {
		return StringUtil.firstCharToUpperCase(getMemberName(field));
	}

	/**
	 * get the getter name
	 *
	 * @param field the field for which to get the getter name
	 * @return the getter name
	 */
	public String getGetterName(Field field) {
		return new StringBuilder("get").append(getMethodName(field)).toString();
	}

	/**
	 * get call to getter
	 *
	 * @param f the field for which to get the call to getter
	 * @return call to getter
	 */
	public String getGetterCall(Field f) {
		return new StringBuilder("$this->").append(getGetterName(f)).append("()").toString();
	}

	/**
	 * get getter call on old instance
	 *
	 * @param f the field for which to get the call to getter
	 * @return getter call on old instance
	 */
	public String getGetterCallOldInstance(Field f) {
		return new StringBuilder("$this->getOldInstance()->").append(getGetterName(f)).append("()").toString();
	}

	/**
	 * get getter definition
	 *
	 * @param field the field for which to get the getter definition
	 * @return the getter definition
	 */
	public String getGetter(Field field) {
		StringBuilder s=new StringBuilder();
		s.append(getSnippetFromFile("CodeGenerator.get.php", field));
		s.append("\tpublic function ");
		s.append(getGetterName(field)).append("() {\n");
		s.append("\t\treturn $this->").append(getMemberName(field)).append(";\n");
		s.append("\t}\n");
		return s.toString();
	}

	/**
	 * get the setter name
	 *
	 * @param field the field for which to get the setter name
	 * @return the setter name
	 */
	public String getSetterName(Field field) {
		return new StringBuilder("set").append(getMethodName(field)).toString();
	}

	/**
	 * get call to setter
	 *
	 * @param f the field for which to get the call to setter
	 * @param param the parameter to pass in the setter call
	 * @param context context for the setter call
	 * @return call to setter
	 */
	public String getSetterCall(Field f, String param, String context) {
		return new StringBuilder(context).append("->").append(getSetterName(f)).append("(").append(param).append(")").toString();
	}

	/**
	 * get call to setter
	 *
	 * @param f the field for which to get the call to setter
	 * @param param the parameter to pass in the setter call
	 * @return call to setter
	 */
	public String getSetterCall(Field f, String param) {
		return getSetterCall(f, param, "$this");
	}

	/**
	 * get setter definition
	 *
	 * @param field the field for which to get the setter definition
	 * @return the setter definition
	 */
	public String getSetter(Field field) {
		StringBuilder s=new StringBuilder();
		s.append(getSnippetFromFile(getSettings().isFluentInterface() ? "CodeGenerator.set.fluent.php" : "CodeGenerator.set.php", field));
		s.append("\tpublic function ");
		if (getSettings().isFluentInterface()) {
			s.append("&");
		}
		s.append(getSetterName(field)).append("($").append(getMemberName(field)).append(") {\n");
		if (isTrackFieldModifications()) {
			s.append("\t\t$this->notifyChanged(self::").append(getConstName(field)).append(",$this->").append(getMemberName(field)).append(",").append("$").append(getMemberName(field)).append(");\n");
		}
		s.append("\t\t$this->").append(getMemberName(field)).append("=$").append(getMemberName(field)).append(";\n");
		if (getSettings().isFluentInterface()) {
			s.append("\t\treturn $this;\n");
		}
		s.append("\t}\n");
		return s.toString();
	}

	/**
	 * get hash to identify tableName/field combination
	 *
	 * @param field
	 * @return
	 */
	public int getHash(Field field) {
		return new StringBuilder().append(getTable().getName()).append(".").append(field.getName()).toString().hashCode();
	}

	/**
	 * get the comma separated list of fields
	 *
	 * @param fields the fields for which to build the list
	 * @return comma separated list of fields
	 */
	public String getFieldList(List<Field> fields) {
		return CollectionUtil.join(fields, ",", new StringMutator() {

			@Override
			public String transform(Object s) {
				return new StringBuilder("$").append(getMemberName((Field) s)).toString();
			}
		});
	}

	/**
	 * get code for all accessors
	 * 
	 * @return code for all accessors
	 */
	public String getAccessors() {
		StringBuilder s=new StringBuilder();
		for (Field f : getTable().getFields()) {
			//s.append("\tconst FIELD_").append(getMethodName(f).toUpperCase()).append("=").append((int) Math.pow(2, i++)).append("\n");
			s.append(getSetter(f)).append(getGetter(f));
		}
		return s.toString();
	}

	/**
	 * get constant name
	 *
	 * @param field
	 * @return constant definitions for fields ids
	 */
	public String getConstName(Field field) {
		return new StringBuilder("FIELD_").append(field.getName().replaceAll("[^a-zA-Z0-9_]", "").toUpperCase()).toString();
	}

	/**
	 * get code to notify that object is in pristine state if tracking is enabled
	 *
	 * @return code to notify that object is in pristine state
	 */
	public String getTrackingPristineState() {
		return isTrackFieldModifications() ? new StringBuilder("\t\t$this->notifyPristine();\n").toString() : new String();
	}

	/**
	 * get constant definitions for fields ids
	 *
	 * @return constant definitions for fields ids
	 */
	public String getConsts() {
		StringBuilder s=new StringBuilder();
		// field ids for misc use
		int i=0;
		for (Field f : getTable().getFields()) {
			//s.append("\tconst FIELD_").append(getMethodName(f).toUpperCase()).append("=").append((int) Math.pow(2, i++)).append("\n");
			//s.append("\tconst ").append(getConstName(f)).append("=").append(i++).append(";\n");
			s.append("\tconst ").append(getConstName(f)).append("=").append(getHash(f)).append(";\n");
		}

		StringMutator mutatorFieldList=new StringMutator() {

			@Override
			public String transform(Object s) {
				return new StringBuilder("self::").append(getConstName((Field) s)).toString();
			}
		};
		// list of primary keys
		s.append("\tprivate static $PRIMARY_KEYS=array(");
		s.append(CollectionUtil.join(getTable().getFieldsIdentifiers(), ",", mutatorFieldList));
		s.append(");\n");

		// list of autoincrement fields
		s.append("\tprivate static $AUTOINCREMENT_FIELDS=array(");
		s.append(CollectionUtil.join(getTable().getFieldsAutoIncrement(), ",", mutatorFieldList));
		s.append(");\n");

		/*
		// list of imported keys
		s.append("\tprivate static $EXPORTED_KEYS=array(");
		s.append(CollectionUtil.join(getTable().getImportedKeys(), ",", new StringMutator() {

			@Override
			public String transform(Object input) {
				ForeignKey fk=(ForeignKey) input;
				StringBuilder s=new StringBuilder();
				s.append("\t\tself::").append(getConstName(fk.getPkField()));
				return s.toString();
				//return new StringBuilder("self::").append(getConstName((Field) i)).toString();
			}
		}));
		s.append(");\n");

		// list of imported keys
		s.append("\tprivate static $IMPORTED_KEYS=array(");
		s.append(CollectionUtil.join(getTable().getExportedKeys(), ",", mutatorFieldList));
		s.append(");\n");
		*/
		
		// field id to field name mapping
		s.append("\tprivate static $FIELD_NAMES=array(\n");
		s.append(CollectionUtil.join(getTable().getFields(), ",\n", new StringMutator() {

			@Override
			public String transform(Object s) {
				Field f=(Field) s;
				return new StringBuilder("\t\tself::").append(getConstName(f)).append("=>").append(getPhpString(f.getName())).toString();
			}
		}));
		s.append(");\n");
		/*
		// field id to field type mapping
		s.append("\tprivate static $FIELD_TYPES=array(\n");
		s.append(CollectionUtil.join(getTable().getFields(), ",\n", new StringMutator() {

			@Override
			public String transform(Object s) {
				Field f=(Field) s;
				return new StringBuilder("\t\tself::").append(getConstName(f)).append("=>").append(getPhpString(f.getTypeName())).toString();
			}
		}));
		s.append(");\n");
		*/
		// field id to field property mapping
		s.append("\tprivate static $PROPERTY_NAMES=array(\n");
		s.append(CollectionUtil.join(getTable().getFields(), ",\n", new StringMutator() {

			@Override
			public String transform(Object s) {
				Field f=(Field) s;
				return new StringBuilder("\t\tself::").append(getConstName(f)).append("=>").append(getPhpString(getMemberName(f))).toString();
			}
		}));
		s.append(");\n");

		// field id to property type mapping
		s.append("\tprivate static $PROPERTY_TYPES=array(\n");
		s.append(CollectionUtil.join(getTable().getFields(), ",\n", new StringMutator() {

			@Override
			public String transform(Object s) {
				Field f=(Field) s;
				return new StringBuilder("\t\tself::").append(getConstName(f)).append("=>").append("Db2PhpEntity::PHP_TYPE_").append(f.getTypePHP().toUpperCase()).toString();
			}
		}));
		s.append(");\n");
		// field id to property type mapping
		s.append("\tprivate static $FIELD_TYPES=array(\n");
		s.append(CollectionUtil.join(getTable().getFields(), ",\n", new StringMutator() {

			@Override
			public String transform(Object s) {
				Field f=(Field) s;
				StringBuilder code=new StringBuilder("\t\tself::").append(getConstName(f)).append("=>array(");
				code.append("Db2PhpEntity::JDBC_TYPE_").append(f.getTypeSqlString()==null ? f.getType() : f.getTypeSqlString());
				code.append(",").append(f.getSize());
				code.append(",").append(f.getDecimalDigits());
				code.append(",").append(f.isNullable() ? "true" : "false");
				code.append(")");
				return code.toString();
			}
		}));
		s.append(");\n");

		// default values
		s.append("\tprivate static $DEFAULT_VALUES=array(\n");
		s.append(CollectionUtil.join(getTable().getFields(), ",\n", new StringMutator() {

			@Override
			public String transform(Object input) {
				Field f=(Field) input;
				StringBuilder s=new StringBuilder("\t\t");
				s.append("self::").append(getConstName(f)).append("=>");
				if (f.isAutoIncrement()||(f.isNullable()&&null==f.getDefaultValue())) {
					s.append("null");
				} else if (null==f.getDefaultValue()) {
					if (f.isNumberType()) {
						s.append("0");
					} else {
						s.append("''");
					}
				} else {
					if (f.isNumberType()) {
						if (0==f.getDefaultValue().length()) {
							s.append("0");
						} else {
							if (f.getDefaultValue().matches(".*[^0-9\\.].*")) {
								//s.append(getPhpString(f.getDefaultValue()));
								s.append("null");
							} else {
								s.append(f.getDefaultValue());
							}
						}
					} else {
						s.append(getPhpString(f.getDefaultValue()));
					}
				}
				return s.toString();
			}
		}));
		s.append(");\n");
		return s.toString();
	}

	/**
	 * get code for the member declaration
	 *
	 * @return code for the member declaration
	 */
	public String getMembers() {
		StringBuilder s=new StringBuilder();
		for (Field f : getTable().getFields()) {
			s.append("\tprivate $").append(getMemberName(f)).append(";\n");
		}
		return s.toString();
	}

	/**
	 * get the string in which to quote the identifiers in SQL
	 *
	 * @return the string in which to quote the identifiers in SQL
	 */
	public String getIdentifierQuoteString() {
		String quote=getSettings().getIdentifierQuoteString()==null ? getTable().getIdentifierQuoteString() : getSettings().getIdentifierQuoteString();
		if ("'".equals(quote)) {
			return "\\'";
		} else if (" ".equals(quote)) {
			return "";
		}
		return quote;
	}

	/**
	 * quote identifier
	 *
	 * @param identifier the identifier to quote
	 * @return the quoted identifier
	 */
	public String quoteIdentifier(String identifier) {
		return new StringBuilder().append(getIdentifierQuoteString()).append(identifier).append(getIdentifierQuoteString()).toString();
	}

	/**
	 * quote identifier
	 *
	 * @param identifier the identifier to quote
	 * @return the quoted identifier
	 */
	public String quoteIdentifier(Field identifier) {
		return quoteIdentifier(identifier.getName());
	}

	/**
	 * get the prepared statements
	 *
	 * @return the INSERT/UPDATE/SELECT/DELETE prepared statements
	 */
	public String getPreparedStatements() {
		Set<Field> fields=getTable().getFields();
		StringBuilder s=new StringBuilder();
		s.append("\tconst SQL_IDENTIFIER_QUOTE='").append(getIdentifierQuoteString()).append("';\n");
		s.append("\tconst SQL_TABLE_NAME=").append(getPhpString(getTable().getName())).append(";\n");

		// insert query
		s.append("\tconst SQL_INSERT='INSERT INTO ").append(quoteIdentifier(getTable().getName()));
		s.append(" (").append(CollectionUtil.join(fields, ",", getIdentifierQuoteString(), getIdentifierQuoteString())).append(") VALUES (").append(StringUtil.repeat("?,", fields.size()-1)).append("?)").append("';\n");

		// insert query with autoincrement columns omitted
		//if (!getTable().getFieldsAutoIncrement().isEmpty()) {
		Set<Field> fieldsNotAutoincrement=getTable().getFieldsNotAutoIncrement();
		s.append("\tconst SQL_INSERT_AUTOINCREMENT='INSERT INTO ").append(quoteIdentifier(getTable().getName()));
		s.append(" (").append(CollectionUtil.join(fieldsNotAutoincrement, ",", getIdentifierQuoteString(), getIdentifierQuoteString())).append(") VALUES (").append(StringUtil.repeat("?,", fieldsNotAutoincrement.size()-1)).append("?)").append("';\n");
		//}

		// update query
		s.append("\tconst SQL_UPDATE='UPDATE ").append(quoteIdentifier(getTable().getName()));
		s.append(" SET ");
		StringMutator fieldAssign=new StringMutator() {

			@Override
			public String transform(Object s) {
				return new StringBuilder().append(getIdentifierQuoteString()).append(((Field) s).getName()).append(getIdentifierQuoteString()).append("=?").toString();
			}
		};
		s.append(CollectionUtil.join(fields, ",", fieldAssign));
		Set<Field> keys=getTable().getFieldsIdentifiers();
		StringBuilder sqlWhere=new StringBuilder();
		if (!keys.isEmpty()) {
			sqlWhere.append(" WHERE ");
			sqlWhere.append(CollectionUtil.join(keys, " AND ", fieldAssign));
		}
		s.append(sqlWhere);
		s.append("';\n");

		// select by id
		s.append("\tconst SQL_SELECT_PK='SELECT * FROM ").append(quoteIdentifier(getTable().getName()));
		s.append(sqlWhere);
		s.append("';\n");

		// delete by id
		s.append("\tconst SQL_DELETE_PK='DELETE FROM ").append(quoteIdentifier(getTable().getName()));
		s.append(sqlWhere);
		s.append("';\n");
		return s.toString();
	}

	/**
	 * get the method code to get array from object
	 *
	 * @return the method code to get array from object
	 */
	public String getUtilMethodToArray() {
		return new StringBuilder().append(getSnippetFromFile("CodeGenerator.toArray.php")).append(getUtilMethodArray(getTable().getFields(), "toArray")).toString();
	}

	/**
	 * get the method code to get array of primary key values
	 * @return the method code to get array of primary key values
	 */
	public String getUtilMethodgetPrimaryKeysToArray() {
		return new StringBuilder().append(getSnippetFromFile("CodeGenerator.getPrimaryKeyValues.php")).append(getUtilMethodArray(getTable().getFieldsIdentifiers(), "getPrimaryKeyValues")).toString();
	}

	/**
	 * helper method to generate array for passed fields
	 *
	 * @param fields the fields which to add to the array
	 * @param methodName the name for the method
	 * @return code to generate array from passed fields
	 */
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

	/**
	 * read a snippet and replace '<type>' by the class name
	 * 
	 * @param fileName filename of the snippet
	 * @return snippet with replaced '<type>'
	 */
	public String getSnippetFromFile(String fileName) {
		return getSnippetFromFile(fileName, null);
	}

	/**
	 * read a snippet and replace '<variable>'
	 *
	 * @param fileName filename of the snippet
	 * @param field tableName field from which to take values
	 * @return snippet with replaced '<variables>'
	 */
	public String getSnippetFromFile(String fileName, Field field) {
		StringBuilder s=new StringBuilder();
		InputStream is=null;
		try {
			is=getClass().getResourceAsStream(new StringBuilder(SNIPPET_PATH).append(fileName).toString());
			String contents=IOUtil.readString(is);
			s.append(StringUtil.replace(contents, getSnippetReplacements(field)));
		} catch (IOException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
		} finally {
			IOUtil.closeQuietly(is);
		}
		return s.toString();
	}

	private Map<CharSequence, CharSequence> getSnippetReplacements(Field field) {
		Map<CharSequence, CharSequence> replacements=new HashMap<CharSequence, CharSequence>();
		if (null!=field) {
			replacements.put("<fieldName>", field.getName());
			replacements.put("<memberName>", getMemberName(field));
			replacements.put("<fieldInfo>", field.getInfoTextCompact());
			replacements.put("<fieldComment>", StringUtil.notNull(field.getComment()));
		}
		String version=new StringBuilder().append(getDb2phpVersion()).append(" - generated: ").append(new SimpleDateFormat().format(Calendar.getInstance().getTime())).toString();
		replacements.put("<db2phpVersion>", version);
		replacements.put("<type>", getClassName());
		replacements.put("<tableDescription>", StringUtil.notNull(getTable().getRemark()));
		replacements.put("<pristine>", getSettings().isTrackFieldModifications() ? "\t\t\t$o->notifyPristine();\n" : "");
		return replacements;
	}

	/**
	 * get all code
	 *
	 * @return all code
	 */
	public String getCode() {
		StringBuilder s=new StringBuilder("<?php\n");
		s.append(getSnippetFromFile("CodeGenerator.class.php"));
		s.append("class ").append(getClassName());
		if (getSettings().isUseInterfaces()) {
			s.append(" implements Db2PhpEntity");
			if (getSettings().isUseInterfaces()) {
				s.append(", Db2PhpEntityModificationTracking");
			}
			s.append("");
		}
		s.append(" {\n");
		s.append(getPreparedStatements());
		s.append(getConsts());
		s.append(getMembers());
		if (isTrackFieldModifications()) {
			s.append(getSnippetFromFile("CodeGenerator.modificationTracking.php"));
		}
		s.append(getAccessors());
		s.append(getSnippetFromFile("CodeGenerator.php"));
		s.append(getUtilMethodToArray());
		s.append(getUtilMethodgetPrimaryKeysToArray());
		s.append(getDatabaseLayer().getCode(this));

		if (getSettings().isEzComponents()) {
			s.append(getSnippetFromFile("CodeGenerator.ezc.php"));
		}
		s.append(getSnippetFromFile("CodeGenerator.dom.php"));
		s.append(getSnippetFromFile("CodeGenerator.toString.php"));
		s.append("}\n");
		s.append("?>");
		return s.toString();
	}

	/**
	 * get the file which this tables code will be written to
	 *
	 * @return  the file which this tables code will be written to
	 * @throws IOException if no directory is set
	 */
	public File getFile() throws IOException {
		if (null==getSettings().getOutputDirectory()) {
			throw new IOException("no directory configured");
		}
		return new File(getSettings().getOutputDirectory(), getFileName());
	}

	/**
	 * write code to specified file
	 *
	 * @param file the file
	 * @throws IOException if the file exists
	 */
	public void writeCode(File file) throws IOException {
		writeCode(file, true);
	}

	/**
	 * write code to specified file
	 *
	 * @param file the file
	 * @param renameOld should old file be renamed?
	 * @throws IOException if the file exists and should not be renamed or rename fails
	 */
	public void writeCode(File file, boolean renameOld) throws IOException {
		if (file.exists()) {
			if (renameOld) {
				File backupFile=null;
				int i=0;
				do {
					backupFile=new File(new StringBuilder(file.getPath()).append(".bak").append(StringUtil.padLeft(i++, "0", 3)).toString());
				} while (backupFile.exists());
				if (!file.renameTo(backupFile)) {
					throw new IOException(new StringBuilder("failed to rename old file to:").append(backupFile.getPath()).toString());
				}
			} else {
				throw new IOException("file exists, refusing to overwrite");
			}
		}
		FileUtil.writeString(getCode(), file);
	}

	/**
	 * write the code
	 *
	 * @throws IOException if the file exists
	 */
	public void writeCode() throws IOException {
		writeCode(getFile());
	}

	/**
	 * @return the camelCaseFairy
	 */
	public CamelCaseFairy getCamelCaseFairy() {
		return camelCaseFairy;
	}

	/**
	 * @param camelCaseFairy the camelCaseFairy to set
	 */
	public void setCamelCaseFairy(CamelCaseFairy camelCaseFairy) {
		this.camelCaseFairy=camelCaseFairy;
	}
}
