package com.employee.exception;

import java.io.IOException;
import java.sql.SQLException;

public class DatabaseException extends SQLException{
	public DatabaseException(String msg) {
		super(msg);
	}
}
