package no.it1901.groups2022.gr2227.timemaster.core;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
    LocalDateTime timeIn = this.getTimeIn();

    // Date
    String dayOfWeek = timeIn.getDayOfWeek().toString();
    String dayOfMonth = String.valueOf(timeIn.getDayOfMonth());
    String month = timeIn.getMonth().toString();
    String year = String.valueOf(timeIn.getYear());

    String date = dayOfWeek + " " + dayOfMonth + " " + month + " " + year;
    
    // Time
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
    String stampIn = timeIn.format(timeFormat);

    String stampOut = "";

    if(timeOut != null) {
      stampOut = this.getTimeOut().format(timeFormat);
    } 

    return String.format("%-26.26s%10s%14s%10s%14s", date, "|", stampIn, "|", stampOut);
  }

  public static void main(String[] args) {
    Workday w = new Workday(LocalDateTime.of(1970, 1, 1, 0, 0, 0));
    w.setTimeOut(LocalDateTime.now());
    System.out.println(w.toString());
  }
}
