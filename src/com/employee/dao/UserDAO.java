package com.employee.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.time.LocalDateTime;

import com.employee.exception.DatabaseException;
import com.employee.model.User;
import com.employee.util.DBConnection;
import com.employee.util.PasswordUtil;


public class UserDAO {

	public int insert(User user) {
		String sql="insert into users(username ,password_hash,role) values(?,?,?)";
		try(Connection con=DBConnection.getConnections();
			PreparedStatement	ps=con.prepareStatement(sql);) {
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getPasswordHash());
			ps.setString(3, user.getRole());
			ps.executeUpdate();
			try(ResultSet keys=ps.getGeneratedKeys()){
				if(keys.next()) {
					return keys.getInt(1);
				}
			}
			return -1;
			
		} catch (Exception e) {
			throw new DatabaseException("Error inserting user: "+e.getMessage());
		}
	}
	
	public void updateLastLogin(int userId, LocalDateTime loginTime) {
		String sql = "update users set last_login=? where user_id=?";
		try (Connection con = DBConnection.getConnections(); PreparedStatement ps = con.prepareStatement(sql);) {

			ps.setTimestamp(1, Timestamp.valueOf(loginTime));
			ps.setInt(2, userId);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException("Error updating last login: " + e.getMessage());

		}
	}

	public User findByUserName(String namename) {
		String sql = "select * from Users where username =?";
		try (Connection con = DBConnection.getConnections(); PreparedStatement ps = con.prepareStatement(sql);) {
			ps.setString(1, namename);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRow(rs);
				}
				return null;

			}

		} catch (SQLException e) {
			throw new DatabaseException("Error finding user by username:" + e.getMessage());
		}
	}

	private User mapRow(ResultSet rs) throws SQLException {
		User u = new User();
		u.setUserId(rs.getInt("user_id"));
		u.setUserName(rs.getString("username"));
		u.setPasswordHash(rs.getString("password_hash"));
		u.setRole(rs.getString("role"));
		java.sql.Timestamp ts = rs.getTimestamp("last_login");
		if (ts != null) {
			u.setLastLogin(ts.toLocalDateTime());
		}
		return u;
	}

//	public static void main(String[] args) {
//		UserDAO u = new UserDAO();
//		//yyyy-mm-dd hh:mm:ss
//		//u.updateLastLogin(1, LocalDateTime.now());
//		
//		User u1=new User();
//		u1.setUserName("jay123");
//		u1.setPasswordHash(PasswordUtil.hash("jay123"));
//		u1.setRole("Admin");
//		//System.out.println(u.insert(u1));
//		System.out.println(u.findByUserName("jay123"));
//
//	}

}
