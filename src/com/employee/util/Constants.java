package com.employee.util;

public class Constants {

	private Constants() {
	}

	// DATABASE
	public static final String DB_URL = "jdbc:mysql://localhost:3306/employee_payroll_db";
	public static final String DB_USER = "root";
	public static final String DB_PASSWORD = "9309";

	// FILE PATH
	public static final String DATA_DIR = "data";
	public static final String EMPLOYEE_DIR = DATA_DIR + "/employee";
	public static final String ATTENDANCE_DIR = DATA_DIR + "/attendance";
	public static final String PAYROLL_DIR = DATA_DIR + "/payroll";
	public static final String ROPORTS_DIR = DATA_DIR + "/repors";
	public static final String LOGS_DIR = DATA_DIR + "/logs";
	public static final String BACKUP_DIR = DATA_DIR + "/backup";

	public static final String LOG_FILE = LOGS_DIR + "/appliction.log";
	public static final String BACKUP_FILE = BACKUP_DIR + "/backup.ser";

	// LOGINS
	public static final int MAX_LOGIN_ATTEMENT = 3;

	// EMAIL
	public static final String SMTP_HOST = "";
	public static final String SMTP_PORT = "587";
	public static final String EMAIL_FROM = "";
	public static final String EMAIL_PASSWORD = "";

	// COMPANY NAME
	public static final String COMPANY_NAME = "Employee Payroll Pvt Ltd.";

}
