package no.it1901.groups2022.gr2227.timemaster.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    assertTrue(true);
    // LocalDateTime timeIn = this.getTimeIn();
    // String dayOfWeek = timeIn.getDayOfWeek().toString();
    // String dayOfMonth = String.valueOf(timeIn.getDayOfMonth());
    // String month = timeIn.getMonth().toString();
    // String year = String.valueOf(timeIn.getYear());
    // String inHour = String.valueOf(timeIn.getHour());
    // String inMinute = String.valueOf(timeIn.getMinute());

    // String date = dayOfWeek + " " + dayOfMonth + " " + month + " " + year;
    // String stampIn = inHour + ":" + inMinute;
    // String stampOut = "";

    // if(timeOut != null) {
    //   stampOut = String.valueOf(timeOut.getHour()) + ":" + String.valueOf(timeOut.getMinute());
    // } 

    // return date + " | " + stampIn + " | " + stampOut;
  }
  
}
