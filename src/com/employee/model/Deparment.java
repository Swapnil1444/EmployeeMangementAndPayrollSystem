package com.employee.model;

public class Deparment {

	private int dept_Id;
	private String dept_Name;
	private String dept_Desc;

	public Deparment() {

	}

	public Deparment(String dept_Name, String dept_Desc) {
		this.dept_Name = dept_Name;
		this.dept_Desc = dept_Desc;
	}

	public Deparment(int dept_Id, String dept_Name, String dept_Desc) {
		this.dept_Id = dept_Id;
		this.dept_Name = dept_Name;
		this.dept_Desc = dept_Desc;
	}

	public int getDept_Id() {
		return dept_Id;
	}

	public void setDept_Id(int dept_Id) {
		this.dept_Id = dept_Id;
	}

	public String getDept_Name() {
		return dept_Name;
	}

	public void setDept_Name(String dept_Name) {
		this.dept_Name = dept_Name;
	}

	public String getDept_Desc() {
		return dept_Desc;
	}

	public void setDept_Desc(String dept_Desc) {
		this.dept_Desc = dept_Desc;
	}

	
	@Override
	public String toString() {
	    return "Department {" +
	           "\n  Department ID          : " + dept_Id +
	           "\n  Department Name        : " + dept_Name +
	           "\n  Department Description : " + dept_Desc +
	           "\n}";
	}

	
	

}
