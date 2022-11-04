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
    try {
      timeMaster.createEmployee(testName);
    } catch (Exception e) {
      // Should never run
      System.err.println("Somehow the API calls are on...");
    }
  }

  @Test
  public void createEmployeeTest() {
    assertThrows(IllegalArgumentException.class, () -> timeMaster.createEmployee(""));

  }

  @Test
  public void getEmployeesTest() {
    assertDoesNotThrow(() -> timeMaster.getEmployees());
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
  }

  @Test
  public void clockEmployeeInOutTest() {
    createTestingEmployee();
    assertDoesNotThrow(() -> timeMaster.setChosenEmployee(timeMaster.getEmployees().get(0)));
    assertDoesNotThrow(() -> timeMaster.clockEmployeeInOut(LocalDateTime.now()));
  }

  @Test
  void autoClockEmployeeInOutTest() {
    createTestingEmployee();
    assertDoesNotThrow(() -> timeMaster.setChosenEmployee(timeMaster.getEmployees().get(0)));
    assertDoesNotThrow(() -> timeMaster.autoClockEmployeeInOut());
  }

}
