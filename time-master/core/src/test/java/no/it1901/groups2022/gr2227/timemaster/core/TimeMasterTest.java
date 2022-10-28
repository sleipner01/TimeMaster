package no.it1901.groups2022.gr2227.timemaster.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TimeMasterTest {
    
  TimeMaster timeMaster;

  @BeforeEach
  public void setup() {
    timeMaster = new TimeMaster();
  }
  
  @Test
  public void createEmployeeTest() {
    assertDoesNotThrow(() -> timeMaster.createEmployee("Anna"));
    assertThrows(IllegalArgumentException.class, () -> timeMaster.createEmployee(""));

  }

  @Test
  public void getEmployeesTest() {
    assertDoesNotThrow(() -> timeMaster.getEmployees());
  }

  @Test
  public void chooseEmployeeTest() {
    timeMaster.createEmployee("Anna");
    assertDoesNotThrow(() -> timeMaster.setChosenEmployee(0));
    assertThrows(IndexOutOfBoundsException.class, () -> timeMaster.setChosenEmployee(1));
  }

  @Test
  public void clockEmployeeInOutTest() {
    timeMaster.createEmployee("Anna");
    assertThrows(IllegalStateException.class, () -> timeMaster.clockEmployeeInOut(LocalDate.EPOCH, LocalTime.MIDNIGHT));
    assertDoesNotThrow(() -> timeMaster.setChosenEmployee(0));
    assertDoesNotThrow(() -> timeMaster.clockEmployeeInOut(LocalDate.EPOCH, LocalTime.MIDNIGHT));
  }

  @Test void autoClockEmployeeInOutTest() {
    timeMaster.createEmployee("Anna");
    assertThrows(IllegalStateException.class, () -> timeMaster.autoClockEmployeeInOut());
    assertDoesNotThrow(() -> timeMaster.setChosenEmployee(0));
    assertDoesNotThrow(() -> timeMaster.autoClockEmployeeInOut());
  }

}
