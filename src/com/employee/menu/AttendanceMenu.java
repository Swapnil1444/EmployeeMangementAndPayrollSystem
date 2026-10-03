package com.employee.menu;

import com.employee.file.FileExportUtil;
import com.employee.model.Attendance;
import com.employee.model.AttendanceStatus;
import com.employee.service.AttendanceService;
import com.employee.util.DateUtil;
import com.employee.util.InputUtil;

import java.time.LocalDate;
import java.util.List;

public class AttendanceMenu {

    private final AttendanceService attendanceService = new AttendanceService();
    private final FileExportUtil fileExportUtil = new FileExportUtil();

    public void show() {
        boolean back = false;
        while (!back) {
        	System.out.println("\n-------------------------------------");
            System.out.println("------- ATTENDANCE MANAGEMENT -------");
        	System.out.println("-------------------------------------");
            System.out.println("1. Mark Attendance");
            System.out.println("2. Update Attendance");
            System.out.println("3. View Employee Attendance");
            System.out.println("4. View Attendance by Date");
            System.out.println("5. Present/Absent/Leave Summary (employee + month)");
            System.out.println("6. Export Attendance Report (CSV)");
            System.out.println("0. Back to Main Menu");
        	System.out.println("-------------------------------------");

            int choice = InputUtil.readInt("Enter choice: ");
        	System.out.println("-------------------------------------");

            try {
                switch (choice) {
                    case 1: markAttendance(); break;
                    case 2: updateAttendance(); break;
                    case 3: viewByEmployee(); break;
                    case 4: viewByDate(); break;
                    case 5: summary(); break;
                    case 6: exportCsv(); break;
                    case 0: back = true; break;
                    default: System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            if (!back) InputUtil.pressEntarToContinue();
        }
    }

    private AttendanceStatus pickStatus() {
        System.out.println("Status: 1=PRESENT 2=ABSENT 3=HALF_DAY 4=LEAVE");
        int c = InputUtil.readInt("Choice: ");
        switch (c) {
            case 2: return AttendanceStatus.absent;
            case 3: return AttendanceStatus.halfDay;
            case 4: return AttendanceStatus.Leave;
            default: return AttendanceStatus.present;
        }
    }

    private void markAttendance() throws Exception {
        int empId = InputUtil.readInt("Employee ID: ");
        LocalDate date = InputUtil.readDate("Attendance date");
        AttendanceStatus status = pickStatus();
        int id = attendanceService.markAttendance(empId, date, status);
        System.out.println("Attendance marked with ID: " + id);
    }

    private void updateAttendance() throws Exception {
        int empId = InputUtil.readInt("Employee ID: ");
        LocalDate date = InputUtil.readDate("Attendance date");
        AttendanceStatus status = pickStatus();
        boolean ok = attendanceService.updateAttendace(empId, date, status);
        System.out.println(ok ? "Attendance updated." : "Update failed.");
    }

    private void viewByEmployee() {
        int empId = InputUtil.readInt("Employee ID: ");
        printList(attendanceService.getAttendanceForEmployee(empId));
    }

    private void viewByDate() {
        LocalDate date = InputUtil.readDate("Date");
        printList(attendanceService.getAttendanceByDate(date));
    }

    private void summary() {
        int empId = InputUtil.readInt("Employee ID: ");
        String month = InputUtil.readString("Payroll month (yyyy-MM): ");
        System.out.println("Present days: " + attendanceService.countPresentDays(empId, month));
        System.out.println("Absent days : " + attendanceService.countAbsentDays(empId, month));
        System.out.println("Leave days  : " + attendanceService.countLeaveDays(empId, month));
        System.out.println("Half days   : " + attendanceService.countHalfDays(empId, month));
    }

    private void exportCsv() {
        int empId = InputUtil.readInt("Employee ID: ");
        List<Attendance> records = attendanceService.getAttendanceForEmployee(empId);
        String fileName = "attendance_" + empId + "_" + System.currentTimeMillis() + ".csv";
        String path = fileExportUtil.exportAttendanceReport(records, fileName);
        System.out.println("Exported to: " + path);
    }

    private void printList(List<Attendance> list) {
        if (list.isEmpty()) {
            System.out.println("No attendance records found.");
            return;
        }
        for (Attendance a : list) {
            System.out.printf("EmployeeID: %-6d Date: %-12s Status: %s%n",
                    a.getEmpId(), DateUtil.formatDate(a.getAttendanceDate()), a.getAttendanceStatus());
        }
    }
}
