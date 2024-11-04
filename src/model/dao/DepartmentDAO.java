package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDAO {
	
	void insert(Department departmentObj);
	void update(Department departmentObj);
	void deleteById(Integer id);
	Department findById(Integer id);
	List<Department> findAll();
	
}
