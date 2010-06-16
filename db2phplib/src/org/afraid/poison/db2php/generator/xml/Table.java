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

	public static Table fromElement(Element element, Settings parentSettings) {
		Table t=new Table();
		return t;
	}

	public static List<Table> fromParent(Parent parent, Settings parentSettings) {
		List<Table> tables=new ArrayList<Table>();
		return tables;
	}
}
