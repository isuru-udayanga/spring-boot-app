package com.app.service;

import java.util.List;
import com.app.Exception.EmployeeNotFoundException;
import com.app.model.Employee;
import com.app.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  @Autowired
  private EmployeeRepository employeeRepository;

  @Override public Employee getEmployee(Integer id) throws EmployeeNotFoundException {
    return employeeRepository.getEmployee(id);
  }

  @Override public List<Employee> getEmployees() {
    return employeeRepository.getEmployees();
  }

  @Override public String deleteEmployee(Integer id) {
    return employeeRepository.deleteEmployee(id);
  }
}
