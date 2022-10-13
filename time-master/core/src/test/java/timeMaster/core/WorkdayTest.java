package timeMaster.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class WorkdayTest {
  
  LocalDate testDate = LocalDate.parse("2022-09-19");
  LocalTime testTimeIn = LocalTime.parse("01:02");
  LocalTime testTimeOut = LocalTime.parse("06:00");
  
  Workday workday;
  
  @BeforeEach
  public void createWorkday() {
    workday = new Workday(testDate, testTimeIn);
  }
  
  @Test
  public void testGetDate() {
    assertEquals(testDate, workday.getDate());
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
    String expectedStringNotTimeOut = testDate.toString() + "," + testTimeIn + "," + null;
    String expectedStringTimeOut = testDate.toString() + "," + testTimeIn + "," + testTimeOut;
    assertEquals(expectedStringNotTimeOut, workday.toString());
    workday.setTimeOut(testTimeOut);
    assertEquals(expectedStringTimeOut, workday.toString());
  }
  
}
