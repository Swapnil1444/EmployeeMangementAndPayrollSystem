package com.employee.service;

import java.time.LocalDate;
import java.util.List;

import com.employee.dao.*;
import com.employee.exception.InvalidAttendanceException;
import com.employee.file.LogUtil;
import com.employee.model.Attendance;
import com.employee.model.AttendanceStatus;

public class AttendanceService {

	private AttendanceDAO attendanceDAO = new AttendanceDAO();

	public int markAttendance(int empId, LocalDate date, AttendanceStatus status) throws InvalidAttendanceException {
		if (date.isAfter(LocalDate.now())) {
			throw new InvalidAttendanceException("Cannot mark attendance for a future date: " + date);

		}
		

		Attendance existing = attendanceDAO.findByEmployeeIdAndDate(empId, date);
		//System.out.println(existing);
		if (existing != null) {
			throw new InvalidAttendanceException(
					"Attendance already marked for employee " + empId + " on " + date + ". Use update instead.");
		}
		
		int id = attendanceDAO.insert(new Attendance(empId, date, status));
		System.out.println(id);
		LogUtil.info("Attendance marked: employee=" + empId + " date=" + date + " status=" + status);

		return id;
	}

	public boolean updateAttendace(int empId, LocalDate date, AttendanceStatus status) {
		Attendance exating = attendanceDAO.findByEmployeeIdAndDate(empId, date);
		if (exating == null) {
			throw new InvalidAttendanceException("No attendance record exists for employee " + empId + " on " + date);
		}
		exating.setAttendanceStatus(status);
		boolean ok = attendanceDAO.update(exating);
		LogUtil.info("Attendance updated: employee=" + empId + " date=" + date + " status=" + status);

		return ok;

	}

	public List<Attendance> getAttendanceForEmployee(int empId) {
		return attendanceDAO.findByEmployee(empId);
	}

	public List<Attendance> getAttendanceForEmployeeMonth(int employeeId, String yearMonth) {
		return attendanceDAO.findByEmployeeAndMonth(employeeId, yearMonth);
	}

	public List<Attendance> getAttendanceByDate(LocalDate date) {
		return attendanceDAO.findByDate(date);
	}

	public long countByStatus(List<Attendance> records, AttendanceStatus status) {
		return records.stream().filter(a -> a.getAttendanceStatus() == status).count();
	}

	public int countPresentDays(int employeeId, String yearMonth) {
		return (int) countByStatus(getAttendanceForEmployeeMonth(employeeId, yearMonth), AttendanceStatus.present);
	}

	public int countAbsentDays(int employeeId, String yearMonth) {
		return (int) countByStatus(getAttendanceForEmployeeMonth(employeeId, yearMonth), AttendanceStatus.absent);
	}

	public int countLeaveDays(int employeeId, String yearMonth) {
		return (int) countByStatus(getAttendanceForEmployeeMonth(employeeId, yearMonth), AttendanceStatus.Leave);
	}

	public int countHalfDays(int employeeId, String yearMonth) {
		return (int) countByStatus(getAttendanceForEmployeeMonth(employeeId, yearMonth), AttendanceStatus.halfDay);
	}

//	public static void main(String[] args) {
//		
//		AttendanceService attendanceService=new AttendanceService();
//		//System.out.println(attendanceService.updateAttendace(4,LocalDate.of(2026,8,02), AttendanceStatus.absent));
//		
//		//System.out.println(attendanceService.countPresentDays(3, "2026-09"));
//		
//	}

}
