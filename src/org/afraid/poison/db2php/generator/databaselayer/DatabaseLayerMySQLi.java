/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.db2php.generator.databaselayer;

import org.afraid.poison.db2php.generator.PhpCodeGenerator;

/**
 *
 * @author Andreas Schnaiter <rc.poison@gmail.com>
 */
public class DatabaseLayerMySQLi extends DatabaseLayer {

	@Override
	public String getName() {
		return "MySQLi";
	}

	@Override
	public String getSelectCode(PhpCodeGenerator generator) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String getInsertCode(PhpCodeGenerator generator) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String getUpdateCode(PhpCodeGenerator generator) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String getDeleteCode(PhpCodeGenerator generator) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String getSnippet() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
