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
import java.util.List;
import org.afraid.poison.common.CollectionUtil;
import org.afraid.poison.db2php.generator.Field;
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

	private String getBindingCodeField(PhpCodeGenerator generator, List<Field> fields) {
		StringBuilder s=new StringBuilder();
		int i=0;
		for (Field f : fields) {
			s.append("\t\t$stmt->bindValue(").append(++i).append(",").append(generator.getGetterCall(f)).append(");\n");
		}
		return s.toString();
	}

	private String getStmtInit(String cstr) {
		return new StringBuilder("\t\tself::prepareStatement(").append(cstr).append(");\n").toString();
	}

	private String getStmtExecute(String cstr) {
		return new StringBuilder("\t\t$result=$stmt->execute();\n").toString();
	}

	@Override
	public String getSelectCode(PhpCodeGenerator generator) {
		StringBuilder s=new StringBuilder("\tpublic static function ").append(METHOD_SELECT_ID_NAME).append("(PDO $db,");
		s.append(generator.getFieldList(new ArrayList<Field>(generator.getTable().getPrimaryKeys())));
		s.append(") {\n");
		s.append(getStmtInit("self::SQL_SELECT_PK"));
		s.append(getBindingCodeField(generator, new ArrayList<Field>(generator.getTable().getPrimaryKeys())));
		s.append("\t}\n");
		return s.toString();
	}

	@Override
	public String getInsertCode(PhpCodeGenerator generator) {
		StringBuilder s=new StringBuilder("\tpublic static function ").append(METHOD_INSERT_NAME).append("(PDO $db) {\n");
		s.append(getStmtInit("self::SQL_INSERT"));
		s.append(getBindingCodeField(generator, new ArrayList<Field>(generator.getTable().getFields())));
		s.append("\t}\n");
		return s.toString();
	}

	@Override
	public String getUpdateCode(PhpCodeGenerator generator) {
		StringBuilder s=new StringBuilder("\tpublic static function ").append(METHOD_UPDATE_NAME).append("(PDO $db) {\n");
		s.append(getStmtInit("self::SQL_UPDATE"));
		List<Field> fields=new ArrayList<Field>(generator.getTable().getFields());
		fields.addAll(generator.getTable().getPrimaryKeys());
		s.append(getBindingCodeField(generator, fields));
		s.append("\t}\n");
		return s.toString();
	}

	@Override
	public String getDeleteCode(PhpCodeGenerator generator) {
		StringBuilder s=new StringBuilder("\tpublic static function ").append(METHOD_DELETE_NAME).append("(PDO $db,");
		s.append(generator.getFieldList(new ArrayList<Field>(generator.getTable().getPrimaryKeys())));
		s.append(") {\n");
		s.append(getStmtInit("self::SQL_DELETE_PK"));
		s.append(getBindingCodeField(generator, new ArrayList<Field>(generator.getTable().getPrimaryKeys())));
		s.append("\t}\n");
		return s.toString();
	}

	@Override
	public String getSnippet() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
