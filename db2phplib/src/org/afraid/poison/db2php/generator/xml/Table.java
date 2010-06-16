/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.afraid.poison.db2php.generator.xml;

import java.util.ArrayList;
import java.util.List;
import org.jdom.Element;
import org.jdom.Parent;

/**
 *
 * @author Andreas Schnaiter <as@euro-solutions.de>
 */
public class Table {
	private String name;
	private Settings settings;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name=name;
	}

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings=settings;
	}

	

	public static Table fromElement(Element element, Settings parentSettings) {
		Table t=new Table();
		t.setName(element.getAttributeValue("name"));
		t.setSettings(Settings.fromElement(element, parentSettings));
		return t;
	}

	public static List<Table> fromParent(Parent parent, Settings parentSettings) {
		List<Table> tables=new ArrayList<Table>();
		for (Element element : JDOMUtil.getElementsByTagName(parent, "table")) {
			tables.add(fromElement(element, parentSettings));
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
		if ((this.name==null) ? (other.name!=null) : !this.name.equals(other.name)) {
			return false;
		}
		if (this.settings!=other.settings&&(this.settings==null||!this.settings.equals(other.settings))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash=5;
		hash=53*hash+(this.name!=null ? this.name.hashCode() : 0);
		hash=53*hash+(this.settings!=null ? this.settings.hashCode() : 0);
		return hash;
	}

	
}
