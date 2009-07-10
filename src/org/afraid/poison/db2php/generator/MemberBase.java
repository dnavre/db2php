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

/**
 *
 * @author Andreas Schnaiter <rc.poison@gmail.com>
 */
abstract public class MemberBase {

	/**
	 * private member
	 */
	public static final int PRIVATE=1;
	/**
	 * protected member
	 */
	public static final int PROTECTED=2;
	/**
	 * public member
	 */
	public static final int PUBLIC=4;
	/**
	 * static member
	 */
	public static final int STATIC=8;
    private int scope;
    private String name;

    /**
     * @return the scope
     */
    public int getScope() {
        return scope;
    }

    /**
     * @param scope the scope to set
     */
    public void setScope(int scope) {
        this.scope=scope;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name=name;
    }
}
