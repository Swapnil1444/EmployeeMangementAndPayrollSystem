package com.employee.menu;

import com.employee.exception.EmployeeNotFoundException;
import com.employee.exception.InvalidSalaryException;
import com.employee.email.EmailService;
import com.employee.file.FileExportUtil;
import com.employee.model.Employee;
import com.employee.model.Payroll;
import com.employee.model.Salary;
import com.employee.pdf.PayslipPDFGenerator;
import com.employee.service.EmployeeService;
import com.employee.service.PayrollService;
import com.employee.util.InputUtil;

import java.io.File;
import java.util.List;

public class PayrollMenu {

    private final PayrollService payrollService = new PayrollService();
    private final EmployeeService employeeService = new EmployeeService();
    private final FileExportUtil fileExportUtil = new FileExportUtil();
    private final PayslipPDFGenerator pdfGenerator = new PayslipPDFGenerator();

    public void show() {
        boolean back = false;
        while (!back) {
        	System.out.println("\n-------------------------------------");

            System.out.println("--------- PAYROLL PROCESSING --------");
        	System.out.println("-------------------------------------");

            System.out.println("1. Process Payroll for an Employee");
            System.out.println("2. Display Payroll (employee + month)");
            System.out.println("3. Search Payroll History (by employee)");
            System.out.println("4. Generate Text Payslip");
            System.out.println("5. Generate PDF Payslip");
            System.out.println("6. Email Payslip (PDF must exist first)");
            System.out.println("7. Export Monthly Payroll Report (CSV)");
            System.out.println("0. Back to Main Menu");
        	System.out.println("-------------------------------------");

            int choice = InputUtil.readInt("Enter choice: ");
        	System.out.println("-------------------------------------");

            try {
                switch (choice) {
                    case 1: processPayroll(); break;
                    case 2: displayPayroll(); break;
                    case 3: history(); break;
                    case 4: textPayslip(); break;
                    case 5: pdfPayslip(); break;
                    case 6: emailPayslip(); break;
                    case 7: exportMonthlyReport(); break;
                    case 0: back = true; break;
                    default: System.out.println("Invalid choice.");
                }
            } catch (EmployeeNotFoundException | InvalidSalaryException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
            if (!back) InputUtil.pressEntarToContinue();
        }
    }

    private void processPayroll() throws EmployeeNotFoundException, InvalidSalaryException {
        int empId = InputUtil.readInt("Employee ID: ");
        String month = InputUtil.readString("Payroll month (yyyy-MM): ");
        double allowance = InputUtil.readDouble("Other allowances: ");
        double deduction = InputUtil.readDouble("Other deductions: ");

        Payroll payroll = payrollService.processPayroll(empId, month, allowance, deduction);
        System.out.println("Payroll processed successfully!");
        System.out.println(payroll);
    }

    private void displayPayroll() {
        int empId = InputUtil.readInt("Employee ID: ");
        String month = InputUtil.readString("Payroll month (yyyy-MM): ");
        Payroll p = payrollService.getPayroll(empId, month);
        if (p == null) {
            System.out.println("No payroll record found for that employee/month.");
        } else {
            System.out.println(p);
        }
    }

    private void history() {
        int empId = InputUtil.readInt("Employee ID: ");
        List<Payroll> list = payrollService.getPayrollHistory(empId);
        if (list.isEmpty()) {
            System.out.println("No payroll history found.");
        } else {
            list.forEach(System.out::println);
        }
    }

    private void textPayslip() throws EmployeeNotFoundException {
        int empId = InputUtil.readInt("Employee ID: ");
        String month = InputUtil.readString("Payroll month (yyyy-MM): ");
        Employee emp = employeeService.getEmployeeOrThrow(empId);
        Salary salary = payrollService.getSalaryBreakdown(empId, month);
        if (salary == null) {
            System.out.println("No salary record found. Process payroll for this month first.");
            return;
        }
        String fileName = "payslip_" + empId + "_" + month + ".txt";
        String path = fileExportUtil.generateTextPayslip(emp, salary, fileName);
        System.out.println("Text payslip generated: " + path);
    }

    private void pdfPayslip() throws EmployeeNotFoundException {
        int empId = InputUtil.readInt("Employee ID: ");
        String month = InputUtil.readString("Payroll month (yyyy-MM): ");
        Employee emp = employeeService.getEmployeeOrThrow(empId);
        Salary salary = payrollService.getSalaryBreakdown(empId, month);
        if (salary == null) {
            System.out.println("No salary record found. Process payroll for this month first.");
            return;
        }
        String fileName = "payslip_" + empId + "_" + month + ".pdf";
        String path = pdfGenerator.generatePayslipPdf(emp, salary, fileName);
        System.out.println("PDF payslip generated: " + path);
    }

    private void emailPayslip() throws EmployeeNotFoundException {
        int empId = InputUtil.readInt("Employee ID: ");
        String month = InputUtil.readString("Payroll month (yyyy-MM): ");
        Employee emp = employeeService.getEmployeeOrThrow(empId);

        File pdfFile = new File(com.employee.util.Constants.ROPORTS_DIR, "payslip_" + empId + "_" + month + ".pdf");
        if (!pdfFile.exists()) {
            System.out.println("PDF payslip not found. Generate it first (option 5).");
            return;
        }
        EmailService emailService = new EmailService();
        emailService.sendPayslip(emp, month, pdfFile);
        System.out.println("Payslip emailed to " + emp.getEmail());
    }

    private void exportMonthlyReport() {
        String month = InputUtil.readString("Payroll month (yyyy-MM): ");
        List<Payroll> list = payrollService.getMonthlyPayrollReport(month);
        String fileName = "payroll_" + month + ".csv";
        String path = fileExportUtil.exportPayrollReport(list, fileName);
        System.out.println("Exported to: " + path);
    }
}
