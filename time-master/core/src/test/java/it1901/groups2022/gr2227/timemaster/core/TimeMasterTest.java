package it1901.groups2022.gr2227.timemaster.core;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it1901.groups2022.gr2227.timemaster.core.TimeMaster;

public class TimeMasterTest {
    
  Path saveDirPath;
  File file;
  String fileName = "employeesTest.json";
  TimeMaster timeMaster;

  @BeforeEach
  public void setup() {
    saveDirPath = Paths.get(System.getProperty("user.dir"), "timeMasterSaveFiles");
    file = new File(saveDirPath.toString(), fileName);
    timeMaster = new TimeMaster(fileName);
  }

  @AfterEach
  public void cleanUp() {
    file.delete();
  }
  
  @Test
  public void createEmployeeTest() {
    assertDoesNotThrow(() -> timeMaster.createEmployee("Anna"));
  }

  @Test
  public void chooseEmployeeTest() {
    timeMaster.createEmployee("Anna");
    assertDoesNotThrow(() -> timeMaster.setChosenEmployee(0));
    assertEquals("Anna", timeMaster.getChosenEmployee().getName());
  }

  @Test
  public void clockEmployeeInOutTest() {
    timeMaster.createEmployee("Anna");
    assertDoesNotThrow(() -> timeMaster.setChosenEmployee(0));
    assertDoesNotThrow(() -> timeMaster.clockEmployeeInOut(LocalDate.EPOCH, LocalTime.MIDNIGHT));
  }

  @Test void autoClockEmployeeInOutTest() {
    timeMaster.createEmployee("Anna");
    assertDoesNotThrow(() -> timeMaster.setChosenEmployee(0));
    assertDoesNotThrow(() -> timeMaster.autoClockEmployeeInOut());
  }

}
