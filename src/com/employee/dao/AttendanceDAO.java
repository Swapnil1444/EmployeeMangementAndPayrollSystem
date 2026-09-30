package com.employee.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.employee.exception.DatabaseException;
import com.employee.model.Attendance;
import com.employee.model.AttendanceStatus;
import com.employee.util.DBConnection;

public class AttendanceDAO {

	public AttendanceDAO() {
		// TODO Auto-generated constructor stub
	}

	public int insert(Attendance attendance) {
		String sql = "INSERT INTO attendance (employee_id, attendance_date, status) VALUES (?, ?, ?)";
		try (Connection con = DBConnection.getConnections();
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, attendance.getEmpId());
			ps.setDate(2, Date.valueOf(attendance.getAttendanceDate()));
			ps.setString(3, attendance.getAttendanceStatus().name());
			ps.executeUpdate();
			try (ResultSet rsResultSet = ps.getGeneratedKeys()) {
				if (rsResultSet.next()) {
					return rsResultSet.getInt(1);
				}
			}
			return -1;

		} catch (SQLException e) {
			throw new DatabaseException("Error inserting attendance:" + e.getMessage());
		}
	}

	public boolean update(Attendance att) {

		String sql = "update attendance set status =? where employee_id=? attendance_date=?";
		try (Connection con = DBConnection.getConnections(); PreparedStatement ps = con.prepareStatement(sql);) {
			ps.setString(1, att.getAttendanceStatus().name());
			ps.setInt(2, att.getEmpId());
			ps.setDate(3, Date.valueOf(att.getAttendanceDate()));
			return ps.executeUpdate() > 0;

		} catch (Exception e) {
			throw new DatabaseException("Error update attendance :" + e.getMessage());
		}
	}

	public Attendance findByEmployeeIdAndDate(int empId, LocalDate date) {
		String sql = "select * from attendance where employee_id=? and attendance_date=?";
		try (Connection con = DBConnection.getConnections(); PreparedStatement ps = con.prepareStatement(sql);) {
			ps.setInt(1, empId);
			ps.setDate(2, Date.valueOf(date));
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRow(rs);
				}
				return null;
			}
		} catch (SQLException e) {
			throw new DatabaseException("Error finding attendance records:" + e.getMessage());
		}
	}

	public List<Attendance> findByDate(LocalDate date) {
		List<Attendance> list = new ArrayList<Attendance>();
		String sql = "select * from attendance where attendance_date=? order by employee_id";
		try (Connection con = DBConnection.getConnections(); PreparedStatement ps = con.prepareStatement(sql);) {
			ps.setDate(1, Date.valueOf(date));
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(mapRow(rs));
				}
			}

		} catch (SQLException e) {
			throw new DatabaseException("Error finding attendanece by date:" + e.getMessage());
		}
		return list;
	}

	public List<Attendance> findByEmployee(int empId) {
		String sql = "SELECT * FROM attendance WHERE employee_id = ? ORDER BY attendance_date";
		return queryList(sql, empId, null);
	}

	public List<Attendance> findByEmployeeAndMonth(int employeeId, String yearMonth) {
		String sql = "SELECT * FROM attendance WHERE employee_id = ? "
				+ "AND DATE_FORMAT(attendance_date, '%Y-%m') = ? ORDER BY attendance_date";
		return queryList(sql, employeeId, yearMonth);
	}

	private List<Attendance> queryList(String sql, int empId, String yearMonth) {
		List<Attendance> list = new ArrayList<Attendance>();
		try (Connection con = DBConnection.getConnections(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, empId);
			if (yearMonth != null) {
				ps.setString(2, yearMonth);
			}
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(mapRow(rs));
				}
			}

		} catch (SQLException e) {
			throw new DatabaseException("Error fatching attendance:" + e.getMessage());
		}
		return list;
	}

	private Attendance mapRow(ResultSet rs) throws SQLException {

		return new Attendance(rs.getInt("attendance_id"), rs.getInt("employee_id"),
				rs.getDate("attendance_date").toLocalDate(), AttendanceStatus.valueOf(rs.getString("status")));
	}

//	public static void main(String[] args) {
//		AttendanceDAO a = new AttendanceDAO();
//
//		Attendance a1 = new Attendance(4, LocalDate.now(), AttendanceStatus.present);
//		// a.insert(a1);
//
//		// System.out.println(a.findByEmployeeAndMonth(3, "2026-09"));
//		System.out.println("------------------------------");
//		//System.out.println(a.findByEmployee(3));
//
//		//System.out.println(a.findByDate(LocalDate.of(2026, 9, 02)));
//		//System.out.println(a.findByEmployeeIdAndDate(3, LocalDate.of(2026, 9, 04)));
//		
//	}

}
