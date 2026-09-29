package com.employee.service;

import com.employee.dao.UserDAO;
import com.employee.file.LogUtil;
import com.employee.model.User;
import com.employee.util.Constants;
import com.employee.util.PasswordUtil;

public class LoginService {

	private final UserDAO userDAO = new UserDAO();
	private int faildAttempts = 0;

	public User login(String username, String password) {
		if (isLockedOut()) {
			LogUtil.warn("Login Block- max attempts for username:" + username);
			return null;
		}
		User user = userDAO.findByUserName(username);
		if (user != null && PasswordUtil.matches(password, user.getPasswordHash())) {
			LogUtil.info("Successful login for username: " + username);
			return user;
		}
		faildAttempts++;
		LogUtil.warn("Failed login attempt #" + faildAttempts + " for username: " + username);
		return null;
	}

	private boolean isLockedOut() {

		return faildAttempts >= Constants.MAX_LOGIN_ATTEMENT;
	}

	public int getRemainingAttempts() {
		return Math.max(0, Constants.MAX_LOGIN_ATTEMENT - faildAttempts);
	}

	public void resetAttempts() {
		faildAttempts = 0;
	}

	public void logout(User user) {
		LogUtil.info("User logged out: " + (user != null ? user.getUserName() : "unknown"));
	}
	
	public static void main(String[] args) {
		LoginService l=new LoginService();
		System.out.println(l.login("admin","admin123" ));
	}

}
