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
package org.afraid.poison.db2php.generator.xml;

import java.util.ArrayList;
import java.util.List;
import org.jdom.Element;
import org.jdom.Parent;

/**
 *
 * @author Andreas Schnaiter <rc.poison@gmail.com>
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

	@Override
	public String toString() {
		return "Table{"+"name="+name+"settings="+settings+'}';
	}

	
}
