/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.afraid.poison.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author poison
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
		//System.err.println(StringUtil.padLeft(1, "0", 4));
		System.err.println(StringUtil.padRight(1, " ", 4));
		System.err.println(StringUtil.padLeft(1, " ", 4));
		Collection c=new ArrayList();
		c.add(new Date());
		c.add("hello");
		c.add("world");
		System.err.println(CollectionUtil.join(c, ";"));

		System.err.println(StringUtil.capitalize("helloWorld"));
		System.err.println(StringUtil.firstCharToUpperCase("helloWorld"));
    }

}
