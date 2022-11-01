package no.it1901.groups2022.gr2227.timemaster.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

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
    assertDoesNotThrow(() -> timeMaster.setChosenEmployee(0));
    assertThrows(IndexOutOfBoundsException.class, () -> timeMaster.setChosenEmployee(1));
  }

  @Test
  public void clockEmployeeInOutTest() {
    assertThrows(IllegalStateException.class, () -> timeMaster.clockEmployeeInOut(LocalDate.EPOCH, LocalTime.MIDNIGHT));
    assertDoesNotThrow(() -> timeMaster.setChosenEmployee(0));
  }

  @Test void autoClockEmployeeInOutTest() {
    assertThrows(IllegalStateException.class, () -> timeMaster.autoClockEmployeeInOut());
    assertDoesNotThrow(() -> timeMaster.setChosenEmployee(0));
  }

}
