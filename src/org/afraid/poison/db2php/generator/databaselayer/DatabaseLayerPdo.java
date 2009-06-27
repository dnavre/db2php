/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
		return new StringBuilder("\t\t$this->prepareStatement(").append(cstr).append(");\n").toString();
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
}
