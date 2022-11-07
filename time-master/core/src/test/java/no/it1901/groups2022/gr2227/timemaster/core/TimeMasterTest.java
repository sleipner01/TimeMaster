package no.it1901.groups2022.gr2227.timemaster.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TimeMasterTest {

  TimeMaster timeMaster;

  @BeforeEach
  public void setup() {
    timeMaster = new TimeMaster();
    timeMaster.setApplicationInTestState();
    try {
      timeMaster.createEmployee("Test Person");
    } catch (IOException e) {
      System.out.println("API probably not connected");
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
  public void chooseEmployeeTest() {
    assertDoesNotThrow(() -> timeMaster.setChosenEmployee(timeMaster.getEmployees().get(0)));
    assertEquals("Test Person", timeMaster.getChosenEmployee().getName());
    assertThrows(IllegalArgumentException.class, () -> timeMaster.setChosenEmployee(new Employee()));
  }

  @Test
  public void clockEmployeeInOutTest() {
    assertThrows(IllegalStateException.class, () -> timeMaster.clockEmployeeInOut(LocalDateTime.MIN));
    assertDoesNotThrow(() -> timeMaster.setChosenEmployee(timeMaster.getEmployees().get(0)));
    assertDoesNotThrow(() -> timeMaster.clockEmployeeInOut(LocalDateTime.MIN));
  }

  @Test
  void autoClockEmployeeInOutTest() {
    assertThrows(IllegalStateException.class, () -> timeMaster.autoClockEmployeeInOut());
    assertDoesNotThrow(() -> timeMaster.setChosenEmployee(timeMaster.getEmployees().get(0)));
    assertDoesNotThrow(() -> timeMaster.autoClockEmployeeInOut());
    assertDoesNotThrow(() -> timeMaster.autoClockEmployeeInOut());
  }

  @Test
  void getEmployeeWorkdayHistoryTest() {
    assertThrows(IllegalStateException.class, () -> timeMaster.getEmployeeWorkdayHistory());
    assertDoesNotThrow(() -> timeMaster.setChosenEmployee(timeMaster.getEmployees().get(0)));
    assertDoesNotThrow(() -> timeMaster.getEmployeeWorkdayHistory());
  }

  @Test
  void editWorkdayTest() {
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
    assertThrows(IllegalStateException.class, () -> timeMaster.deleteWorkdayFromEmployee(null));
    assertDoesNotThrow(() -> timeMaster.setChosenEmployee(timeMaster.getEmployees().get(0)));
    assertDoesNotThrow(() -> timeMaster.clockEmployeeInOut(LocalDateTime.MIN));
    assertDoesNotThrow(() -> timeMaster.deleteWorkdayFromEmployee(timeMaster.getEmployeeWorkdayHistory().get(0)));
  }

}
