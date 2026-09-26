package com.employee.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements Serializable {

	private int userId;
	private String userName;
	private String passwordHash;
	private String role;
	private LocalDateTime lastLogin;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(int userId, String userName, String passwordHash, String role) {
		this.userId = userId;
		this.userName = userName;
		this.passwordHash = passwordHash;
		this.role = role;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	
	@Override
	public String toString() {
	    return "User {" +
	           "\n  User ID       : " + userId +
	           "\n  User Name     : " + userName +
	           "\n  Password Hash : " + passwordHash +
	           "\n  Role          : " + role +
	           "\n  Last Login    : " + lastLogin +
	           "\n}";
	}

	

}
