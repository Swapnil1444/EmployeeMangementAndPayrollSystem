package com.employee.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.employee.exception.DatabaseException;
import com.employee.model.Attendance;
import com.employee.util.DBConnection;

public class AttendanceDAO {

	public int insert(Attendance attendance) {
		String sql="insert into attendance(employee_id,attendance_date,status) values(?,?,?)";
		try  (Connection con=DBConnection.getConnections();
			PreparedStatement	ps=con.prepareStatement(sql);){
			ps.setInt(1, attendance.getEmpId());
			ps.setDate(2,Date.valueOf(attendance.getAttendanceDate()));
			ps.setString(3, attendance.getAttendanceStatus().name());
			ps.executeUpdate();
			try(ResultSet rsResultSet=ps.getGeneratedKeys()){
				if(rsResultSet.next()) {
					return rsResultSet.getInt(1);
				}
			}
			return -1;
			
		} catch (SQLException e) {
		throw new DatabaseException("Error inserting attendance:"+e.getMessage());
		}
	}

	public boolean update(Attendance att) {
		
		String sql="update attendance set status =? where employee_id=? attendance_date=?";
		try (Connection con=DBConnection.getConnections();
			PreparedStatement	ps=con.prepareStatement(sql);){
			ps.setString(1,att.getAttendanceStatus().name());
			ps.setInt(2, att.getEmpId());
			ps.setDate(3, Date.valueOf(att.getAttendanceDate()));
			return ps.executeUpdate()>0;
			
			
		} catch (Exception e) {
			throw new DatabaseException("Error update attendance :"+e.getMessage());
		}
	}
	
	
	
	
	
}
