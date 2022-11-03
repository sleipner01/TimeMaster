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

    for (int i = this.workdays.size() - 1; i >= 0; i--) {
      Workday tempWorkday = workdays.get(i);

      // If the last workday isn't checked out - Error
      if (i == workdays.size() && !tempWorkday.isTimedOut()) {
        throw new IllegalArgumentException(
            "Your last workday isn't clocked out yet...\n"
            + "Workday: " + tempWorkday.toString()
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
          "** Input was in between workday **\n"
          + "Check in: " + tempWorkday.getTimeIn().toString() + "\n"
          + "Check out: " + tempWorkday.getTimeOut().toString()
        );
      }

      // If input is before the first workday checkin - true
      if (i == 0 && tempWorkday.getTimeIn().isAfter(input)) {
        return;
      }

      // As long as we are not checking the last workday
      if (0 < i) {
        // If input is in between tempWorkday and the one before
        if (this.workdays.get(i - 1).getTimeOut().isBefore(input)
            &&
            input.isBefore(tempWorkday.getTimeIn())) {
          return;
        }
      }

    }
  }

  private void validateCheckOutTimestamp(LocalDateTime input, Workday workday) 
      throws IllegalArgumentException {

    if (input.isBefore(workday.getTimeIn())) {
      throw new IllegalArgumentException(
        "Input cannot be before the workday started!\n"
        + "Check in: " + workday.getTimeIn().toString() + "\n"
        + "Input: " + input.toString() 
      );
    }

    if (workday.equals(this.getLatestWorkday())) {
      return;
    }

    for (int i = this.workdays.size() - 1; i >= 0; i--) {

      Workday tempWorkday = this.workdays.get(i);

      // If we are at the latest workday which is not closed,
      // the new workday must be before the latest workday.
      if (tempWorkday.equals(this.getLatestWorkday()) 
          || 
          input.isAfter(tempWorkday.getTimeIn())) {
        if (!tempWorkday.isTimedOut()) {
          if (workday.getTimeIn().isAfter(tempWorkday.getTimeIn())) {
            throw new IllegalArgumentException(
              "** Input comes in conflict with the following workday **\n"
              + "Check in: " + tempWorkday.getTimeIn().toString() + "\n"
              + "Input: " + input.toString()
            );
          }
          continue;
        }
      }

      if (workday.getTimeIn().isAfter(tempWorkday.getTimeOut())) {
        return;
      }

      // If we are at the first workday and the checkout comes in conflict with the workday
      if (i == 0) {
        if (input.isAfter(tempWorkday.getTimeIn())) {
          throw new IllegalArgumentException(
            "** Input comes in conflict with the following workday **\n"
            + "Check in: " + tempWorkday.getTimeIn().toString() + "\n"
            + "Check out: " + tempWorkday.getTimeOut().toString() + "\n"
            + "Input: " + input.toString()
          );
        }
      }

      if (workday.getTimeIn().isAfter(tempWorkday.getTimeIn())) {
        continue;
      }

      if (input.isAfter(tempWorkday.getTimeIn())) {
        throw new IllegalArgumentException(
          "** Input comes in conflict with the following workday **\n"
          + "Check in: " + tempWorkday.getTimeIn().toString() + "\n"
          + "Check out: " + tempWorkday.getTimeOut().toString() + "\n"
          + "Input: " + input.toString() + "\n"
        );
      } else {
        return;
      }

    }

  }
  
  public void checkIn(LocalDateTime dateTimeInput) 
      throws IllegalStateException, IllegalArgumentException {
    if (isAtWork()) { 
      throw new IllegalStateException(this.toString() + " is already at work!");
    }

    // Validating in addWorkday
    this.addWorkday(new Workday(dateTimeInput));
    System.out.println(this.toString() + " checked in at: " + dateTimeInput);
  }
  
  public void checkOut(LocalDateTime dateTimeInput) 
      throws IllegalStateException, IllegalArgumentException {
    if (!isAtWork()) { 
      throw new IllegalStateException(this.toString() + " is not at work!");
    }

    validateCheckOutTimestamp(dateTimeInput, this.getLatestWorkday());

    this.getLatestWorkday().setTimeOut(dateTimeInput);
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
    return this.workdays.get(this.workdays.size() - 1);
  }
  
  public boolean isAtWork() { 
    if (this.workdays.size() < 1) { 
      return false;
    }
    return !this.getLatestWorkday().isTimedOut();
  }
  
  public void addWorkday(Workday workday) throws IllegalArgumentException {
    if (this.workdays.contains(workday)) { 
      throw new IllegalArgumentException("Workday is already added.");
    }
    if (!workday.isTimedOut() && 0 < this.workdays.size()) {
      if (!this.getLatestWorkday().isTimedOut()) {
        throw new IllegalArgumentException(
          "** Cannot add another open workday while another open workday is open **\n"
          + "Conflicting workday: " + this.getLatestWorkday().toString()
        );
      }
      if (this.getLatestWorkday().getTimeOut().isAfter(workday.getTimeIn())) {
        throw new IllegalArgumentException(
          "** An open workday cannot be set before an earlier workday. **\n"
          + "Conflicting workday: " + this.getLatestWorkday().toString()
        );
      }
    }

    validateCheckInTimestamp(workday.getTimeIn());
    if (workday.isTimedOut()) {
      validateCheckOutTimestamp(workday.getTimeOut(), workday);
    }

    this.workdays.add(workday);
    this.sortWorkdaysAscending();
  }

  public void editWorkday(Workday workday, LocalDateTime timeIn, LocalDateTime timeOut)
      throws IllegalArgumentException {
    if (!this.workdays.contains(workday)) { 
      throw new IllegalArgumentException(
        "Workday: " + workday.toString()
        + " doesn't exist at " + this.toString()
      );
    }

    this.deleteWorkday(workday);
    Workday editedWorkday = new Workday(timeIn);
    editedWorkday.setTimeOut(timeOut);
    try {
      this.addWorkday(editedWorkday);
    } catch (IllegalArgumentException e) {
      this.addWorkday(workday);
      throw new IllegalArgumentException(e.getMessage());
    } catch (Exception e) {
      this.addWorkday(workday);
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  public void deleteWorkday(Workday workday) throws IllegalArgumentException {
    if (!this.workdays.contains(workday)) { 
      throw new IllegalArgumentException(
        "Workday: " + workday.toString()
        + " doesn't exist at " + this.toString()
      );
    }

    this.workdays.remove(workday);
  }
  
  public ArrayList<Workday> getWorkdays() {
    return new ArrayList<>(this.workdays);
  }
  
  public String getLatestClockIn() {
    Workday latest = workdays.get(workdays.size() - 1);
    return latest.getTimeInAsFormattedString();
  }

  private void sortWorkdaysAscending() {
    this.workdays.sort((a, b) -> { 
      return a.getTimeIn().compareTo(b.getTimeIn()); 
    });
  }
  
  @Override
  public String toString() {
    return this.id + "," + this.getName();
  }
  
}
