package no.it1901.groups2022.gr2227.timemaster.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TimeMasterJsonParserTest {
  
  TimeMasterJsonParser jsonParser;
  ArrayList<Employee> employees;
  
  @BeforeEach
  public void setup() {
    jsonParser = new TimeMasterJsonParser();
    employees = new ArrayList<Employee>();
  }
  
  @AfterEach
  public void cleanUp() {
    employees.clear();
  }
  
  @Test
  public void writeTest() {
    employees.add(new Employee("Anna"));
    assertDoesNotThrow(() -> jsonParser.write(employees));
  }
  
  @Test
  public void readEmployeeTest() {
    employees.add(new Employee("0", "Anna"));
    employees.add(new Employee("0", "Bernt"));
    employees.add(new Employee("0", "Claus"));

    for (Employee employee : employees) {
      assertDoesNotThrow(() -> jsonParser.readEmployee(jsonParser.write(employee)));
    }
  }

  @Test
  public void readEmployeesTest() {
    employees.add(new Employee("0", "Anna"));
    employees.add(new Employee("0", "Bernt"));
    employees.add(new Employee("0", "Claus"));
    assertDoesNotThrow(() -> jsonParser.readEmployees(jsonParser.write(employees)));
  }

  @Test
  public void readWorkdaysTest() {
    employees.add(new Employee("0", "Anna"));
    employees.add(new Employee("0", "Bernt"));
    employees.add(new Employee("0", "Claus"));

    for (Employee employee : employees) {
      assertDoesNotThrow(() -> jsonParser.readWorkdays(jsonParser.write(employee.getWorkdays())));
    }
  }
  
}
