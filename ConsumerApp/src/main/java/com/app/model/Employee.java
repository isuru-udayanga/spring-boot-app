package com.app.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = Employee.class)
public class Employee {

    private Integer id;
    private String name;
    private String address;
    private Double salary;
    private String type;

    public Employee() {
    }

    public Employee(Integer id, String name, String address, Double salary, String type) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.salary = salary;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Employee { id:" + id + ", name:" + name + ", address:" + address + ", salary:" + salary + ", type:" + type + "}";
    }
}
