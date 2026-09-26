package com.employee.exception;

import java.io.IOException;
import java.sql.SQLException;

public class DatabaseException extends RuntimeException{
	public DatabaseException(String msg) {
		super(msg);
	}
}
