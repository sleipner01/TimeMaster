package no.it1901.groups2022.gr2227.timemaster.core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class Employee {
  
  private String id;
  private String name;
  private ArrayList<Workday> workdays = new ArrayList<>();
  private boolean atWork;
  
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
  
  private Boolean isWorkdayValid(Workday workday) {
    return !this.workdays.contains(workday);
  }
  
  private boolean isValidWorkday(LocalDateTime dateTimeInput) {
    // TODO: Check if any other workday in the close timespan conflicts with this timestamp.
    
    return true;
  }
  
  public void checkIn(LocalDateTime dateTimeInput) {
    if (isAtWork()) { 
      throw new IllegalStateException(this.toString() + " is already at work!");
    }
    if (!isValidWorkday(dateTimeInput)) {
      throw new IllegalArgumentException("This timestamp comes in conflict with another workday");
    }
    //TODO: validate Input
    this.workdays.add(new Workday(dateTimeInput));
    this.atWork = true;
    System.out.println(this.toString() + " checked in at: " + dateTimeInput);
  }
  
  public void checkOut(LocalDateTime dateTimeInput) {
    if (!isAtWork()) { 
      throw new IllegalStateException(this.toString() + " is not at work!");
    }
    //TODO: Validate input
    this.workdays.get(workdays.size() - 1).setTimeOut(dateTimeInput);
    this.atWork = false;
    System.out.println(this.toString() + " checked out at: " + dateTimeInput);
  }
  
  public String getId() { 
    return this.id; 
  }
  
  public String getName() { 
    return this.name; 
  }
  
  public boolean isAtWork() { 
    return this.atWork; 
  }
  
  public void addWorkday(Workday workday) {
    if (!this.isWorkdayValid(workday)) { 
      throw new IllegalArgumentException("Workday is already added.");
    }
    this.workdays.add(workday);
  }
  
  public ArrayList<Workday> getWorkdays() {
    return new ArrayList<>(this.workdays);
  }
  
  public String getLatestClockIn() {
    ArrayList<Workday> workdays = this.getWorkdays();
    Workday latest = workdays.get(workdays.size() - 1);
    return latest.getTimeInAsFormattedString();
  }
  
  public Workday getDate(LocalDateTime dateTime) {
    return this.workdays.stream().filter(e -> e.getTimeIn().equals(dateTime)).findAny().get();
  }
  
  @Override
  public String toString() {
    return this.id + "," + this.getName();
  }
  
}
