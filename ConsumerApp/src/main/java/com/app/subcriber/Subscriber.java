package com.app.subcriber;

import java.io.IOException;
import com.app.model.Employee;
import com.app.repository.EmployeeRepository;
import com.app.repository.EmployeeRepositoryImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Subscriber {

  private Logger logger = Logger.getLogger(Subscriber.class);

  private EmployeeRepository employeeRepository = new EmployeeRepositoryImpl();

  @RabbitListener(queues="${spring.rabbitmq.queue}")
  public void receivedMessage(@Payload Message msg) throws IOException {
	    ObjectMapper mapper = new ObjectMapper();
	    Employee employee = mapper.readValue(msg.getBody(), Employee.class);
	    logger.info("Received Message: " + employee.toString());
      if (employee.getType().equals("save")) {
         logger.info(employeeRepository.addEmployee(employee));
      } else {
         logger.info(employeeRepository.updateEmployee(employee));
      }
    }
}