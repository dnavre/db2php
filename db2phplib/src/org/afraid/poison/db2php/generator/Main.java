/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.afraid.poison.db2php.generator;

import java.io.File;
import java.io.FileNotFoundException;
import org.afraid.poison.db2php.generator.xml.Connection;
import org.afraid.poison.db2php.generator.xml.Table;

/**
 *
 * @author Andreas Schnaiter <as@euro-solutions.de>
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, Exception {
		if (0==args.length) {
			throw new Exception("no xml file passed");
		}
		File f=null;
		f=new File(args[0]);
		if (!f.isFile()) {
			f=new File(System.getProperty("user.dir"), args[0]);
		}
		if (!f.isFile()) {
			throw new FileNotFoundException(args[0]);
		}
		for(Connection connection : Connection.fromXMLFile(f)) {
			for(Table failedTable : connection.writeCode()) {
				System.err.println("failed writing: " + failedTable);
			}
		}

    }

}