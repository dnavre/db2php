/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.afraid.poison.db2php.generator.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jdom.Element;
import org.jdom.Parent;

/**
 *
 * @author Andreas Schnaiter <as@euro-solutions.de>
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

		return tableContainer;
	}

	public static List<TableContainer> fromParent(Parent parent, Settings parentSettings) {
		List<TableContainer> tableContainers=new ArrayList<TableContainer>();
		for (Element element : JDOMUtil.getElementsByTagName(parent, "tables")) {
			tableContainers.add(fromElement(element, parentSettings));
		}
		return tableContainers;
	}

}
