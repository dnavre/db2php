/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.db2php.generator;

import org.netbeans.api.db.explorer.DatabaseConnection;

/**
 *
 * @author poison
 */
public class Table {

	private DatabaseConnection connection;
	private String tableName;

	public Table() {
	}

	public Table(DatabaseConnection connection, String tableName) {
		this.connection=connection;
		this.tableName=tableName;
	}

	/**
	 * @return the connection
	 */
	public DatabaseConnection getConnection() {
		return connection;
	}

	/**
	 * @param connection the connection to set
	 */
	public void setConnection(DatabaseConnection connection) {
		this.connection=connection;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName=tableName;
	}

	@Override
	public String toString() {
		return getTableName();
	}
}
