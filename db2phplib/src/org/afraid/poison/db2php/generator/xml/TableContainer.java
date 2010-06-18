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
public class TableContainer {
	private List<Table> tables;
	private Settings settings;

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings=settings;
	}

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables=tables;
	}

	public static TableContainer fromElement(Element element, Settings parentSettings) {
		TableContainer tableContainer=new TableContainer();
		tableContainer.setSettings(Settings.fromElement(element, parentSettings));
		tableContainer.setTables(Table.fromParent(element, tableContainer.getSettings()));
		return tableContainer;
	}

	public static List<TableContainer> fromParent(Parent parent, Settings parentSettings) {
		List<TableContainer> tableContainers=new ArrayList<TableContainer>();
		for (Element element : JDOMUtil.getElementsByTagName(parent, "tables")) {
			tableContainers.add(fromElement(element, parentSettings));
		}
		return tableContainers;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj==null) {
			return false;
		}
		if (getClass()!=obj.getClass()) {
			return false;
		}
		final TableContainer other=(TableContainer) obj;
		if (this.tables!=other.tables&&(this.tables==null||!this.tables.equals(other.tables))) {
			return false;
		}
		if (this.settings!=other.settings&&(this.settings==null||!this.settings.equals(other.settings))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash=7;
		hash=23*hash+(this.tables!=null ? this.tables.hashCode() : 0);
		hash=23*hash+(this.settings!=null ? this.settings.hashCode() : 0);
		return hash;
	}

	

}
