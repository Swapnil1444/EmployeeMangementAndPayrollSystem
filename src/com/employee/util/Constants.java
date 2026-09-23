package com.employee.util;

public class Constants {

	private Constants() {
	}

	// DATABASE
	private static final String DB_URL = "";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "9309";

	// FILE PATH
	private static final String DATA_DIR = "data";
	private static final String EMPLOYEE_DIR = DATA_DIR + "/employee";
	private static final String ATTENDANCE_DIR = DATA_DIR + "/attendance";
	private static final String PAYROLL_DIR = DATA_DIR + "/payroll";
	private static final String ROPORTS_DIR = DATA_DIR + "/repors";
	private static final String LOGS_DIR = DATA_DIR + "/logs";
	private static final String BACKUP_DIR = DATA_DIR + "/backup";

	private static final String LOG_FILE = LOGS_DIR + "/appliction.log";
	private static final String BACKUP_FILE = BACKUP_DIR + "/backup.ser";

	// LOGINS
	private static final int MAX_LOGIN_ATTEMENT = 3;

	// EMAIL
	private static final String SMTP_HOST = "";
	private static final String SMTP_PORT = "587";
	private static final String EMAIL_FROM = "";
	private static final String EMAIL_PASSWORD = "";

	// COMPANY NAME
	private static final String COMPANY_NAME = "Employee Payroll Pvt Ltd.";

}
