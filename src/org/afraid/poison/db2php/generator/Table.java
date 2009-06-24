/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.db2php.generator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.db.explorer.ConnectionManager;
import org.netbeans.api.db.explorer.DatabaseConnection;
import org.openide.util.Exceptions;

/**
 *
 * @author poison
 */
public class Table {

	private DatabaseConnection connection;
	private String catalog;
	private String schema;
	private String name;

	public Table() {
	}

	public Table(DatabaseConnection connection, String tableName) {
		this.connection=connection;
		this.name=tableName;
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
	 * @return the catalog
	 */
	public String getCatalog() {
		return catalog;
	}

	/**
	 * @param catalog the catalog to set
	 */
	public void setCatalog(String catalog) {
		this.catalog=catalog;
	}

	/**
	 * @return the schema
	 */
	public String getSchema() {
		return schema;
	}

	/**
	 * @param schema the schema to set
	 */
	public void setSchema(String schema) {
		this.schema=schema;
	}

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

	@Override
	public String toString() {
		StringBuilder s=new StringBuilder();
		if (null!=getSchema()) {
			s.append(getSchema()).append(".");
		}
		if (null!=getCatalog()) {
			s.append(getCatalog()).append(".");
		}
		return s.append(getName()).toString();
	}

	public static List<Table> getTables(DatabaseConnection conn) {
		List<Table> tables=new ArrayList<Table>();
		try {
			ConnectionManager.getDefault().showConnectionDialog(conn);
			ResultSet rsetTables=conn.getJDBCConnection().getMetaData().getTables(null, null, null, null);
			String tableName;
			while (rsetTables.next()) {
				tableName=rsetTables.getString("TABLE_NAME");
				tables.add(new Table(conn, tableName));
				System.err.println(tableName);
			}
		} catch (SQLException ex) {
			Exceptions.printStackTrace(ex);
		}
		return tables;
	}
}
