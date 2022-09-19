package timeMaster;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmployeeTest {
    
    String testName = "testName";
    Employee employee1;
    Employee employee2;

    @BeforeEach
    public void createEmployee() {
        employee1 = new Employee("0", testName);
        employee2 = new Employee("1", testName);
    }

    @Test
    public void nameTest() {
        assertTrue(employee1.getName().equals(testName));
    } 

    // TODO: finish test
    @Test
    public void addWorkdaysTest() {
        assertTrue(true);
    } 

    // TODO: finish test
    @Test
    public void getWorkdaysTest() {
        assertTrue(employee1.getName().equals(testName));
    } 

}
