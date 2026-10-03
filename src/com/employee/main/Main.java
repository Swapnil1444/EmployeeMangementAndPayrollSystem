package com.employee.main;

import com.employee.controller.MainController;
import com.employee.menu.AttendanceMenu;
import com.employee.menu.DepartmentMenu;
import com.employee.menu.EmployeeMenu;
import com.employee.menu.LoginMenu;
import com.employee.menu.MainMenu;
import com.employee.menu.PayrollMenu;

class Main {

	public static void main(String[] args) {

	
		MainController mainController=new MainController();
		mainController.run();
		
		//DepartmentMenu departmentMenu=new  DepartmentMenu();
		//departmentMenu.show();
		
		//EmployeeMenu employeeMenu=new  EmployeeMenu();
		//employeeMenu.show();
		
		//AttendanceMenu  attendanceMenu =new AttendanceMenu();
		//attendanceMenu.show();
		
		//PayrollMenu  menu=new PayrollMenu();
	    //menu.show();
	}

}
