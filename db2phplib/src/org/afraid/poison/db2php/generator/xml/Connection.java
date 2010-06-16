/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.db2php.generator.xml;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Element;
import org.jdom.Parent;

/**
 *
 * @author Andreas Schnaiter <as@euro-solutions.de>
 */
public class Connection {

	private String uri;
	private Settings settings;
	private List<Tables> tables;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri=uri;
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings=settings;
	}

	public List<Tables> getTables() {
		return tables;
	}

	public void setTables(List<Tables> tables) {
		this.tables=tables;
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
		return connection;
	}

	public static List<Connection> fromParent(Parent parent) {
		List<Connection> connections=new ArrayList<Connection>();

		return connections;
	}
}
