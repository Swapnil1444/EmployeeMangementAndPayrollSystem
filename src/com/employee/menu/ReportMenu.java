package com.employee.menu;

import com.employee.file.BackupUtil;
import com.employee.file.AppBackup;
import com.employee.model.Attendance;
import com.employee.model.Employee;
import com.employee.model.Payroll;
import com.employee.report.ReportGenerator;
import com.employee.service.AttendanceService;
import com.employee.service.EmployeeService;
import com.employee.service.PayrollService;
import com.employee.util.InputUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ReportMenu {

    private final EmployeeService employeeService = new EmployeeService();
    private final AttendanceService attendanceService = new AttendanceService();
    private final PayrollService payrollService = new PayrollService();
    private final ReportGenerator reportGenerator = new ReportGenerator();
    private final BackupUtil backupUtil = new BackupUtil();

    public void show() {
        boolean back = false;
        while (!back) {
        	System.out.println("\n-------------------------------------");

            System.out.println("------------- REPORTS ---------------");
        	System.out.println("-------------------------------------");

            System.out.println("1. All Employees Report");
            System.out.println("2. Department-wise Employees Report");
            System.out.println("3. Attendance Report (by date)");
            System.out.println("4. Monthly Payroll Report");
            System.out.println("5. Salary Summary");
            System.out.println("6. Backup Application Data");
            System.out.println("7. Restore From Backup");
            System.out.println("0. Back to Main Menu");
        	System.out.println("-------------------------------------");

            int choice = InputUtil.readInt("Enter choice: ");
        	System.out.println("-------------------------------------");

            try {
                switch (choice) {
                    case 1: allEmployees(); break;
                    case 2: departmentWise(); break;
                    case 3: attendanceByDate(); break;
                    case 4: monthlyPayroll(); break;
                    case 5: salarySummary(); break;
                    case 6: backup(); break;
                    case 7: restore(); break;
                    case 0: back = true; break;
                    default: System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            if (!back) InputUtil.pressEntarToContinue();
        }
    }

    private void allEmployees() {
        List<Employee> employees = employeeService.getAllEmployee();
        System.out.println(reportGenerator.allEmployeesReport(employees));
    }

    private void departmentWise() {
        List<Employee> employees = employeeService.getAllEmployee();
        Map<String, List<Employee>> grouped = employeeService.groupByDepartment(employees);
        System.out.println(reportGenerator.departmentWiseReport(grouped));
    }

    private void attendanceByDate() {
        LocalDate date = InputUtil.readDate("Date");
        List<Attendance> records = attendanceService.getAttendanceByDate(date);
        System.out.println(reportGenerator.attendanceReport(records));
    }

    private void monthlyPayroll() {
        String month = InputUtil.readString("Payroll month (yyyy-MM): ");
        List<Payroll> records = payrollService.getMonthlyPayrollReport(month);
        System.out.println(reportGenerator.monthlyPayrollReport(records, month));
    }

    private void salarySummary() {
        List<Employee> employees = employeeService.getAllEmployee();
        double total = employeeService.totalSalaryExpense(employees);
        System.out.println(reportGenerator.salarySummary(total, employees.size()));
    }

    private void backup() {
        List<Employee> employees = employeeService.getAllEmployee();
        String path = backupUtil.backup(new AppBackup(employees));
        System.out.println("Backup saved to: " + path);
    }

    private void restore() {
        AppBackup backup = backupUtil.restore();
        if (backup == null) {
            System.out.println("No backup file found.");
        } else {
            System.out.println("Backup from " + backup.getBackupTime() +
                    " contains " + backup.getEmployees().size() + " employee record(s).");
        }
    }
}
