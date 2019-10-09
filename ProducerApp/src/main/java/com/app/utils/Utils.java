package com.app.utils;

import com.app.model.Employee;
import org.bson.Document;

public class Utils {

  public static Employee convertDocumentToObject(Document document) {
    Employee employee = new Employee();
    employee.setId((Integer) document.get("EmpId"));
    employee.setName((String) document.get("EmpName"));
    employee.setAddress((String) document.get("EmpAddress"));
    employee.setSalary((Double) document.get("EmpSalary"));
    return employee;
  }
}
