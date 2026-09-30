package com.employee.service;

import java.util.List;

import com.employee.dao.PayrollDAO;
import com.employee.file.LogUtil;
import com.employee.model.Employee;
import com.employee.model.Payroll;
import com.employee.model.Salary;
import com.employee.util.DateUtil;
import com.employee.util.Validation;

public class PayrollService {

	private final PayrollDAO payrollDAO = new PayrollDAO();
	private final EmployeeService employeeService =new EmployeeService();
	private final AttendanceService attendanceService=new AttendanceService();
	
	public Payroll processPayroll(int empId,String payrollMonth, double otherAllownce, double otherDedction)  {
		
		if(!DateUtil.inValidPayrollMonth(payrollMonth)) {
            throw new IllegalArgumentException("Payroll month must be in yyyy-MM format, got: " + payrollMonth);

		}
		Validation.validPositivNo(otherAllownce,"Allowance");
		Validation.validPositivNo(otherDedction, "Other Dedection");
		
		Employee emp= employeeService.getEmployeeOrThrow(empId);
		Validation.validSalary(emp.getBaseSalary());
		
		int presentDays=attendanceService.countPresentDays(empId, payrollMonth);
		int absentDays=attendanceService.countAbsentDays(empId, payrollMonth);
		int leaveDays=attendanceService.countLeaveDays(empId, payrollMonth);
		
		Salary salary= new Salary(empId, emp.getBaseSalary(),  otherAllownce,otherDedction,payrollMonth );
		Payroll payroll=new Payroll(empId, payrollMonth, presentDays, absentDays, leaveDays, salary.getNetSalary());
		
		int payrollId=payrollDAO.saveFullPayroll(salary, payroll);
		payroll.setPayrollId(payrollId);
		
		LogUtil.info("Payroll processed for employee=" + empId + " month=" + payrollMonth +
                " netSalary=" + salary.getNetSalary());
		return payroll;
	}
	
	
	public Payroll getPayroll(int empId,String payrollMonth) {
		return payrollDAO.findByEmployeeAndMonth(empId, payrollMonth);
	}
	
	public Salary getSalaryBreakdown(int employeeId, String payrollMonth) {
        return payrollDAO.findSalary(employeeId, payrollMonth);
    }
	
	public List<Payroll> getPayrollHistory(int employeeId) {
        return payrollDAO.findByEmployee(employeeId);
    }
	
	public List<Payroll> getMonthlyPayrollReport(String payrollMonth) {
        return payrollDAO.findByMonth(payrollMonth);
    }
	
	public void markStatus(int payrollId, String status) {
        payrollDAO.updateStatus(payrollId, status);
    }
//	public static void main(String[] args) {
//		PayrollService payrollService=new PayrollService();
//		//System.out.println(payrollService.processPayroll(3,"2026-09" , 200000, 49999));
//		//System.out.println(payrollService.getSalaryBreakdown(3, "2026-09"));
//		//System.out.println(payrollService.getPayrollHistory(3));
//		
//	}
	
	

}
