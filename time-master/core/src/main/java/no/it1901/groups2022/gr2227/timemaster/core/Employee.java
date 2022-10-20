package no.it1901.groups2022.gr2227.timemaster.core;

import java.time.LocalDate;
import java.time.LocalTime;
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
  
  private boolean isValidWorkday(LocalDate date, LocalTime time) {
    // TODO: Check if any other workday in the close timespan conflicts with this timestamp.
    
    return true;
  }
  
  public void checkIn(LocalDate date, LocalTime time) {
    if (isAtWork()) { 
      throw new IllegalStateException(this.toString() + " is already at work!");
    }
    if (!isValidWorkday(date, time)) {
      throw new IllegalArgumentException("This timestamp comes in conflict with another workday");
    }
    this.workdays.add(new Workday(date, time));
    this.atWork = true;
    System.out.println(this.toString() + " checked in at: " + date + " " + time);
  }
  
  public void checkOut(LocalTime time) {
    if (!isAtWork()) { 
      throw new IllegalStateException(this.toString() + " is not at work!");
    }
    this.workdays.get(workdays.size() - 1).setTimeOut(time);
    this.atWork = false;
    System.out.println(this.toString() + " checked out at: " + time);
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
    return latest.getDate().toString() + " " + latest.getTimeIn().toString();
  }
  
  public Workday getDate(LocalDate date) {
    return this.workdays.stream().filter(e -> e.getDate().equals(date)).findAny().get();
  }
  
  @Override
  public String toString() {
    return this.id + "," + this.getName();
  }
  
}
