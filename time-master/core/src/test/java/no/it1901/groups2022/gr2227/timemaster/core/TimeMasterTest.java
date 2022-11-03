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
  }

  @Test
  public void clockEmployeeInOutTest() {
    assertDoesNotThrow(() -> timeMaster.setChosenEmployee(timeMaster.getEmployees().get(0)));
    assertDoesNotThrow(() -> timeMaster.clockEmployeeInOut(LocalDateTime.now()));
  }

  @Test
  void autoClockEmployeeInOutTest() {
    assertDoesNotThrow(() -> timeMaster.setChosenEmployee(timeMaster.getEmployees().get(0)));
    assertDoesNotThrow(() -> timeMaster.autoClockEmployeeInOut());
  }

}
