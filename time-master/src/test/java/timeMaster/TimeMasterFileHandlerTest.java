package timeMaster;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class TimeMasterFileHandlerTest {
    
    private Path saveDirPath = Paths.get(System.getProperty("user.dir"), "lagring");
    private TimeMasterFileHandler timeMasterFileHandler = new TimeMasterFileHandler(saveDirPath);
    private ArrayList<Employee> employees;
    

    @BeforeEach
    public void setup() {
        employees = new ArrayList<Employee>();
        Employee emp1 = new Employee("Anna");
        Employee emp2 = new Employee("Bernt");
        Employee emp3 = new Employee("Claus");
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
