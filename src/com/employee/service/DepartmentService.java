package com.employee.service;

import java.util.List;

import com.employee.dao.DepartmentDAO;
import com.employee.file.LogUtil;
import com.employee.model.Deparment;
import com.employee.util.Validation;

public class DepartmentService {

	private final DepartmentDAO departmentDAO = new DepartmentDAO();

	public Deparment getById(int deptId) {
		return departmentDAO.findById(deptId);
	}

	public List<Deparment> getAll() {
		return departmentDAO.findAll();
	}

	public List<Deparment> getByName(String name) {
		return departmentDAO.searchByName(name);
	}

	public int addDeparment(String name, String description) {
		Validation.requireNonBlank(name, "Decription name");
		int id = departmentDAO.insert(new Deparment(name, description));
		LogUtil.info("Deparmenat add:" + name + " (id:" + id + ")");
		return id;

	}

	public boolean updateDepartment(Deparment dept) {
		Validation.requireNonBlank(dept.getDept_Name(), "Daparment name");
		boolean ok = departmentDAO.update(dept);
		LogUtil.info("Deparment update attempt id=" + dept.getDept_Id() + "success=" + ok);
		return ok;
	}

	public boolean deleteDepartment(int deptId) {
		boolean ok = departmentDAO.delete(deptId);
		LogUtil.info("Deparment delete attempt id:" + deptId + " success=" + ok);
		return ok;
	}

	public List<Deparment> search(String name) {
		return departmentDAO.searchByName(name);
	}

}
