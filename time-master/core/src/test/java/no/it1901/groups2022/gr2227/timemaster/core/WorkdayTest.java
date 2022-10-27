package no.it1901.groups2022.gr2227.timemaster.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class WorkdayTest {
  
  LocalDateTime testTimeIn = LocalDateTime.now();
  LocalDateTime testTimeOut = LocalDateTime.now().plusHours(2);
  
  Workday workday;
  
  @BeforeEach
  public void createWorkday() {
    workday = new Workday(testTimeIn);
  }
  
  @Test
  public void testGetTimeIn() {
    assertEquals(testTimeIn, workday.getTimeIn());
  }
  
  @Test
  @DisplayName("Test setTimeOut()")
  public void setTimeOutTest() {
    workday.setTimeOut(testTimeOut);
    assertEquals(testTimeOut, workday.getTimeOut());
  } 
  
  @Test
  @DisplayName("Test toString")
  public void testToString() {
    String expectedStringBeforeTimeOut = testTimeIn.toString() + "," + null;
    String expectedStringAfterTimeOut = testTimeIn.toString() + "," + testTimeOut.toString();
    assertEquals(expectedStringBeforeTimeOut, workday.toString());
    workday.setTimeOut(testTimeOut);
    assertEquals(expectedStringAfterTimeOut, workday.toString());
  }
  
}
