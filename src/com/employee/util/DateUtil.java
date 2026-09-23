package com.employee.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;


public class DateUtil {

	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-mm-yyyy");
	private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	private static final DateTimeFormatter MONTH_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM");

	private DateUtil() {
		// TODO Auto-generated constructor stub
	}

	public static String formatDate(LocalDate date) {
		return date == null ? "" : date.format(DATE_FORMAT);
	}

	public static String formatDateTime(LocalDate dateTime) {
		return dateTime == null ? "" : dateTime.format(DATE_TIME_FORMAT);
	}

	public static String currentPayrollMonth() {
		return YearMonth.now().format(MONTH_FORMAT);
	}

	public static boolean inValidPayrollMonth(String month) {
		try {
		YearMonth.parse(month,MONTH_FORMAT);
		return true;
	} catch (Exception e) {
			return false;
		}
		
	}
	
	public static int totalDayInMonth(String payrollMonth) {
		YearMonth ym=YearMonth.parse(payrollMonth,MONTH_FORMAT);
		return ym.lengthOfMonth();
	}
	
	public static double dayBetween(LocalDate start ,LocalDate end) {
		return ChronoUnit.DAYS.between(start, end);
	}
}
