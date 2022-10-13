package timeMaster.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TimeMasterJsonParserTest {
  
  TimeMasterJsonParser jsonParser;
  Path path;
  File file;
  String fileName = "employeesTest.json";
  ArrayList<Employee> employees;
  
  @BeforeEach
  public void setup() {
    path = Paths.get(System.getProperty("user.dir"), "timeMasterSaveFiles");
    file = new File(path.toString(), fileName);
    jsonParser = new TimeMasterJsonParser(path, fileName);
    employees = new ArrayList<Employee>();
  }
  
  @AfterEach
  public void cleanUp() {
    employees.clear();
    file.delete();
  }
  
  @Test
  public void writeToJSON() {
    employees.add(new Employee("Anna"));
    jsonParser.write(employees);
    assertNotEquals(String.valueOf(0), String.valueOf(file.length()));
  }
  
  @Test
  public void readFromJSON() {
    employees.add(new Employee("0", "Anna"));
    employees.add(new Employee("0", "Bernt"));
    employees.add(new Employee("0", "Claus"));
    jsonParser.write(employees);
    ArrayList<Employee> newEmployees = jsonParser.read();
    for (int i = 0; i < employees.size(); i++) {
      assertEquals(employees.get(i).getName(), newEmployees.get(i).getName());
    }
  }
  
}
