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
import java.util.Iterator;
import java.util.List;
import org.jdom.Element;
import org.jdom.Parent;
import org.jdom.filter.ElementFilter;

/**
 *
 * @author Andreas Schnaiter <rc.poison@gmail.com>
 */
public class JDOMUtil {

	public static List<Element> getElementsByTagName(Parent parent, CharSequence name) {
		List<Element> elements=new ArrayList<Element>();
		Iterator itr=parent.getDescendants(new ElementFilter(name.toString()));
		while (itr.hasNext()) {
			elements.add((Element) itr.next());
		}
		return elements;
	}
}
