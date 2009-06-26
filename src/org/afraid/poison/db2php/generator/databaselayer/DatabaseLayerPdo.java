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
public class DatabaseLayerPdo extends DatabaseLayer {

	@Override
	public String getName() {
		return "PHP Data Objects";
	}

	@Override
	public String getSelectCode(PhpCodeGenerator generator) {
		StringBuilder s=new StringBuilder();
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
}
