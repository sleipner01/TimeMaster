package timeMaster;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmployeeTest {
    
    String testName = "testName";
    Employee employee1;
    Employee employee2;
    Workday workday1;
    Workday workday2;

    // TODO: Remove id-s when employee class ID function is done
    @BeforeEach
    public void createEmployee() {
        employee1 = new Employee("0", testName);
        employee2 = new Employee("1", testName);

    }

    @BeforeEach
    public void createWorkdays() {
        workday1 = new Workday(LocalDate.parse("2022-09-19"), LocalTime.parse("01:02"));
        workday2 = new Workday(LocalDate.parse("2022-09-20"), LocalTime.parse("06:00"));
    }

    @Test
    public void nameTest() {
        assertTrue(employee1.getName().equals(testName));
    } 

    @Test
    public void idTest() {
        assertNotEquals(employee1.getId(), employee2.getId());
    }

    @Test
    public void addWorkdaysTest() {
        employee1.addWorkday(workday1);
        assertTrue(employee1.getWorkdays().size() == 1);
        assertTrue(employee1.getWorkdays().get(0).equals(workday1));
        employee1.addWorkday(workday2);
        assertTrue(employee1.getWorkdays().get(1).equals(workday2));
    } 

    @Test
    public void addDuplicateWorkdayTest() {
        employee1.addWorkday(workday1);
        assertThrows(IllegalArgumentException.class, () -> { employee1.addWorkday(workday1); });
    }

}
