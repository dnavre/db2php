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
package org.afraid.poison.db2php.generator.databaselayer;

import org.afraid.poison.db2php.generator.CodeGenerator;

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
	public String getDbTypeName() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String getCodeSelect(CodeGenerator generator) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String getCodeInsert(CodeGenerator generator) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String getCodeUpdate(CodeGenerator generator) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String getCodeDelete(CodeGenerator generator) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String getSnippet() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
