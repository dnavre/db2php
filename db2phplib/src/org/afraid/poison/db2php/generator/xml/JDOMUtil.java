
package org.afraid.poison.db2php.generator.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jdom.Element;
import org.jdom.Parent;
import org.jdom.filter.ElementFilter;

/**
 *
 * @author poison
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
