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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;
import org.netbeans.api.db.explorer.ConnectionManager;
import org.netbeans.api.db.explorer.DatabaseConnection;
import org.openide.util.Exceptions;

/**
 * represents meta data for a table in a database
 *
 * @author Andreas Schnaiter <rc.poison@gmail.com>
 */
public class Table {

	private DatabaseConnection connection;
	private String catalog;
	private String schema;
	private String name;
	private Set<Field> fields=null;
	private Set<Field> primaryKeys=null;

	public Table() {
	}

	public Table(DatabaseConnection connection, String tableName) {
		this.connection=connection;
		this.name=tableName;
	}

	public Table(DatabaseConnection connection, String catalog, String schema, String name) {
		this.connection=connection;
		this.catalog=catalog;
		this.schema=schema;
		this.name=name;
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

	/**
	 * get the fields for this table
	 *
	 * @return the fields
	 */
	public Set<Field> getFields() {
		try {
			if (null==fields) {
				// get list of primary keys
				ResultSet rsetPrimaryKeys=getConnection().getJDBCConnection().getMetaData().getPrimaryKeys(getCatalog(), getSchema(), getName());
				Set<String> primaryKeyFields=new LinkedHashSet<String>();
				while (rsetPrimaryKeys.next()) {
					primaryKeyFields.add(rsetPrimaryKeys.getString("COLUMN_NAME"));
				}
				rsetPrimaryKeys.close();
				rsetPrimaryKeys=null;

				// get list of fields
				ResultSet rsetColumns=getConnection().getJDBCConnection().getMetaData().getColumns(getCatalog(), getSchema(), getName(), null);
				Field field;
				fields=new LinkedHashSet<Field>();
				while (rsetColumns.next()) {
					field=new Field();
					field.setName(rsetColumns.getString("COLUMN_NAME"));
					field.setPrimaryKey(primaryKeyFields.contains(field.getName()));
					field.setType(rsetColumns.getInt("DATA_TYPE"));
					field.setTypeName(rsetColumns.getString("TYPE_NAME"));
					field.setSize(rsetColumns.getInt("COLUMN_SIZE"));
					field.setDecimalDigits(rsetColumns.getInt("DECIMAL_DIGITS"));
					field.setDefaultValue(rsetColumns.getString("COLUMN_DEF"));
					field.setNullable(rsetColumns.getString("IS_NULLABLE").equalsIgnoreCase("YES"));
					field.setAutoIncrement(rsetColumns.getString("IS_AUTOINCREMENT").equalsIgnoreCase("YES"));
					fields.add(field);
				}
				rsetColumns.close();
				rsetColumns=null;
			}

		} catch (SQLException ex) {
			Exceptions.printStackTrace(ex);
		}
		return fields;
	}

	/**
	 * get the primary keys for this table
	 *
	 * @return the rpimary keys
	 */
	public Set<Field> getPrimaryKeys() {
		if (null==primaryKeys) {
			primaryKeys=new LinkedHashSet<Field>();
			for (Field f : getFields()) {
				if (f.isPrimaryKey()) {
					primaryKeys.add(f);
				}
			}
		}
		return primaryKeys;
	}

	/**
	 * get all fields which are not primary keys
	 *
	 * @return all fields which are not primary keys
	 */
	public Set<Field> getFieldsNotPrimaryKeys() {
		Set<Field> notPrimary=getFields();
		notPrimary.removeAll(getPrimaryKeys());
		return notPrimary;
	}

	@Override
	public String toString() {
		StringBuilder s=new StringBuilder();
		if (null!=getCatalog()) {
			s.append(getCatalog()).append(".");
		}
		if (null!=getSchema()) {
			s.append(getSchema()).append(".");
		}
		return s.append(getName()).toString();
	}

	/**
	 * get list of tables
	 *
	 * @param conn the database connection for which to get the tables
	 * @return the tables for the passed connection
	 */
	public static Set<Table> getTables(DatabaseConnection conn) {
		Set<Table> tables=new LinkedHashSet<Table>();
		try {
			ConnectionManager.getDefault().showConnectionDialog(conn);
			ResultSet rsetTables=conn.getJDBCConnection().getMetaData().getTables(null, null, null, null);
			String tableName;
			while (rsetTables.next()) {
				tables.add(
						new Table(
						conn,
						rsetTables.getString("TABLE_CAT"),
						rsetTables.getString("TABLE_SCHEM"),
						rsetTables.getString("TABLE_NAME")));
			}
		} catch (SQLException ex) {
			Exceptions.printStackTrace(ex);
		}
		return tables;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj==null) {
			return false;
		}
		if (getClass()!=obj.getClass()) {
			return false;
		}
		final Table other=(Table) obj;
		if (this.connection!=other.connection&&(this.connection==null||!this.connection.equals(other.connection))) {
			return false;
		}
		if ((this.catalog==null) ? (other.catalog!=null) : !this.catalog.equals(other.catalog)) {
			return false;
		}
		if ((this.schema==null) ? (other.schema!=null) : !this.schema.equals(other.schema)) {
			return false;
		}
		if ((this.name==null) ? (other.name!=null) : !this.name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash=3;
		hash=83*hash+(this.connection!=null ? this.connection.hashCode() : 0);
		hash=83*hash+(this.catalog!=null ? this.catalog.hashCode() : 0);
		hash=83*hash+(this.schema!=null ? this.schema.hashCode() : 0);
		hash=83*hash+(this.name!=null ? this.name.hashCode() : 0);
		hash=83*hash+(this.fields!=null ? this.fields.hashCode() : 0);
		return hash;
	}
}
