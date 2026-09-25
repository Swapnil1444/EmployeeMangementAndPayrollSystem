package com.employee.util;

import java.util.Scanner;
import java.util.regex.Pattern;

import com.employee.exception.InvalidInputException;

public class Validation {

	static Scanner sc = new Scanner(System.in);

	public static void requireNonBlank(String value ,String field) throws InvalidInputException {
		if ((value == null || value.trim().isEmpty())) {
			throw new InvalidInputException( field+" cannot be blank..!");
		}
	}

	public static void validEmail(String email)  {
		requireNonBlank(email,"Email");
		if (!(Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}$", email))) {
			throw new InvalidInputException("Invalid Email:"+email);
		}
	}

	public static void validPhoneNo(String no) {
		requireNonBlank(no ,"Phoene No");
		if (!Pattern.matches("[0-9]{10}", no)) {
			throw new InvalidInputException("Invalid Phone Number..!");
		}
	}

	public static void validSalary(double salary)  {
		if (salary <= 0) {
			throw new InvalidInputException("Basic salary must be greater than zero..!");
		}
		if (salary > 10_000_000) {
			throw new InvalidInputException("Basic Salary Looks unrealistically high...!");
		}
	}

	public static void validPositivNo(int no)  {
		if (no <= 0) {
			throw new InvalidInputException("must be greater than zero..!");
		}
	}

	public static void validAdharNo(String adhar)  {
		requireNonBlank(adhar, "Aadhar Number");
		if (!Pattern.matches("^[2-9]{1}[0-9]{3}\\s[0-9]{4}\\s[0-9]{4}$", adhar)) {

			throw new InvalidInputException("Invalid adhar must be 12 digit...!");

		}
	}

	public static void validPanCard(String pan)  {
		requireNonBlank(pan, "Pancard");
		if (!Pattern.matches("[A-Z]{5}[0-9]{4}[A-Z]{1}", pan)) {
			throw new InvalidInputException("Invalid Pancard No:" + pan);
		}
	}
	
	public static void main(String[] args) {
		try {
			validAdharNo("9987 4433 4434");
			validEmail("swapn@gmail.com");
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
