package com.app.repository;

import com.app.model.Employee;
import com.app.mongo.MongoInitializer;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    private static String DATABASE = "Payroll";
    private static String COLLECTION = "Employee";
    private MongoCollection mongoCollection;

    public EmployeeRepositoryImpl() {
        mongoCollection = new MongoInitializer().getMongoCollection(DATABASE, COLLECTION);
    }

    @Override public String addEmployee(Employee employee) {
        Document document = new Document();
        document.append("EmpId", employee.getId());
        document.append("EmpName", employee.getName());
        document.append("EmpAddress", employee.getAddress());
        document.append("EmpSalary", employee.getSalary());
        mongoCollection.insertOne(document);
        return "Successfully inserted EmpId=" + employee.getId();
    }

    @Override public String updateEmployee(Employee employee) {
        Bson filter = Filters.eq("EmpId", employee.getId());
        Document document = new Document();
        document.append("EmpName", employee.getName());
        document.append("EmpAddress", employee.getAddress());
        document.append("EmpSalary", employee.getSalary());
        Bson updateDoc = new Document("$set", document);
        mongoCollection.updateOne(filter, updateDoc);
        return "Successfully updated EmpId=" + employee.getId();
    }
}
