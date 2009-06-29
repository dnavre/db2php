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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.afraid.poison.common.IOUtil;
import org.afraid.poison.db2php.generator.Field;
import org.afraid.poison.db2php.generator.PhpCodeGenerator;
import org.openide.util.Exceptions;

/**
 *
 * @author Andreas Schnaiter <rc.poison@gmail.com>
 */
public class DatabaseLayerPdo extends DatabaseLayer {

	@Override
	public String getName() {
		return "PDO (PHP Data Objects)";
	}

	private String getBindingCodeField(PhpCodeGenerator generator, List<Field> fields) {
		StringBuilder s=new StringBuilder();
		int i=0;
		for (Field f : fields) {
			s.append("\t\t$stmt->bindValue(").append(++i).append(",").append(generator.getGetterCall(f)).append(");\n");
		}
		return s.toString();
	}

	private String getStmtInit(String cstr) {
		return new StringBuilder("\t\t$stmt=self::prepareStatement($db,").append(cstr).append(");\n").toString();
	}

	private String getStmtExecute() {
		return new StringBuilder("\t\t$affected=$stmt->execute();\n").toString();
	}

	private String getStmtCloseCursor() {
		return new StringBuilder("\t\t$stmt->closeCursor();\n").toString();
	}

	private String getReturnResult() {
		return new StringBuilder("\t\treturn $affected;\n").toString();
	}

	private String getSnippetFromResource(PhpCodeGenerator generator, String resource) {
		StringBuilder s=new StringBuilder();
		try {
			s.append(IOUtil.readString(getClass().getResourceAsStream(resource)).replace("<type>", generator.getClassName()));
		} catch (IOException ex) {
			Exceptions.printStackTrace(ex);
		}
		return s.toString();
	}

	@Override
	public String getSelectCode(PhpCodeGenerator generator) {
		StringBuilder s=new StringBuilder();
		// append
		s.append("\tpublic function ").append("assignByHash").append("($result) {\n");
		String rAccess;
		for (Field f : generator.getTable().getFields()) {
			rAccess=new StringBuilder("$result['").append(f.getName()).append("']").toString();
			s.append("\t\t").append(generator.getSetterCall(f, rAccess)).append(";\n");
		}
		s.append("\t}\n");


		s.append(getSnippetFromResource(generator, "DatabaseLayerPdo.snippet.getById.php"));
		s.append("\tpublic static function ").append(METHOD_SELECT_ID_NAME).append("(PDO $db,");
		s.append(generator.getFieldList(new ArrayList<Field>(generator.getTable().getPrimaryKeys())));
		s.append(") {\n");
		s.append(getStmtInit("self::SQL_SELECT_PK"));
		int i=0;
		for (Field f : generator.getTable().getPrimaryKeys()) {
			s.append("\t\t$stmt->bindValue(").append(++i).append(",$").append(generator.getMemberName(f)).append(");\n");
		}
		s.append(getStmtExecute());
		s.append("\t\t$result=$stmt->fetch(PDO::FETCH_ASSOC);\n");
		s.append("\t\t$o=new ").append(generator.getClassName()).append("();\n");
		s.append("\t\t$o->assignByHash($result);\n");
		/*
		for (Field f : generator.getTable().getFields()) {
			rAccess=new StringBuilder("$result['").append(f.getName()).append("']").toString();
			s.append("\t\t").append(generator.getSetterCall(f, rAccess, "$o")).append(";\n");
		}
		*/
		s.append(getStmtCloseCursor());
		if (generator.isTrackFieldModifications()) {
			s.append("\t\t$o->notifyPristine();\n");
		}
		s.append("\t\treturn $o;\n");
		s.append("\t}\n");
		return s.toString();
	}

	@Override
	public String getInsertCode(PhpCodeGenerator generator) {
		StringBuilder s=new StringBuilder();
		s.append(getSnippetFromResource(generator, "DatabaseLayerPdo.snippet.insertIntoDatabase.php"));
		s.append("\tpublic function ").append(METHOD_INSERT_NAME).append("(PDO $db) {\n");
		s.append(getStmtInit("self::SQL_INSERT"));
		s.append(getBindingCodeField(generator, new ArrayList<Field>(generator.getTable().getFields())));
		s.append(getStmtExecute());
		
		for (Field f : generator.getTable().getFieldsAutoIncrement()) {
			s.append("\t\t").append(generator.getSetterCall(f, "$db->lastInsertId()")).append(";\n");
		}
		s.append(getStmtCloseCursor());
		s.append(generator.getTrackingPristineState());
		s.append(getReturnResult());
		s.append("\t}\n");
		return s.toString();
	}

	@Override
	public String getUpdateCode(PhpCodeGenerator generator) {
		StringBuilder s=new StringBuilder();
		s.append(getSnippetFromResource(generator, "DatabaseLayerPdo.snippet.updateToDatabase.php"));
		s.append("\tpublic function ").append(METHOD_UPDATE_NAME).append("(PDO $db) {\n");
		s.append(getStmtInit("self::SQL_UPDATE"));
		List<Field> fields=new ArrayList<Field>(generator.getTable().getFields());
		fields.addAll(generator.getTable().getPrimaryKeys());
		s.append(getBindingCodeField(generator, fields));
		s.append(getStmtExecute());
		s.append(getStmtCloseCursor());
		s.append(generator.getTrackingPristineState());
		s.append(getReturnResult());
		s.append("\t}\n");
		return s.toString();
	}

	@Override
	public String getDeleteCode(PhpCodeGenerator generator) {
		StringBuilder s=new StringBuilder();
		s.append(getSnippetFromResource(generator, "DatabaseLayerPdo.snippet.deleteFromDatabase.php"));
		s.append("\tpublic function ").append(METHOD_DELETE_NAME).append("(PDO $db");
		//s.append(",").append(generator.getFieldList(new ArrayList<Field>(generator.getTable().getPrimaryKeys())));
		s.append(") {\n");
		s.append(getStmtInit("self::SQL_DELETE_PK"));
		s.append(getBindingCodeField(generator, new ArrayList<Field>(generator.getTable().getPrimaryKeys())));
		s.append(getStmtExecute());
		s.append(getStmtCloseCursor());
		s.append(getReturnResult());
		s.append("\t}\n");
		return s.toString();
	}

	@Override
	public String getSnippet() {
		String s=new String();
		try {
			s=IOUtil.readString(getClass().getResourceAsStream("DatabaseLayerPdo.snippet.php"));
		} catch (IOException ex) {
			Exceptions.printStackTrace(ex);
		}
		return s;
	}
}
