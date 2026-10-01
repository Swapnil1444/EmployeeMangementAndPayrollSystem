package com.employee.util;

import java.sql.Connection;
import java.sql.DriverManager;

import com.employee.exception.DatabaseException;

public class DBConnection {

	private DBConnection() {
		
	}
	private static Connection con = null;
	private static int num=1;

	public static boolean loadDriverClass()  throws DatabaseException{

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			num++;
			return true;
		} catch (Exception e) {
			throw new DatabaseException("Driver Is Not Lood..!");
		}
	}

	public static Connection getConnections() throws DatabaseException {

		
			try {  
				if (num==1) {
					  loadDriverClass();
				  }
					con = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
					return con;
			} catch (Exception e) {
				throw new DatabaseException("No Connection Bilding on DataBase...!");

			}

		//return con;
	}

}
