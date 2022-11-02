package no.it1901.groups2022.gr2227.timemaster.core;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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

  private String formatDateTime(LocalDateTime dateTime) {
    // Using Locale.getDefault to create a predictable result. Different regions uses different standards
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E. MMM dd yyyy - HH:mm", Locale.getDefault());
    return dateTime.format(formatter);
  }

  public LocalDateTime getTimeIn() {
    return timeIn;
  }

  public String getTimeInAsFormattedString() {
    return formatDateTime(this.timeIn);
  }

  public LocalDateTime getTimeOut() {
    return timeOut;
  }

  public String getTimeOutAsFormattedString() throws IllegalStateException {
    if (!this.isTimedOut()) {
      throw new IllegalStateException("The workday isn't timed out.");
    }
    return formatDateTime(this.timeOut);
  }

  public boolean isTimedOut() {
    return this.timeOut != null;
  }

  private boolean isValidInput(LocalDateTime input) {
    // Checking for null to satify JSONParser setTimeOut()
    if (input == null) {
      return true;
    }
    if (input.isBefore(this.timeIn)) {
      return false;
    }

    return true;
  }

  public void setTimeOut(LocalDate dateOut, LocalTime timeOut) throws IllegalArgumentException {
    LocalDateTime input = LocalDateTime.of(dateOut, timeOut);
    if (!isValidInput(input)) {
      throw new IllegalArgumentException(
          "The timestamp is before time-in timestamp.\n" +
              "Time in: " + this.timeIn.toString() + "\n" +
              "Input: " + input.toString());
    }
    this.timeOut = input;
  }

  public void setTimeOut(LocalDateTime timeOut) throws IllegalArgumentException {
    if (!isValidInput(timeOut)) {
      throw new IllegalArgumentException(
          "The timestamp is before time-in timestamp.\n" +
              "Time in: " + this.timeIn.toString() + "\n" +
              "Input: " + timeOut.toString());
    }
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
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault());
    String stampIn = timeIn.format(timeFormat);

    String stampOut = "";

    if (timeOut != null) {
      stampOut = this.getTimeOut().format(timeFormat);
    }

    return String.format("%-26.26s%10s%14s%10s%14s", date, "|", stampIn, "|", stampOut);
  }
}
