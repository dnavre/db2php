/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.afraid.poison.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author poison
 */
public class DbUtil {

	public static void closeQuietly(Connection connection) {
		try {
			if (null!=connection) {
				connection.close();
			}
		} catch (SQLException ex) {
		}
	}

	public static void closeQuietly(Statement statement) {
		try {
			if (null!=statement) {
				statement.close();
			}
		} catch (SQLException ex) {
		}
	}

	public static void closeQuietly(ResultSet resultSet) {
		try {
			if (null!=resultSet) {
				resultSet.close();
			}
		} catch (SQLException ex) {
		}
	}
}
