package com.app.service;

import java.util.List;
import com.app.Exception.EmployeeNotFoundException;
import com.app.model.Employee;

public interface EmployeeService {

  Employee getEmployee(Integer id) throws EmployeeNotFoundException;

  List<Employee> getEmployees();

  String deleteEmployee(Integer id);
}
