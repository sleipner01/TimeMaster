package no.it1901.groups2022.gr2227.timemaster.core;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Workday {
  
  private LocalDateTime timeIn;
  private LocalDateTime timeOut;
  
  public Workday() {}
  
  public Workday(LocalDate date, LocalTime timeIn) {
    this.timeIn = LocalDateTime.of(date, timeIn);
  }

  public Workday(LocalDateTime timeIn) {
    this.timeIn = timeIn;
  }
  
  public LocalDateTime getTimeIn() { 
    return timeIn; 
  }
  
  public LocalDateTime getTimeOut() { 
    return timeOut;
  }

  public boolean isTimedOut() {
    return this.timeOut != null;
  }

  public void setTimeOut(LocalDate dateOut,LocalTime timeOut) {
    this.timeOut = LocalDateTime.of(dateOut, timeOut);
  }
  
  public void setTimeOut(LocalDateTime timeOut) {
    this.timeOut = timeOut;
  }
  
  @Override
  public String toString() {
    return this.getTimeIn() + "," + this.getTimeOut();
  }
}
