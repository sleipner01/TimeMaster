package no.it1901.groups2022.gr2227.timemaster.core;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

public class TimeMaster {
  
  private Employee chosenEmployee;
  private Path saveDirPath;
  private TimeMasterJsonParser jsonParser;
  private ArrayList<Employee> employees = new ArrayList<>();
  
  public TimeMaster(String fileName) {
    this.saveDirPath = Paths.get(System.getProperty("user.dir"), "../core/timeMasterSaveFiles");
    this.jsonParser = new TimeMasterJsonParser(saveDirPath, fileName);
  }
  
  public LocalDate getCurrentDate() { 
    return LocalDate.now(); 
  }

  public LocalTime getCurrentTime() { 
    return LocalTime.now(); 
  }
  
  public void setChosenEmployee(int index) throws Exception {
    this.chosenEmployee = this.employees.get(index);
  }
  
  public Employee getChosenEmployee() {
    return this.chosenEmployee;
  }
  
  public ArrayList<Employee> getEmployees() { 
    return new ArrayList<>(this.employees); 
  }
  
  public void createEmployee(String name) throws IllegalArgumentException {
    // TODO: Validate name with Regex. Maybe split into first and sur name
    if (name.equals("")) {
      throw new IllegalArgumentException("Input required, please enter name");
    }
    this.employees.add(new Employee(name));
    this.saveEmployees();
  }
  
  public void saveEmployees() {
    this.jsonParser.write(this.employees);
  }
  
  public void readEmployees() {
    this.employees = this.jsonParser.read();
  }

  public boolean employeeIsSet() {
    return !Objects.isNull(this.chosenEmployee);
  }
  
  // If the employee is clocked in the Workday will be finished with the specified timestamp.
  // Returns true if the employee is at work after successfull execution.
  public boolean clockEmployeeInOut(LocalDate dateInput, LocalTime timeInput) throws IllegalStateException {
    if (!this.employeeIsSet()) {
      throw new IllegalStateException("No employee is selected");
    }
    LocalDate date = dateInput;
    LocalTime time = timeInput;
    
    if (!this.getChosenEmployee().isAtWork()) {
      this.getChosenEmployee().checkIn(date, time);
    } else { 
      this.getChosenEmployee().checkOut(time);
    }
    this.saveEmployees();
    return this.getChosenEmployee().isAtWork();
  }
  
  // If the employee is clocked in the Workday will be finished with the specified timestamp.
  // Returns true if the employee is at work after successfull execution.
  public boolean autoClockEmployeeInOut() throws IllegalStateException {
    if (this.chosenEmployee == null) {
      throw new IllegalStateException("No employee is selected");
    }
    LocalDate date = this.getCurrentDate();
    LocalTime time = this.getCurrentTime();
    
    if (!this.getChosenEmployee().isAtWork()) { 
      this.getChosenEmployee().checkIn(date, time);
    } else { 
      this.getChosenEmployee().checkOut(time);
    }
    this.saveEmployees();
    return this.getChosenEmployee().isAtWork();
  }

  public ArrayList<Workday> getEmployeeWorkdayHistory() throws IllegalStateException {
    if(!this.employeeIsSet()) {
      throw new IllegalStateException("No employee is selected");
    }
  
    return this.getChosenEmployee().getWorkdays();
  }

}