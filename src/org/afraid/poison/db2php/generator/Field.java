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
	private String name;
	private int type;
	private String typeName;
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
