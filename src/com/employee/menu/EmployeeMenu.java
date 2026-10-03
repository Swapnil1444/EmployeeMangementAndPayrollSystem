package com.employee.menu;

import com.employee.exception.EmployeeNotFoundException;
import com.employee.exception.InvalidInputException;
import com.employee.exception.InvalidSalaryException;
import com.employee.file.FileExportUtil;
import com.employee.model.Deparment;
import com.employee.model.Employee;
import com.employee.model.Gender;
import com.employee.service.DepartmentService;
import com.employee.service.EmployeeService;
import com.employee.util.DateUtil;
import com.employee.util.InputUtil;

import java.util.List;

public class EmployeeMenu {

	private final EmployeeService employeeService = new EmployeeService();
	private final DepartmentService departmentService = new DepartmentService();
	private final FileExportUtil fileExportUtil = new FileExportUtil();

	public void show() {
		boolean back = false;
		while (!back) {
			System.out.println("\n-------------------------------------");

			System.out.println("-------- EMPLOYEE MANAGEMENT --------");
			System.out.println("-------------------------------------");

			System.out.println("1. Add Employee");
			System.out.println("2. Update Employee");
			System.out.println("3. Delete Employee");
			System.out.println("4. Search by ID");
			System.out.println("5. Search by Name");
			System.out.println("6. Search by Department");
			System.out.println("7. Display All Employees");
			System.out.println("8. Sort Employees");
			System.out.println("9. Count Employees");
			System.out.println("10. Export Employee Data (CSV)");
			System.out.println("0. Back to Main Menu");
			System.out.println("-------------------------------------");

			int choice = InputUtil.readInt("Enter choice: ");
			System.out.println("-------------------------------------");

			try {
				switch (choice) {
				case 1:
					addEmployee();
					break;
				case 2:
					updateEmployee();
					break;
				case 3:
					deleteEmployee();
					break;
				case 4:
					searchById();
					break;
				case 5:
					searchByName();
					break;
				case 6:
					searchByDepartment();
					break;
				case 7:
					displayAll();
					break;
				case 8:
					sortMenu();
					break;
				case 9:
					System.out.println("Total employees: " + employeeService.countEmployees());
					break;
				case 10:
					exportCsv();
					break;
				case 0:
					back = true;
					break;
				default:
					System.out.println("Invalid choice.");
				}
			} catch (InvalidInputException | InvalidSalaryException | EmployeeNotFoundException e) {
				System.out.println("Error: " + e.getMessage());
			} catch (Exception e) {
				System.out.println("Unexpected error: " + e.getMessage());
			}
			if (!back)
				InputUtil.pressEntarToContinue();
		}
	}

	private Deparment pickDepartment() {
		List<Deparment> depts = departmentService.getAll();
		if (depts.isEmpty()) {
			throw new InvalidInputException("No departments exist yet. Please add a department first.");
		}
		System.out.println("Available departments:");
		for (Deparment d : depts) {
			System.out.println("  [" + d.getDept_Id() + "] " + d.getDept_Name());
		}
		int id = InputUtil.readInt("Enter department ID: ");
		Deparment chosen = departmentService.getById(id);
		if (chosen == null) {
			throw new InvalidInputException("No department with ID: " + id);
		}
		return chosen;
	}

	private void addEmployee() throws InvalidSalaryException {
		String first = InputUtil.readString("First name: ");
		String last = InputUtil.readString("Last name: ");
		String email = InputUtil.readString("Email: ");
		String phone = InputUtil.readString("Phone (10 digits): ");
		String address = InputUtil.readOptionalString("Address: ");
		char g = InputUtil.readChar("Gender (M/F/O): ");
		Gender gender = g == 'M' || g == 'm' ? Gender.male : g == 'F' || g == 'f' ? Gender.female : Gender.other;
		Deparment dept = pickDepartment();
		String designation = InputUtil.readOptionalString("Designation: ");
		java.time.LocalDate joiningDate = InputUtil.readDate("Joining date");
		double basicSalary = InputUtil.readDouble("Basic salary: ");

		Employee emp = new Employee(first, last, email, phone, address, gender, dept, designation, joiningDate,
				basicSalary);
		int id = employeeService.addEmployee(emp);
		System.out.println("Employee added successfully with ID: " + id);
	}

	private void updateEmployee() throws EmployeeNotFoundException, InvalidSalaryException {
		int id = InputUtil.readInt("Enter employee ID to update: ");
		Employee emp = employeeService.getEmployeeOrThrow(id);
		System.out.println("Current details: " + emp);

		emp.setPhoneNo(InputUtil.readString("New phone: "));
		emp.setAddress(InputUtil.readOptionalString("New address: "));
		emp.setDestination(InputUtil.readOptionalString("New designation: "));
		emp.setBaseSalary(InputUtil.readDouble("New basic salary: "));

		employeeService.updateEmployee(emp);
		System.out.println("Employee updated successfully.");
	}

	private void deleteEmployee() throws EmployeeNotFoundException {
		int id = InputUtil.readInt("Enter employee ID to delete: ");
		employeeService.deleteEmployee(id);
		System.out.println("Employee deleted successfully.");
	}

	private void searchById() throws EmployeeNotFoundException {
		int id = InputUtil.readInt("Enter employee ID: ");
		System.out.println(employeeService.getEmployeeOrThrow(id));
	}

	private void searchByName() {
		String name = InputUtil.readString("Enter name (or part of it): ");
		printList(employeeService.searchByName(name));
	}

	private void searchByDepartment() {
		Deparment dept = pickDepartment();
		printList(employeeService.searchByDepartment(dept.getDept_Id()));
	}

	private void displayAll() {
		printList(employeeService.getAllEmployee());
	}

	private void sortMenu() {
		List<Employee> all = employeeService.getAllEmployee();
		System.out.println("Sort by: \n 1=ID \n 2=Name \n 3=Salary(desc) \n 4=Joining Date");
		int c = InputUtil.readInt("Choice: ");
		List<Employee> sorted;
		switch (c) {
		case 2:
			sorted = employeeService.sortByName(all);
			break;
		case 3:
			sorted = employeeService.sortBySalaryDesc(all);
			break;
		case 4:
			sorted = employeeService.sortByJoiningDate(all);
			break;
		default:
			sorted = employeeService.sortById(all);
		}
		printList(sorted);
	}

	private void exportCsv() {
		List<Employee> all = employeeService.getAllEmployee();
		String fileName = "employees_" + System.currentTimeMillis() + ".csv";
		String path = fileExportUtil.exportEmployeesToCsv(all, fileName);
		System.out.println("Exported to: " + path);
	}

	private void printList(List<Employee> list) {
		if (list.isEmpty()) {
			System.out.println("No employees found.");
			return;
		}
		for (Employee e : list) {
			System.out.printf("[%d] %-20s Dept: %-15s Desig: %-15s Joined: %-12s Salary: %.2f Status: %s%n",
					e.getEmpId(), e.getFullName(), e.getDept() != null ? e.getDept().getDept_Name() : "N/A",
					e.getDestination(), DateUtil.formatDate(e.getJoinDate()), e.getBaseSalary(), e.getEmpStatus());
		}
	}
}
