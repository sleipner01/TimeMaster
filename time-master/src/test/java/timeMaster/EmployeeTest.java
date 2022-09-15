package timeMaster;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class EmployeeTest {
    
    String testName = "testName";
    Employee employee;

    @Before
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
