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
    String correctTime1 = "THURSDAY 1 JANUARY 1970            |         00:00         |              ";
    String correctTime2 = "THURSDAY 1 JANUARY 1970            |         00:00         |         01:00";
    Workday workday = new Workday(LocalDateTime.of(1970, 1, 1, 0, 0, 0));

    assertEquals(workday.toString(), correctTime1);

    workday.setTimeOut(LocalDateTime.of(1970, 1, 1, 1, 0, 0));

    assertEquals(workday.toString(), correctTime2);
  }
  
}
