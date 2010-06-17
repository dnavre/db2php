/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.db2php.generator.xml;

import java.io.IOException;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.afraid.poison.common.DbUtil;
import org.afraid.poison.db2php.generator.CodeGenerator;
import org.jdom.Element;
import org.jdom.Parent;

/**
 *
 * @author Andreas Schnaiter <as@euro-solutions.de>
 */
public class Connection {

	private String uri;
	private String catalog;
	private String schema;
	private String user;
	private String password;
	private Settings settings;
	private List<TableContainer> tableContainers;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri=uri;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password=password;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user=user;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog=catalog;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema=schema;
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings=settings;
	}

	public List<TableContainer> getTableContainers() {
		return tableContainers;
	}

	public void setTableContainers(List<TableContainer> tableContainers) {
		this.tableContainers=tableContainers;
	}

	public List<Table> getTables() {
		List<Table> tables=new ArrayList<Table>();
		for (TableContainer tableContainer : getTableContainers()) {
			tables.addAll(tableContainer.getTables());
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
		final Connection other=(Connection) obj;
		if ((this.uri==null) ? (other.uri!=null) : !this.uri.equals(other.uri)) {
			return false;
		}
		if (this.settings!=other.settings&&(this.settings==null||!this.settings.equals(other.settings))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash=3;
		hash=59*hash+(this.uri!=null ? this.uri.hashCode() : 0);
		hash=59*hash+(this.settings!=null ? this.settings.hashCode() : 0);
		return hash;
	}

	public static Connection fromElement(Element element) {
		Connection connection=new Connection();
		connection.setUri(element.getAttributeValue("uri"));
		connection.setUser(element.getAttributeValue("user"));
		connection.setPassword(element.getAttributeValue("password"));
		connection.setSettings(Settings.fromElement(element));
		connection.setTableContainers(TableContainer.fromParent(element, connection.getSettings()));
		return connection;
	}

	public static List<Connection> fromParent(Parent parent) {
		List<Connection> connections=new ArrayList<Connection>();
		for (Element element : JDOMUtil.getElementsByTagName(parent, "connection")) {
			connections.add(fromElement(element));
		}
		return connections;
	}

	/**
	 * write code
	 *
	 * @return the tables for which the code could not be written
	 */
	public Set<Table> writeCode() {
		Set<Table> failed=new LinkedHashSet<Table>();
		java.sql.Connection dbConnection=null;
		try {

			if (null==getUser()) {
				dbConnection=DriverManager.getConnection(getUri());
			} else {
				dbConnection=DriverManager.getConnection(getUri(), getUser(), getPassword());
			}
			try {
				CodeGenerator generator;
				int done=0;
				for (Table t : getTables()) {
					org.afraid.poison.db2php.generator.Table generatorTable=new org.afraid.poison.db2php.generator.Table(dbConnection, t.getName());
					generator=new CodeGenerator(generatorTable, t.getSettings());
					generator.setCamelCaseFairy(t.getSettings().getCamelCaseFairy());
					try {
						generator.writeCode();
					} catch (IOException ex) {
						failed.add(t);
						ex.printStackTrace();
					}
				}
			} catch (Exception e) {
			} finally {
			}
		} catch (Exception exception) {
			failed.addAll(getTables());
			exception.printStackTrace();
		} finally {
			DbUtil.closeQuietly(dbConnection);
		}
		return failed;

	}
}
