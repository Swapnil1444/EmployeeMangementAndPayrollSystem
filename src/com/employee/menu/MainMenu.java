package com.employee.menu;

import com.employee.model.User;
import com.employee.service.LoginService;
import com.employee.util.InputUtil;

public class MainMenu {

	private final EmployeeMenu employeeMenu = new EmployeeMenu();
    private final DepartmentMenu departmentMenu = new DepartmentMenu();
    private final AttendanceMenu attendanceMenu = new AttendanceMenu();
    private final PayrollMenu payrollMenu = new PayrollMenu();
    private final ReportMenu reportMenu = new ReportMenu();
    private final LoginService loginService = new LoginService();
    
    
	public boolean show(User currentUser) {
		 System.out.println("\n========== MAIN MENU (" + currentUser.getUserName() + ") ==========");
	        System.out.println("1. Employee Management");
	        System.out.println("2. Department Management");
	        System.out.println("3. Attendance Management");
	        System.out.println("4. Payroll Processing");
	        System.out.println("5. Reports");
	        System.out.println("6. Logout");
	        System.out.println("0. Exit Application");
        	System.out.println("-------------------------------------");
	        int choice = InputUtil.readInt("Enter choice: ");
        	//System.out.println("-------------------------------------");

	        switch (choice) {
            case 1: employeeMenu.show(); break;
            case 2: departmentMenu.show(); break;
            case 3: attendanceMenu.show(); break;
            case 4: payrollMenu.show(); break;
            case 5: reportMenu.show(); break;
            case 6:
                loginService.logout(currentUser);
                return false; // signals controller to go back to login screen
            case 0:
                System.out.println("Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice.");
        }
	        
        return true;
	}

}
