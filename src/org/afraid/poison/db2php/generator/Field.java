/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.db2php.generator;

/**
 *
 * @author poison
 */
public class Field {

	private String name;
	private int type;
	private String typeName;
	private int size=0;
	private int decimalDigits=0;
	private boolean nullable=true;
	private String defaultValue;
	private boolean primaryKey=false;
	private boolean autoIncrement=false;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
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
		if (this.type!=other.type) {
			return false;
		}
		if ((this.typeName==null) ? (other.typeName!=null) : !this.typeName.equals(other.typeName)) {
			return false;
		}
		if (this.size!=other.size) {
			return false;
		}
		if (this.decimalDigits!=other.decimalDigits) {
			return false;
		}
		if (this.nullable!=other.nullable) {
			return false;
		}
		if ((this.defaultValue==null) ? (other.defaultValue!=null) : !this.defaultValue.equals(other.defaultValue)) {
			return false;
		}
		if (this.primaryKey!=other.primaryKey) {
			return false;
		}
		if (this.autoIncrement!=other.autoIncrement) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash=3;
		hash=73*hash+(this.name!=null ? this.name.hashCode() : 0);
		hash=73*hash+this.type;
		hash=73*hash+(this.typeName!=null ? this.typeName.hashCode() : 0);
		hash=73*hash+this.size;
		hash=73*hash+(this.nullable ? 1 : 0);
		hash=73*hash+(this.defaultValue!=null ? this.defaultValue.hashCode() : 0);
		hash=73*hash+(this.primaryKey ? 1 : 0);
		hash=73*hash+(this.autoIncrement ? 1 : 0);
		return hash;
	}
}
