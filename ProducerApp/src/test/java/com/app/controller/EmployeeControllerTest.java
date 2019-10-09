package com.app.controller;

import java.util.ArrayList;
import java.util.List;
import com.app.Exception.EmployeeNotFoundException;
import com.app.model.Employee;
import com.app.queue.RabbitMQSender;
import com.app.service.EmployeeService;
import com.app.service.EmployeeServiceImpl;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

class EmployeeControllerTest {

  private Logger logger = Logger.getLogger(EmployeeControllerTest.class);
  private EmployeeService employeeService;
  private EmployeeController employeeController;
  private List<Employee> employeesToDelete;

  @BeforeEach
  void setUp() {
    employeeService = Mockito.mock(EmployeeService.class);
    MyRabbitMQSender rabbitMQSender = new MyRabbitMQSender();
    employeeController = new EmployeeController(employeeService, rabbitMQSender);

    Employee employee1 = new Employee(1, "John-delete", "NYL", 30000.0, "");
    Employee employee2 = new Employee(2, "Dave-delete", "Boston", 20000.0, "");
    employeesToDelete = new ArrayList<>();
    employeesToDelete.add(employee1);
    employeesToDelete.add(employee2);
  }

  @Test
  void testGetEmployee() throws EmployeeNotFoundException {
    Employee employee = new Employee(1, "John", "NYL", 30000.0, "");
    Mockito.when(employeeService.getEmployee(1)).thenReturn(employee);
    ResponseEntity responseEntity = employeeController.getEmployee(1);
    Assertions.assertEquals(200, responseEntity.getStatusCode().value());
  }

  @Test
  void testGetEmployees() {
    Employee employee1 = new Employee(1, "John", "NYL", 30000.0, "");
    Employee employee2 = new Employee(2, "Dave", "Boston", 20000.0, "");
    List<Employee> employees = new ArrayList<>();
    employees.add(employee1);
    employees.add(employee2);
    Mockito.when(employeeService.getEmployees()).thenReturn(employees);
    ResponseEntity responseEntity = employeeController.getEmployees();
    List<Employee> response = (ArrayList<Employee>)responseEntity.getBody();
    Assertions.assertEquals(200, responseEntity.getStatusCode().value());
    Assertions.assertEquals(2, response.size());
  }

  @Test
  void testCreateEmployee() {
    Employee employee = new Employee(1, "John", "NYL", 30000.0, "");
    ResponseEntity responseEntity = employeeController.createEmployee(employee);
    Assertions.assertEquals(200, responseEntity.getStatusCode().value());
  }

  @Test
  void testUpdateEmployee() {
    Employee employee = new Employee(1, "John", "NYL", 30000.0, "");
    ResponseEntity responseEntity = employeeController.updateEmployee(employee);
    Assertions.assertEquals(200, responseEntity.getStatusCode().value());
  }

  @Test
  void testDeleteEmployee() {
    MyRabbitMQSender rabbitMQSender = new MyRabbitMQSender();
    MyEmployeeService employeeService = new MyEmployeeService();
    EmployeeController employeeController = new EmployeeController(employeeService, rabbitMQSender);
    Assertions.assertEquals(2, employeesToDelete.size());
    ResponseEntity responseEntity = employeeController.deleteEmployee(1);
    Assertions.assertEquals(200, responseEntity.getStatusCode().value());
    Assertions.assertEquals(1, employeesToDelete.size());
  }

  private class MyRabbitMQSender extends RabbitMQSender {
    @Override public void send(Employee employee) {
      logger.info("Message enqueued");
    }
  }

  private class MyEmployeeService extends EmployeeServiceImpl {
    @Override public String deleteEmployee(Integer id) {
      employeesToDelete.remove((int)id);
      return "Deleted Employee";
    }
  }
}
