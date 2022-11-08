package no.it1901.groups2022.gr2227.timemaster.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;

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
  @DisplayName("Test the empty constructors")
  public void testEmptyConstructor() {
    workday = new Workday();
    assertNull(workday.getTimeIn());
    assertNull(workday.getTimeOut());
  }

  @Test
  @DisplayName("Test the constructor that takes in LocalDateTime")
  public void testDateTimeConstructor() {
    assertEquals(testTimeIn, workday.getTimeIn());
    assertNull(workday.getTimeOut()); 
  }
  
  @Test
  public void testGetTimeIn() {
    assertEquals(testTimeIn, workday.getTimeIn());
  }

  @Test
  @DisplayName("Test formatting of timeIn")
  public void testFormatTimeIn() {
    String correctFormat = "Thu. Jan 01 1970 - 12:00";
    LocalDateTime timeIn = LocalDateTime.of(1970, Month.JANUARY, 1, 12, 0);
    workday = new Workday(timeIn);
    assertEquals(correctFormat, workday.getTimeInAsFormattedString());
  }

  @Test
  @DisplayName("Test formatting of timeOut")
  public void testFormatTimeOut() {
    String correctFormat = "Thu. Jan 01 1970 - 12:00";
    LocalDateTime timeIn = LocalDateTime.of(1970, Month.JANUARY, 1, 11, 0);
    LocalDateTime timeOut = LocalDateTime.of(1970, Month.JANUARY, 1, 12, 0);
    workday = new Workday(timeIn);
    assertThrows(IllegalStateException.class, () -> {
      workday.getTimeOutAsFormattedString();
    });
    workday.setTimeOut(timeOut);
    assertEquals(correctFormat, workday.getTimeOutAsFormattedString());
  }
  
  @Test
  @DisplayName("Test setTimeOut that takes in LocalDateTime")
  public void testSetTimeOutDateTime() {
    workday.setTimeOut(testTimeOut);
    assertEquals(testTimeOut, workday.getTimeOut());

    LocalDateTime outBeforeIn = LocalDateTime.now();
    LocalDateTime inAfterOut = LocalDateTime.now().plusNanos(1);
    workday = new Workday(inAfterOut);
    assertThrows(IllegalArgumentException.class, () -> {
      workday.setTimeOut(outBeforeIn);
    });
  } 

  @Test
  @DisplayName("Test if is timed out") 
  public void testTimedOut() {
    assertFalse(workday.isTimedOut());
    workday.setTimeOut(testTimeOut);
    assertTrue(workday.isTimedOut());
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
