/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.db2php.generator.databaselayer;

import java.util.ArrayList;
import java.util.List;
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
	private String getBindingCodeField (PhpCodeGenerator generator, List<Field> fields) {
		StringBuilder s=new StringBuilder();
		int i=0;
		for (Field f : fields) {
			s.append("\t\t$stmt->bindValue(").append(i++).append(",").append(generator.getGetterCall(f)).append(");\n");
		}
		//
		return s.toString();
	}
	@Override
	public String getSelectCode(PhpCodeGenerator generator) {
		StringBuilder s=new StringBuilder("\npublic static function ").append(METHOD_SELECT_ID_NAME).append("(PDO $db) {\n");
		s.append(getBindingCodeField(generator, new ArrayList<Field>(generator.getTable().getPrimaryKeys())));
		s.append("\t}\n");
		return s.toString();
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
