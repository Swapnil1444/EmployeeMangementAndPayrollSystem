package com.employee.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

	private PasswordUtil() {
		// TODO Auto-generated constructor stub
	}

	public static String hash(String rawPassword) {

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = digest.digest(rawPassword.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (byte b : hashBytes) {
				sb.append(String.format("%02x", b));
			}

			return sb.toString();
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new RuntimeException("Unable to hash Password " + e);
		}

	}
	
	public static boolean matches(String rawPassword, String storedHash) {
		return hash(rawPassword).equals(storedHash);
	}
}
