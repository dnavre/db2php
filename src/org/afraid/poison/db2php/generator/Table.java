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

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * represents meta data for a table in a database
 *
 * @author Andreas Schnaiter <rc.poison@gmail.com>
 */
public class Table {

	private String catalog;
	private String schema;
	private String name;
	private Set<Field> fields=null;
	private Set<Field> primaryKeys=null;
	private String identifierQuoteString;

	/**
	 * CTOR
	 *
	 * @param connection connection from which to init values
	 * @param tableName the name of the table to read
	 */
	public Table(Connection connection, String tableName) {
		setName(name);
		initFields(connection);
	}

	/**
	 * CTOR
	 *
	 * @param connection connection from which to init values
	 * @param catalog the tables catalog
	 * @param schema the tables scheme
	 * @param name the name of the table to read
	 */
	public Table(Connection connection, String catalog, String schema, String name) {
		setName(name);
		setCatalog(catalog);
		setSchema(schema);
		initFields(connection);
	}

	/**
	 * initialise field list
	 *
	 * @param connection the connection to work on
	 */
	private void initFields(Connection connection) {
		try {
			setIdentifierQuoteString(connection.getMetaData().getIdentifierQuoteString());

			// get list of primary keys
			Set<String> primaryKeyFields=new LinkedHashSet<String>();
			ResultSet rsetPrimaryKeys=null;
			try {
				rsetPrimaryKeys=connection.getMetaData().getPrimaryKeys(getCatalog(), getSchema(), getName());
				while (rsetPrimaryKeys.next()) {
					primaryKeyFields.add(rsetPrimaryKeys.getString("COLUMN_NAME"));
				}
				rsetPrimaryKeys.close();
			} catch (SQLException ex) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			} finally {
				rsetPrimaryKeys=null;
			}
			

			// get row identifiers
			Set<String> rowIdentifiers=new LinkedHashSet<String>();
			ResultSet rsetRowIdentifiers=null;
			try {
				rsetRowIdentifiers=connection.getMetaData().getBestRowIdentifier(getCatalog(), getSchema(), getName(), DatabaseMetaData.bestRowNotPseudo, true);
				while (rsetRowIdentifiers.next()) {
					rowIdentifiers.add(rsetRowIdentifiers.getString("COLUMN_NAME"));
				}
				rsetRowIdentifiers.close();
			} catch (SQLException ex) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			} finally {
				rsetRowIdentifiers=null;
			}
			

			// get indexes
			Set<String> indexesUnique=new LinkedHashSet<String>();
			Set<String> indexesNonUnique=new LinkedHashSet<String>();
			ResultSet rsetIndexes=null;
			try {
				rsetIndexes=connection.getMetaData().getIndexInfo(getCatalog(), getSchema(), getName(), false, false);
				String fieldName;
				boolean unique;
				while (rsetIndexes.next()) {
					// NON_UNIQUE
					fieldName=rsetIndexes.getString("COLUMN_NAME");
					unique=!rsetIndexes.getBoolean("NON_UNIQUE");
					if (unique) {
						indexesUnique.add(fieldName);
					} else {
						indexesNonUnique.add(fieldName);
					}
				}
				rsetIndexes.close();
			} catch (SQLException ex) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			} finally {
				rsetIndexes=null;
			}
			


			// get list of fields
			ResultSet rsetColumns=connection.getMetaData().getColumns(getCatalog(), getSchema(), getName(), null);
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
				field.setComment(rsetColumns.getString("REMARKS"));
				field.setRowIdentifier(rowIdentifiers.contains(field.getName()));
				if (indexesUnique.contains(field.getName())) {
					field.setIndex(Field.INDEX_UNIQUE);
				} else if (indexesNonUnique.contains(field.getName())) {
					field.setIndex(Field.INDEX_NON_UNIQUE);
				}
				fields.add(field);
			}
			rsetColumns.close();
			rsetColumns=null;

		} catch (SQLException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
		}
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
		return new LinkedHashSet<Field>(fields);
	}

	/**
	 * get the primary keys for this table
	 *
	 * @return the rpimary keys
	 */
	public synchronized Set<Field> getPrimaryKeys() {
		if (null==primaryKeys) {
			primaryKeys=new LinkedHashSet<Field>();
			for (Field f : getFields()) {
				if (f.isPrimaryKey()) {
					primaryKeys.add(f);
				}
			}
		}
		return new LinkedHashSet<Field>(primaryKeys);
	}

	/**
	 * get fields by which a single record should be identified. returns the primary keys unless none are there. in that case it tries to guess.
	 *
	 * @return fields by which a single record should be identified
	 */
	public Set<Field> getFieldsIdentifiers() {
		Set<Field> identifiers=getPrimaryKeys();
		if (identifiers.isEmpty()) {
			identifiers=getFieldsBestRowIdentifiers();
			if (identifiers.isEmpty()) {
				identifiers=getFieldsIndexesUnique();
				if (identifiers.isEmpty()) {
					identifiers=getFieldsIndexesNonUnique();
					if (identifiers.isEmpty()) {
						identifiers.add(getFields().iterator().next());
					}
				}
			}
		}
		return identifiers;
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

	/**
	 * get fields which are reported as best row identifiers by the jdbc driver
	 *
	 * @return fields which are reported as best row identifiers by the jdbc driver
	 */
	public Set<Field> getFieldsBestRowIdentifiers() {
		Set<Field> bestRowIdentifiers=new LinkedHashSet<Field>();
		for (Field f : getFields()) {
			if (f.isRowIdentifier()) {
				bestRowIdentifiers.add(f);
			}
		}
		return bestRowIdentifiers;
	}

	/**
	 * get the unique indexes
	 *
	 * @return unique indexes
	 */
	public Set<Field> getFieldsIndexesUnique() {
		Set<Field> indexesUnique=new LinkedHashSet<Field>();
		for (Field f : getFields()) {
			if (f.isIndexUnique()) {
				indexesUnique.add(f);
			}
		}
		return indexesUnique;
	}

	/**
	 * get the non-unique indexes
	 *
	 * @return non-unique indexes
	 */
	public Set<Field> getFieldsIndexesNonUnique() {
		Set<Field> indexesNonUnique=new LinkedHashSet<Field>();
		for (Field f : getFields()) {
			if (f.isIndexNonUnique()) {
				indexesNonUnique.add(f);
			}
		}
		return indexesNonUnique;
	}

	/**
	 * gets the autoincrement fields
	 *
	 * @return autoincrement fields
	 */
	public Set<Field> getFieldsAutoIncrement() {
		Set<Field> autoIncrement=new LinkedHashSet<Field>();
		for (Field f : getFields()) {
			if (f.isAutoIncrement()) {
				autoIncrement.add(f);
			}
		}
		return autoIncrement;
	}

	/**
	 * get all fields which are not autoincrement fields
	 *
	 * @return all fields which are not autoincrement fields
	 */
	public Set<Field> getFieldsNotAutoIncrement() {
		Set<Field> notAutoIncrement=getFields();
		notAutoIncrement.removeAll(getFieldsAutoIncrement());
		return notAutoIncrement;
	}

	/**
	 * @return the identifierQuoteString
	 */
	public String getIdentifierQuoteString() {
		return identifierQuoteString;
	}

	/**
	 * @param identifierQuoteString the identifierQuoteString to set
	 */
	public void setIdentifierQuoteString(String identifierQuoteString) {
		this.identifierQuoteString=identifierQuoteString;
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
	 * @param listener the listener which gets an event each time a table is read
	 * @return the tables for the passed connection
	 */
	public static Set<Table> getTables(Connection conn, TableListener listener) {
		Set<Table> tables=new LinkedHashSet<Table>();
		ResultSet rsetTables=null;
		try {
			rsetTables=conn.getMetaData().getTables(null, null, null, null);
			String tableName;
			Table table;
			while (rsetTables.next()) {
				table=new Table(
						conn,
						rsetTables.getString("TABLE_CAT"),
						rsetTables.getString("TABLE_SCHEM"),
						rsetTables.getString("TABLE_NAME"));
				tables.add(table);

				if (null!=listener) {
					TableEvent evt=new TableEvent(conn, table);
					listener.tableStatusChanged(evt);
				}
			}
			rsetTables.close();
		} catch (SQLException ex) {
			Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			rsetTables=null;
		}
		return tables;
	}

	/**
	 * get list of tables
	 *
	 * @param conn the database connection for which to get the tables
	 * @return the tables for the passed connection
	 */
	public static Set<Table> getTables(Connection conn) {
		return getTables(conn, null);
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
		hash=67*hash+(this.catalog!=null ? this.catalog.hashCode() : 0);
		hash=67*hash+(this.schema!=null ? this.schema.hashCode() : 0);
		hash=67*hash+(this.name!=null ? this.name.hashCode() : 0);
		return hash;
	}
}
