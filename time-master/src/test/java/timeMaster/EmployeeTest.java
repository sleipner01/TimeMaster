package timeMaster;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmployeeTest {
    
    String testName = "testName";
    Employee employee;

    @BeforeEach
    public void createEmployee() {
        employee = new Employee(testName);
    }

    @Test
    public void nameTest() {
        assertTrue(employee.getName().equals(testName));
    } 

    // TODO: finish test
    @Test
    public void addWorkdaysTest() {
        assertTrue(true);
    } 

    // TODO: finish test
    @Test
    public void getWorkdaysTest() {
        assertTrue(employee.getName().equals(testName));
    } 

}
