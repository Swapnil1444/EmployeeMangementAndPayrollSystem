package com.employee.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.employee.dao.EmployeeDAO;
import com.employee.exception.EmployeeNotFoundException;
import com.employee.exception.InvalidSalaryException;
import com.employee.file.LogUtil;
import com.employee.model.Deparment;
import com.employee.model.Employee;
import com.employee.model.EmployeeStatus;
import com.employee.model.Gender;
import com.employee.util.Validation;

public class EmployeeService {
	
    private final EmployeeDAO employeeDAO = new EmployeeDAO();
    
    public List<Employee> getAllEmployee(){
    	return employeeDAO.findAll();
    }
    
    public List<Employee> searchByName(String name) {
        return employeeDAO.findByName(name);
    }

    public List<Employee> searchByDepartment(int departmentId) {
        return employeeDAO.findByDepartment(departmentId);
    }
    
    public int countEmployees() {
        return employeeDAO.countAll();
    }

    
    public int addEmployee(Employee emp) {
    	Validation.requireNonBlank(emp.getFirstName(),"frist name");
    	Validation.requireNonBlank(emp.getLastName(), "last name");
    	Validation.validEmail(emp.getEmail());
    	Validation.validPhoneNo(emp.getPhoneNo());
    	Validation.validSalary(emp.getBaseSalary());
    	
    	int id=employeeDAO.insert(emp);
        LogUtil.info("Employee added: " + emp.getFullName() + " (id=" + id + ")");

    	return id;
    }
    
    public void updateEmployee(Employee emp) throws EmployeeNotFoundException, InvalidSalaryException {
        getEmployeeOrThrow(emp.getEmpId());
        Validation.validEmail(emp.getEmail());
        Validation.validPhoneNo(emp.getPhoneNo());
        Validation.validSalary(emp.getBaseSalary());
        employeeDAO.update(emp);
        LogUtil.info("Employee updated: id=" + emp.getEmpId());
    }
    
    public void deleteEmployee(int employeeId) throws EmployeeNotFoundException {
        getEmployeeOrThrow(employeeId);
        employeeDAO.delete(employeeId);
        LogUtil.info("Employee deleted: id=" + employeeId);
    }
    
    public Employee getEmployeeOrThrow(int empId) {
		 Employee emp=employeeDAO.findById(empId);
		 if (emp == null) {
	            throw new EmployeeNotFoundException("No employee found with ID: " + empId);
	        }
	        return emp;
		
	}
    
    
    public List<Employee> sortById(List<Employee> employees) {
        List<Employee> copy = new ArrayList<>(employees);
        Collections.sort(copy);
        return copy;
    }
    
    /** Sorts by name using a Comparator (lambda). */
    public List<Employee> sortByName(List<Employee> employees) {
        List<Employee> copy = new ArrayList<>(employees);
        copy.sort(Comparator.comparing(Employee::getFullName, String.CASE_INSENSITIVE_ORDER));
        return copy;
    }
    
    /** Sorts by basic salary, descending, using a Comparator. */
    public List<Employee> sortBySalaryDesc(List<Employee> employees) {
        List<Employee> copy = new ArrayList<>(employees);
        copy.sort(Comparator.comparingDouble(Employee::getBaseSalary).reversed());
        return copy;
    }
    
    /** Sorts by joining date (oldest first). */
    public List<Employee> sortByJoiningDate(List<Employee> employees) {
        List<Employee> copy = new ArrayList<>(employees);
        copy.sort(Comparator.comparing(Employee::getJoinDate));
        return copy;
    }

    /** Returns the distinct set of department names currently in use (demonstrates Set + Iterator). */
    public Set<String> distinctDepartmentNames(List<Employee> employees) {
        Set<String> names = new HashSet<>();
        Iterator<Employee> it = employees.iterator();
        while (it.hasNext()) {
            Employee e = it.next();
            if (e.getDept() != null) {
                names.add(e.getDept().getDept_Name());
            }
        }
        return names;
    }
    
    /** Groups employees by department name (demonstrates Map usage). */
    public Map<String, List<Employee>> groupByDepartment(List<Employee> employees) {
        Map<String, List<Employee>> grouped = new HashMap<>();
        for (Employee e : employees) {
            String deptName = e.getDept() != null ? e.getDept().getDept_Name() : "Unassigned";
            grouped.computeIfAbsent(deptName, k -> new ArrayList<>()).add(e);
        }
        return grouped;
    }

    public double totalSalaryExpense(List<Employee> employees) {
        double total = 0;
        for (Employee e : employees) {
            total += e.getBaseSalary();
        }
        return total;
    }

	

    
 

}
