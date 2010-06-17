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
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.afraid.poison.common.CollectionUtil;
import org.afraid.poison.common.DbUtil;
import org.afraid.poison.common.PredicateReferenceValue;

/**
 * represents meta data for a table in a database
 *
 * @author Andreas Schnaiter <rc.poison@gmail.com>
 */
public class Table {

	private static final Pattern SERIAL_NAME_PATTERN_PGSQL=Pattern.compile("nextval\\('([^']+)'");
	private String catalog;
	private String schema;
	private String name;
	private String remark;
	private Set<Field> fields=null;
	private Set<Field> fieldsPrimaryKeys=null;
	private Set<Index> indexes=null;
	private Set<ForeignKey> importedKeys=null;
	private Set<ForeignKey> exportedKeys=null;
	private String identifierQuoteString;

	/**
	 * CTOR
	 *
	 * @param connection connection from which to init values
	 * @param tableName the name of the table to read
	 */
	public Table(Connection connection, String tableName) {
		setName(tableName);
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
		fields=new LinkedHashSet<Field>();
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
				DbUtil.closeQuietly(rsetPrimaryKeys);
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
				DbUtil.closeQuietly(rsetRowIdentifiers);
			}

			// get list of fields
			ResultSet rsetColumns=null;
			Map<String, Field> fieldNameMap=new LinkedHashMap<String, Field>();
			Field field;
			try {
				rsetColumns=connection.getMetaData().getColumns(getCatalog(), getSchema(), getName(), null);
				while (rsetColumns.next()) {
					field=new Field();
					field.setName(rsetColumns.getString("COLUMN_NAME"));
					field.setPrimaryKey(primaryKeyFields.contains(field.getName()));
					field.setType(rsetColumns.getInt("DATA_TYPE"));
					field.setTypeName(rsetColumns.getString("TYPE_NAME"));
					field.setSize(rsetColumns.getInt("COLUMN_SIZE"));
					field.setDecimalDigits(rsetColumns.getInt("DECIMAL_DIGITS"));
					field.setDefaultValue(rsetColumns.getString("COLUMN_DEF"));
					field.setNullable("YES".equalsIgnoreCase(rsetColumns.getString("IS_NULLABLE")));
					try {
						// BUG in pgsql JDBC Driver
						field.setAutoIncrement("YES".equalsIgnoreCase(rsetColumns.getString("IS_AUTOINCREMENT")));
					} catch (SQLException ex) {
						//Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
					}
					field.setComment(rsetColumns.getString("REMARKS"));
					field.setRowIdentifier(rowIdentifiers.contains(field.getName()));
					/*if (indexeFieldsUnique.contains(field.getName())) {
					field.setIndex(Field.INDEX_UNIQUE);
					} else if (indexeFieldsNonUnique.contains(field.getName())) {
					field.setIndex(Field.INDEX_NON_UNIQUE);
					}*/
					// pgsql ...
					if ("SERIAL".equalsIgnoreCase(field.getTypeName())||"BIGSERIAL".equalsIgnoreCase(field.getTypeName())) {
						field.setAutoIncrement(true);
						Matcher m=SERIAL_NAME_PATTERN_PGSQL.matcher(field.getDefaultValue());
						if (m.find()) {
							field.setSerialName(m.group(1));
						}
					}
					fields.add(field);
					fieldNameMap.put(field.getName(), field);
				}
			} catch (SQLException ex) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			} finally {
				DbUtil.closeQuietly(rsetColumns);
			}

			// get indexes
			indexes=new LinkedHashSet<Index>();
			ResultSet rsetIndexes=null;
			try {
				rsetIndexes=connection.getMetaData().getIndexInfo(getCatalog(), getSchema(), getName(), false, false);
				String indexName;
				String fieldName;
				Index index;
				boolean unique;
				while (rsetIndexes.next()) {
					// NON_UNIQUE
					indexName=rsetIndexes.getString("INDEX_NAME");
					fieldName=rsetIndexes.getString("COLUMN_NAME");
					unique=!rsetIndexes.getBoolean("NON_UNIQUE");
					index=CollectionUtil.find(indexes, new PredicateReferenceValue(indexName) {

						@Override
						public boolean evaluate(Object o) {
							String n=((Index) o).getName();
							return (null==n&&null==getReferenceValue())||(null!=getReferenceValue()&&getReferenceValue().equals(n));
						}
					});
					if (null==index) {
						index=new Index();
						index.setName(indexName);
						index.setUnique(unique);
						indexes.add(index);
					}
					field=fieldNameMap.get(fieldName);
					if (null!=field) {
						if (unique) {
							field.setIndex(Field.INDEX_UNIQUE);
						} else {
							field.setIndex(Field.INDEX_NON_UNIQUE);
						}
						index.getFields().add(field);
					} else {
						Logger.getLogger(getClass().getName()).log(Level.WARNING, new StringBuilder("Field ").append(fieldName).append(" not found defined in index ").append(indexName).toString());
					}
				}
				rsetIndexes.close();
			} catch (SQLException ex) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			} finally {
				DbUtil.closeQuietly(rsetIndexes);
			}

			// imported keys
			ResultSet rsetImportedKeys=null;
			importedKeys=new LinkedHashSet<ForeignKey>();
			try {
				rsetImportedKeys=connection.getMetaData().getImportedKeys(getCatalog(), getSchema(), getName());
				importedKeys.addAll(getFk(rsetImportedKeys));
			} catch (SQLException ex) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			} finally {
				DbUtil.closeQuietly(rsetImportedKeys);
			}

			// exported keys
			ResultSet rsetExportedKeys=null;
			exportedKeys=new LinkedHashSet<ForeignKey>();
			try {
				rsetExportedKeys=connection.getMetaData().getExportedKeys(getCatalog(), getSchema(), getName());
				exportedKeys.addAll(getFk(rsetExportedKeys));
			} catch (SQLException ex) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
			} finally {
				DbUtil.closeQuietly(rsetExportedKeys);
			}

		} catch (SQLException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * get foreign keys from rset
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private static Set<ForeignKey> getFk(ResultSet rs) throws SQLException {
		Set<ForeignKey> fks=new LinkedHashSet<ForeignKey>();
		while (rs.next()) {
			ForeignKey fk=new ForeignKey();
			fk.setPkName(rs.getString("PK_NAME"));
			fk.setFkName(rs.getString("FK_NAME"));
			fk.setPkTableName(rs.getString("PKTABLE_NAME"));
			fk.setPkFieldName(rs.getString("PKCOLUMN_NAME"));
			fk.setFkTableName(rs.getString("FKTABLE_NAME"));
			fk.setFkFieldName(rs.getString("FKCOLUMN_NAME"));
			fk.setUpdateRule(rs.getShort("UPDATE_RULE"));
			fk.setDeleteRule(rs.getShort("DELETE_RULE"));
			fks.add(fk);
		}
		return fks;
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
	 * @return the primary key fields
	 */
	public synchronized Set<Field> getFieldsPrimaryKey() {
		if (null==fieldsPrimaryKeys) {
			fieldsPrimaryKeys=new LinkedHashSet<Field>();
			for (Field f : getFields()) {
				if (f.isPrimaryKey()) {
					fieldsPrimaryKeys.add(f);
				}
			}
		}
		return new LinkedHashSet<Field>(fieldsPrimaryKeys);
	}

	/**
	 * get fields by which a single record should be identified. returns the primary keys unless none are there. in that case it tries to guess.
	 *
	 * @return fields by which a single record should be identified
	 */
	public Set<Field> getFieldsIdentifiers() {
		Set<Field> identifiers=getFieldsPrimaryKey();
		if (identifiers.isEmpty()) {
			identifiers=getFieldsBestRowIdentifiers();
			if (identifiers.isEmpty()) {
				if (!getIndexes(true).isEmpty()) {
					identifiers.addAll(getIndexes(true).iterator().next().getFields());
				} else {
					identifiers=getFieldsIndexesUnique();
				}
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
		notPrimary.removeAll(getFieldsPrimaryKey());
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
		return getFieldsIndexes(true);
	}

	/**
	 * get the non-unique indexes
	 *
	 * @return non-unique indexes
	 */
	public Set<Field> getFieldsIndexesNonUnique() {
		return getFieldsIndexes(false);
	}

	/**
	 * get index fields which are unique/non unique
	 * 
	 * @param unique true for unique
	 * @return the index fields
	 */
	public Set<Field> getFieldsIndexes(boolean unique) {
		Set<Field> indexFields=new LinkedHashSet<Field>();
		for (Field f : getFields()) {
			if ((f.isIndexUnique()&&unique)||(f.isIndexNonUnique()&&!unique)) {
				indexFields.add(f);
			}
		}
		return indexFields;
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
	 * get indexes for this table
	 * 
	 * @return the indexes
	 */
	public Set<Index> getIndexes() {
		return new LinkedHashSet<Index>(indexes);
	}

	/**
	 * only get indexes which are unique/non-unique
	 *
	 * @param unique
	 * @return indexes which are unique/non-unique
	 */
	public Set<Index> getIndexes(boolean unique) {
		Set<Index> is=new LinkedHashSet<Index>();
		for (Index i : getIndexes()) {
			if (i.isUnique()==unique) {
				is.add(i);
			}
		}
		return is;
	}

	public Set<Index> getIndexesUnique() {
		return getIndexesUnique();
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

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark=remark;
	}

	/**
	 * @return the importedKeys
	 */
	public Set<ForeignKey> getImportedKeys() {
		return importedKeys;
	}

	/**
	 * @param importedKeys the importedKeys to set
	 */
	public void setImportedKeys(Set<ForeignKey> importedKeys) {
		this.importedKeys=importedKeys;
	}

	/**
	 * @return the exportedKeys
	 */
	public Set<ForeignKey> getExportedKeys() {
		return exportedKeys;
	}

	/**
	 * @param exportedKeys the exportedKeys to set
	 */
	public void setExportedKeys(Set<ForeignKey> exportedKeys) {
		this.exportedKeys=exportedKeys;
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
		if (null==conn) {
			return tables;
		}
		ResultSet rsetTables=null;
		try {
			DatabaseMetaData dmd=conn.getMetaData();
			if (null!=dmd) {
				rsetTables=dmd.getTables(null, null, null, null);
				String tableName;
				Table table;
				while (rsetTables.next()) {
					table=new Table(
							conn,
							rsetTables.getString("TABLE_CAT"),
							rsetTables.getString("TABLE_SCHEM"),
							rsetTables.getString("TABLE_NAME"));
					table.setRemark(rsetTables.getString("REMARKS"));
					tables.add(table);

					if (null!=listener) {
						TableEvent evt=new TableEvent(conn, table);
						listener.tableStatusChanged(evt);
					}
				}
			}
		} catch (SQLException ex) {
			Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			DbUtil.closeQuietly(rsetTables);
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
