package com.employee.model;

import java.time.LocalDate;
import java.time.Period;

public class Employee {

	private int empId;
	private String firstName;
	private String lastName;
	private String email;
	private long phoneNo;
	private	String address;
	private Gender gender;
	private Deparment dept;
	private String destination;
	private LocalDate joinDate;
	private double baseSalary;
	private EmployeeStatus empStatus;

	public Employee(String firstName, String lastName, String email, long phoneNo, String address, Gender gender,
			Deparment dept, String destination, LocalDate joinDate, double baseSalary, EmployeeStatus empStatus) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNo = phoneNo;
		this.address = address;
		this.gender = gender;
		this.dept = dept;
		this.destination = destination;
		this.joinDate = joinDate;
		this.baseSalary = baseSalary;
		this.empStatus = empStatus.active;
	}

	public Employee(int empId, String firstName, String lastName, String email, long phoneNo, String address,
			Gender gender, Deparment dept, String destination, LocalDate joinDate, double baseSalary,
			EmployeeStatus empStatus) {

		this(firstName, lastName, email, phoneNo, address, gender, dept, destination, joinDate, baseSalary, empStatus);
		this.empId = empId;
		this.empStatus = empStatus;
	}

	public EmployeeStatus getEmpStatus() {
		return empStatus;
	}

	public void setEmpStatus(EmployeeStatus empStatus) {
		this.empStatus = empStatus;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(long phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Deparment getDept() {
		return dept;
	}

	public void setDept(Deparment dept) {
		this.dept = dept;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}

	public double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(double baseSalary) {
		this.baseSalary = baseSalary;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public int getYearService() {
		if (joinDate == null) {
			return 0;

		}
		return Period.between(joinDate, LocalDate.now()).getYears();
	}


	@Override
	public String toString() {
	    return "Employee {" +
	           "\n  Employee ID     : " + empId +
	           "\n  First Name      : " + firstName +
	           "\n  Last Name       : " + lastName +
	           "\n  Email           : " + email +
	           "\n  Phone No        : " + phoneNo +
	           "\n  Address         : " + address +
	           "\n  Gender          : " + gender +
	           "\n  Department      : " + dept +
	           "\n  Designation     : " + destination +
	           "\n  Join Date       : " + joinDate +
	           "\n  Base Salary     : " + baseSalary +
	           "\n  Employee Status : " + empStatus +
	           "\n}";
	}

	
	

}
