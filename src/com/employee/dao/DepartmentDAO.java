package com.employee.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.employee.exception.DatabaseException;
import com.employee.model.Deparment;
import com.employee.util.DBConnection;

public class DepartmentDAO {

	public int insert(Deparment dept) {
		String sql = "insert into departments (department_name, description) values (?,?)";
		try (Connection con = DBConnection.getConnections(); PreparedStatement ps = con.prepareStatement(sql);) {
			ps.setString(1, dept.getDept_Name());
			ps.setString(2, dept.getDept_Desc());
			ps.executeUpdate();
			try (ResultSet keys = ps.getGeneratedKeys();) {
				if (keys.next()) {
					return keys.getInt(1);
				}
			}
			return -1;

		} catch (SQLException e) {
			throw new DatabaseException("Error inserting description:" + e.getMessage());
		}
	}

	public boolean update(Deparment dept) {
		String sql = "update departments set department_name =? ,description= ? where department_id=?";
		try (Connection con = DBConnection.getConnections(); PreparedStatement ps = con.prepareStatement(sql);) {
			ps.setString(1, dept.getDept_Name());
			ps.setString(2, dept.getDept_Desc());
			ps.setInt(3, dept.getDept_Id());
			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			throw new DatabaseException("Error updating department:" + e.getMessage());
		}
	}

	public boolean delete(int deptId) {
		String sql = "delete from departments where department_id=?";
		try (Connection con=DBConnection.getConnections();
			PreparedStatement	ps=con.prepareStatement(sql);){
			ps.setInt(1, deptId);
			return ps.executeUpdate()>0;
		} catch (SQLException e) {
		  throw new DatabaseException("Error deleting department (it may still have employees assigned):"+e.getMessage());
		}
	}

	public Deparment findById(int deparmentId) {
		String sql = "SELECT * FROM departments WHERE department_id = ?";
		try (Connection con = DBConnection.getConnections(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, deparmentId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRow(rs);
				}
				return null;
			}
		} catch (SQLException e) {
			throw new DatabaseException("Error Finding Deparment:" + e.getMessage());
		}

	}

	public List<Deparment> findAll() {
		List<Deparment> list = new ArrayList<Deparment>();
		String sql = "SELECT * FROM departments ORDER BY department_name";
		try (Connection con = DBConnection.getConnections(); PreparedStatement ps = con.prepareStatement(sql);) {
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(mapRow(rs));
				}
			}

		} catch (SQLException e) {
			throw new DatabaseException("Error Fetching Deparments :" + e.getMessage());

		}
		return list;
	}

	public List<Deparment> searchByName(String name) {
		List<Deparment> list = new ArrayList<Deparment>();
		String sql = "select * from departments where department_name Like ?";
		try (Connection con = DBConnection.getConnections(); PreparedStatement ps = con.prepareStatement(sql);) {

			ps.setString(1, "%" + name + "%");
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(mapRow(rs));
				}
			}
		} catch (SQLException e) {
			throw new DatabaseException("Error searching departments:" + e.getMessage());
		}

		return list;

	}

	private Deparment mapRow(ResultSet rs) throws SQLException {

		return new Deparment(rs.getInt("department_id"), rs.getString("department_name"), rs.getString("description"));
	}

//	public static void main(String[] args) {
//		DepartmentDAO d = new DepartmentDAO();
//		// System.out.println(d.findById(1));
//		System.out.println(d.findAll());
//		// System.out.println(d.searchByName("e"));
//		Deparment d1 = new Deparment("AIDS", "crateing the ai and chat boots in this aids");
//		Deparment d2 = new Deparment(4, "CA", "handling all finacilay deparment");
//		// System.out.println( d.update(d2));
//		// System.out.println(d.insert(d1));
//		//System.out.println(d.delete(4));
//
//	}

}
