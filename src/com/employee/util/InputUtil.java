package com.employee.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import com.employee.exception.InvalidInputException;

public class InputUtil {

	static Scanner sc = new Scanner(System.in);

	private InputUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public static String readString(String promt)  {
		System.out.println(promt);
		String value=sc.next();
		if(value==null|| value.trim().isEmpty()) {
			throw new InvalidInputException("Input cannot be Blank..!");
		}
		return value.trim();
	}
	public static String readOptionalString(String promt)  {
		System.out.println(promt);
		String value=sc.nextLine();
		return value==null ? "" : value.trim();
	}
	
	public static int readInt(String promt)  {
		System.out.println(promt);
		String value=sc.nextLine().trim();
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new InvalidInputException("Expected a whole number but got: '"+value+"'");
		}
	}
	
	public static double readDouble(String promt)  {
		System.out.println(promt);
		String value=sc.nextLine().trim();
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			throw new InvalidInputException("Expected a number but got: '"+value+"'");
		}
	}
	
	public static LocalDate readDate(String promt)  {
		System.out.println(promt+" (yyyy-MM-dd):");
		String value=sc.nextLine().trim();
		try {
			return LocalDate.parse(value);
		} catch (DateTimeParseException e) {
			throw new InvalidInputException("Expected date is (yyyy-MM-dd) format but got: '"+value+"'");
		}
	}
	
	public static char readChar(String promt)  {
		System.out.println(promt);
		String value=sc.nextLine().trim();
		if(value.length()!=1) {
			throw new InvalidInputException("Expected  single charactar");
		}
		return value.charAt(0);
	}
	
	public static void pressEntarToContinue() {
		System.out.println("\nPress ENTER to Continue...");
		sc.nextLine();
	}
	
	
}
