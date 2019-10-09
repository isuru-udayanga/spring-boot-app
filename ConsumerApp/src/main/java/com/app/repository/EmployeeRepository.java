package com.app.repository;

import com.app.model.Employee;

public interface EmployeeRepository {

    String addEmployee(Employee employee);

    String updateEmployee(Employee employee);
}
