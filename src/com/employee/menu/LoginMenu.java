package com.employee.menu;

import com.employee.model.User;
import com.employee.service.LoginService;
import com.employee.util.InputUtil;

public class LoginMenu {

	private final LoginService loginService = new LoginService();

	public User show() {
		
		System.out.println("************************************************");
		System.out.println("     EMPLOYEE MANGMENT AND PAYROLL SYSTEM       ");
		System.out.println("************************************************");

		while (!loginService.isLockedOut()) {
			try {

				String usrename = InputUtil.readString("UserName:");
				String password = InputUtil.readString("Password:");

				User user = loginService.login(usrename, password);

				
				if (user != null) {
					System.out.println("\nLogin successful. Welcome, " + user.getUserName() + "!\n");
					return user;
				} else {
					System.out.println(
							"Invalid credentials. Attempts remaining: " + loginService.getRemainingAttempts() + "\n");
				}
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage() + "\n");
			}
		}

		System.out.println("Maximum login attempts exceeded. Exiting application.");
		return null;
	}

}
