package com.employee.menu;

import java.util.List;

import com.employee.model.Deparment;
import com.employee.service.DepartmentService;
import com.employee.util.InputUtil;

public class DepartmentMenu {

	private final DepartmentService departmentService = new DepartmentService();

	public void show() {
		boolean back = false;
		while (!back) {
			System.out.println("\n-------------------------------------");

			System.out.println("------- DEPARTMENT MANAGEMENT -------");
			System.out.println("-------------------------------------");

			System.out.println("1. Add Department");
			System.out.println("2. Update Department");
			System.out.println("3. Delete Department");
			System.out.println("4. Search Department");
			System.out.println("5. Display All Departments");
			System.out.println("0. Back to Main Menu");
			System.out.println("-------------------------------------");

			int choice = InputUtil.readInt("Enter choice: ");
			System.out.println("-------------------------------------");

			try {
				switch (choice) {
				case 1:
					addDepartment();
					break;
				case 2:
					updateDepartment();
					break;
				case 3:
					deleteDepartment();
					break;
				case 4:
					searchDepartment();
					break;
				case 5:
					displayAll();
					break;
				case 0:
					back = true;
					break;
				default:
					System.out.println("Invalid choice.");
				}
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
			if (!back)
				InputUtil.pressEntarToContinue();
		}
	}

	private void addDepartment() {
		String name = InputUtil.readString("Department name: ");
		String desc = InputUtil.readOptionalString("Description: ");
		int id = departmentService.addDeparment(name, desc);
		System.out.println("Department added with ID: " + id);
	}

	private void displayAll() {
		printList(departmentService.getAll());
	}

	private void printList(List<Deparment> list) {
		if (list.isEmpty()) {
			System.out.println("No departments found.");
			return;
		}
		for (Deparment d : list) {
			System.out.printf("[%d] %-20s %s%n", d.getDept_Id(), d.getDept_Name(), d.getDept_Desc());
		}
	}

	private void updateDepartment() {
		int id = InputUtil.readInt("Department ID to update: ");
		Deparment dept = departmentService.getById(id);
		if (dept == null) {
			System.out.println("No department found with ID: " + id);
			return;
		}
		dept.setDept_Name(InputUtil.readString("New name: "));
		dept.setDept_Desc(InputUtil.readOptionalString("New description: "));
		boolean ok = departmentService.updateDepartment(dept);
		System.out.println(ok ? "Department updated." : "Update failed.");
	}

	private void deleteDepartment() {
		int id = InputUtil.readInt("Department ID to delete: ");
		boolean ok = departmentService.deleteDepartment(id);
		System.out.println(ok ? "Department deleted." : "Delete failed (it may still have employees assigned).");
	}

	private void searchDepartment() {
		String name = InputUtil.readString("Search by name: ");
		printList(departmentService.search(name));
	}

}
