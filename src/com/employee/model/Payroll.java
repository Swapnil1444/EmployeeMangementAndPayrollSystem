package com.employee.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Payroll implements Serializable{

	private int payrollId;
	private int employeeId;
	private String payrollMonth;
	private int presentDays;
	private int absentDays;
	private int leaveDays;
	private double netSalary;
	private LocalDateTime processedOn;
	private String status;

	public Payroll() {
		super();
	}
	
	public Payroll(int employeeId, String payrollMonth, int presentDays, int absentDays, int leaveDays,
			double netSalary) {
		super();
		this.employeeId = employeeId;
		this.payrollMonth = payrollMonth;
		this.presentDays = presentDays;
		this.absentDays = absentDays;
		this.leaveDays = leaveDays;
		this.netSalary = netSalary;
		this.processedOn=LocalDateTime.now();
		this.status="GENERATED";
	}

	public int getPayrollId() {
		return payrollId;
	}

	public void setPayrollId(int payrollId) {
		this.payrollId = payrollId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getPayrollMonth() {
		return payrollMonth;
	}

	public void setPayrollMonth(String payrollMonth) {
		this.payrollMonth = payrollMonth;
	}

	public int getPresentDays() {
		return presentDays;
	}

	public void setPresentDays(int presentDays) {
		this.presentDays = presentDays;
	}

	public int getAbsentDays() {
		return absentDays;
	}

	public void setAbsentDays(int absentDays) {
		this.absentDays = absentDays;
	}

	public int getLeaveDays() {
		return leaveDays;
	}

	public void setLeaveDays(int leaveDays) {
		this.leaveDays = leaveDays;
	}

	public double getNetSalary() {
		return netSalary;
	}

	public void setNetSalary(double netSalary) {
		this.netSalary = netSalary;
	}

	public LocalDateTime getProcessedOn() {
		return processedOn;
	}

	public void setProcessedOn(LocalDateTime processedOn) {
		this.processedOn = processedOn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Payroll [payrollId=" + payrollId + ", employeeId=" + employeeId + ", payrollMonth=" + payrollMonth
				+ ", presentDays=" + presentDays + ", absentDays=" + absentDays + ", leaveDays=" + leaveDays
				+ ", netSalary=" + netSalary + ", processedOn=" + processedOn + ", status=" + status + "]";
	}
	
	

	

}
