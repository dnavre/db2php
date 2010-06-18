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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.afraid.poison.common.IOUtil;
import org.afraid.poison.common.StringUtil;
import org.afraid.poison.db2php.generator.Field;
import org.afraid.poison.db2php.generator.CodeGenerator;
import org.afraid.poison.db2php.generator.ForeignKey;

/**
 *
 * @author Andreas Schnaiter <rc.poison@gmail.com>
 */
public class DatabaseLayerPdo extends DatabaseLayer {

	private static final String name="PDO (PHP Data Objects)";
	private static final String SNIPPET_EXCEPTION="DatabaseLayer.exception.php";

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDbTypeName() {
		return "PDO";
	}

	private String getBindingCodeField(CodeGenerator generator, List<Field> fields, int start, boolean fromOldValue, int identLevel) {
		StringBuilder s=new StringBuilder();
		String ident=StringUtil.repeat("\t", identLevel);
		for (Field f : fields) {
			s.append(ident).append("$stmt->bindValue(").append(++start).append(",");
			if (fromOldValue) {
				s.append(generator.getGetterCallOldInstance(f));
			} else {
				s.append(generator.getGetterCall(f));
			}
			s.append(");\n");
		}
		return s.toString();
	}

	private String getBindingCodeField(CodeGenerator generator, List<Field> fields) {
		return getBindingCodeField(generator, fields, 0, false, 2);
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

	@Override
	public String getCodeSelect(CodeGenerator generator) {
		StringBuilder s=new StringBuilder();
		s.append(getSnippetFromFile(generator, "DatabaseLayerPdo.findByExample.php"));
		s.append(getAssignByHash(generator));

		// prepare/execute statement
		s.append(getSnippetFromFile(generator, "DatabaseLayer.findById.php"));
		s.append("\tpublic static function ").append(METHOD_SELECT_ID_NAME).append("(").append(getDbTypeName()).append(" $db");
		if (!generator.getTable().getFieldsIdentifiers().isEmpty()) {
			s.append(",");
			s.append(generator.getFieldList(new ArrayList<Field>(generator.getTable().getFieldsIdentifiers())));
		}
		s.append(") {\n");
		s.append(getStmtInit("self::SQL_SELECT_PK"));
		int i=0;
		for (Field f : generator.getTable().getFieldsIdentifiers()) {
			s.append("\t\t$stmt->bindValue(").append(++i).append(",$").append(generator.getMemberName(f)).append(");\n");
		}
		s.append(getStmtExecute());
		s.append(getSnippetFromFile(generator, SNIPPET_EXCEPTION));
		s.append("\t\t$result=$stmt->fetch(PDO::FETCH_ASSOC);\n");
		s.append(getStmtCloseCursor());

		s.append("\t\tif(!$result) {\n");
		s.append("\t\t\treturn null;\n");
		s.append("\t\t}\n");

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
		// extra function to bind values
		s.append(getSnippetFromFile(generator, "DatabaseLayerPdo.bindValues.php"));
		s.append("\tprotected function ").append("bindValues").append("(PDOStatement &$stmt) {\n");
		s.append(getBindingCodeField(generator, new ArrayList<Field>(generator.getTable().getFields())));
		s.append("\t}\n");

		//
		s.append(getSnippetFromFile(generator, "DatabaseLayer.insertIntoDatabase.php"));
		s.append("\tpublic function ").append(METHOD_INSERT_NAME).append("(").append(getDbTypeName()).append(" $db) {\n");

		// if we have autoincrement fields, ...
		if (!generator.getTable().getFieldsAutoIncrement().isEmpty()) {
			Field firstAutoIncrement=generator.getTable().getFieldsAutoIncrement().iterator().next();
			s.append("\t\tif (null===").append(generator.getGetterCall(firstAutoIncrement)).append(") {\n");
			s.append("\t").append(getStmtInit("self::SQL_INSERT_AUTOINCREMENT"));
			s.append(getBindingCodeField(generator, new ArrayList<Field>(generator.getTable().getFieldsNotAutoIncrement()), 0, false, 3));
			s.append("\t\t} else {\n");
			s.append("\t").append(getStmtInit("self::SQL_INSERT"));
			s.append("\t\t\t$this->bindValues($stmt);\n");
			s.append("\t\t}\n");
			
		} else {
			s.append(getStmtInit("self::SQL_INSERT"));
			s.append("\t\t$this->bindValues($stmt);\n");
		}
		
		s.append(getStmtExecute());
		s.append(getSnippetFromFile(generator, SNIPPET_EXCEPTION));
		// TODO: check how to safely fetch insert ids
		for (Field f : generator.getTable().getFieldsAutoIncrement()) {
			s.append("\t\t$lastInsertId=").append("$db->lastInsertId(").append(null==f.getSerialName() ? StringUtil.EMTPY : CodeGenerator.getPhpString(f.getSerialName())).append(");\n");
			s.append("\t\tif (false!==$lastInsertId) {\n");
			s.append("\t\t\t").append(generator.getSetterCall(f, "$lastInsertId")).append(";\n");
			s.append("\t\t}\n");
		}
		s.append(getStmtCloseCursor());
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
		s.append(getStmtInit("self::SQL_UPDATE"));
		s.append("\t\t$this->bindValues($stmt);\n");
		if (false && generator.isTrackFieldModifications()) {
			s.append("\t\tif (null!==$this->getOldInstance()) {\n");
			s.append(getBindingCodeField(generator, new ArrayList<Field>(generator.getTable().getFieldsIdentifiers()), generator.getTable().getFields().size(), true, 3));
			s.append("\t\t} else {\n");
			s.append(getBindingCodeField(generator, new ArrayList<Field>(generator.getTable().getFieldsIdentifiers()), generator.getTable().getFields().size(), false, 3));
			s.append("\t\t}\n");
		} else {
			s.append(getBindingCodeField(generator, new ArrayList<Field>(generator.getTable().getFieldsIdentifiers()), generator.getTable().getFields().size(), false, 2));
		}
		s.append(getStmtExecute());
		s.append(getSnippetFromFile(generator, SNIPPET_EXCEPTION));
		s.append(getStmtCloseCursor());
		s.append(generator.getTrackingPristineState());
		s.append(getReturnResult());
		s.append("\t}\n");
		return s.toString();
	}

	@Override
	public String getCodeDelete(CodeGenerator generator) {
		StringBuilder s=new StringBuilder();
		s.append(getSnippetFromFile(generator, "DatabaseLayer.deleteFromDatabase.php"));
		s.append("\tpublic function ").append(METHOD_DELETE_NAME).append("(").append(getDbTypeName()).append(" $db");
		//s.append(",").append(generator.getFieldList(new ArrayList<Field>(generator.getTable().getFieldsPrimaryKey())));
		s.append(") {\n");
		s.append(getStmtInit("self::SQL_DELETE_PK"));
		s.append(getBindingCodeField(generator, new ArrayList<Field>(generator.getTable().getFieldsIdentifiers())));
		s.append(getStmtExecute());
		s.append(getSnippetFromFile(generator, SNIPPET_EXCEPTION));
		s.append(getStmtCloseCursor());
		s.append(getReturnResult());
		s.append("\t}\n");
		return s.toString();
	}

	private static String findNextUnusedName(Set<String> used, String name) {
		if (used.contains(name)) {
			int num=0;
			StringBuilder possibleName;
			do {
				possibleName=new StringBuilder(name).append(++num);
			} while (used.contains(possibleName.toString()));
			return possibleName.toString();
		}
		return name;
	}

	public String getCodeExportedKeys(CodeGenerator generator) {
		StringBuilder s=new StringBuilder();
		Set<String> usedMethods=new LinkedHashSet<String>();
		for (ForeignKey fk : generator.getTable().getExportedKeys()) {
			String fkType=findNextUnusedName(usedMethods, generator.getClassName(fk.getFkTableName()));
			usedMethods.add(fkType);
			// DatabaseLayerPdo.importedKeys
			Map<CharSequence, CharSequence> replacements=new HashMap<CharSequence, CharSequence>();
			replacements.put("<pkTableName>", fk.getPkTableName());
			replacements.put("<pkFieldName>", fk.getPkFieldName());
			replacements.put("<fkTableName>", fk.getFkTableName());
			replacements.put("<fkFieldName>", fk.getFkFieldName());
			replacements.put("<fkType>", fkType);
			replacements.put("<fkFieldConst>", generator.getConstName(fk.getFkField()));
			replacements.put("<pkGetter>", generator.getGetterCall(fk.getPkField()));
			s.append(StringUtil.replace(generator.getSnippetFromFile("DatabaseLayerPdo.exportedKeys.php"), replacements));
		}
		
		return s.toString();
	}

	public String getCodeImportedKeys(CodeGenerator generator) {
		StringBuilder s=new StringBuilder();
		Set<String> usedMethods=new LinkedHashSet<String>();
		for (ForeignKey fk : generator.getTable().getImportedKeys()) {
			String pkType=findNextUnusedName(usedMethods, generator.getClassName(fk.getPkTableName()));
			usedMethods.add(pkType);
			// DatabaseLayerPdo.importedKeys
			Map<CharSequence, CharSequence> replacements=new HashMap<CharSequence, CharSequence>();
			replacements.put("<pkTableName>", fk.getPkTableName());
			replacements.put("<pkFieldName>", fk.getPkFieldName());
			replacements.put("<fkTableName>", fk.getFkTableName());
			replacements.put("<fkFieldName>", fk.getFkFieldName());
			replacements.put("<pkType>", pkType);
			replacements.put("<pkFieldConst>", generator.getConstName(fk.getPkField()));
			replacements.put("<fkGetter>", generator.getGetterCall(fk.getFkField()));
			s.append(StringUtil.replace(generator.getSnippetFromFile("DatabaseLayerPdo.importedKeys.php"), replacements));
		}

		return s.toString();
	}

	@Override
	public String getSnippet() {
		String s=new String();
		InputStream is=null;
		try {
			is=getClass().getResourceAsStream("/org/afraid/poison/db2php/generator/snippets/DatabaseLayerPdo.php");
			s=IOUtil.readString(getClass().getResourceAsStream("/org/afraid/poison/db2php/generator/snippets/DatabaseLayerPdo.php"));
		} catch (IOException ex) {
			Logger.getLogger(getClass().getCanonicalName()).log(Level.SEVERE, null, ex);
		} finally {
			IOUtil.closeQuietly(is);
		}
		return s;
	}

	@Override
	public String getCode(CodeGenerator generator) {
		return new StringBuilder(super.getCode(generator)).append(getCodeExportedKeys(generator)).append(getCodeImportedKeys(generator)).toString();
	}
}
