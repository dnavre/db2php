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

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.SQLXML;
import java.sql.Struct;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.afraid.poison.common.CollectionUtil;
import org.afraid.poison.common.StringUtil;

/**
 * represents meta data for a field in a database table
 *
 * @author Andreas Schnaiter <rc.poison@gmail.com>
 */
public class Field {

	/**
	 * field is not an index
	 */
	public static final int INDEX_NONE=0;
	/**
	 * field is a unique index
	 */
	public static final int INDEX_UNIQUE=1;
	/**
	 * field is non-unique index
	 */
	public static final int INDEX_NON_UNIQUE=2;
	/**
	 * number types
	 */
	public static final int[] NUMBER_TYPES={Types.BIGINT, Types.DECIMAL, Types.DOUBLE, Types.FLOAT, Types.INTEGER, Types.NUMERIC, Types.SMALLINT, Types.TINYINT};
	static {
		Arrays.sort(NUMBER_TYPES);
	}
	/**
	 * @see http://java.sun.com/j2se/1.5.0/docs/guide/jdbc/getstart/mapping.html
	 */
	private static final Map<Integer, Class<?>> TYPES_JAVA=new HashMap<Integer, Class<?>>();
	static {
		TYPES_JAVA.put(Types.CHAR, String.class);
		TYPES_JAVA.put(Types.NCHAR, String.class);
		TYPES_JAVA.put(Types.VARCHAR, String.class);
		TYPES_JAVA.put(Types.NVARCHAR, String.class);
		TYPES_JAVA.put(Types.LONGVARCHAR, String.class);
		TYPES_JAVA.put(Types.LONGNVARCHAR, String.class);
		TYPES_JAVA.put(Types.NUMERIC, BigDecimal.class);
		TYPES_JAVA.put(Types.DECIMAL, BigDecimal.class);
		TYPES_JAVA.put(Types.BIT, Boolean.class);
		TYPES_JAVA.put(Types.BOOLEAN, Boolean.class);
		TYPES_JAVA.put(Types.TINYINT, Integer.class);
		TYPES_JAVA.put(Types.SMALLINT, Integer.class);
		TYPES_JAVA.put(Types.INTEGER, Integer.class);
		TYPES_JAVA.put(Types.BIGINT, Long.class);
		TYPES_JAVA.put(Types.REAL, Float.class);
		TYPES_JAVA.put(Types.FLOAT, Double.class);
		TYPES_JAVA.put(Types.DOUBLE, Double.class);
		TYPES_JAVA.put(Types.BINARY, Byte.class);
		TYPES_JAVA.put(Types.VARBINARY, Byte.class);
		TYPES_JAVA.put(Types.LONGVARBINARY, Byte.class);
		TYPES_JAVA.put(Types.DATE, Date.class);
		TYPES_JAVA.put(Types.TIME, Time.class);
		TYPES_JAVA.put(Types.TIMESTAMP, Timestamp.class);
		TYPES_JAVA.put(Types.CLOB, Clob.class);
		TYPES_JAVA.put(Types.NCLOB, NClob.class);
		TYPES_JAVA.put(Types.BLOB, Blob.class);
		TYPES_JAVA.put(Types.ARRAY, Array.class);
		TYPES_JAVA.put(Types.STRUCT, Struct.class);
		TYPES_JAVA.put(Types.SQLXML, SQLXML.class);
		TYPES_JAVA.put(Types.JAVA_OBJECT, Object.class);
		TYPES_JAVA.put(Types.OTHER, Object.class);
	}

	private static final Map<Integer, String> TYPES_SQL=new HashMap<Integer, String>();
	static {
		TYPES_SQL.put(Types.CHAR, "CHAR");
		TYPES_SQL.put(Types.NCHAR, "NCHAR");
		TYPES_SQL.put(Types.VARCHAR, "VARCHAR");
		TYPES_SQL.put(Types.NVARCHAR, "NVARCHAR");
		TYPES_SQL.put(Types.LONGVARCHAR, "LONGVARCHAR");
		TYPES_SQL.put(Types.LONGNVARCHAR, "LONGNVARCHAR");
		TYPES_SQL.put(Types.NUMERIC, "NUMERIC");
		TYPES_SQL.put(Types.DECIMAL, "DECIMAL");
		TYPES_SQL.put(Types.BIT, "BIT");
		TYPES_SQL.put(Types.BOOLEAN, "BOOLEAN");
		TYPES_SQL.put(Types.TINYINT, "TINYINT");
		TYPES_SQL.put(Types.SMALLINT, "SMALLINT");
		TYPES_SQL.put(Types.INTEGER, "INTEGER");
		TYPES_SQL.put(Types.BIGINT, "BIGINT");
		TYPES_SQL.put(Types.REAL, "REAL");
		TYPES_SQL.put(Types.FLOAT, "FLOAT");
		TYPES_SQL.put(Types.DOUBLE, "DOUBLE");
		TYPES_SQL.put(Types.BINARY, "BINARY");
		TYPES_SQL.put(Types.VARBINARY, "VARBINARY");
		TYPES_SQL.put(Types.LONGVARBINARY, "LONGVARBINARY");
		TYPES_SQL.put(Types.DATE, "DATE");
		TYPES_SQL.put(Types.TIME, "TIME");
		TYPES_SQL.put(Types.TIMESTAMP, "TIMESTAMP");
		TYPES_SQL.put(Types.CLOB, "CLOB");
		TYPES_SQL.put(Types.NCLOB, "NCLOB");
		TYPES_SQL.put(Types.BLOB, "BLOB");
		TYPES_SQL.put(Types.ARRAY, "ARRAY");
		TYPES_SQL.put(Types.STRUCT, "STRUCT");
		TYPES_SQL.put(Types.SQLXML, "SQLXML");
		TYPES_SQL.put(Types.JAVA_OBJECT, "JAVA_OBJECT");
		TYPES_SQL.put(Types.OTHER, "OTHER");
	}
	/*
	private static final Map<Integer, Class<?>> TYPES_PHP=new HashMap<Integer, Class<?>>();
	static {
		TYPES_PHP.put(Types.CHAR, String.class);
		TYPES_PHP.put(Types.NCHAR, String.class);
		TYPES_PHP.put(Types.VARCHAR, String.class);
		TYPES_PHP.put(Types.NVARCHAR, String.class);
		TYPES_PHP.put(Types.LONGNVARCHAR, String.class);
		TYPES_PHP.put(Types.NUMERIC, BigDecimal.class);
		TYPES_PHP.put(Types.DECIMAL, BigDecimal.class);
		TYPES_PHP.put(Types.BIT, Boolean.class);
		TYPES_PHP.put(Types.BOOLEAN, Boolean.class);
		TYPES_PHP.put(Types.TINYINT, Integer.class);
		TYPES_PHP.put(Types.SMALLINT, Integer.class);
		TYPES_PHP.put(Types.INTEGER, Integer.class);
		TYPES_PHP.put(Types.BIGINT, Long.class);
		TYPES_PHP.put(Types.REAL, Float.class);
		TYPES_PHP.put(Types.FLOAT, Double.class);
		TYPES_PHP.put(Types.DOUBLE, Double.class);
		TYPES_PHP.put(Types.BINARY, Byte.class);
		TYPES_PHP.put(Types.VARBINARY, Byte.class);
		TYPES_PHP.put(Types.LONGVARBINARY, Byte.class);
		TYPES_PHP.put(Types.DATE, Date.class);
		TYPES_PHP.put(Types.TIME, Time.class);
		TYPES_PHP.put(Types.TIMESTAMP, Timestamp.class);
		TYPES_PHP.put(Types.CLOB, Clob.class);
		TYPES_PHP.put(Types.NCLOB, NClob.class);
		TYPES_PHP.put(Types.BLOB, Blob.class);
		TYPES_PHP.put(Types.TIMESTAMP, Timestamp.class);
		TYPES_PHP.put(Types.ARRAY, Array.class);
		TYPES_PHP.put(Types.STRUCT, Struct.class);
		TYPES_PHP.put(Types.SQLXML, SQLXML.class);
		TYPES_PHP.put(Types.JAVA_OBJECT, Object.class);
		TYPES_PHP.put(Types.OTHER, Object.class);
	}*/
	private String name;
	private int type;
	private String typeName;
	private String serialName;
	private int size=0;
	private int decimalDigits=0;
	private boolean nullable=true;
	private String defaultValue;
	private boolean primaryKey=false;
	private boolean autoIncrement=false;
	private String comment;
	private boolean rowIdentifier;
	private int index=0;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the name with the first char uppercase
	 */
	public String getNameFirstCharUpper() {
		return StringUtil.firstCharToUpperCase(getName());
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
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName=typeName;
	}

	public Class<?> getTypeJava() {
		return TYPES_JAVA.get(getType());
	}

	public String getTypeSqlString() {
		return TYPES_SQL.get(getType());
	}

	public String getTypePHP() {
		Class<?> tC=getTypeJava();
		if (Boolean.class.equals(tC)) {
			return "bool";
		} else if (Integer.class.equals(tC)) {
			return "int";
		} else if (Long.class.equals(tC) || Float.class.equals(tC) || Double.class.equals(tC) || BigDecimal.class.equals(tC)) {
			return "float";
		}
		return "string";
	}

	/**
	 * @return the serialName
	 */
	public String getSerialName() {
		return serialName;
	}

	/**
	 * @param serialName the serialName to set
	 */
	public void setSerialName(String serialName) {
		this.serialName=serialName;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size=size;
	}

	/**
	 * @return the decimalDigits
	 */
	public int getDecimalDigits() {
		return decimalDigits;
	}

	/**
	 * @param decimalDigits the decimalDigits to set
	 */
	public void setDecimalDigits(int decimalDigits) {
		this.decimalDigits=decimalDigits;
	}

	/**
	 * @return the nullable
	 */
	public boolean isNullable() {
		return nullable;
	}

	/**
	 * @param nullable the nullable to set
	 */
	public void setNullable(boolean nullable) {
		this.nullable=nullable;
	}

	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue=defaultValue;
	}

	/**
	 * @return the primaryKey
	 */
	public boolean isPrimaryKey() {
		return primaryKey;
	}

	/**
	 * @param primaryKey the primaryKey to set
	 */
	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey=primaryKey;
	}

	/**
	 * @return the autoIncrement
	 */
	public boolean isAutoIncrement() {
		return autoIncrement;
	}

	/**
	 * @param autoIncrement the autoIncrement to set
	 */
	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement=autoIncrement;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment=comment;
	}

	/**
	 * @return the rowIdentifier
	 */
	public boolean isRowIdentifier() {
		return rowIdentifier;
	}

	/**
	 * @param identifier the rowIdentifier
	 */
	public void setRowIdentifier(boolean identifier) {
		this.rowIdentifier=identifier;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index=index;
	}

	/**
	 * check if unique index
	 *
	 * @return true if unique index
	 */
	public boolean isIndexUnique() {
		return getIndex()==INDEX_UNIQUE;
	}

	/**
	 * check if non-unique index
	 *
	 * @return true if non-unique index
	 */
	public boolean isIndexNonUnique() {
		return getIndex()==INDEX_NON_UNIQUE;
	}

	/**
	 * get a bit of human readable information for this field
	 *
	 * @return a bit of human readable information for this field
	 */
	public String getInfoText() {
		StringBuilder s=new StringBuilder();
		s.append("type:").append(getTypeName()).append(",");
		s.append("size:").append(getSize()).append(",");
		s.append("nullable:").append(isNullable()).append(",");
		s.append("default:").append(getDefaultValue()).append(",");
		s.append("primary:").append(isPrimaryKey()).append(",");
		s.append("unique:").append(isIndexUnique()).append(",");
		s.append("index:").append(isIndexNonUnique());
		return s.toString();
	}

	/**
	 * get a bit of human readable information for this field
	 *
	 * @return a bit of human readable information for this field
	 */
	public String getInfoTextCompact() {
		StringBuilder s=new StringBuilder();
		s.append("type:").append(getTypeName()).append(",");
		s.append("size:").append(getSize()).append(",");
		s.append("default:").append(getDefaultValue());
		ArrayList<String> prop=new ArrayList<String>();
		if (isPrimaryKey()) {
			prop.add("primary");
		}
		if (isIndexUnique()) {
			prop.add("unique");
		}
		if (isIndexNonUnique()) {
			prop.add("index");
		}
		if (isAutoIncrement()) {
			prop.add("autoincrement");
		}
		if (isNullable()) {
			prop.add("nullable");
		}
		if (!prop.isEmpty()) {
			s.append(",").append(CollectionUtil.join(prop, ","));
		}
		return s.toString();
	}

	/**
	 * check if field type is a numeric type
	 *
	 * @return true if numeric type
	 */
	public boolean isNumberType() {
		return Arrays.binarySearch(NUMBER_TYPES, getType())>=0;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj==null) {
			return false;
		}
		if (getClass()!=obj.getClass()) {
			return false;
		}
		final Field other=(Field) obj;
		if ((this.name==null) ? (other.name!=null) : !this.name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash=7;
		hash=61*hash+(this.name!=null ? this.name.hashCode() : 0);
		return hash;
	}

	@Override
	public String toString() {
		return getName();
	}
}
