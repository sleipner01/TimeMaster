package no.it1901.groups2022.gr2227.timemaster.core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class Employee {
  
  private String id;
  private String name;
  private ArrayList<Workday> workdays = new ArrayList<>();
  
  public Employee() {}
  
  public Employee(String name) {
    this.id = generateId();
    this.name = name;
  }
  
  // Only for filewriters/readers
  public Employee(String id, String name) {
    this.id = id;
    this.name = name;
  }
  
  private String generateId() { 
    return UUID.randomUUID().toString(); 
  }

  private void validateCheckInTimestamp(LocalDateTime input) throws IllegalArgumentException {
    this.sortWorkdaysAscending();

    for (int i = this.workdays.size() - 1; i >= 0; i--) {
      Workday tempWorkday = workdays.get(i);

      // If the last workday isn't checked out - Error
      if (i == workdays.size() && !tempWorkday.isTimedOut()) {
        throw new IllegalArgumentException(
            "Your last workday isn't clocked out yet...\n" + 
            "Workday: " + tempWorkday.toString()
            );
      }

      // If the timestamp is newer than the last ended workday - true
      if (i == workdays.size() - 1 && tempWorkday.isTimedOut()) {
        if (input.isAfter(tempWorkday.getTimeOut())) {
          return;
        }
      }
      
      // If the timestamp is in the middle of another workday - error
      if (input.isAfter(tempWorkday.getTimeIn()) && input.isBefore(tempWorkday.getTimeOut())) {
        throw new IllegalArgumentException(
          "** Input was in between workday **\n" + 
          "Check in: " + tempWorkday.getTimeIn().toString() + "\n" +
          "Check out: " + tempWorkday.getTimeOut().toString()
        );
      }

      // If input is before the first workday checkin - true
      if (i == 0 && tempWorkday.getTimeIn().isAfter(input)) {
        return;
      }

      // As long as we are not checking the last workday
      if (0 < i) {
        // If input is in between tempWorkday and the one before
        if (this.workdays.get(i - 1).getTimeOut().isBefore(input) && input.isBefore(tempWorkday.getTimeIn())) {
          return;
        }
      }

    }
  }

  private void validateCheckOutTimestamp(LocalDateTime input) throws IllegalArgumentException {

    for (int i = this.workdays.size() - 1; i >= 0; i--) {
      Workday tempWorkday = workdays.get(i);

      // If the last workday isn't checked out - Error
      if (i == workdays.size() && !tempWorkday.isTimedOut()) {
        throw new IllegalArgumentException(
            "Your last workday isn't clocked out yet...\n" + 
            "Workday: " + tempWorkday.toString()
            );
      }

      // If the timestamp is newer than the last ended workday - true
      if (i == workdays.size() - 1 && tempWorkday.isTimedOut()) {
        if (input.isAfter(tempWorkday.getTimeOut())) {
          return;
        }
      }
      
      // If the timestamp is in the middle of another workday - error
      if (input.isAfter(tempWorkday.getTimeIn()) && input.isBefore(tempWorkday.getTimeOut())) {
        throw new IllegalArgumentException(
          "** Input was in between workday **\n" + 
          "Check in: " + tempWorkday.getTimeIn().toString() + "\n" +
          "Check out: " + tempWorkday.getTimeOut().toString()
        );
      }

      // If input is before the first workday checkin - true
      if (i == 0 && tempWorkday.getTimeIn().isAfter(input)) {
        return;
      }

      // As long as we are not checking the last workday
      if (0 < i) {
        // If input is in between tempWorkday and the one before
        if (this.workdays.get(i - 1).getTimeOut().isBefore(input) && input.isBefore(tempWorkday.getTimeIn())) {
          return;
        }
      }

    }
  }
  
  public void checkIn(LocalDateTime dateTimeInput) throws IllegalStateException, IllegalArgumentException {
    if (isAtWork()) { 
      throw new IllegalStateException(this.toString() + " is already at work!");
    }

    validateCheckInTimestamp(dateTimeInput);

    this.addWorkday(new Workday(dateTimeInput));
    System.out.println(this.toString() + " checked in at: " + dateTimeInput);
  }
  
  public void checkOut(LocalDateTime dateTimeInput) {
    if (!isAtWork()) { 
      throw new IllegalStateException(this.toString() + " is not at work!");
    }
    //TODO: Validate input
    this.workdays.get(workdays.size() - 1).setTimeOut(dateTimeInput);
    System.out.println(this.toString() + " checked out at: " + dateTimeInput);
  }
  
  public String getId() { 
    return this.id; 
  }
  
  public String getName() { 
    return this.name; 
  }

  private Workday getLatestWorkday() {
    if (this.workdays.size() < 1) {
      return null;
    }
    return this.workdays.get(this.workdays.size()-1);
  }
  
  public boolean isAtWork() { 
    if(this.workdays.size() < 1) return false;
    return !this.getLatestWorkday().isTimedOut();
  }
  
  public void addWorkday(Workday workday) {
    if (this.workdays.contains(workday)) { 
      throw new IllegalArgumentException("Workday is already added.");
    }
    this.workdays.add(workday);
    this.sortWorkdaysAscending();
  }
  
  public ArrayList<Workday> getWorkdays() {
    return new ArrayList<>(this.workdays);
  }
  
  public String getLatestClockIn() {
    Workday latest = workdays.get(workdays.size() - 1);
    return latest.getTimeInAsFormattedString();
  }

  private void sortWorkdaysAscending() {
    this.workdays.sort((a, b) -> { return a.getTimeIn().compareTo(b.getTimeIn()); });
  }
  
  @Override
  public String toString() {
    return this.id + "," + this.getName();
  }
  
}
