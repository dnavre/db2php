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
package org.afraid.poison.db2php.generator;

import org.afraid.poison.common.StringUtil;

/**
 * generates PHP CLass code from a table
 *
 * @author Andreas Schnaiter <rc.poison@gmail.com>
 */
public class PhpCodeGenerator {

	private Table table;
	private DatabaseLayer databaseLayer;
	private boolean generateChecks;
	private boolean trackFieldModifications;

	/**
	 * @return the table
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * @param table the table to set
	 */
	public void setTable(Table table) {
		this.table=table;
	}

	/**
	 * @return the databaseLayer
	 */
	public DatabaseLayer getDatabaseLayer() {
		return databaseLayer;
	}

	/**
	 * @param databaseLayer the databaseLayer to set
	 */
	public void setDatabaseLayer(DatabaseLayer databaseLayer) {
		this.databaseLayer=databaseLayer;
	}

	/**
	 * @return the generateChecks
	 */
	public boolean isGenerateChecks() {
		return generateChecks;
	}

	/**
	 * @param generateChecks the generateChecks to set
	 */
	public void setGenerateChecks(boolean generateChecks) {
		this.generateChecks=generateChecks;
	}

	/**
	 * @return the trackFieldModifications
	 */
	public boolean isTrackFieldModifications() {
		return trackFieldModifications;
	}

	/**
	 * @param trackFieldModifications the trackFieldModifications to set
	 */
	public void setTrackFieldModifications(boolean trackFieldModifications) {
		this.trackFieldModifications=trackFieldModifications;
	}

	public String getClassName() {
		return StringUtil.firstCharToUpperCase(getTable().getName());
	}

	public String getGetter(Field field) {
		return new StringBuilder("get").append(getMethodName(field)).toString();
	}

	public String getSetter(Field field) {
		return new StringBuilder("set").append(getMethodName(field)).toString();
	}

	public String getMethodName(Field field) {
		return field.getNameFirstCharUpper();
	}
}
