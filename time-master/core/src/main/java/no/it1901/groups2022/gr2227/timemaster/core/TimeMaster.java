package no.it1901.groups2022.gr2227.timemaster.core;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class TimeMaster {
  
  private Employee chosenEmployee;
  private ArrayList<Employee> employees;
  private ApiHandler apiHandler;
  
  public TimeMaster() {
    this.employees = new ArrayList<Employee>();
    this.apiHandler = new ApiHandler();
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
  
  public void createEmployee(String name) throws IllegalArgumentException, IOException {
    // TODO: Validate name with Regex. Maybe split into first and sur name
    if (name.equals("")) {
      throw new IllegalArgumentException("Input required, please enter name");
    }
    Employee employee = new Employee(name);
    this.employees.add(employee);
    this.apiHandler.createEmployee(employee);
    this.readEmployees(); //Updates the list of employees after a new employee has been added. 
  }
  
  public void readEmployees() throws IOException {
      this.employees = this.apiHandler.getEmployees();
  }
  
  // If the employee is clocked in the Workday will be finished with the specified timestamp.
  // Returns true if the employee is at work after successfull execution.
  public boolean clockEmployeeInOut(LocalDate dateInput, LocalTime timeInput) throws IllegalStateException, IOException {
    if (this.chosenEmployee == null) {
      throw new IllegalStateException("No employee is selected");
    }
    LocalDate date = dateInput;
    LocalTime time = timeInput;
    
    if (!this.getChosenEmployee().isAtWork()) {
      this.getChosenEmployee().checkIn(date, time);
    } else { 
      this.getChosenEmployee().checkOut(time);
    }
    this.apiHandler.updateEmployee(this.getChosenEmployee());
    return this.getChosenEmployee().isAtWork();
  }
  
  // If the employee is clocked in the Workday will be finished with the specified timestamp.
  // Returns true if the employee is at work after successfull execution.
  public boolean autoClockEmployeeInOut() throws IllegalStateException, IOException {
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
    this.apiHandler.updateEmployee(this.getChosenEmployee());
    return this.getChosenEmployee().isAtWork();
  }
}
