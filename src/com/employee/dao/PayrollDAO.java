package com.employee.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.employee.exception.DatabaseException;
import com.employee.model.Payroll;
import com.employee.model.Salary;
import com.employee.util.DBConnection;

public class PayrollDAO {

	public int saveFullPayroll(Salary salary, Payroll payroll) {
		Connection con = null;
		String salarySQL = "INSERT INTO salary (employee_id, basic_salary, hra, da, allowance, pf, "
				+ "  professional_tax, other_deduction, gross_salary, total_deduction, net_salary, payroll_month)"
				+ "  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		String payrollSQL = "INSERT INTO payroll (employee_id, payroll_month, present_days, absent_days,"
				+ "leave_days, net_salary, processed_on, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			con = DBConnection.getConnections();
			con.setAutoCommit(false);

			try (PreparedStatement salaryPS = con.prepareStatement(salarySQL)) {
				salaryPS.setInt(1, salary.getEmployeeId());
				salaryPS.setDouble(2, salary.getBasicSalary());
				salaryPS.setDouble(3, salary.getHra());
				salaryPS.setDouble(4, salary.getDa());
				salaryPS.setDouble(5, salary.getAllowance());
				salaryPS.setDouble(6, salary.getPf());
				salaryPS.setDouble(7, salary.getProfessionalTex());
				salaryPS.setDouble(8, salary.getOtherDeduction());
				salaryPS.setDouble(9, salary.getGrossSalary());
				salaryPS.setDouble(10, salary.getTotalDeduction());
				salaryPS.setDouble(11, salary.getNetSalary());
				salaryPS.setString(12, salary.getPayrollMonth());
				salaryPS.executeUpdate();

			}

			int payrollId;
			try (PreparedStatement payrollPS = con.prepareStatement(payrollSQL, Statement.RETURN_GENERATED_KEYS)) {
				payrollPS.setInt(1, payroll.getEmployeeId());
				payrollPS.setString(2, payroll.getPayrollMonth());
				payrollPS.setInt(3, payroll.getPresentDays());
				payrollPS.setInt(4, payroll.getAbsentDays());
				payrollPS.setInt(5, payroll.getLeaveDays());
				payrollPS.setDouble(6, payroll.getNetSalary());
				payrollPS.setTimestamp(7, Timestamp.valueOf(payroll.getProcessedOn()));
				payrollPS.setString(8, payroll.getStatus());
				payrollPS.executeUpdate();
				try (ResultSet rs = payrollPS.getGeneratedKeys()) {
					payrollId = rs.next() ? rs.getInt(1) : -1;
				}

			}

			con.commit();
			return payrollId;

		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException rollback) {

					throw new DatabaseException("Rollback failed: " + rollback.getMessage());
				}
			}
			throw new DatabaseException("Error saving payroll (transaction rolled back): " + e.getMessage());

		} finally {
			if (con != null) {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (SQLException ignored) {

				}
			}
		}

	}

	public List<Payroll> findByEmployee(int empId) {
		List<Payroll> list = new ArrayList<Payroll>();
		String sql = "select * from payroll where employee_id=? order by payroll_month";
		try (Connection con = DBConnection.getConnections(); PreparedStatement ps = con.prepareStatement(sql);) {
			ps.setInt(1, empId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(mapPayrollRow(rs));
				}
			}
		} catch (SQLException e) {

		}
		return list;
	}

	public Payroll findByEmployeeAndMonth(int employeeId, String payrollMonth) {
		String sql = "SELECT * FROM payroll WHERE employee_id = ? AND payroll_month = ?";
		try (Connection con = DBConnection.getConnections(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, employeeId);
			ps.setString(2, payrollMonth);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next() ? mapPayrollRow(rs) : null;
			}
		} catch (SQLException e) {
			throw new DatabaseException("Error fetching payroll: " + e.getMessage());
		}
	}
	
	public Salary findSalary(int employeeId, String payrollMonth) {
        String sql = "SELECT * FROM salary WHERE employee_id = ? AND payroll_month = ?";
        try (Connection con = DBConnection.getConnections();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, employeeId);
            ps.setString(2, payrollMonth);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapSalaryRow(rs) : null;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error fetching salary: " + e.getMessage());
        }
    }

	public List<Payroll> findByMonth(String payrollMonth) {
        String sql = "SELECT * FROM payroll WHERE payroll_month = ? ORDER BY employee_id";
        List<Payroll> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnections();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, payrollMonth);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapPayrollRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error fetching monthly payroll report: " + e.getMessage());
        }
        return list;
    }
	
	public void updateStatus(int payrollId, String status) {
        String sql = "UPDATE payroll SET status = ? WHERE payroll_id = ?";
        try (Connection con = DBConnection.getConnections();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, payrollId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error updating payroll status: " + e.getMessage());
        }
    }
	private Payroll mapPayrollRow(ResultSet rs) throws SQLException {
		Payroll p = new Payroll();
		p.setPayrollId(rs.getInt("payroll_id"));
		p.setEmployeeId(rs.getInt("employee_id"));
		p.setPayrollMonth(rs.getString("payroll_month"));
		p.setPresentDays(rs.getInt("present_days"));
		p.setAbsentDays(rs.getInt("absent_days"));
		p.setLeaveDays(rs.getInt("leave_days"));
		p.setNetSalary(rs.getDouble("net_salary"));
		Timestamp ts = rs.getTimestamp("processed_on");
		if (ts != null) {
			p.setProcessedOn(ts.toLocalDateTime());
		}
		p.setStatus(rs.getString("status"));
		return p;
	}

	private Salary mapSalaryRow(ResultSet rs) throws SQLException {
		Salary s = new Salary();
		s.setSalaryId(rs.getInt("salary_id"));
		s.setEmployeeId(rs.getInt("employee_id"));
		s.setBasicSalary(rs.getDouble("basic_salary"));
		s.setAllowance(rs.getDouble("allowance"));
		s.setOtherDeduction(rs.getDouble("other_deduction"));
		s.setPayrollMonth(rs.getString("payroll_month"));
		s.computeAll(); // recompute hra/da/pf/etc from basic + allowance + deduction
		return s;
	}

	
}
