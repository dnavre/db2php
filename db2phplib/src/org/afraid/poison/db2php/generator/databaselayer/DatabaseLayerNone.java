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
 * @author andreas.schnaiter
 */
public class DatabaseLayerNone extends DatabaseLayer {

	private static final String name="none";

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDbTypeName() {
		return new String();
	}

	@Override
	public String getCodeSelect(CodeGenerator generator) {
		return new String();
	}

	@Override
	public String getCodeInsert(CodeGenerator generator) {
		return new String();
	}

	@Override
	public String getCodeUpdate(CodeGenerator generator) {
		return new String();
	}

	@Override
	public String getCodeDelete(CodeGenerator generator) {
		return new String();
	}

	@Override
	public String getSnippet() {
		return new String();
	}
}
