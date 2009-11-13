/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.db2php.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author andreas.schnaiter
 */
public class ForeignKey {

	private String pkName;
	private String fkName;
	private String pkTableName;
	private String pkFieldName;
	private String fkTableName;
	private String fkFieldName;
	private short updateRule;
	private short deleteRule;

	/**
	 * @return the pkName
	 */
	public String getPkName() {
		return pkName;
	}

	/**
	 * @param pkName the pkName to set
	 */
	public void setPkName(String pkName) {
		this.pkName=pkName;
	}

	/**
	 * @return the fkName
	 */
	public String getFkName() {
		return fkName;
	}

	/**
	 * @param fkName the fkName to set
	 */
	public void setFkName(String fkName) {
		this.fkName=fkName;
	}

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
	 * @return the pkFieldName
	 */
	public String getPkFieldName() {
		return pkFieldName;
	}

	/**
	 * @param pkFieldName the pkFieldName to set
	 */
	public void setPkFieldName(String pkFieldName) {
		this.pkFieldName=pkFieldName;
	}

	public Field getPkField() {
		Field f=new Field();
		f.setName(getPkFieldName());
		return f;
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
	 * @return the fkFieldName
	 */
	public String getFkFieldName() {
		return fkFieldName;
	}

	/**
	 * @param fkFieldName the fkFieldName to set
	 */
	public void setFkFieldName(String fkFieldName) {
		this.fkFieldName=fkFieldName;
	}

	public Field getFkField() {
		Field f=new Field();
		f.setName(getFkFieldName());
		return f;
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
