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

import java.util.ArrayList;
import org.afraid.poison.db2php.generator.CodeGenerator;
import org.afraid.poison.db2php.generator.Field;

/**
 *
 * @author Andreas Schnaiter <rc.poison@gmail.com>
 */
public class DatabaseLayerInterface extends DatabaseLayer {

	@Override
	public String getName() {
		return "Simple Interface";
	}

	@Override
	public String getCodeSelect(CodeGenerator generator) {
		StringBuilder s=new StringBuilder();
		s.append("\tpublic static function ").append(METHOD_SELECT_ID_NAME).append("(PDO $db");
		if (!generator.getTable().getPrimaryKeys().isEmpty()) {
			s.append(",");
			s.append(generator.getFieldList(new ArrayList<Field>(generator.getTable().getPrimaryKeys())));
		}
		s.append(") {\n");
		s.append("\t\t$sql=").append(getSqlSelect(generator)).append(";\n");
		s.append("\t}\n");
		s.append(getAssignByHash(generator));
		return s.toString();
	}

	@Override
	public String getCodeInsert(CodeGenerator generator) {
		StringBuilder s=new StringBuilder();
		s.append("\tpublic function ").append(METHOD_INSERT_NAME).append("(PDO $db) {\n");
		s.append("\t\t$sql=").append(getSqlInsert(generator)).append(";\n");
		s.append("\t}\n");
		return s.toString();
	}

	@Override
	public String getCodeUpdate(CodeGenerator generator) {
		StringBuilder s=new StringBuilder();
		s.append("\tpublic function ").append(METHOD_UPDATE_NAME).append("(PDO $db) {\n");
		s.append("\t\t$sql=").append(getSqlUpdate(generator)).append(";\n");
		s.append("\t}\n");
		return s.toString();
	}

	@Override
	public String getCodeDelete(CodeGenerator generator) {
		StringBuilder s=new StringBuilder();
		s.append("\tpublic function ").append(METHOD_DELETE_NAME).append("(PDO $db) {\n");
		s.append("\t\t$sql=").append(getSqlDelete(generator)).append(";\n");
		s.append("\t}\n");
		return s.toString();
	}

	@Override
	public String getSnippet() {
		return new String();
	}
}
