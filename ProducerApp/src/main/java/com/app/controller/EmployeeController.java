package com.app.controller;

import com.app.Exception.EmployeeNotFoundException;
import com.app.model.Employee;
import com.app.queue.RabbitMQSender;
import com.app.service.EmployeeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private Logger logger = Logger.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired RabbitMQSender rabbitMQSender;

    public EmployeeController(EmployeeService employeeService, RabbitMQSender rabbitMQSender) {
        this.employeeService = employeeService;
        this.rabbitMQSender = rabbitMQSender;
    }

    @GetMapping("/{id}")
    ResponseEntity getEmployee(@PathVariable Integer id) {
        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.NOT_FOUND);
        try {
            responseEntity = ResponseEntity.ok(employeeService.getEmployee(id));
        } catch (EmployeeNotFoundException e) {
            logger.error("Error occurring while getting employee");
        }
        return responseEntity;
    }

    @GetMapping("/") ResponseEntity getEmployees() {
        return ResponseEntity.ok(employeeService.getEmployees());
    }

    @PostMapping("/")
    ResponseEntity createEmployee(@RequestBody Employee employee) {
        employee.setType("save");
        rabbitMQSender.send(employee);
        return ResponseEntity.ok("Message sent for SAVE to the RabbitMQ Successfully");
    }

    @PutMapping("/")
    ResponseEntity updateEmployee(@RequestBody Employee employee) {
        employee.setType("update");
        rabbitMQSender.send(employee);
        return ResponseEntity.ok("Message sent for UPDATE to the RabbitMQ Successfully");
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteEmployee(@PathVariable Integer id) {
        return ResponseEntity.ok(employeeService.deleteEmployee(id));
    }
}
