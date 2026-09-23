package com.employee.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Attendance implements Serializable{

	private int attendanceId;
	private int empId;
	private LocalDate attendanceDate;
	private AttendanceStatus attendanceStatus;

	public Attendance() {
		super();
	}

	public Attendance(int empId, LocalDate attendanceDate, AttendanceStatus attendanceStatus) {
		super();
		this.empId = empId;
		this.attendanceDate = attendanceDate;
		this.attendanceStatus = attendanceStatus;
	}

	public Attendance(int attendanceId, int empId, LocalDate attendanceDate, AttendanceStatus attendanceStatus) {
		this(empId, attendanceDate, attendanceStatus);

		this.attendanceId = attendanceId;

	}

	public int getAttendanceId() {
		return attendanceId;
	}

	public void setAttendanceId(int attendanceId) {
		this.attendanceId = attendanceId;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public LocalDate getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(LocalDate attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

	public AttendanceStatus getAttendanceStatus() {
		return attendanceStatus;
	}

	public void setAttendanceStatus(AttendanceStatus attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
	}

	@Override
	public String toString() {
		return "Attendance [attendanceId=" + attendanceId + ", empId=" + empId + ", attendanceDate=" + attendanceDate
				+ ", attendanceStatus=" + attendanceStatus + "]";
	}
	

}
