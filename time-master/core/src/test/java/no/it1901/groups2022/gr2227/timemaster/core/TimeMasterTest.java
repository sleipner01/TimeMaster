package no.it1901.groups2022.gr2227.timemaster.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.ConnectException;
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
    assertThrows(ConnectException.class,() -> timeMaster.createEmployee("Anna"));
    assertThrows(IllegalArgumentException.class, () -> timeMaster.createEmployee(""));

  }

  @Test
  public void getEmployeesTest() {
    assertDoesNotThrow(() -> timeMaster.getEmployees());
  }

  @Test
  public void chooseEmployeeTest() {
    assertThrows(ConnectException.class,() -> timeMaster.createEmployee("Anna"));
    assertDoesNotThrow(() -> timeMaster.setChosenEmployee(0));
    assertThrows(IndexOutOfBoundsException.class, () -> timeMaster.setChosenEmployee(1));
  }

  @Test
  public void clockEmployeeInOutTest() {
    assertThrows(ConnectException.class,() -> timeMaster.createEmployee("Anna"));
    assertThrows(IllegalStateException.class, () -> timeMaster.clockEmployeeInOut(LocalDate.EPOCH, LocalTime.MIDNIGHT));
    assertDoesNotThrow(() -> timeMaster.setChosenEmployee(0));
    assertThrows(ConnectException.class, () -> timeMaster.clockEmployeeInOut(LocalDate.EPOCH, LocalTime.MIDNIGHT));
  }

  @Test void autoClockEmployeeInOutTest() {
    assertThrows(ConnectException.class,() -> timeMaster.createEmployee("Anna"));
    assertThrows(IllegalStateException.class, () -> timeMaster.autoClockEmployeeInOut());
    assertDoesNotThrow(() -> timeMaster.setChosenEmployee(0));
    assertThrows(ConnectException.class, () -> timeMaster.autoClockEmployeeInOut());
  }

}
