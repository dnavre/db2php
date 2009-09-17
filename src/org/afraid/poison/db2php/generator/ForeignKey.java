/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.db2php.generator;

/**
 *
 * @author andreas.schnaiter
 */
public class ForeignKey {

	private String pkTableName;
	private Field pkField;
	private String fkTableName;
	private Field fkField;
	private short updateRule;
	private short deleteRule;

	/**
	 * @return the pkTableName
	 */
	public String getPkTableName() {
		return pkTableName;
	}

	/**
	 * @param pkTableName the pkTableName to set
	 */
	public void setPkTableName(String pkTableName) {
		this.pkTableName=pkTableName;
	}

	/**
	 * @return the pkField
	 */
	public Field getPkField() {
		return pkField;
	}

	/**
	 * @param pkField the pkField to set
	 */
	public void setPkField(Field pkField) {
		this.pkField=pkField;
	}

	/**
	 * @return the fkTableName
	 */
	public String getFkTableName() {
		return fkTableName;
	}

	/**
	 * @param fkTableName the fkTableName to set
	 */
	public void setFkTableName(String fkTableName) {
		this.fkTableName=fkTableName;
	}

	/**
	 * @return the fkField
	 */
	public Field getFkField() {
		return fkField;
	}

	/**
	 * @param fkField the fkField to set
	 */
	public void setFkField(Field fkField) {
		this.fkField=fkField;
	}

	/**
	 * @return the updateRule
	 */
	public short getUpdateRule() {
		return updateRule;
	}

	/**
	 * @param updateRule the updateRule to set
	 */
	public void setUpdateRule(short updateRule) {
		this.updateRule=updateRule;
	}

	/**
	 * @return the deleteRule
	 */
	public short getDeleteRule() {
		return deleteRule;
	}

	/**
	 * @param deleteRule the deleteRule to set
	 */
	public void setDeleteRule(short deleteRule) {
		this.deleteRule=deleteRule;
	}
}
