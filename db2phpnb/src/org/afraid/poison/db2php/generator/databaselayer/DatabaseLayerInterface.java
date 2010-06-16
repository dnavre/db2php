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

	private static final String name="Simple Interface";

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDbTypeName() {
		return "SimpleDatabaseInterface";
	}

	private String getCodeExecute() {
		return new StringBuilder("\t\t$affected=$db->executeSql($sql);\n").toString();
	}

	@Override
	public String getCodeSelect(CodeGenerator generator) {
		StringBuilder s=new StringBuilder();
		s.append(getAssignByHash(generator));
		s.append(getSnippetFromFile(generator, "DatabaseLayer.findById.php"));
		s.append("\tpublic static function ").append(METHOD_SELECT_ID_NAME).append("(").append(getDbTypeName()).append(" $db");
		if (!generator.getTable().getFieldsIdentifiers().isEmpty()) {
			s.append(",");
			s.append(generator.getFieldList(new ArrayList<Field>(generator.getTable().getFieldsIdentifiers())));
		}
		s.append(") {\n");
		s.append("\t\t$sql=").append(getSqlSelect(generator)).append(";\n");
		s.append("\t\t$result=$db->getResult($sql);\n");
		s.append("\t\t$o=new ").append(generator.getClassName()).append("();\n");
		s.append("\t\t$o->assignByHash($result);\n");
		if (generator.isTrackFieldModifications()) {
			s.append("\t\t$o->notifyPristine();\n");
		}
		s.append("\t\treturn $o;\n");
		s.append("\t}\n");
		return s.toString();
	}

	@Override
	public String getCodeInsert(CodeGenerator generator) {
		StringBuilder s=new StringBuilder();
		s.append(getSnippetFromFile(generator, "DatabaseLayer.insertIntoDatabase.php"));
		s.append("\tpublic function ").append(METHOD_INSERT_NAME).append("(").append(getDbTypeName()).append(" $db) {\n");
		s.append("\t\t$sql=").append(getSqlInsert(generator)).append(";\n");
		s.append(getCodeExecute());
		for (Field f : generator.getTable().getFieldsAutoIncrement()) {
			s.append("\t\t").append(generator.getSetterCall(f, "$db->lastInsertId()")).append(";\n");
		}
		s.append(generator.getTrackingPristineState());
		s.append(getReturnResult());
		s.append("\t}\n");
		return s.toString();
	}

	@Override
	public String getCodeUpdate(CodeGenerator generator) {
		StringBuilder s=new StringBuilder();
		s.append(getSnippetFromFile(generator, "DatabaseLayer.updateToDatabase.php"));
		s.append("\tpublic function ").append(METHOD_UPDATE_NAME).append("(").append(getDbTypeName()).append(" $db) {\n");
		s.append("\t\t$sql=").append(getSqlUpdate(generator)).append(";\n");
		s.append(getCodeExecute());
		s.append(generator.getTrackingPristineState());
		s.append(getReturnResult());
		s.append("\t}\n");
		return s.toString();
	}

	@Override
	public String getCodeDelete(CodeGenerator generator) {
		StringBuilder s=new StringBuilder();
		s.append(getSnippetFromFile(generator, "DatabaseLayer.deleteFromDatabase.php"));
		s.append("\tpublic function ").append(METHOD_DELETE_NAME).append("(").append(getDbTypeName()).append(" $db) {\n");
		s.append("\t\t$sql=").append(getSqlDelete(generator)).append(";\n");
		s.append(getCodeExecute());
		s.append(getReturnResult());
		s.append("\t}\n");
		return s.toString();
	}

	@Override
	public String getSnippet() {
		return new String();
	}
}
