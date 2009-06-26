/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.db2php.generator.databaselayer;

import org.afraid.poison.db2php.generator.PhpCodeGenerator;

/**
 *
 * @author andreas.schnaiter
 */
public class DatabaseLayerNone extends DatabaseLayer {

	@Override
	public String getName() {
		return "none";
	}

	@Override
	public String getSelectCode(PhpCodeGenerator generator) {
		return null;
	}

	@Override
	public String getInsertCode(PhpCodeGenerator generator) {
		return null;
	}

	@Override
	public String getUpdateCode(PhpCodeGenerator generator) {
		return null;
	}

	@Override
	public String getDeleteCode(PhpCodeGenerator generator) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
