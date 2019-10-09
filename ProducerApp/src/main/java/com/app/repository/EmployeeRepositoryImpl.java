package com.app.repository;

import java.util.ArrayList;
import java.util.List;
import com.app.Exception.EmployeeNotFoundException;
import com.app.model.Employee;
import com.app.mongo.MongoInitilizer;
import com.app.utils.Utils;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

  private static String DATABASE = "Payroll";
  private static String COLLECTION = "Employee";
  private MongoCollection mongoCollection;

  public EmployeeRepositoryImpl() {
    mongoCollection = new MongoInitilizer().getMongoCollection(DATABASE, COLLECTION);
  }

  @Override public Employee getEmployee(Integer id) throws EmployeeNotFoundException {
    Document employeeDoc = null;
    Bson filter = Filters.eq("EmpId", id);
    FindIterable findIterable = mongoCollection.find(filter);
    MongoCursor mongoCursor = findIterable.iterator();
    if (mongoCursor.hasNext()) {
      employeeDoc = (Document) mongoCursor.next();
    }
    if (employeeDoc == null) {
      throw new EmployeeNotFoundException("");
    } else {
      return Utils.convertDocumentToObject(employeeDoc);
    }
  }

  @Override public List<Employee> getEmployees() {
    List<Employee> employees = new ArrayList<>();
    FindIterable findIterable = mongoCollection.find();
    for (Object object : findIterable) {
      Employee employee = Utils.convertDocumentToObject((Document) object);
      employees.add(employee);
    }
    return employees;
  }

  @Override public String deleteEmployee(Integer id) {
    Bson filter = Filters.eq("EmpId", id);
    mongoCollection.findOneAndDelete(filter);
    return "Successfully deleted EmpId=" + id;
  }


}
