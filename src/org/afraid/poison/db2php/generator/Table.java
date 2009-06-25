/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.db2php.generator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
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
	private Set<Field> fields;

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
					field.setDefaultValue(rsetColumns.getNString("COLUMN_DEF"));
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
		Set<Field> primaryKeys=new LinkedHashSet<Field>();
		for (Field f : getFields()) {
			if (f.isPrimaryKey()) {
				primaryKeys.add(f);
			}
		}
		return primaryKeys;
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
}
