package timeMaster.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

    @BeforeEach
    public void createEmployee() {
        employee1 = new Employee(testName);
        employee2 = new Employee(testName);

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
    public void checkInTest() {
        employee1.checkIn(LocalDate.parse("1970-01-01"), LocalTime.parse("00:00"));
        assertTrue(employee1.isAtWork());
    }

    @Test
    public void checkOutTest() {
        employee1.checkIn(LocalDate.parse("1970-01-01"), LocalTime.parse("00:00"));
        employee1.checkOut(LocalTime.parse("12:00"));
        assertFalse(employee1.isAtWork());
    }

    @Test
    public void getDateTest() {
        employee1.checkIn(LocalDate.parse("1970-01-01"), LocalTime.parse("00:00"));
        assertEquals("1970-01-01", 
        employee1.getDate(LocalDate.parse("1970-01-01")).getDate().toString());
    }

    @Test
    public void getLatestClockInTest() {
        employee1.checkIn(LocalDate.parse("1970-01-01"), LocalTime.parse("00:00"));
        assertEquals("1970-01-01 00:00", employee1.getLatestClockIn());
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
