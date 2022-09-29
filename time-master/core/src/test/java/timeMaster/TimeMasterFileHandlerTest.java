package timeMaster;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class TimeMasterFileHandlerTest {
    
    private Path saveDirPath = Paths.get(System.getProperty("user.dir"), "timeMasterSaveFiles");
    private TimeMasterFileHandler timeMasterFileHandler = new TimeMasterFileHandler(saveDirPath);
    private ArrayList<Employee> employees;
    

    @BeforeEach
    public void setup() {
        employees = new ArrayList<Employee>();
        Employee emp1 = new Employee("0", "Anna");
        Employee emp2 = new Employee("1", "Bernt");
        Employee emp3 = new Employee("2", "Claus");
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
    }

    @Test
    @DisplayName("Test skrive til og lese fra fil")
    public void testWriteToReadFromFile() {
        timeMasterFileHandler.writeEmployees(employees);
        ArrayList<Employee> readEmployees = timeMasterFileHandler.readEmployees();
        assertEquals(employees.get(0).getName(), readEmployees.get(0).getName());
        assertEquals(employees.get(1).getName(), readEmployees.get(1).getName());
        assertEquals(employees.get(2).getName(), readEmployees.get(2).getName());
    }

}
