package com.employee.model;

import java.io.Serializable;

public class Salary implements Serializable{

	public final static double HRA_PERCENT = 20.0;
	public final static double DA_PERCENT = 10.0;
	public final static double PF_PERCENT = 12.0;
	public final static double PROFESSIONAL_TEX = 200.0;

	private int salaryId;
	private int employeeId;
	private double basicSalary;
	private double hra;
	private double da;
	private double allowance;
	private double pf;
	private double professionalTex;
	private double otherDeduction;
	private double grossSalary;
	private double totalDeduction;
	private double netSalary;
	private String payrollMonth;

	public Salary() {
		// TODO Auto-generated constructor stub
	}
	

	public Salary(int employeeId, double basicSalary, double allowance, double otherDeduction, String payrollMonth) {
		
		this.employeeId = employeeId;
		this.basicSalary = basicSalary;
		this.allowance = allowance;
		this.otherDeduction = otherDeduction;
		this.payrollMonth = payrollMonth;
		computeAll();
	}

	public void computeAll() {
		calculateDA();
		calculateHRA();
		calculatePF();
		calculateProfessionalTex();
		calculateGressSalary();
		calculateTotalDeduction();
		calculateNetSalary();
		
	}

	public double calculateHRA() {
		this.hra = basicSalary * (HRA_PERCENT / 100.0);
		return this.hra;
	}

	public double calculateDA() {
		this.da = basicSalary * (DA_PERCENT / 100.0);
		return this.da;
	}

	public double calculatePF() {
		this.pf = basicSalary * (PF_PERCENT / 100.0);
		return this.pf;
	}

	public double calculateGressSalary() {
		this.grossSalary = basicSalary + hra + da + allowance;
		return this.grossSalary;
	}

	public double calculateTotalDeduction() {
		this.totalDeduction = pf + PROFESSIONAL_TEX + otherDeduction;
		return this.totalDeduction;
	}

	public double calculateNetSalary() {
		this.netSalary=grossSalary-totalDeduction;
		return this.netSalary;
	}
	public double calculateProfessionalTex() {
		 this.professionalTex=PROFESSIONAL_TEX;
		 return this.professionalTex;
		
		
	}


	public int getSalaryId() {
		return salaryId;
	}


	public void setSalaryId(int salaryId) {
		this.salaryId = salaryId;
	}


	public int getEmployeeId() {
		return employeeId;
	}


	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}


	public double getBasicSalary() {
		return basicSalary;
	}


	public void setBasicSalary(double basicSalary) {
		this.basicSalary = basicSalary;
	}


	public double getHra() {
		return hra;
	}


	public void setHra(double hra) {
		this.hra = hra;
	}


	public double getDa() {
		return da;
	}


	public void setDa(double da) {
		this.da = da;
	}


	public double getAllowance() {
		return allowance;
	}


	public void setAllowance(double allowance) {
		this.allowance = allowance;
	}


	public double getPf() {
		return pf;
	}


	public void setPf(double pf) {
		this.pf = pf;
	}


	public double getProfessionalTex() {
		return professionalTex;
	}


	public void setProfessionalTex(double professionalTex) {
		this.professionalTex = professionalTex;
	}


	public double getOtherDeduction() {
		return otherDeduction;
	}


	public void setOtherDeduction(double otherDeduction) {
		this.otherDeduction = otherDeduction;
	}


	public double getGrossSalary() {
		return grossSalary;
	}


	public void setGrossSalary(double grossSalary) {
		this.grossSalary = grossSalary;
	}


	public double getTotalDeduction() {
		return totalDeduction;
	}


	public void setTotalDeduction(double totalDeduction) {
		this.totalDeduction = totalDeduction;
	}


	public double getNetSalary() {
		return netSalary;
	}


	public void setNetSalary(double netSalary) {
		this.netSalary = netSalary;
	}


	public String getPayrollMonth() {
		return payrollMonth;
	}


	public void setPayrollMonth(String payrollMonth) {
		this.payrollMonth = payrollMonth;
	}


	public static double getHraPercent() {
		return HRA_PERCENT;
	}


	public static double getDaPercent() {
		return DA_PERCENT;
	}


	public static double getPfPercent() {
		return PF_PERCENT;
	}


	public static double getProfessionalTex1() {
		return PROFESSIONAL_TEX;
	}


	@Override
	public String toString() {
		return "Salary [salaryId=" + salaryId + ", employeeId=" + employeeId + ", basicSalary=" + basicSalary + ", hra="
				+ hra + ", da=" + da + ", allowance=" + allowance + ", pf=" + pf + ", professionalTex="
				+ professionalTex + ", otherDeduction=" + otherDeduction + ", grossSalary=" + grossSalary
				+ ", totalDeduction=" + totalDeduction + ", netSalary=" + netSalary + ", payrollMonth=" + payrollMonth
				+ "]";
	}
	
	
}
