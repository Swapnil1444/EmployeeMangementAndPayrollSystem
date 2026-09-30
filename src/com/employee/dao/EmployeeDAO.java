package com.employee.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.employee.exception.DatabaseException;
import com.employee.model.Deparment;
import com.employee.model.Employee;
import com.employee.model.EmployeeStatus;
import com.employee.model.Gender;
import com.employee.util.DBConnection;

public class EmployeeDAO {

	private static final String SELECT_JOIN = "SELECT e.*, d.department_name, d.description AS dept_description "
			+ "FROM employees e LEFT JOIN departments d ON e.department_id = d.department_id ";

	public int insert(Employee emp) {
		String sql = "INSERT INTO employees (first_name, last_name, email, phone, address, gender, "
				+ "department_id, designation, joining_date, basic_salary, status) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection con = DBConnection.getConnections();
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			bindEmployee(ps, emp);
			ps.executeUpdate();
			try (ResultSet keys = ps.getGeneratedKeys()) {
				if (keys.next()) {
					return keys.getInt(1);
				}
			}
			return -1;
		} catch (SQLException e) {
			throw new DatabaseException("Error inserting Employee:" + e.getMessage());
		}
	}

	public boolean update(Employee emp) {
		String sql = "UPDATE employees SET first_name=?, last_name=?, email=?, phone=?, address=?, gender=?, "
				+ "department_id=?, designation=?, joining_date=?, basic_salary=?, status=? WHERE employee_id=?";
		try (Connection con = DBConnection.getConnections(); PreparedStatement ps = con.prepareStatement(sql)) {
			bindEmployee(ps, emp);
			ps.setInt(12, emp.getEmpId());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new DatabaseException("Error updating employee: " + e.getMessage());
		}
	}

	public boolean delete(int employeeId) {
		String sql = "DELETE FROM employees WHERE employee_id = ?";
		try (Connection con = DBConnection.getConnections(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, employeeId);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			throw new DatabaseException("Error deleting employee: " + e.getMessage());
		}
	}

	public Employee findById(int employeeId) {
		String sql = SELECT_JOIN + "WHERE e.employee_id = ?";
		try (Connection con = DBConnection.getConnections(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, employeeId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRow(rs);
				}
				return null;
			}
		} catch (SQLException e) {
			throw new DatabaseException("Error finding employee: " + e.getMessage());
		}
	}

	public List<Employee> findByName(String name) {
		String sql = SELECT_JOIN + "WHERE e.first_name LIKE ? OR e.last_name LIKE ?";
		List<Employee> list = new ArrayList<>();
		try (Connection con = DBConnection.getConnections(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, "%" + name + "%");
			ps.setString(2, "%" + name + "%");
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(mapRow(rs));
				}
			}
		} catch (SQLException e) {
			throw new DatabaseException("Error searching employees by name: " + e.getMessage());
		}
		return list;
	}

	public List<Employee> findByDepartment(int departmentId) {
		String sql = SELECT_JOIN + "WHERE e.department_id = ?";
		List<Employee> list = new ArrayList<>();
		try (Connection con = DBConnection.getConnections(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, departmentId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(mapRow(rs));
				}
			}
		} catch (SQLException e) {
			throw new DatabaseException("Error searching employees by department: " + e.getMessage());
		}
		return list;
	}

	public List<Employee> findAll() {
		String sql = SELECT_JOIN + "ORDER BY e.employee_id";
		List<Employee> list = new ArrayList<>();
		try (Connection con = DBConnection.getConnections();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				list.add(mapRow(rs));
			}
		} catch (SQLException e) {
			throw new DatabaseException("Error fetching employees: " + e.getMessage());
		}
		return list;
	}

	public int countAll() {
		String sql = "SELECT COUNT(*) FROM employees";
		try (Connection con = DBConnection.getConnections();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		} catch (SQLException e) {
			throw new DatabaseException("Error counting employees: " + e.getMessage());
		}
	}

	private void bindEmployee(PreparedStatement ps, Employee emp) throws SQLException {
		ps.setString(1, emp.getFirstName());
		ps.setString(2, emp.getLastName());
		ps.setString(3, emp.getEmail());
		ps.setString(4, emp.getPhoneNo());
		ps.setString(5, emp.getAddress());
		ps.setString(6, emp.getGender().name());
		ps.setInt(7, emp.getDept().getDept_Id());
		ps.setString(8, emp.getDestination());
		ps.setDate(9, Date.valueOf(emp.getJoinDate()));
		ps.setDouble(10, emp.getBaseSalary());
		ps.setString(11, emp.getEmpStatus().name());

	}

	private Employee mapRow(ResultSet rs) throws SQLException {

		Deparment dept = new Deparment(rs.getInt("department_id"), rs.getString("department_name"),
				rs.getString("dept_description"));

		Employee emp = new Employee();
		emp.setEmpId(rs.getInt("employee_id"));
		emp.setFirstName(rs.getString("first_name"));
		emp.setLastName(rs.getString("last_name"));
		emp.setEmail(rs.getString("email"));
		emp.setPhoneNo(rs.getString("phone"));
		emp.setAddress(rs.getString("address"));
		emp.setGender(Gender.valueOf(rs.getString("gender")));
		emp.setDept(dept);
		emp.setDestination(rs.getString("designation"));
		emp.setJoinDate(rs.getDate("joining_date").toLocalDate());
		emp.setBaseSalary(rs.getDouble("basic_salary"));
		emp.setEmpStatus(EmployeeStatus.valueOf(rs.getString("status")));
		return emp;
	}
	
	

}
