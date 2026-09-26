package com.employee.util;

import java.sql.Connection;
import java.sql.DriverManager;

import com.employee.exception.DatabaseException;

public class DBConnection {

	private DBConnection() {
		
	}
	private static Connection con = null;

	public static boolean loadDriverClass()  throws DatabaseException{

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return true;
		} catch (Exception e) {
			throw new DatabaseException("Driver Is Not Lood..!");
		}
	}

	public static Connection getConnections() throws DatabaseException {

		if (con == null) {
			try {
				if (loadDriverClass()) {
					con = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
					return con;
				}

			} catch (Exception e) {
				throw new DatabaseException("No Connection Bilding on DataBase...!");

			}

		}
		return con;
	}

}
