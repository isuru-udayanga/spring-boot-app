package com.app.repository;

import java.util.List;
import com.app.Exception.EmployeeNotFoundException;
import com.app.model.Employee;

public interface EmployeeRepository {

  Employee getEmployee(Integer id) throws EmployeeNotFoundException;

  List<Employee> getEmployees();

  String deleteEmployee(Integer id);
}
