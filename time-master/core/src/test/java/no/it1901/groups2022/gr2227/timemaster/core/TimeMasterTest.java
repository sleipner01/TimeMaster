package no.it1901.groups2022.gr2227.timemaster.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TimeMasterTest {

  TimeMaster timeMaster;
  String testName = "Test Name";

  @BeforeEach
  public void setup() {
    timeMaster = new TimeMaster();
    timeMaster.setApplicationInTestState();
  }

  private void createTestingEmployee() {
    assertDoesNotThrow(() -> timeMaster.createEmployee(testName));
  }

  @Test
  public void createEmployeeTest() {
    assertThrows(IllegalArgumentException.class, () -> timeMaster.createEmployee(""));
    assertDoesNotThrow(() -> timeMaster.createEmployee(testName));
    assertEquals(1, timeMaster.getEmployees().size());
    assertEquals(testName, timeMaster.getEmployees().get(0).getName());
  }

  @Test
  public void getEmployeesTest() {
    assertDoesNotThrow(() -> timeMaster.getEmployees());
    assertEquals(0, timeMaster.getEmployees().size());
    createTestingEmployee();
    assertEquals(1, timeMaster.getEmployees().size());
    assertEquals(testName, timeMaster.getEmployees().get(0).getName());
  }

  @Test
  public void setApplicationInTestStateTest() {
    assertDoesNotThrow(() -> timeMaster.createEmployee(testName));
    timeMaster.setApplicationInTestState();
    assertTrue(timeMaster.getEmployees().size() == 0);
  }

  @Test
  public void chooseEmployeeTest() {
    createTestingEmployee();
    assertDoesNotThrow(() -> timeMaster.setChosenEmployee(timeMaster.getEmployees().get(0)));
    assertEquals(testName, timeMaster.getChosenEmployee().getName());
    assertThrows(IllegalArgumentException.class, () -> timeMaster.setChosenEmployee(new Employee()));
  }

  @Test
  public void clockEmployeeInOutTest() {
    assertDoesNotThrow(() -> timeMaster.createEmployee(testName));
    assertThrows(IllegalStateException.class, () -> timeMaster.clockEmployeeInOut(LocalDateTime.MIN));
    assertDoesNotThrow(() -> timeMaster.setChosenEmployee(timeMaster.getEmployees().get(0)));
    assertDoesNotThrow(() -> timeMaster.clockEmployeeInOut(LocalDateTime.MIN));
  }

  @Test
  void autoClockEmployeeInOutTest() {
    assertDoesNotThrow(() -> timeMaster.createEmployee(testName));
    assertThrows(IllegalStateException.class, () -> timeMaster.autoClockEmployeeInOut());
    assertDoesNotThrow(() -> timeMaster.setChosenEmployee(timeMaster.getEmployees().get(0)));
    assertDoesNotThrow(() -> timeMaster.autoClockEmployeeInOut());
    assertDoesNotThrow(() -> timeMaster.autoClockEmployeeInOut());
  }

  @Test
  void getEmployeeWorkdayHistoryTest() {
    assertDoesNotThrow(() -> timeMaster.createEmployee(testName));
    assertThrows(IllegalStateException.class, () -> timeMaster.getEmployeeWorkdayHistory());
    assertDoesNotThrow(() -> timeMaster.setChosenEmployee(timeMaster.getEmployees().get(0)));
    assertDoesNotThrow(() -> timeMaster.getEmployeeWorkdayHistory());
  }

  @Test
  void editWorkdayTest() {
    assertDoesNotThrow(() -> timeMaster.createEmployee(testName));
    assertThrows(IllegalStateException.class, () -> timeMaster.editWorkday(null, null, null));
    assertDoesNotThrow(() -> timeMaster.setChosenEmployee(timeMaster.getEmployees().get(0)));
    assertDoesNotThrow(() -> timeMaster.clockEmployeeInOut(LocalDateTime.MIN));
    assertDoesNotThrow(() -> timeMaster.clockEmployeeInOut(LocalDateTime.MIN));
    assertDoesNotThrow(() -> timeMaster.editWorkday(timeMaster.getEmployeeWorkdayHistory().get(0),
        LocalDateTime.MIN,
        LocalDateTime.MIN));
  }

  @Test
  void deleteWorkdayFromEmployeeTest() {
    assertDoesNotThrow(() -> timeMaster.createEmployee(testName));
    assertThrows(IllegalStateException.class, () -> timeMaster.deleteWorkdayFromEmployee(null));
    assertDoesNotThrow(() -> timeMaster.setChosenEmployee(timeMaster.getEmployees().get(0)));
    assertDoesNotThrow(() -> timeMaster.clockEmployeeInOut(LocalDateTime.MIN));
    assertDoesNotThrow(() -> timeMaster.deleteWorkdayFromEmployee(timeMaster.getEmployeeWorkdayHistory().get(0)));
  }

}
