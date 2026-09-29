package com.employee.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import com.employee.util.Constants;
import com.employee.util.DateUtil;

public final class LogUtil {

	private LogUtil() {
		// TODO Auto-generated constructor stub
	}

	static {
		new File(Constants.LOGS_DIR).mkdirs();
	}

	public static void info(String msg) {
		write("INFO", msg);
	}

	public static void warn(String message) {
		write("WARN", message);
	}

	public static void error(String message) {
		write("ERROR", message);
	}

	private static void write(String level, String msg) {

		File file = new File(Constants.LOG_FILE);
		try (FileWriter fw = new FileWriter(file, true); BufferedWriter bw = new BufferedWriter(fw)) {

			String line = "[" + DateUtil.formatDateTime(LocalDateTime.now()) + "][" + level + "]" + msg;
			bw.write(line);
			bw.newLine();
		} catch (IOException e) {
			// Fall back to console if logging itself fails - never crash the app over
			// logging.
			System.err.println("Failed to write log entry: " + e.getMessage());
		}
	}
	
//	public static void main(String[] args) {
//		info("test line");
//	}

}
