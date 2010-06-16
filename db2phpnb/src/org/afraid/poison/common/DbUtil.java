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

	/**
	 * close passed connection and suppress exception
	 *
	 * @param connection the connection
	 */
	public static void closeQuietly(Connection connection) {
		try {
			if (null!=connection) {
				connection.close();
			}
		} catch (SQLException ex) {
		}
	}

	/**
	 * close passed statement and suppress exception
	 *
	 * @param statement the statement
	 */
	public static void closeQuietly(Statement statement) {
		try {
			if (null!=statement) {
				statement.close();
			}
		} catch (SQLException ex) {
		}
	}

	/**
	 * close passed result set and suppress exception
	 *
	 *
	 * @param resultSet the result set
	 */
	public static void closeQuietly(ResultSet resultSet) {
		try {
			if (null!=resultSet) {
				resultSet.close();
			}
		} catch (SQLException ex) {
		}
	}
}
