package com.employee.exception;

public class InvalidAttendanceException extends RuntimeException{

	public InvalidAttendanceException(String msg) {
		super(msg);
	}
}
